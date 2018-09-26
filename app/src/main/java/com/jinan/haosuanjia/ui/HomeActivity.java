package com.jinan.haosuanjia.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.AgentListBean;
import com.jinan.haosuanjia.bean.TipsList;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.Constant;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.SPUtil;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.view.TipsViewPagerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *首页
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
private LinearLayout ll_quotation;
private LinearLayout ll_business;
private LinearLayout ll_labour_services;
private LinearLayout ll_information_consultation;
private LinearLayout ll_forum;
private LinearLayout ll_other;
private LinearLayout ll_company;
private LinearLayout ll_agent;
private LinearLayout ll_customer_service;
private LinearLayout ll_zsq;
private TextView tv_count_num;
private TextView tv_address;
private ImageView iv_right;
private ViewPager view_pager;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
//原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    public void onCreate() {

    }
    List<TipsList> tipslist; // 动态数组
    TipsViewPagerAdapter tipsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUi();
        registerLin();
        tipslist= new ArrayList<>();
        viewPageInit();
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
//        Toast.makeText(context,"首页",Toast.LENGTH_SHORT).show();
        mLocationClient.start();
        if(!TextUtils.isEmpty(SPUtil.get(ConstantString.CITY)))
        tv_address.setText(SPUtil.get(ConstantString.CITY));
    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            double Latitude = location.getLatitude();    //获取街道信息
            double Longitude = location.getLongitude();    //获取街道信息
            ShowToastUtil.Short(addr+"|"+country+"|"+province+"|"+city+"|"+district+"|"+street);
            if(!TextUtils.isEmpty(city)){
                SPUtil.set(context,ConstantString.CITY,city);
                tv_address.setText(city);
//                tv_address.setText(addr+"|"+country+"|"+province+"|"+city+"|"+district+"|"+street+"|"+Latitude+"|"+Longitude);
            }
        }
    }
    boolean openTimer;
    public Timer mTimerWait;// 定时器
    private int viewpagerHeaderIndex = 0;
    @SuppressLint("HandlerLeak")
    public Handler mHandlerwait = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (viewpagerHeaderIndex > tipslist.size()) {
                viewpagerHeaderIndex = 0;
            }
            view_pager.setCurrentItem(viewpagerHeaderIndex);
            viewpagerHeaderIndex++;
            super.handleMessage(msg);
        }
    };

    public void timerTaskWait(int index) {
        viewpagerHeaderIndex = index;
        mTimerWait = new Timer();
        mTimerWait.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandlerwait.sendEmptyMessage(1);// 向Handler发送消息
            }
        }, 2000, 5000);// 定时任务
    }

    private void viewPageInit() {
        /**
         * 创建多个item （每一条viewPager都是一个item） 从服务器获取完数据（如文章标题、url地址） 后，再设置适配器
         */
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        if (tipslist!=null)
            tipslist.clear();
        for (int i=0;i<3;i++){
            TipsList tips=new TipsList();
            tips.imageUrl="";
            tips.id=i;
            tipslist.add(tips);
        }
//        tipslist.add()
//        try {
//            tipslist = ParseJson.parseGetResultCollection(new JSONObject(json), "data", TipsList.class);
        if(tipslist.size()==0){
//                lv_community_main.removeHeaderView(view_head_sublist);
        }else {
//                lv_community_main.removeHeaderView(view_head_sublist);
//                lv_community_main.addHeaderView(view_head_sublist);
            try {
                for (int i = 0; i < tipslist.size(); i++) {
//                list1.add(inflater.inflate(R.layout.community_item_recommend_head, null));
                    if (tipslist.size() > 1) {
                        ll_zsq.setVisibility(View.VISIBLE);
                        View iv = new View(context);
                        if (i == 0) {
                            iv.setBackgroundResource(R.mipmap.icon_select_yes);
                        } else {
                            iv.setBackgroundResource(R.mipmap.icon_select_no);
                        }
                        iv.measure(width, height);
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                                iv.getMeasuredWidth(), iv.getMeasuredHeight());
                        ll.leftMargin = 10;
                        ll.rightMargin = 10;
                        iv.setLayoutParams(ll);
                        ll_zsq.addView(iv);
                    } else {
                        ll_zsq.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            // 创建适配器， 把组装完的组件传递进去
            if (tipsAdapter == null) {
                tipsAdapter = new TipsViewPagerAdapter(context,tipslist,R.layout.community_item_recommend_head);
                tipsAdapter.setData(tipslist);
                view_pager.setAdapter(tipsAdapter);
                view_pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        for (int i = 0; i < ll_zsq.getChildCount(); i++) {
                            if (ll_zsq.getChildAt(i) != null) {
                                ll_zsq.getChildAt(i).setBackgroundResource(
                                        R.mipmap.icon_select_no);
                            }
                        }
                        ll_zsq.getChildAt(position).setBackgroundResource(
                                R.mipmap.icon_select_yes);
                        viewpagerHeaderIndex = position;
                    }
                });
            } else {
                tipsAdapter.setData(tipslist);
                tipsAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(tipsAdapter != null){
            tipsAdapter.notifyDataSetChanged();
            if (!openTimer) {
                timerTaskWait(0);
                openTimer = true;
            }
        }
    }

    private void initUi() {
        ll_quotation=(LinearLayout) findViewById(R.id.ll_quotation);
        ll_business=(LinearLayout) findViewById(R.id.ll_business);
        ll_labour_services=(LinearLayout) findViewById(R.id.ll_labour_services);
        ll_information_consultation=(LinearLayout) findViewById(R.id.ll_information_consultation);
        ll_forum=(LinearLayout) findViewById(R.id.ll_forum);
        ll_other=(LinearLayout) findViewById(R.id.ll_other);
        ll_company=(LinearLayout) findViewById(R.id.ll_company);
        ll_agent=(LinearLayout) findViewById(R.id.ll_agent);
        ll_customer_service=(LinearLayout) findViewById(R.id.ll_customer_service);
        ll_zsq=(LinearLayout) findViewById(R.id.ll_zsq);
        iv_right=(ImageView) findViewById(R.id.iv_right);
        tv_count_num=(TextView) findViewById(R.id.tv_count_num);
        tv_address=(TextView) findViewById(R.id.tv_address);
        view_pager=(ViewPager) findViewById(R.id.view_pager);

    }
    private void registerLin() {
        ll_quotation.setOnClickListener(this);
        ll_business.setOnClickListener(this);
        ll_labour_services.setOnClickListener(this);
        ll_information_consultation.setOnClickListener(this);
        ll_forum.setOnClickListener(this);
        ll_other.setOnClickListener(this);
        ll_company.setOnClickListener(this);
        ll_agent.setOnClickListener(this);
        ll_customer_service.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_address.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessageCount();
    }

    private void getMessageCount() {

        AuctionModule.getInstance().getMessageCount(context, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        int count=jsonObject.getIntValue("data");
                        if(count>0){
                            tv_count_num.setText(jsonObject.getIntValue("data")+"");
                            tv_count_num.setVisibility(View.VISIBLE);

                        }else{
                            tv_count_num.setVisibility(View.GONE);
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
            case R.id.ll_quotation:
                Intent intent1 = new Intent();
                ConstantString.index_tab = 1;
                intent1.setAction(ConstantString.MINE_LOGIN_ACTION);
                context.sendBroadcast(intent1);
                break;
            case R.id.ll_business:
                Intent intent2 = new Intent();
                ConstantString.index_tab = 2;
                intent2.setAction(ConstantString.MINE_LOGIN_ACTION);
                context.sendBroadcast(intent2);
                break;
            case R.id.ll_labour_services:
                Intent  intent3 = new Intent().setClass(this, LabourServicesActivity.class);//ThemeFeedActivity
                startActivity(intent3);
                break;
            case R.id.ll_information_consultation:
                Intent intent4=new Intent(this,InformationConsultationActivity.class);
                startActivity(intent4);
                break;
            case R.id.ll_forum:
                Intent intent5=new Intent(this,ForumActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_other:
                Intent intent6=new Intent(this,AudioBroadcastActivity.class);
                startActivity(intent6);
                break;
            case R.id.ll_company:
                Intent intent7=new Intent(this,CompanyEnterpriseActivity.class);
                startActivity(intent7);
                break;
            case R.id.ll_agent:
                Intent intent8=new Intent(this,AgentActivity.class);
                startActivity(intent8);
                break;
            case R.id.ll_customer_service:
                Intent intent9=new Intent(this,CustomerServiceActivity.class);
                startActivity(intent9);
                break;
            case R.id.iv_right:
                Intent intent10=new Intent(this,MessageListActivity.class);
                startActivity(intent10);
                break;
            case R.id.tv_address:
                mLocationClient.stop();
                mLocationClient.start();
                tv_address.setText("定位中");
                break;
            default:
                break;

        }

    }
}
