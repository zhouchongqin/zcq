package com.example.myaerkande.httpMesholds;

import com.example.myaerkande.common.MyApplication;
import com.example.myaerkande.httpResult.HttpResult;
import com.example.myaerkande.service.CateService;
import com.example.myaerkande.service.GoodsService;
import com.example.myaerkande.service.UserService;
import com.example.myaerkande.unity.RequestInterceptor;
import com.example.myaerkande.unity.ResponseInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HttpMesholds {

    private static HttpMesholds mInstance;//用于判断HttpMesholds是否创建
    public static UserService userService;//声明全局变量因为其他地方需要用
    public static CateService cateService;
    public static GoodsService goodsService;
    //第一步，创建构造方法，用于创建Retrofit
    public HttpMesholds() {
        if(mInstance==null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new ResponseInterceptor(MyApplication.getContext()))
                    .addInterceptor(new RequestInterceptor(MyApplication.getContext()))
                    .build();//声明okhttp
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApplication.baseUrl)//访问链接
                    .addConverterFactory(GsonConverterFactory.create())//使用GSON解析数据
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加适配器用于数据转换
                    .client(client).build();
            userService = retrofit.create(UserService.class);//
            cateService = retrofit.create(CateService.class);
            goodsService = retrofit.create(GoodsService.class);
        }
    }
    //编写创建HttpMesholds的实例
    public static HttpMesholds getInstance(){
        if(mInstance==null){
            mInstance = new HttpMesholds();
        }
        return mInstance;
    }

    //第4步，编写func1,但是我们不知道返回的具体类型是什么，所以编写了httpResult接收返回数据
    //数据解析我们不知道返回类型是什么，所以用T
    //HttpResult<T>,T 数据返回成HttpResult，解析成T
    public static class ResultFunc1<T> implements Func1<HttpResult<T>,T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
            return tHttpResult.getData();
        }
    }
    //第5步，编写公共部分
    public static<T> void toSub(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}