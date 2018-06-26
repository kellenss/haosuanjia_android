package com.jinan.haosuanjia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.jinan.haosuanjia.R;

public class StatisticsActivity extends FragmentActivity {

    public Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题栏！
        setContentView(R.layout.activity_base);
        context = this;
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        initObserver();
//        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /** 使用SSO授权必须添加如下代码 */
        try {
//            SsoHandler ssoHandler = BKShareService.getInstance(context).getmSsoHandler();
//            if (ssoHandler != null) {
//                ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
