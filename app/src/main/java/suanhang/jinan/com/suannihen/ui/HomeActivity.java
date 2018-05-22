package suanhang.jinan.com.suannihen.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.bean.TipsList;
import suanhang.jinan.com.suannihen.utils.ConstantString;
import suanhang.jinan.com.suannihen.view.TipsViewPagerAdapter;

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
private ViewPager view_pager;


    List<TipsList> tipslist; // 动态数组
    TipsViewPagerAdapter tipsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUi();
        registerLin();
        tipslist=new ArrayList<TipsList>();
        viewPageInit();
//        Toast.makeText(context,"首页",Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_quotation:
                Intent intents = new Intent();
                ConstantString.index_tab = 1;
                intents.setAction(ConstantString.MINE_LOGIN_ACTION);
                context.sendBroadcast(intents);
                break;
            case R.id.ll_business:
                Intent intent1 = new Intent();
                ConstantString.index_tab = 2;
                intent1.setAction(ConstantString.MINE_LOGIN_ACTION);
                context.sendBroadcast(intent1);
                break;
            case R.id.ll_labour_services:
                Intent  intent2 = new Intent().setClass(this, LabourServicesActivity.class);//ThemeFeedActivity
                startActivity(intent2);
                break;
            case R.id.ll_information_consultation:
            case R.id.ll_forum:
            case R.id.ll_other:
            case R.id.ll_company:
            case R.id.ll_agent:
            case R.id.ll_customer_service:
            Intent intent=new Intent(this,ForumActivity.class);
            startActivity(intent);
            break;
            default:
                break;

        }

    }
}
