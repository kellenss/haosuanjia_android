package com.jinan.haosuanjia.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jinan.haosuanjia.bean.InformationConsultationBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darcy  on 2016/03/16
 * Function: json解析 工具类
 * Modify by darcy on 2016/03/16
 * Modify Reason:
 */
public class ParseJson {
    public static final String RESULT = "result";
    public static final String STATUS = "status";
//    public static final String DATA = "data";
    public static final String DATA = "data1";
    public static final String API_STATUS = "apistatus";
    public static final String ERROR_CODE = "error_code";
    public static final String ERROR_ZH_CN = "error_zh_CN";
    public static final String ERROR = "error";

    private static Gson gson = new Gson();

    /**
     * 宝库项目网络访问获取data字段的值
     */
    public static JSONObject parseGetDataObject(JSONObject response) {
        JSONObject result = parseGetJSONObject(response, DATA);
        return result == null ? response : result;
    }

    public static JSONObject parseGetResultObject(JSONObject response, Class<InformationConsultationBean> informationConsultationBeanClass) {
        JSONObject result = parseGetJSONObject(response, RESULT);
        return result == null ? response : result;
    }

    public static JSONObject parseGetJSONObject(JSONObject response, String fieldName) {
        JSONObject result = null;
        if (response != null && response.has(fieldName)) {
            result = response.optJSONObject(fieldName);
        }
        return result;
    }

//    public static String parseGetErrorCode(JSONObject response) {
//        String errorMsg = parseGetString(response, ERROR_ZH_CN);
//        if (TextUtils.isEmpty(errorMsg)) {
//            errorMsg = parseGetString(response, ERROR);
//        }
//        return errorMsg = TextUtils.isEmpty(errorMsg) ? BaseHandler.NET_ERROR : errorMsg;
//    }

//    public static String parseGetErrorString(JSONObject response) {
//        String errorMsg = parseGetString(response, ERROR_ZH_CN);
//        if (TextUtils.isEmpty(errorMsg)) {
//            errorMsg = parseGetString(response, ERROR);
//        }
//        return errorMsg = TextUtils.isEmpty(errorMsg) ? BaseHandler.NET_ERROR : errorMsg;
//    }

    public static String parseGetString(JSONObject response, String fieldName) {
        String result = "";
        if (response != null && response.has(fieldName)) {
            result = response.optString(fieldName);
        }
        return result;
    }

    public static int parseGetInt(JSONObject response, String fieldName) {
        int result = -100;
        if (response != null && response.has(fieldName)) {
            result = response.optInt(fieldName, -1);
        }
        return result;
    }

    public static int parseGetStatusInt(JSONObject response) {
        int result = parseGetInt(response, API_STATUS);
        if (result == -100 || result == -1) {
            result = parseGetInt(response, STATUS);
        }
        return result;
    }

    public static long parseGetLong(JSONObject response, String fieldName) {
        long result = -100L;
        if (response != null && response.has(fieldName)) {
            result = response.optLong(fieldName, -1L);
        }
        return result;
    }

    public static boolean parseGetBoolean(JSONObject response, String fieldName) {
        boolean result = false;
        if (response != null && response.has(fieldName)) {
            result = response.optBoolean(fieldName, false);
        }
        return result;
    }

    public static <T> T parseConvertString2Object(String jsonStr, Class<T> classOfT) {
        if (!TextUtils.isEmpty(jsonStr)) {
            return gson.fromJson(jsonStr, classOfT);
        }
        return null;
    }

    public static <T> T parseConvertResultObject(JSONObject response, Class<T> classOfT) {
        if (response != null) {
            String resultStr = response.toString();
            return gson.fromJson(resultStr, classOfT);
        }
        return null;
    }

    public static <T> T parseGetResultFieldObject(JSONObject response, String fieldName, Class<T> classOfT) {
        if (response != null) {
            try {
                String resultStr = response.getString(fieldName);
                return gson.fromJson(resultStr, classOfT);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> List<T> parseGetResultCollection(JSONObject response, String fieldName, Class<T> classOfT) {
        if (response != null) {
            try {
                String resultStr = response.getString(fieldName);
                List<T> list = getListFromJsonArray(classOfT, resultStr);
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @NonNull
    public static <T> List<T> getListFromJsonArray(Class<T> classOfT, String resultStr) {
        List<T> list = new ArrayList<T>();
        try {
            if (TextUtils.isEmpty(resultStr)) return list;
            JsonArray array = new JsonParser().parse(resultStr).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, classOfT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @NonNull
    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }

    public static Gson getGson(){
        return gson;
    }
}
