package com.jinan.haosuanjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.Constant;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.SPUtil;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * 我的
 */
public class MineActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_setting;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_hyfw;
    private TextView tv_zffs;
    private TextView tv_yjfk;
    private TextView tv_bzzx;
    private TextView tv_fabu_zulin;
    private LinearLayout ll_mine_info;
    private ImageView iv_user_photo;
    private ImageView iv_head_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        init();
        Glide.with(context)//磨砂，毛玻璃效果使用此方法
                .load(R.mipmap.icon_market)//radius为模糊半径，如果不写，默认为25
                .bitmapTransform(new BlurTransformation(context, 45))
                .into(iv_head_bg);
//        Toast.makeText(context,"我的",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (SPUtil.get())
        if(!TextUtils.isEmpty( SPUtil.get(ConstantString.PHONENUM))){
            tv_phone.setText("手机号："+ SPUtil.get(ConstantString.PHONENUM));
            tv_name.setText(""+ SPUtil.get(ConstantString.USERNICKNAME));
            BitmapUtil.loadImageUrl(iv_user_photo, R.mipmap.icon_my_head_img, HMApplication.KP_BASE_URL_YU+SPUtil.get(ConstantString.AVATAR));
            Glide.with(context)//磨砂，毛玻璃效果使用此方法
                    .load(HMApplication.KP_BASE_URL_YU+SPUtil.get(ConstantString.AVATAR))//radius为模糊半径，如果不写，默认为25
                    .bitmapTransform(new BlurTransformation(context, 25))
                    .into(iv_head_bg);
        }else{
            tv_phone.setText("");
            tv_name.setText("请登录");
            BitmapUtil.loadImageUrl(iv_user_photo, R.mipmap.icon_my_head_img, HMApplication.KP_BASE_URL_YU+SPUtil.get(ConstantString.AVATAR));
            Glide.with(context)//磨砂，毛玻璃效果使用此方法
                    .load(R.mipmap.icon_market)//radius为模糊半径，如果不写，默认为25
                    .bitmapTransform(new BlurTransformation(context, 45))
                    .into(iv_head_bg);
        }
    }

    private void init() {
        iv_setting=(ImageView) findViewById(R.id.iv_setting);
        tv_name=(TextView) findViewById(R.id.tv_name);
        tv_phone=(TextView) findViewById(R.id.tv_phone);
        tv_hyfw=(TextView) findViewById(R.id.tv_hyfw);
        tv_zffs=(TextView) findViewById(R.id.tv_zffs);
        tv_yjfk=(TextView) findViewById(R.id.tv_yjfk);
        tv_bzzx=(TextView) findViewById(R.id.tv_bzzx);
        tv_fabu_zulin=(TextView) findViewById(R.id.tv_fabu_zulin);
        ll_mine_info=(LinearLayout) findViewById(R.id.ll_mine_info);
        iv_user_photo=(ImageView) findViewById(R.id.iv_user_photo);
        iv_head_bg=(ImageView) findViewById(R.id.iv_head_bg);
        iv_setting.setOnClickListener(this);
        tv_hyfw.setOnClickListener(this);
        tv_zffs.setOnClickListener(this);
        tv_yjfk.setOnClickListener(this);
        tv_bzzx.setOnClickListener(this);
        tv_fabu_zulin.setOnClickListener(this);
        ll_mine_info.setOnClickListener(this);
        iv_user_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_hyfw:
                Intent intent6 =new Intent(this,PayOrderActivity.class);
                startActivity(intent6);
                break;
            case R.id.tv_zffs:
                Intent intent7 =new Intent(this,PayOrderActivity.class);
                startActivity(intent7);
                break;
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
             case R.id.iv_user_photo:
                 if (TextUtils.isEmpty(SPUtil.get(ConstantString.PHONENUM))){
                     Intent intent4 =new Intent(this,LoginActivity.class);
                     startActivity(intent4);
                     return;
                 }
                Intent intent5 =new Intent(this,PersonalInformationActivity.class);
                startActivity(intent5);

                break;
            default:
                    break;
        }

    }
}
