package com.jinan.haosuanjia.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.NewsCompanyBean;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.view.adapter.AdapterItem;
import com.jinan.haosuanjia.view.adapter.CommonAdapter;
import com.jinan.haosuanjia.view.listviewload.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业列表
 * create by gc on 2018/05/30
 */
public class CompanyItemActivity extends StatisticsActivity implements  View.OnClickListener, XListView.IXListViewListener {
    private XListView lv_activity_main;
    List<NewsCompanyBean> activityList; // 动态数组
    BussinessFragmentAdapter feedAdapter;
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private String  title_head="热门企业";
    private String class_id="";//1:热门，2：推荐，3：最新，4：重点
    private int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        inits();
        initdata();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

        }
    }

    private void initdata() {
        lv_activity_main.setPullLoadEnable(true);
        lv_activity_main.setXListViewListener((XListView.IXListViewListener) this);
//
        if (activityList!=null){
            activityList.clear();
        }
        activityList=new ArrayList<>();
//        for (int i=0;i<10;i++){
//            NewsCompanyBean bean=new NewsCompanyBean();
//            bean.company_name=""+i;
//            activityList.add(bean);
//        }
//        viewEmpty = (TextView) view.findViewById(R.id.tv_discribe);
//        v_default = view.findViewById(R.id.v_default);
        feedAdapter = new BussinessFragmentAdapter(activityList);;//type复用adapter传2为服务列表3活动
        lv_activity_main.setAdapter(feedAdapter);
        lv_activity_main.setPullLoadEnable(true);
        lv_activity_main.setXListViewListener(this);
//
//
        lv_activity_main.setOnScrollListener(new AbsListView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                firstVisibleItem = lv_community_main.getFirstVisiblePosition();
//                lastVisibleItem = lv_community_main.getLastVisiblePosition();
            }
        });
        initDataPost(true);
    }

    private void inits() {
        title_head=getIntent().getStringExtra("title_name");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("class_id"))){
            class_id=getIntent().getStringExtra("class_id");
        }

        lv_activity_main = (XListView) findViewById(R.id.lv_bussiness_main);
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText(title_head);
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void initDataPost(final boolean needclear) {

        AuctionModule.getInstance().getCommpanyList(context, class_id,page,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        page++;
                    }else{
                        ShowToastUtil.Short(jsonObject.getString("msg"));
                    }
                    activityList = ParseJson.parseGetResultCollection(result.getJSONObject("data"), "data", NewsCompanyBean.class);
                    if (needclear) {
                        lv_activity_main.stopRefresh();
                        feedAdapter.updateData(activityList);
                    } else {
                        feedAdapter.addListData(activityList);
                        lv_activity_main.stopLoadMore();
                    }

                    activityList = feedAdapter.getDataList();
                    if(activityList.size()>0){
                    }else{
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    ShowToastUtil.Short("解析异常！");
                }
                onLoad();
            }

            @Override
            public void onGotError(String code, String error) {
                onLoad();
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
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        page=1;
        initDataPost(true);

    }

    @Override
    public void onLoadMore() {
        initDataPost(false);
    }

    class BussinessFragmentAdapter extends CommonAdapter<NewsCompanyBean> implements AbsListView.OnScrollListener {
        //        private String type;
        //屏幕宽
        int widthScreen;
        //下方多图的高的值取屏幕宽的1/3，
        int collectionImgSize;
        int itemSpace;

        BussinessFragmentAdapter(List<NewsCompanyBean> data) {
            super(data, 10);
        }

        void setScreenWidth(int width, int space) {
            collectionImgSize = width / 3 - itemSpace/* - 2*space*/;
            this.widthScreen = width;
            itemSpace = space;
        }

        final int VIEWTYPE_PICTURE = 1;//图片
        final int VIEWTYPE_VEDIO = 2;//视频

        @Override
        public AdapterItem<NewsCompanyBean> getItemView(int itemViewType) {
            AdapterItem item = null;
//            if (itemViewType == VIEWTYPE_VEDIO) {
//                item = new ItemVedio();
//            } else {
            item = new ItemFeed();
//            }
            return item;
        }

        @Override
        public int getItemViewType(int position) {
            int itemType = 0;

//            if (attentionList_merge != null && position < attentionList_merge.size()) {
//                if (attentionList_merge.get(position).moment.type == 1) {
//                    itemType = VIEWTYPE_PICTURE;
//                } else {
//                    itemType = VIEWTYPE_VEDIO;
//                }
//            }
            return itemType;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    class ItemFeed extends AdapterItem<NewsCompanyBean> implements View.OnClickListener {

        @Override
        public int getLayoutResId() {
            return R.layout.company_list_item;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back_head:
                    finish();
                    break;
                case R.id.tv_right_head:
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onSetViews() {
//            getView(R.id.tv_user_name).setOnClickListener(this);

        }


        @Override
        public void onUpdateViews(final NewsCompanyBean auctionBean, final int position) {
            ((TextView)getView(R.id.tv_commpany_name)).setText(auctionBean.company_name);
            ((TextView)getView(R.id.tv_zczb_money)).setText("注册资金："+auctionBean.reg_amount);
            ((TextView)getView(R.id.tv_about_desc)).setText("成立日期："+auctionBean.company_createdate);
            ((TextView)getView(R.id.tv_username)).setText("联系人："+auctionBean.contact);
            ((TextView)getView(R.id.tv_commpany_address)).setText("详细地址："+auctionBean.address);
            BitmapUtil.loadImageUrl(((ImageView) getView(R.id.iv_commpany_logo)), R.mipmap.icon_commpany_zd, HMApplication.KP_BASE_URL_YU + auctionBean.banner);
        }
    }
    private void onLoad() {
        if (lv_activity_main != null) {
            lv_activity_main.stopRefresh();
            lv_activity_main.stopLoadMore();
        }
    }
}
