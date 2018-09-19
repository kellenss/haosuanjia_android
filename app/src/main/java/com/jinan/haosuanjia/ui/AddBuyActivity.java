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
 * 发布大蒜收购
 * create by gc on 2018/06/29
 */
public class AddBuyActivity extends StatisticsActivity implements  View.OnClickListener{

    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private EditText et_send_user_name;
    private EditText et_title;
    private EditText et_address;
    private EditText et_requirement;
    private EditText et_want_price;
    private EditText et_spec;
    private EditText et_crop;
    private EditText et_amount;
    private EditText et_phone_number;
    private String send_user_name;
    private String title;
    private String crop;
    private String amount;
    private String spec;
    private String wantPrice;
    private String address;
    private String requirement;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buy);
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
        et_title= (EditText) findViewById(R.id.et_title);
        et_address= (EditText) findViewById(R.id.et_address);
        et_requirement= (EditText) findViewById(R.id.et_requirement);
        et_want_price= (EditText) findViewById(R.id.et_want_price);
        et_spec= (EditText) findViewById(R.id.et_spec);
        et_crop= (EditText) findViewById(R.id.et_crop);
        et_amount= (EditText) findViewById(R.id.et_amount);
        et_phone_number= (EditText) findViewById(R.id.et_phone_number);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("发布购蒜需求");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.VISIBLE);
        tv_right_head.setText("发送");
//        tv_right_head.setTextColor(getResources().getColor(R.color.color_main));
//        tv_right_head.setBackgroundResource(R.mipmap.icon_send_message);
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void SendMessagePost(String send_user_name,String title,String crop,String address,String phone,String spec,String wantPrice,
                                 String requirement,String amount,String user_id) {
        AuctionModule.getInstance().getAddBuy(context,send_user_name,title,crop,address,phone,spec,wantPrice,
                requirement,amount,user_id,new BaseHandlerJsonObject() {
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

                send_user_name=et_send_user_name.getText().toString();
                title=et_title.getText().toString();
                crop=et_crop.getText().toString();
                address=et_address.getText().toString();
                phone=et_phone_number.getText().toString();
                spec=et_spec.getText().toString();
                amount=et_amount.getText().toString();
                requirement=et_requirement.getText().toString();
                wantPrice=et_want_price.getText().toString();
                if(TextUtils.isEmpty(title)){
                    ShowToastUtil.Short("请输入标题");
                    return;
                }
                if(TextUtils.isEmpty(send_user_name)){
                    ShowToastUtil.Short("请输入联系人");
                    return;
                }
                if(TextUtils.isEmpty(crop)){
                    ShowToastUtil.Short("请输入购买品种");
                    return;
                }
                if(TextUtils.isEmpty(amount)){
                    ShowToastUtil.Short("请输入求购数量");
                    return;
                }
                if(TextUtils.isEmpty(wantPrice)){
                    ShowToastUtil.Short("请输入理想价位");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    ShowToastUtil.Short("请输入地址");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    ShowToastUtil.Short("请输入联系电话");
                    return;
                }
                SendMessagePost(send_user_name,title,crop,address,phone,spec,wantPrice,
                        requirement,amount,SPUtil.get(ConstantString.USERID));
                break;
            default:
                break;
        }
    }
}
