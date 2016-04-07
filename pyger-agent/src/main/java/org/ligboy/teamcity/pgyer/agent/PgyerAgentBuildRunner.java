package org.ligboy.teamcity.pgyer.agent;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.*;
import org.jetbrains.annotations.NotNull;
import org.ligboy.teamcity.pgyer.PgyerConstants;

import java.io.File;
import java.util.Map;

/**
 * @author ligboy
 */
public class PgyerAgentBuildRunner implements AgentBuildRunner {

    @NotNull
    @Override
    public BuildProcess createBuildProcess(@NotNull AgentRunningBuild runningBuild, @NotNull BuildRunnerContext context) throws RunBuildException {
        final Map<String, String> runnerParameters = context.getRunnerParameters();
        String apiKey = runnerParameters.get(PgyerConstants.PARAM_API_KEY);
        String userKey = runnerParameters.get(PgyerConstants.PARAM_USER_KEY);
        String filePath = runnerParameters.get(PgyerConstants.PARAM_SOURCE_PATH);
        String installPassword = runnerParameters.get(PgyerConstants.PARAM_INSTALL_PASSWORD);
        String uploadInstructs = runnerParameters.get(PgyerConstants.PARAM_UPDATE_DESCRIPTION);
        boolean isPublishToPublic = Boolean.valueOf(runnerParameters.get(PgyerConstants.PARAM_PUBLISH_TO_PUBLIC));
        File file = getFile(context, filePath);
        if (file.exists()) {
            return new PgyerBuildProcessAdapter(context, apiKey, userKey, file, uploadInstructs, installPassword, isPublishToPublic);
        }
        throw new RunBuildException("\"" + filePath + "\" is not exists.");
    }

    @NotNull
    @Override
    public AgentBuildRunnerInfo getRunnerInfo() {
        return new PgyerAgentBuildRunnerInfo();
    }

    private File getFile(@NotNull BuildRunnerContext context, String path) {
        return new File(context.getWorkingDirectory(), path);
    }
}
