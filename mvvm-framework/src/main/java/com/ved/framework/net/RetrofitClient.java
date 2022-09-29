package com.ved.framework.net;

import com.ved.framework.http.cookie.CookieJarImpl;
import com.ved.framework.http.cookie.store.PersistentCookieStore;
import com.ved.framework.http.interceptor.CacheInterceptor;
import com.ved.framework.utils.Configure;
import com.ved.framework.utils.MyGson;
import com.ved.framework.utils.Utils;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;


class RetrofitClient {
    private static Retrofit retrofit;

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {
        this(null);
    }

    private RetrofitClient(Map<String, String> headers) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        retrofit = (new Retrofit.Builder()).client((new OkHttpClient.Builder()).cookieJar(new CookieJarImpl(new PersistentCookieStore(Utils.getContext()))).addInterceptor(new MyInterceptor(headers)).addInterceptor(new CacheInterceptor(Utils.getContext())).sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager).connectTimeout(20L, TimeUnit.SECONDS).writeTimeout(20L, TimeUnit.SECONDS).connectionPool(new ConnectionPool(8, 15L, TimeUnit.SECONDS)).proxy(Proxy.NO_PROXY).build()).addConverterFactory(GsonDConverterFactory.create(MyGson.getInstance().getGson())).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).baseUrl(Configure.getUrl()).build();
    }

    public <T> T create(Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        } else {
            return retrofit.create(service);
        }
    }

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        return null;
    }

    private static class SingletonHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();

        private SingletonHolder() {
        }
    }

}
