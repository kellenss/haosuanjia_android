package com.jinan.haosuanjia.utils;

import com.jinan.haosuanjia.commons.LogX;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hu on 2016/2/23.
 * Description：
 */
public class UpLoadInfoUtils {

    public interface UpLoadPicCallBack {
        void onSuccess(int statusCode, String content);

        void onFailure(int statusCode, Throwable error, String content);
    }

    String uploadUrl;
    RequestParams params;
    UpLoadPicCallBack upLoadPicCallBack;
    AsyncHttpClient asyncHttpClient;

    public UpLoadInfoUtils(String uploadUrl, Map<String, String> source, UpLoadPicCallBack upLoadPicCallBack) {
        asyncHttpClient = new AsyncHttpClient();
        this.uploadUrl = uploadUrl;
        this.params = new RequestParams(source);
        this.upLoadPicCallBack = upLoadPicCallBack;
    }

    public UpLoadInfoUtils(String uploadUrl, UpLoadPicCallBack upLoadPicCallBack) {
        asyncHttpClient = new AsyncHttpClient();
        this.uploadUrl = uploadUrl;
        this.params = new RequestParams();
        this.upLoadPicCallBack = upLoadPicCallBack;
    }

    public UpLoadInfoUtils(UpLoadPicCallBack upLoadPicCallBack, File file, int type) {
        asyncHttpClient = new AsyncHttpClient();//http
//        asyncHttpClient = new AsyncHttpClient(true, 80, 443);//https

//        headers.put("_bkAccessToken_",
//                SPUtil.get(BaokuStatic.application, "bkAccessToken"));
//        headers.put("guestId", BaokuStatic.guestId);
//        headers.put("_tokenVersion_", BaokuStatic.tokenVersion);//从2.0版本区分新旧版本token
//        headers.put("APP_VERSION", BaokuStatic.version_name);
//        headers.put("APP_VERSION_CODE", BaokuStatic.version_code);
//        headers.put("APP_PLATFORM", BaokuStatic.app_platform);
//        asyncHttpClient.addHeader("_bkAccessToken_",
//                SPUtil.get(HMApplication.application, "bkAccessToken"));
//        asyncHttpClient.addHeader("guestId", HMApplication.guestId);
//        asyncHttpClient.addHeader("_tokenVersion_", HMApplication.tokenVersion);
//        asyncHttpClient.addHeader("APP_VERSION", HMApplication.version_name);
//        asyncHttpClient.addHeader("APP_VERSION_CODE", HMApplication.version_code);
//        asyncHttpClient.addHeader("APP_PLATFORM", HMApplication.app_platform);
//        asyncHttpClient.addHeader("CHANNEL_ID", BaokuStatic.getChannel());
//        if (type == 1) {
            this.uploadUrl = UrlUtils.AddUpload();
            this.params = new RequestParams();
            try {
//                params.put(ConstantString.USERID, SPUtil.get(ConstantString.USERID));
                params.put("file", file);// 上传的文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//        } else {
//            this.uploadUrl = UrlUtils.AddUpload();
//            this.params = new RequestParams();
//            try {
//                params.put("vedio", file);// 上传的文件
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
        this.upLoadPicCallBack = upLoadPicCallBack;
    }

    public UpLoadInfoUtils() {
        this.params = new RequestParams();
    }

    public void setTimeout(int timeOut) {
        asyncHttpClient.setTimeout(10000);// 设置超时时间
    }

    public void setUploadUrl(String url) {
        this.uploadUrl = url;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public void setParams(Map<String, String> source) {
        this.params = new RequestParams(source);
    }

    public void setParams(String key, File file) throws FileNotFoundException {
        this.params.put(key, file);
    }

    public void put(String key, String value) {
        this.params.put(key, value);
    }

    public void setUpLoadPicCallBack(UpLoadPicCallBack upLoadPicCallBack) {
        this.upLoadPicCallBack = upLoadPicCallBack;
    }

    public RequestHandle startUploadPic() {

        return asyncHttpClient.post(uploadUrl, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        upLoadPicCallBack.onSuccess(statusCode, new String(responseBody));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        try {
                            LogX.e("上传异常", error.getMessage() + "");
                            upLoadPicCallBack.onFailure(statusCode, error, "");
//                            upLoadPicCallBack.onFailure(statusCode, error, new String(responseBody));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void addHeader(String key, String value) {
        asyncHttpClient.addHeader(key, value);
    }

    public void put(String key, InputStream stream, String name, String contentType) {
        params.put(key, stream, name, contentType);
    }

}
