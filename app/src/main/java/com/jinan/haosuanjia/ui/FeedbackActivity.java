package com.jinan.haosuanjia.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.SPUtil;
import com.jinan.haosuanjia.utils.ShowToastUtil;

import org.json.JSONObject;

/**
 * 添加意见反馈
 * create by gc on 2018/06/27
 */
public class FeedbackActivity extends StatisticsActivity implements  View.OnClickListener{

    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private EditText et_title;
    private EditText et_content;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);
        initUI();
        initdata();
    }

    private void initdata() {
//        initDataPost(true);
    }

    private void initUI() {
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("意见反馈");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.VISIBLE);
        tv_right_head.setText("发送");
//        tv_right_head.setTextColor(getResources().getColor(R.color.color_main));
//        tv_right_head.setBackgroundResource(R.mipmap.icon_send_message);
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void SendMessagePost(String title,String content) {

        AuctionModule.getInstance().getAddFeedback(context,title, content,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        finish();
                    }else{
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
        String titleNama="";
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_right_head:
                title=et_title.getText().toString();
                content=et_content.getText().toString();
                if(TextUtils.isEmpty(title)){
                    ShowToastUtil.toastShow("请输入标题在发送哦！");
                    return;
                }
                if(TextUtils.isEmpty(content)){
                    ShowToastUtil.toastShow("请输入内容在发送哦！");
                    return;
                }
                SendMessagePost(title,content);
                break;
            default:
                break;
        }
    }
}
