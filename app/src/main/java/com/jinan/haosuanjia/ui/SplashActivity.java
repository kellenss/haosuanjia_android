package com.jinan.haosuanjia.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.SPUtil;

/**
 * 初始化界面activity
 * 
 * @author admin
 * 
 */
public class SplashActivity extends StatisticsActivity {
	// 是否是第一次使用
	private boolean isFirstUse;
	// 是否同意协议
	private boolean isFirstGetMap;
	private static EditText tv_url;
	int goGuidance=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		context = this;
		goGuidance=SPUtil.getInt(context,ConstantString.VERSIONCODE);
		 new MyThread().start();
	}
	// 线程阻塞事件
	private class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				Thread.sleep(2000);
				if (goGuidance <HMApplication.version_code) {// 是否是第一次启动APP
					Intent intent = new Intent(getApplicationContext(),GuidanceActivity.class);
					startActivity(intent);
					SplashActivity.this.finish();
				} else {
					toMainPage();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void toMainPage() {
//        Intent intent = new Intent(getApplicationContext(),
//                SplashVideoActivityity.class);
		if(SPUtil.get(getApplicationContext(), ConstantString.USERNICKNAME).equals("")){
			Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(intent);
		}
		SplashActivity.this.finish();
	}
}
