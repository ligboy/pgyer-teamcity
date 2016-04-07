package org.ligboy.teamcity.pgyer.agent.api;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.ligboy.teamcity.pgyer.agent.bean.UploadResultBody;

import java.io.File;
import java.util.concurrent.TimeUnit;


public final class PgyerApi2 {
    private static final OkHttpClient mHttpClient = new OkHttpClient.Builder()
                                                        .readTimeout(60, TimeUnit.SECONDS)
                                                        .connectTimeout(10, TimeUnit.SECONDS)
                                                        .build();

    public PgyerApi2() {

    }

    public UploadResultBody upload(String apiKey, String userKey, File file, String password, String updateDescription, boolean isPublishToPublic) throws Exception {
        Request request = makeRequest(apiKey, userKey, file, password, updateDescription, isPublishToPublic);
        Response response = mHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {
            return JSON.parseObject(response.body().bytes(), UploadResultBody.class);
        }
        return null;
    }


    private static Request makeRequest(String apiKey, String userKey, File file, String password, String updateDescription, boolean isPublishToPublic) throws Exception {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("_api_key", apiKey)
                .addFormDataPart("uKey", userKey)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/vnd.android.package-archive"), file));

        if (password != null) {
            multipartBuilder.addFormDataPart("password", password);
        }
        if (updateDescription != null) {
            multipartBuilder.addFormDataPart("updateDescription", updateDescription);
        }
        multipartBuilder.addFormDataPart("isPublishToPublic", isPublishToPublic ? "1" : "2");

        Request request = new Request.Builder().url("http://www.pgyer.com/apiv1/app/upload").post(multipartBuilder.build()).build();
        return request;
    }
}
