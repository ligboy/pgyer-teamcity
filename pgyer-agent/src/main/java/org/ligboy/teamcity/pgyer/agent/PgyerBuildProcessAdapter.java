package org.ligboy.teamcity.pgyer.agent;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.vcs.VcsChangeInfo;
import jetbrains.buildServer.vcs.VcsRootEntry;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.ligboy.teamcity.pgyer.PgyerConstants;
import org.ligboy.teamcity.pgyer.agent.api.PgyerApi2;
import org.ligboy.teamcity.pgyer.agent.bean.AppBean;
import org.ligboy.teamcity.pgyer.agent.bean.UploadResultBody;
import org.ligboy.teamcity.pgyer.agent.convert.FastConverterFactory;
import retrofit2.Retrofit;

import java.io.File;
import java.util.List;

/**
 * @author ligboy
 */
public class PgyerBuildProcessAdapter extends SyncBuildProcessAdapter {

    private String mApiKey;
    private String mUserKey;
    private File mFile;
    private String mUpdateInstructs;
    private String mPassword;
    private boolean mIsPublishToPublic;
    private Retrofit mRetrofit;

    public PgyerBuildProcessAdapter(@NotNull BuildRunnerContext context, String apiKey, String userKey, File file,
                                    @Nullable String updateInstructs, @Nullable String password,
                                    boolean isPublishToPublic) {
        super(context.getBuild().getBuildLogger());
        mApiKey = apiKey;
        mUserKey = userKey;
        mFile = file;
        mUpdateInstructs = updateInstructs;
        mPassword = password;
        mIsPublishToPublic = isPublishToPublic;
        mRetrofit = new Retrofit.Builder().baseUrl(PgyerConstants.PGYER_API_BASE_URL)
                .addConverterFactory(FastConverterFactory.create()).build();
    }

    @Override
    protected void runProcess() throws RunBuildException {
        mLogger.message("Api Key: " + mApiKey);
        mLogger.message("Uploading " + mFile.getPath() + " to Pgyer server...");
        try {
            UploadResultBody uploadResultBody = new PgyerApi2().upload(mApiKey, mUserKey,
                mFile, mPassword, mUpdateInstructs, mIsPublishToPublic);
            if (uploadResultBody != null) {
                AppBean appBean = uploadResultBody.data;
                if (uploadResultBody.code == 0 && appBean != null) {
                    mLogger.message(appBean.appIdentifier + " uploaded successfully! ");
                    return;
                } else {
                    throw new RunBuildException(uploadResultBody.message);
                }
            }
            throw new RunBuildException("Unknown Error!");
        } catch (Exception e) {
            throw new RunBuildException(e);
        }
    }
}
