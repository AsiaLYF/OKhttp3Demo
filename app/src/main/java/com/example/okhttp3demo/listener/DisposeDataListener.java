package com.example.okhttp3demo.listener;

/**
 * 功能描述：自定义事件监听回调，处理请求成功和失败时的回调函数
 * Created by AsiaLYF on 2018/3/30.
 */

public interface DisposeDataListener {
    //请求成功回调事件处理
    public void onSuccess(Object responseObj);

    //请求失败回调事件处理
    public void onFailure(Object responseObj);
}
