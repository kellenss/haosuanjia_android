package com.jinan.haosuanjia.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.InformationConsultationBean;
import com.jinan.haosuanjia.bean.NewsInformationBean;
import com.jinan.haosuanjia.bean.TipsList;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.view.TipsViewPagerAdapter;
import com.jinan.haosuanjia.view.adapter.AdapterItem;
import com.jinan.haosuanjia.view.adapter.CommonAdapter;
import com.jinan.haosuanjia.view.listviewload.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 信息资讯详情
 * create by gc on 2018/07/17
 */
public class InformationConsultationDetailActivity extends StatisticsActivity implements View.OnClickListener {
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private TextView tv_content_title;
    private ImageView iv_company_img;
    private WebView wv_content_detail;
    private InformationConsultationBean informationConsultationBean;

    String class_id ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conmpany_enterprise_detail);
        initUI();
        initdata();
    }

    private void initdata() {
        initDataPost(true);
    }

    private void initUI() {
//        title_head=getIntent().getStringExtra("title_name");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("class_id"))){
            class_id=getIntent().getStringExtra("class_id");
        }
        informationConsultationBean=new InformationConsultationBean();
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        tv_content_title = (TextView) findViewById(R.id.tv_content_title);
        iv_company_img = (ImageView) findViewById(R.id.iv_company_img);
        wv_content_detail = (WebView) findViewById(R.id.wv_content_detail);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("信息资讯详情");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void initDataPost(final boolean needclear) {

        AuctionModule.getInstance().getNewsInformationDetail(context,class_id, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        try{
//                            informationConsultationBean = ParseJson.parseGetResultObject(result.getJSONObject("data"), InformationConsultationBean.class);
                            informationConsultationBean=ParseJson.parseConvertResultObject(result.getJSONObject("data"), InformationConsultationBean.class);
                            tv_content_title.setText(informationConsultationBean.title);
                            BitmapUtil.loadImageUrl(iv_company_img, R.mipmap.icon_commpany_zd, HMApplication.KP_BASE_URL_YU + informationConsultationBean.cover);
                            String htmlData = informationConsultationBean.content;
                            if(!TextUtils.isEmpty(htmlData)){
                                  htmlData = htmlData.replaceAll("&amp;", "")
                                          .replaceAll("&quot;", "\"")
                                          .replaceAll("&lt;", "<")
                                          .replaceAll("&gt;", ">");
                                  wv_content_detail.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
                            }
                        }catch (Exception e ){
                            e.printStackTrace();
                            ShowToastUtil.Short("没有更多数据！");
                        }
                    }else{
                        ShowToastUtil.Short(jsonObject.getString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ShowToastUtil.Short("解析异常！");
                }
            }

            @Override
            public void onGotError(String code, String error) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            default:
                break;
        }
    }
}
