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
 * 发布劳务需求
 * create by gc on 2018/06/20
 */
public class AddDemandActivity extends StatisticsActivity implements  View.OnClickListener{

    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private EditText et_company_name;
    private EditText et_address;
    private EditText et_phone_number;
    private EditText et_job_content;
    private EditText et_job_start_date;
    private EditText et_job_end_date;
    private EditText et_all_day;
    private EditText et_need_number;
    private EditText et_one_price;
    private EditText et_all_price;
    private String company_name;
    private String address;
    private String phone_number;
    private String job_content;
    private String job_start_date;
    private String job_end_date;
    private String all_day;
    private String need_number;
    private String one_price;
    private String all_price;
    private String status="0";//状态 0：正常，1：作废
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demand);
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
        et_company_name= (EditText) findViewById(R.id.et_company_name);
        et_address= (EditText) findViewById(R.id.et_address);
        et_phone_number= (EditText) findViewById(R.id.et_phone_number);
        et_job_content= (EditText) findViewById(R.id.et_job_content);
        et_job_start_date= (EditText) findViewById(R.id.et_job_start_date);
        et_job_end_date= (EditText) findViewById(R.id.et_job_end_date);
        et_all_day= (EditText) findViewById(R.id.et_all_day);
        et_need_number= (EditText) findViewById(R.id.et_need_number);
        et_one_price= (EditText) findViewById(R.id.et_one_price);
        et_all_price= (EditText) findViewById(R.id.et_all_price);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("添加劳务需求");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.VISIBLE);
        tv_right_head.setText("发送");
//        tv_right_head.setTextColor(getResources().getColor(R.color.color_main));
//        tv_right_head.setBackgroundResource(R.mipmap.icon_send_message);
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void SendMessagePost(String unitName,String address,String phone,String workContent,String startDate,
                                 String endDate,String workDays,String workers,String price,String amount,String status,String user_id,String user_nickname) {
        AuctionModule.getInstance().getAddDemand(context,unitName,address,phone,workContent,startDate,
                endDate,workDays,workers,price,amount,status,user_id,user_nickname,new BaseHandlerJsonObject() {
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
                company_name=et_company_name.getText().toString();
                address=et_address.getText().toString();
                phone_number=et_phone_number.getText().toString();
                job_content=et_job_content.getText().toString();
                job_start_date=et_job_start_date.getText().toString();
                job_end_date=et_job_end_date.getText().toString();
                all_day=et_all_day.getText().toString();
                need_number=et_need_number.getText().toString();
                one_price=et_one_price.getText().toString();
                all_price=et_all_price.getText().toString();

                SendMessagePost(company_name,address,phone_number,job_content,job_start_date,
                        job_end_date,all_day,need_number,one_price,all_price,status,SPUtil.get(ConstantString.USERID),SPUtil.get(ConstantString.USERNICKNAME));
                break;
            default:
                break;
        }
    }
}
