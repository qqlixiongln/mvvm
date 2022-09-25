package com.ved.framework.net;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.ved.framework.base.BaseViewModel;
import com.ved.framework.http.ResponseThrowable;
import com.ved.framework.utils.NetUtil;
import com.ved.framework.utils.RxUtils;

import androidx.annotation.Nullable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 网络请求
 *
 * @param <T> service interface
 * @param <K> 返回的数据类型
 */
public abstract class ARequest<T, K> {

    /**
     * 可自定义code 封装处理    继承 ApiDisposableObserver
     */
    @SuppressLint("CheckResult")
    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method, @Nullable IResponse<K> iResponse) {
        if (NetUtil.getNetWorkStart(activity) == 1){
            iResponse.onError("网络异常");
            exceptionHandling(activity,"网络异常",-1);
        }else {
            try {
                method.method(RetrofitClient.getInstance().create(service))
                        .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider())) // 请求与View周期同步
                        .compose(RxUtils.schedulersTransformer())
                        .compose(RxUtils.exceptionTransformer())
                        .subscribe((Consumer<K>) iResponse::onSuccess, (Consumer<ResponseThrowable>) throwable -> {
                            iResponse.onError(throwable.message);
                            if (throwable.getCause() instanceof ResultException){
                                ResultException resultException = (ResultException) throwable.getCause();
                                exceptionHandling(activity,resultException.getErrMsg(),resultException.getErrCode());
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("CheckResult")
    public void request(@Nullable Activity activity, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method, @Nullable IResponse<K> iResponse) {
        if (NetUtil.getNetWorkStart(activity) == 1){
            iResponse.onError("网络异常");
            exceptionHandling(activity,"网络异常",-1);
        }else {
            try {
                method.method(RetrofitClient.getInstance().create(service))
                        .compose(RxUtils.schedulersTransformer())
                        .compose(RxUtils.exceptionTransformer())
                        .subscribe((Consumer<K>) iResponse::onSuccess, (Consumer<ResponseThrowable>) throwable -> {
                            iResponse.onError(throwable.message);
                            if (throwable.getCause() instanceof ResultException){
                                ResultException resultException = (ResultException) throwable.getCause();
                                exceptionHandling(activity,resultException.getErrMsg(),resultException.getErrCode());
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void exceptionHandling(@Nullable Activity activity, @Nullable String error, int code);
}
