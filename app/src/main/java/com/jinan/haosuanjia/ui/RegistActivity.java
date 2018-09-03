package com.jinan.haosuanjia.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.DialogTools;
import com.jinan.haosuanjia.utils.FormatTools;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.utils.UrlUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


/**
 * 注册界面
 * @author admin
 *
 */
public class RegistActivity extends StatisticsActivity implements OnClickListener{
	//控件
	private LinearLayout ll_back;
	private TextView tv_title;
//	private TextView tv_shenqing;
	private EditText et_phone;
	private EditText et_yanzheng;
	private EditText et_pwd;
	private Button btn_yanzheng;
	private Button btn_submit;
	//字符串
	private String phone;
	private String yanzhengma;
	private String pwd;
	//用到的类
	private FormatTools formatTools;
	private DialogTools dialogtools;
	private Context context;
	private TimeCount time;
	private String type="2";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		init();
		time = new TimeCount(60000, 1000);//构造CountDownTimer对象
		type=getIntent().getExtras().getString("type");
		if(type.equals("1")){
			tv_title.setText("找回密码");
		}else{
			tv_title.setText("注册");
		}
//		tv_shenqing.setVisibility(View.GONE);
	}
	private void init(){
		//实例化类
		formatTools = new FormatTools();
		dialogtools = new DialogTools();
		context = this;
		//实例化控件
		ll_back = (LinearLayout)findViewById(R.id.ll_back);
		tv_title = (TextView)findViewById(R.id.tv_title);
//		tv_shenqing = (TextView)findViewById(R.id.tv_shenqing);
		et_phone = (EditText)findViewById(R.id.et_phone);
		et_yanzheng = (EditText)findViewById(R.id.et_yanzheng);
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		btn_yanzheng = (Button)findViewById(R.id.btn_yanzheng);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		//监听
		ll_back.setOnClickListener(this);
		btn_yanzheng.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		//返回textview
		case R.id.ll_back:
			finish();
			break;
		//获取验证码button
		case R.id.btn_yanzheng:
			phone = et_phone.getText().toString().trim();
			time.start();
			if(!phone.equals("")){
				if(FormatTools.IsPhoneNum(phone)){
//					dialogtools.showDialog(context);
					AuctionModule.getInstance().getPhoneCode(context, phone, new BaseHandlerJsonObject() {
						@Override
						public void onGotJson(org.json.JSONObject result) {
							org.json.JSONObject jsonObject=result;

						}

						@Override
						public void onGotError(String code, String error) {

						}
					});
					//请求参数
//					RequestParams params=new RequestParams();
//					params.put("PhoneNumber", phone);
//					if(type.equals("2")){
//						params.put("type", "1");
//					}else{
//						params.put("type", "2");
//					}

					//
//					new AsyncHttpClient().post(UrlUtils.getYanZhengMa(),
//							params,new AsyncHttpResponseHandler(){
//						@Override
//						public void onSuccess(String data) {
//
//							super.onSuccess(data);
//							try {
//								JSONObject jsonObject = JSON.parseObject(data);
//								if(jsonObject.getBoolean("success")){
////									time.start();
////									Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
////											Toast.LENGTH_SHORT).show();
////									String str = jsonObject.getString("codenum");
////									et_yanzheng.setText(str);
//									dialogtools.dismissDialog();
//								}else{
////									Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
////											Toast.LENGTH_SHORT).show();
//									dialogtools.dismissDialog();
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//								ShowToastUtil.Short("解析异常！");
////								Toast.makeText(ZhuCeActivity.this, "未知异常！", Toast.LENGTH_LONG).show();
//								dialogtools.dismissDialog();
//							}
//						}
//
//						@Override
//						public void onFailure(Throwable arg0,
//							String arg1) {
//							ShowToastUtil.Short("服务器异常！");
//							dialogtools.dismissDialog();
//							super.onFailure(arg0, arg1);
//						}
//					});
				}else{
					ShowToastUtil.Short("请填写正确的手机号！");
				}
			}else{
				ShowToastUtil.Short("手机号不能为空！");
			}
			break;
		//
		case R.id.btn_submit:
			phone = et_phone.getText().toString().trim();
			yanzhengma = et_yanzheng.getText().toString().trim();
			pwd = et_pwd.getText().toString().trim();
			if(!phone.equals("")){
					if(!yanzhengma.equals("")){
						if(!pwd.equals("")){
//							dialogtools.showDialog(context);
							if(type.equals("2")){
								regist();
							}else{
								findPassword();
							}
						}else{
							Toast.makeText(context, "密码不能为空！", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(context, "验证码不能为空！", Toast.LENGTH_SHORT).show();
					}
			}else{
				Toast.makeText(context, "手机号不能为空！", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
	private void regist(){
		AuctionModule.getInstance().getRegister(context, phone, pwd, yanzhengma, new BaseHandlerJsonObject() {
			@Override
			public void onGotJson(org.json.JSONObject result) {
				try {
					JSONObject jsonObject = JSON.parseObject(result.toString());
					if(jsonObject.getInteger("status")==1){
//											Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
//													Toast.LENGTH_SHORT).show();
//						dialogtools.dismissDialog();
						LoginActivity.phone=phone;
						ShowToastUtil.Short(jsonObject.getString("msg"));
						finish();
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
	}
	private void findPassword(){
		//请求参数
		RequestParams params=new RequestParams();
		params.put("username", phone);
		params.put("password", pwd);
		params.put("smscode", yanzhengma);
		new AsyncHttpClient().post(UrlUtils.getfindpassword(),params,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					JSONObject jsonObject = JSON.parseObject(responseBody.toString());
					if(jsonObject.getBoolean("success")){
						ShowToastUtil.Short(jsonObject.getString("msg"));
						dialogtools.dismissDialog();
						finish();
					}else{
						ShowToastUtil.Short(jsonObject.getString("msg"));
						dialogtools.dismissDialog();
					}
				} catch (Exception e) {
					ShowToastUtil.Short("解析异常");
					dialogtools.dismissDialog();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				if(dialogtools!=null){
					dialogtools.dismissDialog();
				}
			}

		/*	@Override
			public void onSuccess(String data) {

				super.onSuccess(data);
				try {
					JSONObject jsonObject = JSON.parseObject(data);
					if(jsonObject.getBoolean("success")){
						ShowToastUtil.Short(jsonObject.getString("msg"));
						dialogtools.dismissDialog();
						finish();
					}else{
						ShowToastUtil.Short(jsonObject.getString("msg"));
						dialogtools.dismissDialog();
					}
				} catch (Exception e) {
					ShowToastUtil.Short("解析异常");
					dialogtools.dismissDialog();
				}
			}

			@Override
			public void onFailure(Throwable arg0,
								  String arg1) {

//									Toast.makeText(ZhuCeActivity.this, "服务器异常！", Toast.LENGTH_LONG).show();
				if(dialogtools!=null){
					dialogtools.dismissDialog();
				}

				super.onFailure(arg0, arg1);
			}*/
		});
	}
	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
		}
		@Override
		public void onFinish() {//计时完毕时触发
			btn_yanzheng.setText("点击重新获取");
			btn_yanzheng.setClickable(true);
		}
		@Override
		public void onTick(long millisUntilFinished){//计时过程显示
			btn_yanzheng.setClickable(false);
			if(millisUntilFinished /1000<1){
				btn_yanzheng.setText("0"+"秒");
			}else{
				
				btn_yanzheng.setText(millisUntilFinished /1000+"秒");
			}
		}
	}
}
