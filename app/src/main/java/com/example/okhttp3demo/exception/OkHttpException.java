package com.example.okhttp3demo.exception;

/**
 * 功能描述：okHttp异常处理
 * Created by AsiaLYF on 2018/3/30.
 */

public class OkHttpException extends Exception{
    private int ecode;
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public Object getEmsg() {
        return emsg;
    }

    public void setEmsg(Object emsg) {
        this.emsg = emsg;
    }
}
