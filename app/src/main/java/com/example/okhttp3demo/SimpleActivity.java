package com.example.okhttp3demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.okhttp3demo.cookie.PersistentCookieStore;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 功能描述：OKhttp3简单使用
 * Created by AsiaLYF on 2018/4/3.
 */

public class SimpleActivity extends Activity {

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_layout);
        okHttpClient = new OkHttpClient();
        //设置cookie持久化
        okHttpClient.newBuilder().cookieJar(new CookiesManager());
    }

    /**
     * get请求
     *
     * @param view
     */
    public void doGet(View view) {
        Request request = new Request.Builder()
                .url("")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SimpleActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    /**
     * post请求
     *
     * @param view
     */
    public void doPost(View view) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("phone", "12345678901");
        formBody.add("pwd", "000000");

        Request request = new Request.Builder()
                .url("")
                .post(formBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SimpleActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    /**
     * post json
     *
     * @param view
     */
    public void doPostJson(View view) {
        JSONObject jsonObject = new JSONObject();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url("")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SimpleActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /**
     * post file
     *
     * @param view
     */
    public void doPostFile(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "logo.png");
        if (!file.exists()) {
            return;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        Request request = new Request.Builder()
                .url("")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 上传文件(带参数)
     *
     * @param view
     */
    public void doUpLoad(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "logo.png");
        if (!file.exists()) {
            return;
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) //记得添加type
                .addFormDataPart("phone", "12345678901")
                .addFormDataPart("pwd", "000000")
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                .addFormDataPart("mPhoto", file.getName(), RequestBody.create(MediaType.parse(""), file))
                .build();
        Request request = new Request.Builder()
                .url("")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    /**
     * 下载文件
     *
     * @param view
     */
    public void doDownLoad(View view) {
        Request request = new Request.Builder()
                .url("")
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                //如果是一张图片
                //Bitmap bitmap = BitmapFactory.decodeStream(is);
                int len = 0;
                byte[] buf = new byte[1024];
                File file = new File(Environment.getExternalStorageDirectory(), "logo.png");
                FileOutputStream fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }

                fos.flush();
                fos.close();
                is.close();

            }
        });

    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class CookiesManager implements CookieJar {

        private final PersistentCookieStore cookieStore = new PersistentCookieStore(getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

}
