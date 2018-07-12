package com.jinan.haosuanjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.SPUtil;


/**
 * 我的
 */
public class MineActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_setting;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_yjfk;
    private TextView tv_bzzx;
    private TextView tv_fabu_zulin;
    private LinearLayout ll_mine_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        init();
//        Toast.makeText(context,"我的",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (SPUtil.get())
        if(!TextUtils.isEmpty( SPUtil.get(ConstantString.PHONENUM))){
            tv_phone.setText("手机号："+ SPUtil.get(ConstantString.PHONENUM));
            tv_name.setText(""+ SPUtil.get(ConstantString.USERNICKNAME));
        }else{
            tv_phone.setText("");
            tv_name.setText("请登录");
        }
    }

    private void init() {
        iv_setting=(ImageView) findViewById(R.id.iv_setting);
        tv_name=(TextView) findViewById(R.id.tv_name);
        tv_phone=(TextView) findViewById(R.id.tv_phone);
        tv_yjfk=(TextView) findViewById(R.id.tv_yjfk);
        tv_bzzx=(TextView) findViewById(R.id.tv_bzzx);
        tv_fabu_zulin=(TextView) findViewById(R.id.tv_fabu_zulin);
        ll_mine_info=(LinearLayout) findViewById(R.id.ll_mine_info);
        iv_setting.setOnClickListener(this);
        tv_yjfk.setOnClickListener(this);
        tv_bzzx.setOnClickListener(this);
        tv_fabu_zulin.setOnClickListener(this);
        ll_mine_info.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_yjfk:
                Intent intent =new Intent(this,FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bzzx:
                Intent intent1 =new Intent(this,HelpCenterActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_setting:
                Intent intent2 =new Intent(this,SettingActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_fabu_zulin:
                Intent intent3 =new Intent(this,AddColdStorageActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_mine_info:
                if (TextUtils.isEmpty(SPUtil.get(ConstantString.PHONENUM))){
                    Intent intent4 =new Intent(this,LoginActivity.class);
                    startActivity(intent4);
                 }else{

                  }
                break;
            default:
                    break;
        }

    }
}
