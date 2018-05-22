package suanhang.jinan.com.suannihen.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import suanhang.jinan.com.suannihen.R;

public class BaseActivity extends FragmentActivity {
	protected Activity context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_base);
		context = this;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); //调用双击退出函数
			return true;
		}
//		return false;
		return super.onKeyDown(keyCode, event);
	}

    private void doKillProcess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//				ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        },  500);
    }

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
//		VolleyUtils.cancelAll(this);
		super.onDestroy();
	}
	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
//			CYIO.getInstance().clear();
			doKillProcess();
//			finish();
//			System.exit(0);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
//		if(SPUtil.getBoolean(context,"isShareSN")){
//			SPUtil.setBoolean(context,"isShareSN",false);
//			BKShareService.getInstance(context).doResultIntent(intent);
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		/** 使用SSO授权必须添加如下代码 */
		try {
//			SsoHandler ssoHandler = BKShareService.getInstance(context).getmSsoHandler();
//			if (ssoHandler != null) {
//				ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
