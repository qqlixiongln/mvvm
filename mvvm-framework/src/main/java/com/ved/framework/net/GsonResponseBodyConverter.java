package com.ved.framework.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ved.framework.utils.Configure;
import com.ved.framework.utils.Utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter < T > implements Converter<ResponseBody,
        T > {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        String packageName = Utils.getContext().getPackageName();
        Class<?> entityResponse = null;
        try {
            entityResponse = Class.forName(packageName + ".mode.EntityResponse");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (entityResponse == null){
            throw new ResultException("服务器异常", -2);
        }else {
            Object result = gson.fromJson(response, entityResponse);
            Method[] allMethods = entityResponse.getDeclaredMethods();
            String methodName = "";
            String methodNameContent = "";
            String methodResultStr = "";
            for (Method method : allMethods) {
                String returnSimpleName = method.getReturnType().getSimpleName();
                switch (returnSimpleName) {
                    case "int":
                        methodName = method.getName();
                        break;
                    case "Object":
                        methodNameContent = method.getName();
                        break;
                    case "String":
                        methodResultStr = method.getName();
                        break;
                }
            }
            Method method = null;
            try {
                method = entityResponse.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            int code = 0;
            try {
                code = (int) method.invoke(result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (code == Configure.getCode()) {
                return gson.fromJson(response, type);
            } else {
                Object errResponse = gson.fromJson(response, entityResponse);
                if (!TextUtils.isEmpty(methodNameContent)) {
                    Method methodContent = null;
                    try {
                        methodContent = entityResponse.getDeclaredMethod(methodNameContent);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    String errorMsg = null;
                    try {
                        errorMsg = (String) methodContent.invoke(errResponse);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(errorMsg)){
                        throw new ResultException(errorMsg, code);
                    }else if (!TextUtils.isEmpty(methodResultStr)){
                        Method methodResult2 = null;
                        try {
                            methodResult2 = entityResponse.getDeclaredMethod(methodResultStr);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        String errorMsg2 = null;
                        try {
                            errorMsg2 = (String) methodResult2.invoke(errResponse);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        throw new ResultException(errorMsg2, code);
                    }else {
                        throw new ResultException("", code);
                    }
                }else if (!TextUtils.isEmpty(methodResultStr)){
                    Method methodResult = null;
                    try {
                        methodResult = entityResponse.getDeclaredMethod(methodResultStr);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    String errorMsg1 = null;
                    try {
                        errorMsg1 = (String) methodResult.invoke(errResponse);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    throw new ResultException(errorMsg1, code);
                }else {
                    throw new ResultException("服务器异常", code);
                }
            }
        }
    }
}