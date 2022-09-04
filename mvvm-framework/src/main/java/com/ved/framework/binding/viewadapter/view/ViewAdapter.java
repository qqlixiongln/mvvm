package com.ved.framework.binding.viewadapter.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding4.view.RxView;
import com.ved.framework.binding.command.BindingCommand;
import com.ved.framework.listener.OnViewGlobalLayoutListener;
import com.ved.framework.utils.DpiUtils;

import java.util.concurrent.TimeUnit;

import androidx.databinding.BindingAdapter;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;

public class ViewAdapter {
    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand<Void> clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(unit -> {
                        if (clickCommand != null) clickCommand.execute();
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(unit -> {
                        if (clickCommand != null) clickCommand.execute();
                    });
        }
    }

    /**
     * view的onLongClick事件绑定
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"onLongClickCommand"}, requireAll = false)
    public static void onLongClickCommand(View view, final BindingCommand<Void> clickCommand) {
        RxView.longClicks(view)
                .subscribe(unit -> {
                    if (clickCommand != null) clickCommand.execute();
                });
    }

    /**
     * 回调控件本身
     *
     * @param currentView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"currentView"}, requireAll = false)
    public static void replyCurrentView(View currentView, BindingCommand<View> bindingCommand) {
        if (bindingCommand != null) {
            bindingCommand.execute(currentView);
        }
    }

    /**
     * view是否需要获取焦点
     */
    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     */
    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final BindingCommand<Boolean> onFocusChangeCommand) {
        RxView.focusChanges(view).subscribe(hasFocus -> {
            if (onFocusChangeCommand != null) onFocusChangeCommand.execute(hasFocus);
        });
    }

    /**
     * view的显示隐藏
     */
    @BindingAdapter(value = {"isVisible"}, requireAll = false)
    public static void isVisible(View view, final Boolean visibility) {
        try {
            RxView.visibility(view).accept(visibility);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置view的最大高度
     */
    @BindingAdapter(value = {"maxHeight"}, requireAll = false)
    public static void maxHeight(View view, final int maxHeight) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new OnViewGlobalLayoutListener(view,DpiUtils.dip2px(view.getContext(),maxHeight)));
    }

    @BindingAdapter({"onTouchCommand"})
    public static void onTouchCommand(View view, final BindingCommand<MotionEvent> onTouchCommand) {
        RxView.touches(view).subscribe(motionEvent -> {
            if (onTouchCommand != null) onTouchCommand.execute(motionEvent);
        });
    }

    @BindingAdapter("android:layout_marginTop")
    public static void setTopMargin(View view, int topMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, DpiUtils.dip2px(view.getContext(),topMargin),
                layoutParams.rightMargin,layoutParams.bottomMargin);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_marginBottom")
    public static void setBottomMargin(View view, int bottomMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                layoutParams.rightMargin,DpiUtils.dip2px(view.getContext(),bottomMargin));
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_marginLeft")
    public static void setLeftMargin(View view, int leftMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(DpiUtils.dip2px(view.getContext(),leftMargin), layoutParams.topMargin,
                layoutParams.rightMargin,layoutParams.bottomMargin);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_marginRight")
    public static void setRightMargin(View view, int rightMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                DpiUtils.dip2px(view.getContext(),rightMargin),layoutParams.bottomMargin);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_margin")
    public static void setMargin(View view, int margin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(DpiUtils.dip2px(view.getContext(),margin), DpiUtils.dip2px(view.getContext(),margin),
                DpiUtils.dip2px(view.getContext(),margin),DpiUtils.dip2px(view.getContext(),margin));
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int paddingLeft) {
        view.setPadding(DpiUtils.dip2px(view.getContext(),paddingLeft),
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    @BindingAdapter("android:paddingRight")
    public static void setPaddingRight(View view, int paddingRight) {
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop(),
                DpiUtils.dip2px(view.getContext(),paddingRight),
                view.getPaddingBottom());
    }

    @BindingAdapter("android:paddingTop")
    public static void setPaddingTop(View view, int paddingTop) {
        view.setPadding(view.getPaddingLeft(),
                DpiUtils.dip2px(view.getContext(),paddingTop),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    @BindingAdapter("android:paddingBottom")
    public static void setPaddingBottom(View view, int paddingBottom) {
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                DpiUtils.dip2px(view.getContext(),paddingBottom));
    }

    @BindingAdapter("android:padding")
    public static void setPadding(View view, int padding) {
        view.setPadding(DpiUtils.dip2px(view.getContext(),padding),
                DpiUtils.dip2px(view.getContext(),padding),
                DpiUtils.dip2px(view.getContext(),padding),
                DpiUtils.dip2px(view.getContext(),padding));
    }

    @BindingAdapter("android:textSize")
    public static void setTextSize(TextView textView,float textSize){
        textView.setTextSize(textSize);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View textView,int layoutHeight){
        ViewGroup.LayoutParams lp = textView.getLayoutParams();
        lp.height=DpiUtils.dip2px(textView.getContext(),layoutHeight);
        textView.setLayoutParams(lp);
    }

    @BindingAdapter("android:drawableRight")
    public static void setDrawableRight(TextView textView, Drawable drawable){
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @BindingAdapter("android:drawableLeft")
    public static void setDrawableLeft(TextView textView, Drawable drawable){
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @BindingAdapter("android:drawableTop")
    public static void setDrawableTop(TextView textView, Drawable drawable){
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @BindingAdapter("android:drawableBottom")
    public static void setDrawableBottom(TextView textView, Drawable drawable){
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, null, drawable);
        }
    }
}
