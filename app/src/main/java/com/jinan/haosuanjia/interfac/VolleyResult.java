package com.jinan.haosuanjia.interfac;

public interface VolleyResult {
	void success(String result, String method);

	void failure(String error, String method);
}
