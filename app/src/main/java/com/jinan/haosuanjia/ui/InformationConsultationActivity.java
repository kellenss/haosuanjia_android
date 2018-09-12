package com.jinan.haosuanjia.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.NewsInformationBean;
import com.jinan.haosuanjia.bean.TipsList;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.view.TipsViewPagerAdapter;
import com.jinan.haosuanjia.view.adapter.AdapterItem;
import com.jinan.haosuanjia.view.adapter.CommonAdapter;
import com.jinan.haosuanjia.view.listviewload.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 信息资讯
 * create by gc on 2018/06/27
 */
public class InformationConsultationActivity extends StatisticsActivity implements  View.OnClickListener, XListView.IXListViewListener {
    private XListView lv_activity_main;
    List<NewsInformationBean> activityList; // 动态数组
    BussinessFragmentAdapter feedAdapter;
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;

    View view_head_sublist;
    ViewPager view_pager;
    LinearLayout ll_zsq;
    LayoutInflater inflater;
    int Width;

    List<TipsList> tipslist; // 动态数组
    TipsViewPagerAdapter tipsAdapter;
    boolean openTimer;
    public Timer mTimerWait;// 定时器
    private int viewpagerHeaderIndex = 0;
    int page=1;
    String class_id ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conmpany_enterprise);
        initUI();
        initdata();
        inflater = LayoutInflater.from(context);
        Width = this.getWindowManager().getDefaultDisplay().getWidth();// 获取屏幕高度
        view_head_sublist = inflater.inflate(
                R.layout.head_informartation, null);
        view_pager= (ViewPager) view_head_sublist.findViewById(R.id.view_pager);
        ll_zsq= (LinearLayout) view_head_sublist.findViewById(R.id.ll_zsq);
        lv_activity_main.removeHeaderView(view_head_sublist);
        lv_activity_main.addHeaderView(view_head_sublist);
        tipslist= new ArrayList<>();
        viewPageInit();
    }
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
//        if (tipslist!=null)
//            tipslist.clear();
//        for (int i=0;i<3;i++){
//            TipsList tips=new TipsList();
//            tips.imageUrl="";
//            tips.id=i;
//            tipslist.add(tips);
//        }
//        tipslist.add()
//        try {
//            tipslist = ParseJson.parseGetResultCollection(new JSONObject(json), "data", TipsList.class);
        if(tipslist.size()==0){
            lv_activity_main.removeHeaderView(view_head_sublist);
        }else {
            lv_activity_main.removeHeaderView(view_head_sublist);
            lv_activity_main.addHeaderView(view_head_sublist);
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
    private void initdata() {
        lv_activity_main.setPullLoadEnable(true);
        lv_activity_main.setXListViewListener((XListView.IXListViewListener) this);
//
        if (activityList!=null){
            activityList.clear();
        }
        activityList=new ArrayList<>();
//        for (int i=0;i<10;i++){
//            NewsInformationBean bean=new NewsInformationBean();
//            bean.title=""+i;
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
//        lv_activity_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(context,InformationConsultationDetailActivity.class);
//                intent.putExtra("class_id",activityList.get(position).id);
//                startActivity(intent);
//            }
//        });
        initDataPost(true);
        getTips();
    }

    private void initUI() {
        lv_activity_main = (XListView) findViewById(R.id.lv_bussiness_main);
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("信息资讯");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void initDataPost(final boolean needclear) {

        AuctionModule.getInstance().getNewsInformationList(context,class_id,page, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        page++;
                        try{
                            activityList = ParseJson.parseGetResultCollection(result.getJSONObject("data"), "data", NewsInformationBean.class);
                        }catch (Exception e ){
                            e.printStackTrace();
                            ShowToastUtil.Short("没有更多数据！");
                        }
                        if (needclear) {
                            feedAdapter.updateData(activityList);
                        } else {
                            feedAdapter.addListData(activityList);
                        }
                    }else{
                        ShowToastUtil.Short(jsonObject.getString("msg"));
                    }
                    lv_activity_main.stopRefresh();
                    lv_activity_main.stopLoadMore();
                    activityList = feedAdapter.getDataList();
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
    private void getTips() {

        AuctionModule.getInstance().getBannerList(context, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
//                        page++;
                        try{
                            tipslist = ParseJson.parseGetResultCollection(result, "data", TipsList.class);
                            viewPageInit();
                        }catch (Exception e ){
                            e.printStackTrace();
                            ShowToastUtil.Short("没有更多数据！");
                        }

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
    @Override
    public void onClick(View v) {
        String titleNama="";
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_right_head:
                break;
            case R.id.ll_zx_company:
                titleNama="";
                Intent intent1=new Intent(this,CompanyItemActivity.class);
                intent1.putExtra("title_name","最新企业");
                intent1.putExtra("class_id","new_order");
                startActivity(intent1);
                break;
            case R.id.ll_tj_company:
                Intent intent2=new Intent(this,CompanyItemActivity.class);
                intent2.putExtra("title_name","推荐企业");
                intent2.putExtra("class_id","extension_order");
                startActivity(intent2);
                break;
            case R.id.ll_rm_company:
                Intent intent3=new Intent(this,CompanyItemActivity.class);
                intent3.putExtra("title_name","热门企业");
                intent3.putExtra("class_id","hot_order");
                startActivity(intent3);
                break;
            case R.id.ll_lkzlj_company:
                Intent intent4=new Intent(this,CompanyItemActivity.class);
                intent4.putExtra("title_name","冷库租赁价");
                startActivity(intent4);
                break;
//            case R.id.ll_xhsgj_company:
//                Intent intent5=new Intent(this,CompanyItemActivity.class);
//                intent5.putExtra("title_name","现货收购价");
//                startActivity(intent5);
//                break;
//            case R.id.ll_xhckj_company:
//                Intent intent6=new Intent(this,CompanyItemActivity.class);
//                intent6.putExtra("title_name","现货出库价");
//                startActivity(intent6);
//                break;
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

    class BussinessFragmentAdapter extends CommonAdapter<NewsInformationBean> implements AbsListView.OnScrollListener {
        //        private String type;
        //屏幕宽
        int widthScreen;
        //下方多图的高的值取屏幕宽的1/3，
        int collectionImgSize;
        int itemSpace;

        BussinessFragmentAdapter(List<NewsInformationBean> data) {
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
        public AdapterItem<NewsInformationBean> getItemView(int itemViewType) {
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

    class ItemFeed extends AdapterItem<NewsInformationBean> implements View.OnClickListener {

        @Override
        public int getLayoutResId() {
            return R.layout.item_information_consultation;
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
            getView(R.id.ll_item_parent).setOnClickListener(this);

        }


        @Override
        public void onUpdateViews(final NewsInformationBean auctionBean, final int position) {
            if (position==0){
                ((TextView)getView(R.id.tv_head_title)).setVisibility(View.VISIBLE);
            }else {
                ((TextView)getView(R.id.tv_head_title)).setVisibility(View.GONE);
            }
                String htmlData = auctionBean.content;

//                htmlData = htmlData.;
//                htmlData = htmlData;
//                htmlData = htmlData;
//            ((WebView)getView(R.id.wv_shoucang)).loadData(htmlData, "text/html" , "utf-8");
//            ((WebView)getView(R.id.wv_shoucang)).loadDataWithBaseURL(htmlData, null, "text/html", "utf-8", null);
//            ((TextView)getView(R.id.tv_shoucang)).setText(Html.fromHtml(htmlData));
            ((TextView)getView(R.id.tv_shoucang)).setText(auctionBean.title);
            getView(R.id.tv_shoucang).setVisibility(View.VISIBLE);
            getView(R.id.wv_shoucang).setVisibility(View.GONE);
//            if(!TextUtils.isEmpty(htmlData)){
//                htmlData = htmlData.replaceAll("&amp;", "")
//                        .replaceAll("&quot;", "\"")
//                        .replaceAll("&lt;", "<")
//                        .replaceAll("&gt;", ">");
//                ((WebView)getView(R.id.wv_shoucang)).loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
//            }

            BitmapUtil.loadImageUrl(((ImageView) getView(R.id.iv_commpany_logo)), R.mipmap.icon_commpany_zd, HMApplication.KP_BASE_URL_YU + auctionBean.cover);
//            Bitmap bitmap;
//            bitmap = BitmapUtil.convertViewToBitmap(getView(R.id.iv_commpany_logo));
            getView(R.id.ll_item_parent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,InformationConsultationDetailActivity.class);
                    intent.putExtra("class_id",auctionBean.id+"");
                    startActivity(intent);
                }
            });
        }
    }
    private void onLoad() {
        if (lv_activity_main != null) {
            lv_activity_main.stopRefresh();
            lv_activity_main.stopLoadMore();

        }
    }
}
