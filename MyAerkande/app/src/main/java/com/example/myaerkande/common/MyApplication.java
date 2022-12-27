package com.example.myaerkande.common;

import android.app.Application;
import android.content.Context;

import com.example.myaerkande.httpMesholds.HttpMesholds;

public class MyApplication extends Application {
    public static final String baseUrl = "http://192.168.38.147:8080/";
    public static final String imageUrl = "http://192.168.38.147:9001/image/";
    public static final String webUrl = "http://192.168.119.147:8848/bootstrop/index.html";
    public static  int AD_TIME = 5;//广告页倒计时事件
    private static Context context;
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        ImageLoaderManager.getInstance();
        HttpMesholds.getInstance();
    }


    public static Context getContext() {
        return context;
    }
}

