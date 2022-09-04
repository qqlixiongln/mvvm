package com.ved.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.ved.framework.BuildConfig;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;


public final class SPUtils {

    private final String SECRET_KEY = BuildConfig.ENCRYPT_KEY;

    private static Map<String, SPUtils> sSPMap = new HashMap<>();
    private SharedPreferences sp;


    public static SPUtils getInstance() {
        return getInstance("");
    }


    public static SPUtils getInstance(@Nullable String spName) {
        if (isSpace(spName)) spName = "spUtils";
        SPUtils sp = sSPMap.get(spName);
        if (sp == null) {
            sp = new SPUtils(spName);
            sSPMap.put(spName, sp);
        }
        return sp;
    }

    private SPUtils(@Nullable final String spName) {
        sp = Utils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    private static boolean isSpace(@Nullable final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void put(@Nullable String key, @Nullable Object object) {
        saveValue(key, object);
    }

    public Object get(@Nullable String key, @Nullable Object defaultObject) {
        return getValue(key, defaultObject);
    }


    public boolean saveValue(@Nullable String key, @Nullable Object value) {
        if (null == sp) {
            return false;
        }
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            return editor.putString(key, (String) encryptDES((String) value)).commit();
        } else if (value instanceof Boolean) {
            return editor.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Float) {
            return editor.putFloat(key, (Float) value).commit();
        } else if (value instanceof Integer) {
            return editor.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            return editor.putLong(key, (Long) value).commit();
        } else if (value instanceof Set) {
            throw new IllegalArgumentException("Value can not be Set object!");
        }
        return false;
    }


    public Object getValue(@Nullable String key, @Nullable Object defaultValue) {
        if (null == sp) {
            return null;
        }

        if (defaultValue instanceof String) {
            return decryptDES(sp.getString(key, (String) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Set) {
            throw new IllegalArgumentException("Can not to get Set value!");
        }
        return null;
    }


    public boolean contains(@Nullable String key) {
        return null != sp && sp.contains(key);
    }


    public boolean remove(@Nullable String key) {
        if (null == sp) {
            return false;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }


    public boolean clear() {
        if (null == sp) {
            return false;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }


    public Map<String, ?> getAll() {
        if (null == sp) {
            return null;
        }
        return sp.getAll();
    }
    public boolean saveEntity(@Nullable final Object obj) {
        if (null != obj) {
            final String innerKey = getKey(obj.getClass());
            if (null != innerKey) {
                String value = objTostr(obj);
                if (TextUtils.isEmpty(value)) {
                    return false;
                }
                return saveValue(innerKey, encryptDES(value));
            }
        }
        return false;
    }


    public <T> boolean saveList(@Nullable final Class<? extends T> clazz, @Nullable List<? extends T> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            return false;
        }
        final String innerKey = getKey(clazz);
        if (null != innerKey) {
            String value = objTostr(datalist);
            if (TextUtils.isEmpty(value)) {
                return false;
            }
            return saveValue(innerKey, encryptDES(value));
        }

        return false;
    }


    private String encryptDES(@Nullable String value) {
        if (!TextUtils.isEmpty(SECRET_KEY)) {
            try {
                return DES.encryptDES(value, SECRET_KEY);
            } catch (Exception e) {
                e.printStackTrace();
                String base64 = null;
                try {
                    base64 = Base64.encodeToString(value.getBytes(), Base64.DEFAULT);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return value;
                }
                return base64;
            }
        } else {
            String b64 = null;
            try {
                b64 = Base64.encodeToString(value.getBytes(), Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
                return value;
            }
            return b64;
        }
    }


    public <T> T getEntity(@Nullable final Class<? extends T> clazz, @Nullable final T defaultValue) {
        final String innerKey = getKey(clazz);
        if (!TextUtils.isEmpty(innerKey)) {
            T ret = strToobj(decryptDES((String) getValue(innerKey, "")), clazz);
            if (null != ret) {
                return ret;
            }
        }
        return defaultValue;
    }


    public <T> List<T> getList(@Nullable final Class<? extends T> clazz) {
        List<T> datalist = new ArrayList<>();
        final String innerKey = getKey(clazz);
        if (!TextUtils.isEmpty(innerKey)) {
            Gson gson = new Gson();
            String json = decryptDES((String) getValue(innerKey, ""));
            datalist = gson.fromJson(json, new ParameterizedTypeImpl(clazz));
            if (null != datalist) {
                return datalist;
            }
        }

        return datalist;

    }

    private  class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(@Nullable Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }


    private String decryptDES(@Nullable String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        if (!TextUtils.isEmpty(SECRET_KEY)) {
            try {
                return DES.decryptDES(value, SECRET_KEY);
            } catch (Exception e) {
                e.printStackTrace();
                String base64 = null;
                try {
                    base64 = new String(Base64.decode(value, Base64.DEFAULT));
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return value;
                }
                return base64;
            }
        } else {
            String b64 = null;
            try {
                b64 = new String(Base64.decode(value, Base64.DEFAULT));
            } catch (Exception e) {
                e.printStackTrace();
                return value;
            }
            return b64;
        }
    }


    private String getKey(@Nullable final Class<?> clazz) {
        if (null != clazz) {
            return clazz.getName();
        }
        return null;
    }


    public String objTostr(@Nullable final Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public <T> T strToobj(@Nullable final String string, @Nullable final Class<? extends T> clazz) {
        try {
            return new Gson().fromJson(string, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
