package org.ligboy.teamcity.pgyer.agent.convert;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class FastConverterFactory extends Converter.Factory {

    public static FastConverterFactory create() {
        return new FastConverterFactory();
    }

    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastResponseBodyConverter<Object>();
    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastRequestBodyConverter<Object>();
    }
}
