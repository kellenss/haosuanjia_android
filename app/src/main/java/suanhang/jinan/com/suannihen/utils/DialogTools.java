package suanhang.jinan.com.suannihen.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import suanhang.jinan.com.suannihen.R;


public class DialogTools {
	private Dialog alertDialog;
	//显示加载对话框
	public void showDialog(Context context) {
		if (alertDialog == null) {
//			alertDialog = new AlertDialog.Builder(context).create();
			
			alertDialog = new Dialog(context, R.style.dialog);
			alertDialog.setContentView(R.layout.dialog_layout);
			Window window = alertDialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			int width = Utils.getScreenWidth(context);
			lp.width = (int) (0.6 * width);
//			View v = View.inflate(context, R.layout.dialog_layout, null);
//			alertDialog.setView(v);
		}
		alertDialog.show();
	}
	//关闭加载对话框
	public void dismissDialog(){
		alertDialog.dismiss();
	}
}
