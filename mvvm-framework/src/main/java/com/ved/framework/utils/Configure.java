package com.ved.framework.utils;

import android.text.TextUtils;

public class Configure {
    private static String url;
    private static String imageHeard;
    private static int code;
    private static int authNumber;
    private static int form;
    private static int to;
    private static long timeout;

    private Configure() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setUrl(String url,String imageHeard,int code,long timeout){
        Configure.url = url;
        Configure.imageHeard = imageHeard;
        Configure.code = code;
        Configure.timeout = timeout;
    }

    public static void setAuth(int auth,int form,int to){
        Configure.authNumber = auth;
        Configure.form = form;
        Configure.to = to;
    }

    public static int getAuthNumber() {
        return authNumber;
    }

    public static int getForm() {
        return form;
    }

    public static int getTo() {
        return to;
    }

    public static String getUrl() {
        if (!TextUtils.isEmpty(url)) {
            return url;
        }
        throw new NullPointerException("should be set in net url");
    }

    public static String getImageHeardUrl() {
        if (!TextUtils.isEmpty(imageHeard)) {
            return imageHeard;
        }
        throw new NullPointerException("should be set in image url");
    }

    public static int getCode() {
        return code;
    }

    public static long getTimeOut() {
        return timeout;
    }
}
