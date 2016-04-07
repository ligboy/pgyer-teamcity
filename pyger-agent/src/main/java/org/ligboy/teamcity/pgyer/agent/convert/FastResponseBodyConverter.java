package org.ligboy.teamcity.pgyer.agent.convert;

import com.alibaba.fastjson.JSON;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;


final class FastResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    @SuppressWarnings("unchecked")
    @Override
    public T convert(ResponseBody value) throws IOException {
        return (T) JSON.parse(value.bytes());
    }
}
