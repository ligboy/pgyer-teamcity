package org.ligboy.teamcity.pgyer.agent.bean;

/**
 * Created by ligboy on 16-4-6.
 */
public class UploadResultBody {
    public int code;
    public String message;
    public AppBean data;

    @Override
    public String toString() {
        return "UploadResultBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
