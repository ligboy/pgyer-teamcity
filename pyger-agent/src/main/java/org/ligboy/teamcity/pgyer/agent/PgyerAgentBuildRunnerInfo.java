package org.ligboy.teamcity.pgyer.agent;

import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.BuildParametersMap;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.ligboy.teamcity.pgyer.PgyerConstants;

import java.util.Map;

/**
 * @author ligboy
 */
public class PgyerAgentBuildRunnerInfo implements AgentBuildRunnerInfo {

    @NotNull
    @Override
    public String getType() {
        return PgyerConstants.RUN_TYPE;
    }

    @Override
    public boolean canRun(@NotNull BuildAgentConfiguration agentConfiguration) {
//        boolean canRun = true;
//        BuildParametersMap buildParameters = agentConfiguration.getBuildParameters();
//        Map<String, String> allParameters = buildParameters.getAllParameters();
//        String appId = allParameters.get(PgyerConstants.PARAM_API_KEY);
//        String userKey = allParameters.get(PgyerConstants.PARAM_USER_KEY);
//        String sourceFilePath = allParameters.get(PgyerConstants.PARAM_SOURCE_PATH);
//        if ((StringUtil.isEmpty(appId) || appId.length() != 32)) {
//            canRun = false;
//        }
//        if ((StringUtil.isEmpty(userKey) || userKey.length() != 32)) {
//            canRun = false;
//        }
//        if (StringUtil.isEmpty(sourceFilePath)) {
//            canRun = false;
//        }
        return true;
    }
}
