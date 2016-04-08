package org.ligboy.teamcity.pgyer.server;

import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.parameters.AbstractBuildParametersProvider;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.VcsModificationHistory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ligboy
 */
public class PgyerBuildStartContextProcessor extends AbstractBuildParametersProvider implements BuildStartContextProcessor {

    private static final String PARAMETER_CHANGELOG = "build.changelog";

    private final SBuildServer mServer;
    private final VcsModificationHistory mVcsHistory;

    public PgyerBuildStartContextProcessor(SBuildServer server, VcsModificationHistory vcsHistory) {
        this.mServer = server;
        this.mVcsHistory = vcsHistory;
    }

    @NotNull
    @Override
    public Collection<String> getParametersAvailableOnAgent(@NotNull final SBuild build) {
        Collection<String> values = build.getBuildPromotion().getParameters().values();
        Set<String> allValues = new HashSet<String>(values.size() + 1);
        allValues.add(PARAMETER_CHANGELOG);
        return allValues;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void updateParameters(@NotNull BuildStartContext context) {
        SRunningBuild build = context.getBuild();
        List<SVcsModification> modifications = build.getContainingChanges();
        StringBuilder sb = new StringBuilder();
        SVcsModification sVcsModification;
        for (int i = 0; i < modifications.size(); i++) {
            sVcsModification = modifications.get(i);
            String change = getChange(sVcsModification);
            if (!StringUtil.isEmptyOrSpaces(change)) {
                if (i > 0) {
                    sb.append('\n');
                }
                sb.append(i + 1).append(". ").append(change).append(';');
            }
        }
        context.addSharedParameter(PARAMETER_CHANGELOG, sb.toString());
    }

    @SuppressWarnings("Duplicates")
    @Nullable
    private static String getChange(SVcsModification vcsModification) {
        if (vcsModification != null) {
            String description = vcsModification.getDescription();
            if (!StringUtil.isEmptyOrSpaces(description)) {
                String[] split = description.split("\n", 2);
                String change = split[0];
                if (!StringUtil.isEmptyOrSpaces(change)) {
                    change = StringUtil.replace(change, REMOVE_OLD_STRINGS, REMOVE_NEW_STRINGS);
                    Matcher matcher = PATTERN_REMOVE_PREFIX.matcher(change);
                    change = matcher.replaceFirst("");
                    matcher = PATTERN_REMOVE_SUFFIX.matcher(change);
                    change = matcher.replaceFirst("");
                }
                return change;
            }
        }

        return null;
    }

    private static final Pattern PATTERN_REMOVE_SUFFIX = Pattern.compile("[\\.\\s。，;/\\\\!！～~]*?$");
    private static final Pattern PATTERN_REMOVE_PREFIX = Pattern.compile("^[\\.\\s。，;/\\\\!！～~]*?");

    private static final String[] REMOVE_OLD_STRINGS = new String[] {"\n", "\r", "\t"};
    private static final String[] REMOVE_NEW_STRINGS = new String[] {"", "", ""};
}
