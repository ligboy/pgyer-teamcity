package org.ligboy.teamcity.pgyer.server;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.ligboy.teamcity.pgyer.PgyerConstants;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author ligboy
 */
public class PgyerRunType extends RunType {
    private static final String DESCRIPTION = "Runner able to uploader apk/ipa package to pgyer.";


    private PluginDescriptor mDescriptor;

    public PgyerRunType(@NotNull final RunTypeRegistry registry,
                              @NotNull final PluginDescriptor descriptor) {
        registry.registerRunType(this);
        mDescriptor = descriptor;
    }

    @NotNull
    @Override
    public String getType() {
        return PgyerConstants.RUN_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return PgyerConstants.DISPLAY_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Nullable
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return new PropertiesProcessor() {
            @Override
            public Collection<InvalidProperty> process(Map<String, String> map) {
                Collection<InvalidProperty> result = new HashSet<InvalidProperty>();
                String appId = map.get(PgyerConstants.PARAM_API_KEY);
                String userKey = map.get(PgyerConstants.PARAM_USER_KEY);
                String sourcePath = map.get(PgyerConstants.PARAM_SOURCE_PATH);
                String installPassword = map.get(PgyerConstants.PARAM_INSTALL_PASSWORD);
                if (StringUtil.isEmptyOrSpaces(appId)) {
                    result.add(new InvalidProperty(PgyerConstants.PARAM_API_KEY, "The app Id must be specified." + appId));
                }
                if (StringUtil.isEmptyOrSpaces(userKey)) {
                    result.add(new InvalidProperty(PgyerConstants.PARAM_USER_KEY, "The user key must be specified." + userKey));
                }
                if (StringUtil.isEmptyOrSpaces(sourcePath)) {
                    result.add(new InvalidProperty(PgyerConstants.PARAM_SOURCE_PATH, "The uploaded file path must be specified." + sourcePath));
                }
                if (!StringUtil.isEmptyOrSpaces(installPassword)) {
                    result.add(new InvalidProperty(PgyerConstants.PARAM_INSTALL_PASSWORD, "The password length must not greater than 30." + installPassword));
                }
                return result;
            }
        };
    }

    @Nullable
    @Override
    public String getEditRunnerParamsJspFilePath() {
        return mDescriptor.getPluginResourcesPath() + "editPgyerParams.jsp";
    }

    @Nullable
    @Override
    public String getViewRunnerParamsJspFilePath() {
        return mDescriptor.getPluginResourcesPath() + "viewPgyerParams.jsp";
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        return new HashMap<String, String>();
    }

    @NotNull
    @Override
    public String describeParameters(@NotNull Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("Target App ID: ").append(parameters.get(PgyerConstants.PARAM_API_KEY));
        String userKey = parameters.get(PgyerConstants.PARAM_USER_KEY);
        sb.append('\n').append("User Key: ");
        if (StringUtil.isNotEmpty(userKey) && userKey.length() == 32) {
            sb.append(userKey.substring(0, 8)).append("****************").append(userKey.substring(24));
        } else {
            sb.append("********************************");
        }
        sb.append('\n').append("Source File Path: ").append(parameters.get(PgyerConstants.PARAM_SOURCE_PATH));
        String installPassword = parameters.get(PgyerConstants.PARAM_INSTALL_PASSWORD);
        if (StringUtil.isNotEmpty(installPassword)) {
            sb.append('\n').append("Install Password: ").append(installPassword);
        }
        String publishToPublic = parameters.get(PgyerConstants.PARAM_PUBLISH_TO_PUBLIC);
        if (StringUtil.isNotEmpty(publishToPublic)) {
            sb.append('\n').append("Publish To Public: ").append(publishToPublic);
        }
        String updateDescription = parameters.get(PgyerConstants.PARAM_UPDATE_DESCRIPTION);
        if (StringUtil.isNotEmpty(updateDescription)) {
            sb.append('\n').append("Update Instructs: ").append(updateDescription);
        }
        return sb.toString();
    }
}
