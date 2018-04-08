package com.example.okhttp3demo.listener;

/**
 * 功能描述：封装回调接口和要转换的实体对象
 * Created by AsiaLYF on 2018/3/30.
 */

public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        this.mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener mListener, Class<?> clazz) {
        this.mListener = mListener;
        this.mClass = clazz;
    }
}
