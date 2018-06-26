package com.jinan.haosuanjia.interfac;

/**
 * @author admin
 * @time 2015-11-19
 */
public interface VolleyCallBack {
	void success(String result, String method);

	void failure(String error, String method, int type);
}
