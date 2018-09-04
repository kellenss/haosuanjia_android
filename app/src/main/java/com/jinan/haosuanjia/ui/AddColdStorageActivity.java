package com.jinan.haosuanjia.ui;

import android.os.Bundle;
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
 * 发布冷库租赁信息
 * create by gc on 2018/07/11
 */
public class AddColdStorageActivity extends StatisticsActivity implements  View.OnClickListener{

    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private EditText et_lease_type;
    private EditText et_lease_price;
    private EditText et_lease_days;
    private EditText et_lease_all_price;
    private EditText et_lease_surplus;
    private EditText et_lease_address;
    private String cold_storage_cate;
    private String price;
    private String amount;
    private String daylength;
    private String surplus;
    private String address;
//    private String job_start_date;
//    private String job_end_date;
//    private String flag="0";//状态 0：正常，1：作废
//    private String createTime="2018-06-20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cold_storage);
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
        et_lease_type= (EditText) findViewById(R.id.et_lease_type);
        et_lease_price= (EditText) findViewById(R.id.et_lease_price);
        et_lease_days= (EditText) findViewById(R.id.et_lease_days);
        et_lease_all_price= (EditText) findViewById(R.id.et_lease_all_price);
        et_lease_surplus= (EditText) findViewById(R.id.et_lease_surplus);
        et_lease_address= (EditText) findViewById(R.id.et_lease_address);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("发布冷库租赁信息");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.VISIBLE);
        tv_right_head.setText("发送");
//        tv_right_head.setTextColor(getResources().getColor(R.color.color_main));
//        tv_right_head.setBackgroundResource(R.mipmap.icon_send_message);
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void SendMessagePost(String cold_storage_cate,String price,String amount,String surplus,
                                 String daylength,String address,String user_id) {
        AuctionModule.getInstance().getAddColdLease(context,cold_storage_cate,price,amount,surplus,
                daylength,address,user_id,new BaseHandlerJsonObject() {
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
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_right_head:
                cold_storage_cate=et_lease_type.getText().toString();
                price=et_lease_price.getText().toString();
                amount=et_lease_all_price.getText().toString();
                address=et_lease_address.getText().toString();
                surplus=et_lease_surplus.getText().toString();
                daylength=et_lease_days.getText().toString();

                SendMessagePost(cold_storage_cate,price,amount,surplus,
                        daylength,address,SPUtil.get(ConstantString.USERID));
                break;
            default:
                break;
        }
    }
}
