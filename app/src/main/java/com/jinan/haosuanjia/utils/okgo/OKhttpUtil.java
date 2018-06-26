package com.jinan.haosuanjia.utils.okgo;

import com.jinan.haosuanjia.commons.LogX;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.LiteHttp;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by hu on 2017/6/20.
 */

public class OKhttpUtil {

    public static void initOkGo() {
        if(OkGo.getInstance().getContext() != null) return;
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("_bkAccessToken_",
//                SPUtil.get(BaokuStatic.application, "bkAccessToken"));
//        headers.put("guestId", BaokuStatic.guestId);
//        headers.put("_tokenVersion_", BaokuStatic.tokenVersion);
//        headers.put("APP_VERSION", BaokuStatic.version_name);
//        headers.put("APP_VERSION_CODE", BaokuStatic.version_code);
//        headers.put("APP_PLATFORM", BaokuStatic.app_platform);
//        headers.put("CHANNEL_ID", BaokuStatic.getChannel());
//        headers.put("kp-app-id", BaokuStatic.kp_app_id);

        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.NONE);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(HMApplication.application)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(HMApplication.application)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }

//    //上传图片
//    public static void upLoadFile(int type, final File file, final JsonCallback<LzyResponse<UploadBitmapModel>> callBack){
//        Luban.get(HMApplication.application).load(file).setCompressListener(new OnCompressListener() {
//            @Override
//            public void onStart() { }
//
//            @Override
//            public void onSuccess(File file) {
//                upLoadFile(file, callBack);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                try {
//                    if(Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
//                        upLoadFile(file, callBack);
//                    } else {
//                        upLoadFile(new File(BitmapUtil.compressImage(file.toString())), callBack);
//                    }
//                }catch (Exception e1){
//                    e1.printStackTrace();
//                }
//
//            }
//        }).launch();
//    }
//    //上传图片
//    public static void upLoadFile(File file, final JsonCallback<LzyResponse<UploadBitmapModel>> callBack){
//        upLoadFile("pic", URLUtil.getkUploadPicUrl(), file, new JsonCallback<LzyResponse<UploadBitmapModel>>() {
//            @Override
//            public void onSuccess(Response<LzyResponse<UploadBitmapModel>> response) {
//                if(response.body() == null){
//                    onError(response);
//                    return;
//                }
//
//                LzyResponse result = response.body();
//                if(!result.status)
//                    ShowToastUtil.Short(ConstantString.TEXT_EMPTY + result.msg);
//                else if(result.code != 0)
//                    ShowToastUtil.Short(ConstantString.TEXT_EMPTY + result.msg);
//                if(callBack != null)
//                    callBack.onSuccess(response);
//            }
//
//            @Override
//            public void onError(Response<LzyResponse<UploadBitmapModel>> response) {
//                super.onError(response);
//                if(callBack != null)
//                    callBack.onError(response);
//            }
//        });
//    }
//    //上传语音
//    public static void upLoadVideo(File file, final JsonCallback<LzyResponse<UploadVideoModel>> callBack){
//        upLoadFile("vedio", URLUtil.getkUploadVedioUrl(), file, new JsonCallback<LzyResponse<UploadVideoModel>>() {
//            @Override
//            public void onSuccess(Response<LzyResponse<UploadVideoModel>> response) {
//                if(response.body() == null)return;
//
//                LzyResponse result = response.body();
//                if(!result.status)
//                    ShowToastUtil.Short(ConstantString.TEXT_EMPTY + result.msg);
//                else if(result.code != 0)
//                    ShowToastUtil.Short(ConstantString.TEXT_EMPTY + result.msg);
//                if(callBack != null)
//                    callBack.onSuccess(response);
//            }
//
//            @Override
//            public void onError(Response<LzyResponse<UploadVideoModel>> response) {
//                super.onError(response);
//                if(callBack != null)
//                    callBack.onError(response);
//            }
//        });
//    }
//    //上传图片
//    public static void upLoadFile(String key, String uploadUrl, File file, final JsonCallback callBack){
//        initOkGo();
//
//        OkGo.<LzyResponse<UploadBitmapModel>>post(uploadUrl)//
//                .tag(ConstantString.TAG_UPLOAD_PIC)//
//                .params(ConstantString.USERID, SPUtil.get(ConstantString.USERID))
//                .params(key, file)   //这种方式为一个key，对应一个文件
////                .params("file2",new File("文件路径"))
////                .params("file3",new File("文件路径"))
////                .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
//                .execute(callBack);
//    }
//    //上传图片
//    public static void upLoadJson(String key, String uploadUrl, String json, JsonCallback<LzyResponse<String>> callBack){
//        initOkGo();
//
//        OkGo.<LzyResponse<String>>post(uploadUrl)//
//                .tag(ConstantString.TAG_UPLOAD_PIC)//
//                .params(ConstantString.USERID, SPUtil.get(ConstantString.USERID))
//                .params("contacts", json)   //这种方式为一个key，对应一个文件
//                .params("Content-Type", "application/json")   //这种方式为一个key，对应一个文件
//                .params("charset", "utf-8")   //这种方式为一个key，对应一个文件
//                .params("filename", "contacts")   //这种方式为一个key，对应一个文件
//                .params("name", "contacts")   //这种方式为一个key，对应一个文件
//                .params("Content-Disposition", "form-data; name=contacts; filename=contacts")   //这种方式为一个key，对应一个文件
//                .execute(callBack);
//    }
//    //上传Json
//    public static void upLoadFile(String uploadUrl, String json, final JsonCallback callBack){
//        initOkGo();
//        final OkHttpClient client = new OkHttpClient();
//
//        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json;charset=utf-8");
//
////        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/gzip;charset=utf-8");
////        MultipartBody requestBody = new MultipartBody.Builder().addPart(
////                Headers.of(new String[]{"Content-Disposition",
////                        "form-data; name=contacts; filename=contacts","Content-Type","application/gzip"}),
////                create(MEDIA_TYPE_MARKDOWN, stream)).build();
////        MultipartBody requestBody = new MultipartBody.Builder()
////                .addFormDataPart("contacts", "contacts", create(MEDIA_TYPE_MARKDOWN, stream))
////                .build();
////        RequestBody requestBody=RequestBody.create(MEDIA_TYPE_MARKDOWN,json);
//
//        MultipartBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("filename", "contacts")
//                .addFormDataPart("name", "contacts",
//                        RequestBody.create(MEDIA_TYPE_MARKDOWN, json))
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(uploadUrl)
//                .addHeader("filename", "contacts")   //这种方式为一个key，对应一个文件
//                .addHeader("name", "contacts")   //这种方式为一个key，对应一个文件
//                .addHeader("Content-Disposition", "form-data; name=contacts; filename=contacts")
//                .post(requestBody)
//                .build();
//
//        LiteHttp.getInstence().executeAsync(new Runnable() {
//            @Override
//            public void run() {
//                final okhttp3.Response response;
//                try {
//                    final Call rawCall = client.newCall(request);
//                    response = rawCall.execute();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    LogX.e(BaokuStatic.TAG, e.getMessage());
//                    return;
//                }
//
//                if (response.isSuccessful()) {
//                    LogX.e(BaokuStatic.TAG, response.body().toString());
//                }
//                LogX.e(BaokuStatic.TAG, response.message().toString());
//            }
//        });
//    }

    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }
    //上传Json
    public static void upLoadFile(String uploadUrl, String stream){
        initOkGo();
        final OkHttpClient client = new OkHttpClient();

        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/gzip;charset=utf-8");

        MultipartBody requestBody = new MultipartBody.Builder().addPart(
                Headers.of(new String[]{"Content-Disposition",
                        "form-data; name=contacts; filename=contacts","Content-Type","application/gzip"}),
                create(MEDIA_TYPE_MARKDOWN, stream)).build();
//        MultipartBody requestBody = new MultipartBody.Builder()
//                .addFormDataPart("contacts", "contacts", create(MEDIA_TYPE_MARKDOWN, stream))
//                .addFormDataPart("Content-Type","application/gzip")
//                .setType(MultipartBody.FORM)
//                .build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(uploadUrl)
                .addHeader("Content-Type","application/gzip")
                .post(requestBody)
                .build();


        LiteHttp.getInstence().executeAsync(new Runnable() {
            @Override
            public void run() {
                final okhttp3.Response response;
                try {
                    final Call rawCall = client.newCall(request);
                    response = rawCall.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogX.e(ConstantString.TAG, e.getMessage());
                    return;
                }

                if (response.isSuccessful()) {
                    LogX.e(ConstantString.TAG, response.body().toString());
                }
                LogX.e(ConstantString.TAG, response.message().toString());
            }
        });
    }

    public static RequestBody create(final MediaType mediaType, final String inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(inputStream);
            }
        };
    }
}
