package suanhang.jinan.com.suannihen.utils;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.SsX509TrustManager;
import com.android.volley.toolbox.StringRequest;
import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.commons.LogX;
import suanhang.jinan.com.suannihen.dialog.DialogDefaultLoading;
import suanhang.jinan.com.suannihen.interfac.VolleyCallBack;
import suanhang.jinan.com.suannihen.request.RequestManager;
import suanhang.jinan.com.suannihen.request.VolleyBaseString;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;


public class VolleyUtils {
    @NonNull
    public static String parseZipResponse(NetworkResponse response) {
        String parsed;
        try {
            String encoding = response.headers.get("Content-Encoding");
            boolean gzipped = encoding!=null && encoding.toLowerCase().contains("gzip");
            if(gzipped)
                parsed = new String(VolleyUtils.gzipDecompress(response.data), HttpHeaderParser.parseCharset(response.headers));
            else
                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            parsed = new String(response.data);
        } catch (IOException e) {
            e.printStackTrace();
            parsed = new String(response.data);
        }
        return parsed;
    }

    /**
     * @author admin
     * @time 2015-11-19
     */
    public static void sendPostMethod(final String url,
                                      final Map<String, String> map, final VolleyCallBack v,
                                      final boolean isDialog, Context context) {
        if (map != null) {
            LogX.d(HMApplication.TAG, url + "请求参数："+map.toString());
        }
        if (!isDialog) {
            sendPostMethod(url, map, v, context);
        } else {
            DialogDefaultLoading dialogUtils = new DialogDefaultLoading(context);
            sendPostMethod(url, map, v, dialogUtils, context);
        }
    }

    /**
     * @author admin
     * @time 2015-11-19
     */
    public static void sendPostMethod(final String url,
                                      final Map<String, String> map, final VolleyCallBack v,
                                      Context context) {
        sendPostMethod(url, map, v, null, context);
    }

    public static final int VOLLEYCALLBACK_STATUS_NOT_TRUE = 0;
    public static final int VOLLEYCALLBACK_CODE_NOT_ZERO = 1;
    public static final int VOLLEYCALLBACK_HTTP_ASK_FAIL = 2;
    public static final int VOLLEYCALLBACK_PARSE_JSON_ERROR = 3;

    /**
     * 对数据的返回状态及返回码统一处理可调用此方法
     *
     * @author admin
     * @time 2015-11-19
     */
    public static void sendPostMethod(final String url,
                                      final Map<String, String> map, final VolleyCallBack v,
                                      final DialogDefaultLoading dialogUtils, final Context context) {
        if(map!=null){
            LogX.d(HMApplication.TAG, url + "--请求参数:"+map.toString());
        }
        if (!JinChaoUtils.hasNetwork(context)) {
            hasNoNetWork(context.getString(R.string.network_error), url, v);
            return;
        }
        try {
            SsX509TrustManager.allowAllSSL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final StringRequest stringRequest = new VolleyBaseString(map, Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogX.d(HMApplication.TAG, url + "---" + response);
                        if (dialogUtils != null)
                            dialogUtils.dismiss();
                        try {
                            if (doJudgeStatus(response, context, v, url)) return;

                            v.success(response, url);
                        } catch (Exception e) {
                            e.printStackTrace();
                            parseDataException(context.getString(R.string.request_json_parse_exception), v, url);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialogUtils != null) {
                    dialogUtils.dismiss();
                }
                doErrorResponse(context, v, url);
            }
        });

        if (dialogUtils != null) {
            dialogUtils.show();
        }
        RequestManager.addRequest(context, stringRequest);
    }

    private static void doErrorResponse(Context context, VolleyCallBack v, String url) {
        try {
            ShowToastUtil.toastShow(context.getString(R.string.network_error));

            v.failure(context.getString(R.string.network_error),
                    url, VOLLEYCALLBACK_HTTP_ASK_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据解析异常的统一处理
     */
    private static void parseDataException(String text, VolleyCallBack v, String url) {
        ShowToastUtil.toastShow(text);
        v.failure(text, url, VOLLEYCALLBACK_PARSE_JSON_ERROR);
    }

    /**
     * 没有网络的统一处理
     */
    private static void hasNoNetWork(String error, String url, VolleyCallBack v) {
        boolean isMinaThread = Thread.currentThread() == Looper.getMainLooper().getThread();
        if (isMinaThread) {//判断此线程是否为主线程
            ShowToastUtil.toastShow(error);
        }
        v.failure( error,
                url, VOLLEYCALLBACK_HTTP_ASK_FAIL);
    }

    public static void cancelAll(Object tag) {
        RequestManager.cancelAll(tag);
    }

    public static Map<String, String> initVolleyHeader(Map<String, String> params) {
        Map<String, String> headers = new HashMap<String, String>();
        long timestamp = System.currentTimeMillis() / 1000; // unix时间戳 System.currentTime()/1000 获取
        headers.put("x-matrix-uid", "dd");

        LogX.sysO("headers",headers.toString());
        return headers;
    }


    public static byte[] gzipDecompress(byte[] data) throws IOException {
        GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(data));
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int iRead = 0;
        while(0 < (iRead = gzip.read(buffer))){
            o.write(buffer, 0, iRead);
        }
        gzip.close();

        return o.toByteArray();
    }

    /**
     * 需要对返回数据的状态或返回码进行特殊判断处理的地方可调用此方法
     * 获取非统一格式的网络数据可调用此方法
     * 微信获取token接口
     */
    public static void getWeixinNetData(final String url,
                                        final Map<String, String> map, final VolleyCallBack v,
                                        final Context context) {

        getWeixinNetData(url, map, v, true, context);
    }/**
     * 需要对返回数据的状态或返回码进行特殊判断处理的地方可调用此方法
     * 获取非统一格式的网络数据可调用此方法
     * 微信获取token接口
     */
    public static void getWeixinNetData(final String url,
                                        final Map<String, String> map, final VolleyCallBack v,
                                        final boolean showDialog, final Context context) {

        if (!JinChaoUtils.hasNetwork(context)) {
            hasNoNetWork(context.getString(R.string.network_error), url, v);
            return;
        }

        final DialogDefaultLoading dialogUtils = new DialogDefaultLoading(context);

        final StringRequest stringRequest = new VolleyBaseString(map, Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogX.d(HMApplication.TAG, url + "---" + response);
                        try {
                            if(showDialog)
                                dialogUtils.dismiss();
                            v.success(response, url);
                        } catch (Exception e) {
                            e.printStackTrace();
                            parseDataException(context.getString(R.string.request_json_parse_exception), v, url);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if(showDialog)
                        dialogUtils.dismiss();
                    doErrorResponse(context, v, url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if(showDialog)
            dialogUtils.show();
        RequestManager.addRequest(context, stringRequest);
    }

    /**
     * 对数据的返回状态及返回码统一处理可调用此方法
     *
     * @author admin
     * @time 2015-11-19
     */
    public static void sendGetMethod(final String url, final VolleyCallBack v, final Context context) {
        if (!JinChaoUtils.hasNetwork(context)) {
            hasNoNetWork(context.getString(R.string.network_error), url, v);
            return;
        }

        final StringRequest stringRequest = new VolleyBaseString(new HashMap<String, String>(), Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogX.d(HMApplication.TAG, url + "---" + response);

                        try {
                            if (doJudgeStatus(response, context, v, url)) return;

                            v.success(response, url);
                        } catch (Exception e) {
                            e.printStackTrace();
                            parseDataException(context.getString(R.string.request_json_parse_exception), v, url);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                doErrorResponse(context, v, url);
            }
        });

        RequestManager.addRequest(context, stringRequest);
    }

    public static boolean doJudgeStatus(String response, final Context context, VolleyCallBack v, String url) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);

        if (jsonObject.getBoolean("status")) {
            return false;
        }

        String status = jsonObject.getString("code");

        return false;
    }
}
