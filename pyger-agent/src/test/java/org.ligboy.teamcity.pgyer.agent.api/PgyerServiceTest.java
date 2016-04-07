package org.ligboy.teamcity.pgyer.agent.api;

import okhttp3.OkHttpClient;
import org.junit.Test;
import org.ligboy.teamcity.pgyer.PgyerConstants;
import org.ligboy.teamcity.pgyer.agent.bean.UploadResultBody;
import org.ligboy.teamcity.pgyer.agent.convert.FastConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by ligboy on 16-4-7.
 */
public class PgyerServiceTest {

    private Retrofit mRetrofit;

    @org.junit.Before
    public void setUp() throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS);
//        builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));
        mRetrofit = new Retrofit.Builder().baseUrl(PgyerConstants.PGYER_API_BASE_URL)
                .client(builder.build())
                .addConverterFactory(FastConverterFactory.create())
                .build();

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void uploadApp() throws Exception {
        PgyerService pgyerService = mRetrofit.create(PgyerService.class);
        PgyerApi pgyerApi = new PgyerApi();

        Response<UploadResultBody> response =
                pgyerApi.uploadApp("078e319344f328075a15fb513c1e1a4b", "10656b1dac2e8de4b9775fc0a856db85",
                        new File("/home/ligboy/Desktop/xjexport-zte-debug.apk"), "", "", false);
        System.out.println(response.body());
//        try {
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void uploadApp2() throws Exception {
        UploadResultBody upload = new PgyerApi2().upload("078e319344f328075a15fb513c1e1a4b", "10656b1dac2e8de4b9775fc0a856db85",
                new File("/home/ligboy/Desktop/xjexport-zte-debug.apk"), "", "", false);
        System.out.println(upload);
    }

}