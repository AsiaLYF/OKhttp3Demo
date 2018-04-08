package com.example.okhttp3demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.okhttp3demo.listener.DisposeDataHandle;
import com.example.okhttp3demo.listener.DisposeDataListener;
import com.example.okhttp3demo.request.CommonRequest;
import com.example.okhttp3demo.request.RequestParams;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGet,btnPost;
    private TextView tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tv_info);
        btnGet = findViewById(R.id.btn_get);
        btnPost = findViewById(R.id.btn_post);

        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get:
                getRequest();
                break;
            case R.id.btn_post:
                postRequest();
                break;
        }

    }

    private void getRequest(){
        CommonOkHttpClient.get(CommonRequest.createGetRequest("https://publicobject.com/helloworld.txt"
        ,null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                tvInfo.setText(responseObj.toString());
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        }));
    }

    private void postRequest(){
        RequestParams params = new RequestParams();
        params.put("mb","19811230100");
        params.put("pwd","999999q");
        CommonOkHttpClient.post(CommonRequest.createPostRequest("",params),
                new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                tvInfo.setText(responseObj.toString());

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        }));
    }


}
