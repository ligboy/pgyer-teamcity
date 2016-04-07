package org.ligboy.teamcity.pgyer.agent.api;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.ligboy.teamcity.pgyer.agent.bean.UploadResultBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author ligboy
 */
public interface PgyerService {

     MediaType MEDIA_TYPE_APK = MediaType.parse("application/vnd.android.package-archive");
     MediaType MEDIA_TYPE_IPA = MediaType.parse("application/octet-stream");
     MediaType MEDIA_TYPE_OTHERS = MediaType.parse("application/octet-stream");
     MediaType MEDIA_TYPE_PLAIN_TEXT = MediaType.parse("text/plain");

    @Multipart
    @POST("apiv1/app/upload")
    Call<UploadResultBody> uploadApp(@Part("_api_key") RequestBody apiKey, @Part("uKey") RequestBody userKey,
                                     @Part("file") RequestBody fileBody, @Part("password") RequestBody password,
                                     @Part("updateDescription") RequestBody updateInstructs,
                                     @Part("isPublishToPublic") RequestBody isPublishToPublic);

}
