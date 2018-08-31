package com.jinan.haosuanjia.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.CoinConfigBean;
import com.jinan.haosuanjia.commons.LogX;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.ui.pay.AuthResult;
import com.jinan.haosuanjia.ui.pay.PayResult;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.SPUtil;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 个人资料
 * create by gc on 2018/08/22
 */
public class PayOrderActivity extends StatisticsActivity implements  View.OnClickListener{


    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private TextView tv_pay_icon_1;
    private TextView tv_pay_icon_2;
    private TextView tv_pay_icon_3;
    private TextView tv_pay_icon_4;
    private TextView tv_pay_icon_5;
    private TextView tv_pay_icon_6;
    private TextView tv_my_phone_num;
    private TextView tv_my_coin_num;
    RecyclerView mRecyclerView;
    String coin_config_id="";
    String user_id ="";
    List<CoinConfigBean> activityList; // 动态数组
    GridAdapter mAdapter;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        initUI();
        //Setup RecyclerView
        activityList=new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new GridAdapter(context,activityList);
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
        initDataPost(true);
        getUserCoin("");
    }

//    private void initdata() {
//        getAboutUsPost();
//    }
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SimpleViewHolder> {

    public static final int LAST_POSITION = -1;
    private static final int COUNT = 3;

    private final Context mContext;
    private final List<CoinConfigBean> mItems;
    private int mCurrentItemId = 0;

    public GridAdapter(Context context,List arrayList) {
        mContext = context;
        mItems =arrayList;
//        for (int i = 0; i < COUNT; i++) {
//            addItem(i);
//        }
    }

    public  class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv_icon_money;
        public final TextView tv_icon_number;

        public SimpleViewHolder(View view) {
            super(view);
            tv_icon_money = (TextView) view.findViewById(R.id.tv_icon_money);
            tv_icon_number = (TextView) view.findViewById(R.id.tv_icon_number);
        }
    }

    public GridAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final View view = LayoutInflater.from(mContext).inflate(R.layout.activity_about_us, parent, false);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item_coin_config, parent, false);
        return new GridAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GridAdapter.SimpleViewHolder holder, final int position) {
        holder.tv_icon_money.setText(mItems.get(position).price+"元");
        holder.tv_icon_number.setText(mItems.get(position).amount+"金币");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = holder.getLayoutPosition();
                getCreateOrder(mItems.get(position).id+"");
//                addItem(itemPosition + 1);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int itemPosition = holder.getLayoutPosition();
//                removeItem(itemPosition);
                return true;
            }
        });
    }

  /*  public void addItem(int position) {
        final int id = mCurrentItemId++;
        position = position == LAST_POSITION ? getItemCount() : position;
//        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {

        if (position == LAST_POSITION && getItemCount() > 0)
            position = getItemCount() - 1;

        if (position > LAST_POSITION && position < getItemCount()) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }
*/
    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

    private void initUI() {
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        tv_pay_icon_1 = (TextView) findViewById(R.id.tv_pay_icon_1);
        tv_pay_icon_2 = (TextView) findViewById(R.id.tv_pay_icon_2);
        tv_pay_icon_3 = (TextView) findViewById(R.id.tv_pay_icon_3);
        tv_pay_icon_4 = (TextView) findViewById(R.id.tv_pay_icon_4);
        tv_pay_icon_5 = (TextView) findViewById(R.id.tv_pay_icon_5);
        tv_pay_icon_6 = (TextView) findViewById(R.id.tv_pay_icon_6);
        tv_my_phone_num = (TextView) findViewById(R.id.tv_my_phone_num);
        tv_my_coin_num = (TextView) findViewById(R.id.tv_my_coin_num);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_pay_icon_1.setOnClickListener(this);
        tv_pay_icon_2.setOnClickListener(this);
        tv_pay_icon_3.setOnClickListener(this);
        tv_pay_icon_4.setOnClickListener(this);
        tv_pay_icon_5.setOnClickListener(this);
        tv_pay_icon_6.setOnClickListener(this);
        tv_title_head.setText("会员服务");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
        iv_right_head.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SPUtil.get(ConstantString.PHONENUM))){
            tv_my_phone_num.setText("当前帐号："+SPUtil.get(ConstantString.PHONENUM));

        }else{
            tv_my_phone_num.setText("");
        }
    }

    private void initDataPost(final boolean needclear) {
//        if (user_id.equals("")){
//            user_id= SPUtil.get(ConstantString.USERID);
//        }
        AuctionModule.getInstance().getCoinConfig(context, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
//                        page++;
                        try{
                            activityList = ParseJson.parseGetResultCollection(result, "data", CoinConfigBean.class);
                            mAdapter = new GridAdapter(PayOrderActivity.this,(ArrayList) activityList);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }catch (Exception e ){
                            e.printStackTrace();
                            ShowToastUtil.Short("没有更多数据！");
                        }
//                        if (needclear) {
//                            feedAdapter.updateData(activityList);
//                        } else {
//                            feedAdapter.addListData(activityList);
//                        }
                    }else{
                        ShowToastUtil.Short(jsonObject.getString("msg"));
                    }
//                    lv_activity_main.stopRefresh();
//                    lv_activity_main.stopLoadMore();
//                    activityList = feedAdapter.getDataList();
                } catch (Exception e) {
                    e.printStackTrace();
                    ShowToastUtil.Short("解析异常！");
                }
//                onLoad();
            }

            @Override
            public void onGotError(String code, String error) {
//                onLoad();
            }

        });
    }
    private void getCreateOrder(String coin_config_id) {
        AuctionModule.getInstance().getCreateOrder(context,coin_config_id,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
//                        tv_about_us_content.setText();
                        String info = jsonObject.getJSONObject("data").getString("StrOrder");
                        toPay(info);
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

    private void toPay(String info) {
        final String orderInfo = info;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String aa = "1";
            Map<String, String> result = (Map<String, String>) msg.obj;
            LogX.e("alipay", result.toString());
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultInfo);
                        final  String out_trade_no=jsonObject.getJSONObject("alipay_trade_app_pay_response").getString("out_trade_no");
//                        new Handler().postDelayed(new Runnable(){
//                            public void run() {
                                getOrderResult(out_trade_no);
//                            }
//                        }, 3000);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void getOrderResult(String out_trade_no) {
        AuctionModule.getInstance().getOrderResult(context,out_trade_no,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
//                        tv_about_us_content.setText();
                        getUserCoin("");
//                        String info = jsonObject.getJSONObject("data").getString("point_amount");
//                        toPay(info);
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
    private void getUserCoin(String out_trade_no) {
        AuctionModule.getInstance().getUserCoin(context,out_trade_no,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
//                        tv_about_us_content.setText();
                        String coin_num = jsonObject.getString("data");
                        tv_my_coin_num.setText(coin_num);
//                        toPay(info);
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
        String titleNama="";
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_pay_icon_1:
                coin_config_id="1";
                getCreateOrder(coin_config_id);
                break;
            case R.id.tv_pay_icon_2:
                coin_config_id="2";
                getCreateOrder(coin_config_id);
                break;
            case R.id.tv_pay_icon_3:
                coin_config_id="3";
                getCreateOrder(coin_config_id);
                break;
            case R.id.tv_pay_icon_4:
                coin_config_id="4";
                getCreateOrder(coin_config_id);
                break;
            case R.id.tv_pay_icon_5:
                coin_config_id="5";
                getCreateOrder(coin_config_id);
                break;
            case R.id.tv_pay_icon_6:
                coin_config_id="6";
                getCreateOrder(coin_config_id);
                break;
            case R.id.tv_right_head:
//                company_name=et_company_name.getText().toString();
//                address=et_address.getText().toString();
//                phone_number=et_phone_number.getText().toString();
//                job_content=et_job_content.getText().toString();
//                job_start_date=et_job_start_date.getText().toString();
//                job_end_date=et_job_end_date.getText().toString();
//                all_day=et_all_day.getText().toString();
//                need_number=et_need_number.getText().toString();
//                one_price=et_one_price.getText().toString();
//                all_price=et_all_price.getText().toString();
//
//                SendMessagePost(company_name,address,phone_number,job_content,job_start_date,
//                        job_end_date,all_day,need_number,one_price,all_price,status,SPUtil.get(ConstantString.USERID),SPUtil.get(ConstantString.USERNICKNAME));
                break;
            default:
                break;
        }
    }
}
