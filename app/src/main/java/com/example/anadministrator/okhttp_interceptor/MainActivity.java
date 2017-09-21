package com.example.anadministrator.okhttp_interceptor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 日志拦截器:请求来了,先在这里处理,可以得到 发送请求 到 得到请求 消耗了多长时间
 * 作用:可以排查网络请求速度慢的根本原因
 * 1.可能网速慢
 * 2.可能是服务端,有问题(硬件,逻辑代码)
 * 3.可能是客户端的问题 循环什么的运行卡了 导致看起来网速很慢
 *
 * 压缩拦截器:压缩你请求的内容,需要服务器的支持
 * 提示Http1.1默认压缩
 */

//OkHttp底层网络请求使用的是Socket,长连接
public class MainActivity extends AppCompatActivity {
    private String Path = "http://publicobject.com/helloworld.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    //okhttp应用程序拦截器
    public void interceptor(View v) {
        new Thread(){
            @Override
            public void run() {
                try {
                    //建立OkHttpClient对象时,传入拦截器对象

                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

                    Request request = new Request.Builder().url(Path).build();

                    Response response = okHttpClient.newCall(request).execute();

                    String string = response.body().string();
                    System.out.println(string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    //okhttp网络拦截器
    public void interceptorNetWork(View v) {
        new Thread(){
            @Override
            public void run() {
                try {
                    //缓存拦截器
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new CacheInterceptor()).build();

                    Request request = new Request.Builder().url(Path).build();

                    Response response = okHttpClient.newCall(request).execute();

                    String string = response.body().string();
                    System.out.println(string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
