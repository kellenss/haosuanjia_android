package suanhang.jinan.com.suannihen.request;

import android.content.Context;
import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.commons.LogX;
import suanhang.jinan.com.suannihen.dialog.DialogDefaultLoading;
import suanhang.jinan.com.suannihen.utils.HMApplication;
import suanhang.jinan.com.suannihen.utils.JinChaoUtils;
import suanhang.jinan.com.suannihen.utils.ParseJson;
import suanhang.jinan.com.suannihen.utils.ShowToastUtil;

public class VolleyUtilKupai {

    /**
     * @author admin
     * @time 2015-11-19
     */
    public static void sendPostMethod(final String url,
                                      final Map<String, String> map, final BaseHandlerJsonObject v,
                                      final boolean isDialog, Context context) {
        if (map != null) {
            LogX.d(url + "请求参数：", map.toString());
        }
        if (!isDialog) {
            sendRequestMethod(url, map, v, null, Request.Method.POST, context, true);
        } else {
            DialogDefaultLoading dialogUtils = new DialogDefaultLoading(context);
            sendRequestMethod(url, map, v, dialogUtils, Request.Method.POST, context, true);
        }
    }

    public static void sendRequestMethod(final String url,
                                      final Map<String, String> map, final BaseHandlerJsonObject v,
                                      final boolean isDialog, Context context) {
        if (map != null) {
            LogX.d(url + "请求参数：", map.toString());
        }
        if (!isDialog) {
            sendRequestMethod(url, map, v, null, Request.Method.GET, context, true);
        } else {
            DialogDefaultLoading dialogUtils = new DialogDefaultLoading(context);
            sendRequestMethod(url, map, v, dialogUtils, Request.Method.GET, context, true);
        }
    }

    /**
     * 对数据的返回状态及返回码统一处理可调用此方法
     *
     * @author admin
     * @time 2015-11-19
     */
    public static void sendRequestMethod(final String url,
                                         final Map<String, String> map, final BaseHandlerJsonObject handler,
                                         final DialogDefaultLoading dialogUtils,
                                         int method, final Context context,
                                         final boolean showToast) {
        try {
            if (context==null)return;
            if (!JinChaoUtils.hasNetwork(context)) {
                boolean isMinaThread = Thread.currentThread() == Looper.getMainLooper().getThread();
                if (isMinaThread) {//判断此线程是否为主线程
                    ShowToastUtil.toastShow(context.getString(R.string.network_error));
                }
                handler.onGotError("", context.getString(R.string.network_error));
                return;
            }

            JsonObjectRequest jsonObjectRequest = new VolleyBaseJsonObject(map, method, url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            LogX.d(HMApplication.TAG, url + "---" + response.toString());
                            try {
                                if (!doJudgeStatus(response, context, handler, showToast)) {
                                    JSONObject result = ParseJson.parseGetDataObject(response);
                                    handler.onGotJson(result);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowToastUtil.toastShow(context.getString(R.string.request_json_parse_exception));
                                handler.onGotError("", context.getString(R.string.request_json_parse_exception));
                            }
                            if (dialogUtils != null) {
                                dialogUtils.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                LogX.e(url, ":error:" + error.toString());
                                if (dialogUtils != null) {
                                    dialogUtils.dismiss();
                                }

                                ShowToastUtil.toastShow(context.getString(R.string.network_error));

                                handler.onGotError("", error.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

            if (dialogUtils != null) {
                dialogUtils.show();
            }
            RequestManager.addRequest(context, jsonObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 对数据的返回状态及返回码统一处理可调用此方法
     *
     * @author admin
     * @time 2015-11-19
     */
    public static void sendRequestNoToast(final String url,
                                      final Map<String, String> map,
                                          final BaseHandlerJsonObject handler,
                                          final Context context) {
        sendRequestMethod(url, map, handler, null, Request.Method.POST, context, false);
    }

    public static void cancelAll(Object tag) {
        RequestManager.cancelAll(tag);
    }

    //{"code":"0000","msg":"success","status":true,"data":{"salingCount":0}}
    public static boolean doJudgeStatus(JSONObject jsonObject, final Context context, BaseHandlerJsonObject handler, boolean showToast) throws JSONException {

        if (jsonObject.getInt("status")==1) {
            return false;
        }

        String code = jsonObject.getString("errorCode");

        return false;
    }
    
    public static boolean specialPayErrorCode(String code){
        return code.equals("10072") || code.equals("10075");
    }
}
