package suanhang.jinan.com.suannihen.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.utils.ConstantString;
import suanhang.jinan.com.suannihen.utils.SPUtil;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		context = this;
		 new MyThread().start();
	}
	// 线程阻塞事件
	private class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				Thread.sleep(2000);
				if(SPUtil.get(getApplicationContext(), ConstantString.USERNICKNAME).equals("")){
					Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(intent);
				}
				SplashActivity.this.finish();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
