package org.ligboy.teamcity.pgyer.agent.api;

import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.ligboy.teamcity.pgyer.PgyerConstants;
import org.ligboy.teamcity.pgyer.agent.bean.UploadResultBody;
import org.ligboy.teamcity.pgyer.agent.convert.FastConverterFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by ligboy on 16-4-7.
 */
public final class PgyerApi {

    private final OkHttpClient mHttpClient = new OkHttpClient.Builder().connectTimeout(15L, TimeUnit.SECONDS).readTimeout(90L, TimeUnit.SECONDS).build();

    private final Retrofit mRetrofit = new Retrofit.Builder().baseUrl(PgyerConstants.PGYER_API_BASE_URL)
            .client(mHttpClient)
            .addConverterFactory(FastConverterFactory.create())
            .build();
    private PgyerService mPgyerService;

    public PgyerApi() {
        mPgyerService = mRetrofit.create(PgyerService.class);
    }

    Response<UploadResultBody> uploadApp(String apiKey, String userKey, File file, String password, String updateInstructs,
                                         boolean isPublishToPublic) throws IOException {
            String fileSuffix = FileUtil.getExtension(file.getAbsolutePath());
            MediaType mediaType = PgyerService.MEDIA_TYPE_OTHERS;
            if (StringUtil.areEqualIgnoringCase("apk", fileSuffix)) {
                mediaType = PgyerService.MEDIA_TYPE_APK;
            } else if (StringUtil.areEqualIgnoringCase("ipa", fileSuffix)) {
                mediaType = PgyerService.MEDIA_TYPE_IPA;
            }
        Call<UploadResultBody> resultBodyCall = mPgyerService.uploadApp(
                RequestBody.create(PgyerService.MEDIA_TYPE_PLAIN_TEXT, apiKey),
                RequestBody.create(PgyerService.MEDIA_TYPE_PLAIN_TEXT, userKey),
                RequestBody.create(PgyerService.MEDIA_TYPE_OTHERS, file),
                StringUtil.isEmptyOrSpaces(password) ? null : RequestBody.create(PgyerService.MEDIA_TYPE_PLAIN_TEXT, password),
                StringUtil.isEmptyOrSpaces(updateInstructs) ? null : RequestBody.create(PgyerService.MEDIA_TYPE_PLAIN_TEXT, updateInstructs),
                RequestBody.create(PgyerService.MEDIA_TYPE_PLAIN_TEXT, isPublishToPublic ? "1" : "2"));
        return resultBodyCall.execute();
    }
}
