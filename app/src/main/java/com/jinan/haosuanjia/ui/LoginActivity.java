package com.jinan.haosuanjia.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.SPUtil;
import com.jinan.haosuanjia.utils.ShowToastUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * 登录界面activity
 * @author admin
 *
 */
public class LoginActivity extends StatisticsActivity implements OnClickListener {

	private AlertDialog alertDialog;
	private EditText et_username,et_password;
	private TextView login_button;
	private TextView tv_forget_password ,tv_regist;
	private Context context=null;
	public static String phone="";

	String registration_id="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		if (SPUtil.get(getApplicationContext(), ConstantString.USERNICKNAME) == "") {
		} else {
			et_username.setText(SPUtil.get(getApplicationContext(), ConstantString.USERNICKNAME));
//			et_password.setText(SPUtil.get(getApplicationContext(), "PsWord"));
		}
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SPUtil.get(getApplicationContext(), ConstantString.USERNICKNAME) == "") {
					LoginActivity.this.finish();
					Intent intent= new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);	
				} else {
					LoginActivity.this.finish();
				}
				}
		});
	}


	private void init() {
		// TODO Auto-generated method stub
	context=this;
	login_button=(TextView) findViewById(R.id.login_button);
	tv_forget_password=(TextView) findViewById(R.id.tv_forget_password);
	tv_forget_password.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
	tv_regist=(TextView) findViewById(R.id.tv_regist);
	et_username = (EditText) findViewById(R.id.et_username);
	et_password=(EditText) findViewById(R.id.et_password);
	login_button.setOnClickListener(this);
	tv_forget_password.setOnClickListener(this);
	tv_regist.setOnClickListener(this);
	et_username.setText(SPUtil.get(ConstantString.PHONENUM));
	et_password.setText(SPUtil.get(ConstantString.PASSWORD));
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!TextUtils.isEmpty(phone)){
			et_username.setText(phone);
			phone="";
		}
	}

	//对话框
	private void showDialog() {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(this).create();
			View v = View.inflate(getApplicationContext(), R.layout.custom_progress_loading, null);
			alertDialog.setView(v);
		}
		alertDialog.show();
	}
//对话框

	private void RegisteJpush(String registration_id) {
		AuctionModule.getInstance().RegisteJpush(context, registration_id, new BaseHandlerJsonObject() {
			@Override
			public void onGotJson(org.json.JSONObject result) {
				try {
					JSONObject jsonObject = JSON.parseObject(result.toString());
					if(jsonObject.getInteger("status")==1){
//						SPUtil.set(ConstantString.PHONENUM, jsonObject.getJSONObject("data").getString("mobile"));
//						SPUtil.set(ConstantString.USERNICKNAME, jsonObject.getJSONObject("data").getString("user_nickname"));
//						SPUtil.set(ConstantString.USERID, jsonObject.getJSONObject("data").getString("id"));
//						SPUtil.set(ConstantString.TOKEN, jsonObject.getJSONObject("data").getString(ConstantString.TOKEN));
////											Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
////													Toast.LENGTH_SHORT).show();
////						dialogtools.dismissDialog();
						ShowToastUtil.Short(jsonObject.getString("msg"));
////						finish();
					}else{
////											Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
////													Toast.LENGTH_SHORT).show();
						ShowToastUtil.Short(jsonObject.getString("msg"));
////						dialogtools.dismissDialog();
					}
				} catch (Exception e) {
					e.printStackTrace();
					ShowToastUtil.Short("解析异常！");
//										Toast.makeText(ZhuCeActivity.this, "未知异常！", Toast.LENGTH_LONG).show();
//					dialogtools.dismissDialog();
				}
			}
			@Override
			public void onGotError(String code, String error) {
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_forget_password:
				Intent intent=new Intent(context,RegistActivity.class);
				intent.putExtra("type","1");
				startActivity(intent);
				break;
			case R.id.login_button:
				final String username = et_username.getText().toString().trim();
				final String password = et_password.getText().toString().trim();

				if (username.isEmpty()) {
					ShowToastUtil.Short("请输入用户名");
					return;
				}
				if (password.isEmpty()) {
					ShowToastUtil.Short("请输入密码");
					return;
				}
				AuctionModule.getInstance().getLogin(context, username, password, new BaseHandlerJsonObject() {
					@Override
					public void onGotJson(org.json.JSONObject result) {
						try {
							JSONObject jsonObject = JSON.parseObject(result.toString());
													if (jsonObject.getInteger("status")==1) {
							SPUtil.set(ConstantString.PHONENUM, username);
							SPUtil.set(ConstantString.PASSWORD, password);


							Intent intent = new Intent(getApplicationContext(),MainActivity.class);
							startActivity(intent);
						}
							if(jsonObject.getInteger("status")==1){
								SPUtil.set(ConstantString.PHONENUM, jsonObject.getJSONObject("data").getString("mobile"));
								SPUtil.set(ConstantString.USERNICKNAME, jsonObject.getJSONObject("data").getString("user_nickname"));
								SPUtil.set(ConstantString.AVATAR, jsonObject.getJSONObject("data").getString("avatar"));
								SPUtil.set(ConstantString.BIRTHDAY, jsonObject.getJSONObject("data").getString("birthday"));
								SPUtil.set(ConstantString.SEX, jsonObject.getJSONObject("data").getString("sex"));
								SPUtil.set(ConstantString.USERID, jsonObject.getJSONObject("data").getString("id"));
								SPUtil.set(ConstantString.TOKEN, jsonObject.getJSONObject("data").getString(ConstantString.TOKEN));
								registration_id = JPushInterface.getRegistrationID(getApplicationContext());
								RegisteJpush(registration_id);
//											Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
//													Toast.LENGTH_SHORT).show();
//						dialogtools.dismissDialog();
								ShowToastUtil.Short(jsonObject.getString("msg"));
//						finish();
							}else{
//											Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
//													Toast.LENGTH_SHORT).show();
								ShowToastUtil.Short(jsonObject.getString("msg"));
//						dialogtools.dismissDialog();
							}
						} catch (Exception e) {

							e.printStackTrace();
							ShowToastUtil.Short("解析异常！");
//										Toast.makeText(ZhuCeActivity.this, "未知异常！", Toast.LENGTH_LONG).show();
//					dialogtools.dismissDialog();
						}
					}

					@Override
					public void onGotError(String code, String error) {

					}
				});
				//网络请求接口
//				showDialog();
//				RequestParams params1 = new RequestParams();
//				params1.put("PhoneNumber", username);
//				params1.put("PassWord", password);
//				new AsyncHttpClient().get(UrlUtils.getMemberLogin(), params1, new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(String arg0) {
//						super.onSuccess(arg0);
//						Log.v("LoginActivity", arg0);
//						if (JSONObject.parseObject(arg0).getBoolean("success")) {
//							SPUtil.set(ConstantString.USERID, JSONObject.parseObject(arg0).getJSONObject("doctor").getString(ConstantString.USERID));
//							SPUtil.set(ConstantString.USERNAME, JSONObject.parseObject(arg0).getJSONObject("doctor").getString(ConstantString.USERNAME));
//
//							Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//							startActivity(intent);
//						}
//						ShowToastUtil.Short(JSONObject.parseObject(arg0).getString("msg"));
//						alertDialog.dismiss();
//					}
//
//					@Override
//					public void onFailure(Throwable arg0, String arg1) {
//						super.onFailure(arg0, arg1);
//						ShowToastUtil.Short("登录失败，请检查网络连接");
//						alertDialog.dismiss();
//					}
//				});
				break;
			case R.id.tv_regist:
				Intent intent1=new Intent(context,RegistActivity.class);
				intent1.putExtra("type","2");
				startActivity(intent1);
				break;
		}
	}
}
