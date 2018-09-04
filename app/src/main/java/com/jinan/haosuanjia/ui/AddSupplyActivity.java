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
 * 发布劳务输出
 * create by gc on 2018/06/20
 */
public class AddSupplyActivity extends StatisticsActivity implements  View.OnClickListener{

    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private EditText et_send_user_name;
    private EditText et_job_number;
    private EditText et_job_status;
    private EditText et_address;
    private EditText et_phone_number;
    private EditText et_job_start_date;
    private EditText et_job_end_date;
    private String send_user_name;
    private String job_number;
    private String job_status;
    private String address;
    private String phone_number;
    private String job_start_date;
    private String job_end_date;
    private String flag="0";//状态 0：正常，1：作废
    private String createTime="2018-06-20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supply);
        initUI();
        initdata();
//        inflater = LayoutInflater.from(context);
//        Width = this.getWindowManager().getDefaultDisplay().getWidth();// 获取屏幕高度
//        view_head_sublist = inflater.inflate(
//                R.layout.head_conmpany_enterprise, null);
//        ll_zx_company=view_head_sublist.findViewById(R.id.ll_zx_company);
//        ll_tj_company=view_head_sublist.findViewById(R.id.ll_tj_company);
//        ll_rm_company=view_head_sublist.findViewById(R.id.ll_rm_company);
//        ll_lkzlj_company=view_head_sublist.findViewById(R.id.ll_lkzlj_company);
//        ll_xhsgj_company=view_head_sublist.findViewById(R.id.ll_xhsgj_company);
//        ll_xhckj_company=view_head_sublist.findViewById(R.id.ll_xhckj_company);
//        ll_zx_company.setOnClickListener(this);
//        ll_tj_company.setOnClickListener(this);
//        ll_rm_company.setOnClickListener(this);
//        ll_lkzlj_company.setOnClickListener(this);
//        ll_xhsgj_company.setOnClickListener(this);
//        ll_xhckj_company.setOnClickListener(this);
//        lv_activity_main.removeHeaderView(view_head_sublist);
//        lv_activity_main.addHeaderView(view_head_sublist);

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
        et_send_user_name= (EditText) findViewById(R.id.et_send_user_name);
        et_job_number= (EditText) findViewById(R.id.et_job_number);
        et_job_status= (EditText) findViewById(R.id.et_job_status);
        et_address= (EditText) findViewById(R.id.et_address);
        et_phone_number= (EditText) findViewById(R.id.et_phone_number);
        et_job_start_date= (EditText) findViewById(R.id.et_job_start_date);
        et_job_end_date= (EditText) findViewById(R.id.et_job_end_date);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("添加劳务输出");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.VISIBLE);
        tv_right_head.setText("发送");
//        tv_right_head.setTextColor(getResources().getColor(R.color.color_main));
//        tv_right_head.setBackgroundResource(R.mipmap.icon_send_message);
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void SendMessagePost(String user_nickname,String supplyNum,String workType,String startDate,
                                 String endDate,String address,String phone,String flag,String createTime,String user_id) {
        AuctionModule.getInstance().getAddSupply(context,user_nickname,supplyNum,workType,startDate,
                endDate,address,phone,flag,createTime,user_id,new BaseHandlerJsonObject() {
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
                send_user_name=et_send_user_name.getText().toString();
                job_number=et_job_number.getText().toString();
                job_status=et_job_status.getText().toString();
                address=et_address.getText().toString();
                phone_number=et_phone_number.getText().toString();
                job_start_date=et_job_start_date.getText().toString();
                job_end_date=et_job_end_date.getText().toString();

                SendMessagePost(send_user_name,job_number,job_status,job_start_date,
                        job_end_date,address,phone_number,flag,createTime,SPUtil.get(ConstantString.USERID));
                break;
            default:
                break;
        }
    }
}
