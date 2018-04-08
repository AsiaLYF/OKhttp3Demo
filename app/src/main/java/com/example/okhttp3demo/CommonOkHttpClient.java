package com.example.okhttp3demo;

import com.example.okhttp3demo.https.HttpsUtils;
import com.example.okhttp3demo.listener.DisposeDataHandle;
import com.example.okhttp3demo.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 功能描述：请求的发送，请求参数的配置，https支持
 * Created by AsiaLYF on 2018/3/30.
 */

public class CommonOkHttpClient {
    private static final int TIME_OUT = 30; //超时参数
    private static OkHttpClient mOkHttpClient;

    //为Client配置参数，使用静态语句块来配置
    static {
        OkHttpClient.Builder okHttpBuilder  = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT,TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
        //允许重定向
        .followRedirects(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).sslSocketFactory(HttpsUtils.initSSLSocketFactory(),HttpsUtils.initTrustManager());

        mOkHttpClient = okHttpBuilder.build();
    }

    public static Call sendRequest(Request request, CommonJsonCallback commonCallback){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commonCallback);
        return call;
    }

    //GET请求
    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    //POST请求
    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }
}
