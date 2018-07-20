package com.jinan.haosuanjia.utils;

import android.app.Application;

/**
 * Created by admin on 2017/09/06.
 * 一些静态参数，从Application中转移到这里，方便后续使用tinker
 */

public class HMApplication extends Application {

    public static Application application = null;
    public static boolean log = true; // true--打开log  false--关闭log
    public static final String TAG = "hmsysLog";
    public static boolean isTest = false; // true--测试环境  false--线上环境
//    public static final String KP_BASE_URL = "http://47.94.210.116/app";//库拍请求服务器地址线上
    public static final String KP_BASE_URL = "http://www.haosuanjia.com/app";//库拍请求服务器地址线上
    public static final String KP_BASE_URL_YU = "http://www.haosuanjia.com";//库拍请求服务器地址线上
    public static String version_name = "1.1";
    public static int version_code = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        application= (Application) getApplicationContext();
    }
}
