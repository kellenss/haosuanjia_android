package com.jinan.haosuanjia.commons;


import com.jinan.haosuanjia.utils.HMApplication;

/**
 * 日志
 * 
 * @author xieqq
 */
public class LogX {
	public static Boolean bLogOut = HMApplication.log;
//	public static Boolean bLogOut = true;


	/**
	 * 错误日志
	 * 
	 * @param TAG
	 *            类名
	 * @param ex
	 *            信息
	 */
	public static void e(String TAG, Exception ex) {
		e(TAG, ex.getMessage());
		ex.printStackTrace();
	}

	/**
	 * 错误日志
	 * 
	 * @param TAG
	 *            类名
	 * @param msg
	 *            信息
	 */
	public static void e(String TAG, String msg) {
        if (bLogOut)
		    android.util.Log.e(TAG, msg);
	}

	/**
	 * 信息日志
	 * 
	 * @param TAG
	 * @param msg
	 */
	public static void i(String TAG, String msg) {
		android.util.Log.i(TAG, msg);
	}

	/**
	 * 调试日志
	 * 
	 * @param TAG
	 * @param msg
	 */
	public static void d(String TAG, String msg) {
		if (bLogOut) {
			android.util.Log.d(TAG, msg);
		}
	}

	/**
	 * 警告日志
	 * 
	 * @param TAG
	 * @param msg
	 */
	public static void w(String TAG, String msg) {
		android.util.Log.w(TAG, msg);
	}

	public static void v(String TAG, String msg) {
		android.util.Log.v(TAG, msg);
	}

	public static void sysO(String TAG, String msg) {
		if (bLogOut) {
			System.out.println(TAG + ":" + msg);
		}
	}
}
