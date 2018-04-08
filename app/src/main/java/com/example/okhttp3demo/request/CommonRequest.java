package com.example.okhttp3demo.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 功能描述：接收请求参数，生成Request对象
 * Created by AsiaLYF on 2018/3/30.
 */

public class CommonRequest {

    /**
     * 创建Get请求的Request
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");

        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        Request request = new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get().build();
        return request;

    }

    /**
     * 创建Post请求的Request
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder mFromBodyBuilder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
            mFromBodyBuilder.add(entry.getKey(), entry.getValue());
        }
        FormBody mFormBody = mFromBodyBuilder.build();
        Request request = new Request.Builder().url(url).post(mFormBody).build();
        return request;

    }
}
