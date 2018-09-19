package com.jinan.haosuanjia.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.InformationConsultationBean;
import com.jinan.haosuanjia.bean.MessageDetailBean;
import com.jinan.haosuanjia.bean.MessageListBean;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.view.CircleImageView;

import org.json.JSONObject;

/**
 * 我的消息详情
 * create by gc on 2018/09/13
 */
public class MessageDetailActivity extends StatisticsActivity implements View.OnClickListener {
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private TextView tv_content_title;
    private EditText et_comments_edit;
    private TextView tv_comments_send;
    private TextView tv_create_time;
    private ImageView iv_company_img;
    private CircleImageView iv_user_photo;
    private TextView tv_about_title;
    private TextView tv_about_user_name;
    private TextView tv_about_content;
    private WebView wv_content_detail;
    private MessageDetailBean messageListBean;

    String class_id ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initUI();
        initdata();
    }

    private void initdata() {
        initDataPost();
    }

    private void initUI() {
//        title_head=getIntent().getStringExtra("title_name");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("class_id"))){
            class_id=getIntent().getStringExtra("class_id");
        }
        messageListBean=new MessageDetailBean();
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        tv_content_title = (TextView) findViewById(R.id.tv_content_title);
        tv_create_time = (TextView) findViewById(R.id.tv_create_time);
        et_comments_edit = (EditText) findViewById(R.id.et_comments_edit);
        tv_comments_send = (TextView) findViewById(R.id.tv_comments_send);
        iv_company_img = (ImageView) findViewById(R.id.iv_company_img);
        iv_user_photo = (CircleImageView) findViewById(R.id.iv_user_photo);
        tv_about_title = (TextView) findViewById(R.id.tv_about_title);
        tv_about_user_name= (TextView) findViewById(R.id.tv_about_user_name);
        tv_about_content = (TextView) findViewById(R.id.tv_about_content);
        wv_content_detail = (WebView) findViewById(R.id.wv_content_detail);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_comments_send.setOnClickListener(this);
        tv_title_head.setText("消息详情");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void initDataPost() {

        AuctionModule.getInstance().getMessageDetail(context,class_id, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        try{
//                            informationConsultationBean = ParseJson.parseGetResultObject(result.getJSONObject("data"), InformationConsultationBean.class);
                            messageListBean=ParseJson.parseConvertResultObject(result.getJSONObject("data"), MessageDetailBean.class);
                            tv_about_title.setText(messageListBean.aboutinfo.title);
                            tv_about_user_name.setText(messageListBean.aboutinfo.user_nickname);
                            tv_about_content.setText(messageListBean.aboutinfo.content);

                            BitmapUtil.loadImageUrl(iv_user_photo, R.mipmap.icon_my_head_img, HMApplication.KP_BASE_URL_YU+messageListBean.aboutinfo.avatar);
                            tv_content_title.setText(messageListBean.msginfo.title+"  来自:"+messageListBean.msginfo.from_user);
                            tv_create_time.setText("时间："+messageListBean.msginfo.createtime);

//                            BitmapUtil.loadImageUrl(iv_company_img, R.mipmap.icon_commpany_zd, HMApplication.KP_BASE_URL_YU + messageListBean.cover);
                            String htmlData = messageListBean.msginfo.type_name+" : "+messageListBean.msginfo.content;
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
String content="";
    private void getAddNewsComments() {
        content=et_comments_edit.getText().toString().trim();
        AuctionModule.getInstance().getAddNewsComments(context,class_id,content, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        try{
                            et_comments_edit.setText("");
                        }catch (Exception e ){
                            e.printStackTrace();
                            ShowToastUtil.Short("没有更多数据！");
                        }
                    }else{
//                        ShowToastUtil.Short(jsonObject.getString("msg"));
                    }
                    ShowToastUtil.Short(jsonObject.getString("msg"));
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
            case R.id.tv_comments_send:
                getAddNewsComments();
                break;
            default:
                break;
        }
    }
}
