package com.ved.framework.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonPraise {

    private static Gson gson = new Gson();

    /**
     * obj转换为json字符串
     *
     * @param obj
     * @return
     */
    public static String objToJson(Object obj) {
        String tmp = null;
        try {
            tmp = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /***
     * json字符串转换为obj
     *
     * @param json
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object jsonToObj(String json, final Class clazz) {
        Object o = null;
        try {
            o = gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * 函数名称: parseData 函数描述: 将json字符串转换为map
     *
     * @param data
     * @return
     */
    public static Map<String, Object> jsonToMapObj(String data) {
        return gson.fromJson(data, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    /**
     * 函数名称: parseData 函数描述: 将json字符串转换为map
     *
     * @param data
     * @return
     */
    public static Map<String, String> jsonToMap(String data) {
        return gson.fromJson(data, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    public static <T> T parseJSON(String json, Type type) {
        Gson gson = new Gson();
        T info = gson.fromJson(json, type);
        return info;
    }

    /**
     * 获取参数值
     *
     * @param jsonStr
     * @return
     * @throws JSONException
     */
    public static String optCode(String jsonStr, String key) {
        String code = null;
        try {
            code = new JSONObject(jsonStr).opt(key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 将001的data对象转换为Map<String,Object>
     *
     * @param data
     * @return
     */
    public static Map<String, Object> jsonToObjMap(String data) {
        return gson.fromJson(data, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    /**
     * 获取对象值
     *
     * @param jsonStr
     * @return
     * @throws JSONException
     */
    public static Object opt001ObjData(String jsonStr,
                                       @SuppressWarnings("rawtypes") Class clazz, String keys) throws Exception {
        JSONObject dataJson = new JSONObject(jsonStr);
        JSONObject obj = (JSONObject) dataJson.opt(keys);
        return optObj(obj.toString(), clazz);
    }

    /**
     * 获取List值
     *
     * @return
     * @throws JSONException
     */
    public static Object opt001ListData(JSONArray obj, Type type)
            throws Exception {
        if (obj != null) {
            return optObj(obj.toString(), type);
        } else {
            return null;
        }
    }

    /**
     * 获取List值
     *
     * @return
     * @throws JSONException
     */
    public static Object opt001ListData(String json, Type type) {
        if (json != null) {
            return optObj(json, type);
        } else {
            return null;
        }
    }

    /**
     * 获取List值
     *
     * @param jsonStr
     * @return
     * @throws JSONException
     */
    public static Object opt001ListData(String jsonStr, Type type, String keys)
            throws JSONException {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        JSONObject dataJson = new JSONObject(jsonStr);

        JSONArray goods = dataJson.optJSONArray(keys);
        if (goods != null) {
            return optObj(goods.toString(), type);
        } else {
            return null;
        }
    }

    public static Object optObj(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }

    @SuppressWarnings("unchecked")
    public static Object optObj(String jsonStr,
                                @SuppressWarnings("rawtypes") Class clazz) {
        return gson.fromJson(jsonStr, clazz);
    }

    public static String mapToJson(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        return gson.toJson(map);
    }

}
