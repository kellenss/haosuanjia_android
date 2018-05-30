package suanhang.jinan.com.suannihen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.bean.BussinessFragmentBean;
import suanhang.jinan.com.suannihen.view.adapter.AdapterItem;
import suanhang.jinan.com.suannihen.view.adapter.CommonAdapter;
import suanhang.jinan.com.suannihen.view.listviewload.XListView;

/**
 * 公司企业
 * create by gc on 2018/05/30
 */
public class CompanyEnterpriseActivity extends StatisticsActivity implements  View.OnClickListener, XListView.IXListViewListener {
    private XListView lv_activity_main;
    List<BussinessFragmentBean> activityList; // 动态数组
    BussinessFragmentAdapter feedAdapter;
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;

    View view_head_sublist;
    LayoutInflater inflater;
    int Width;

    private LinearLayout ll_zx_company;
    private LinearLayout ll_tj_company;
    private LinearLayout ll_rm_company;
    private LinearLayout ll_lkzlj_company;
    private LinearLayout ll_xhsgj_company;
    private LinearLayout ll_xhckj_company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conmpany_enterprise);
        inits();
        initdata();
        inflater = LayoutInflater.from(context);
        Width = this.getWindowManager().getDefaultDisplay().getWidth();// 获取屏幕高度
        view_head_sublist = inflater.inflate(
                R.layout.head_conmpany_enterprise, null);
        ll_zx_company=view_head_sublist.findViewById(R.id.ll_zx_company);
        ll_tj_company=view_head_sublist.findViewById(R.id.ll_tj_company);
        ll_rm_company=view_head_sublist.findViewById(R.id.ll_rm_company);
        ll_lkzlj_company=view_head_sublist.findViewById(R.id.ll_lkzlj_company);
        ll_xhsgj_company=view_head_sublist.findViewById(R.id.ll_xhsgj_company);
        ll_xhckj_company=view_head_sublist.findViewById(R.id.ll_xhckj_company);
        ll_zx_company.setOnClickListener(this);
        ll_tj_company.setOnClickListener(this);
        ll_rm_company.setOnClickListener(this);
        ll_lkzlj_company.setOnClickListener(this);
        ll_xhsgj_company.setOnClickListener(this);
        ll_xhckj_company.setOnClickListener(this);
        lv_activity_main.removeHeaderView(view_head_sublist);
        lv_activity_main.addHeaderView(view_head_sublist);

    }

    private void initdata() {
        lv_activity_main.setPullLoadEnable(true);
        lv_activity_main.setXListViewListener((XListView.IXListViewListener) this);
//
        if (activityList!=null){
            activityList.clear();
        }
        activityList=new ArrayList<>();
        for (int i=0;i<10;i++){
            BussinessFragmentBean bean=new BussinessFragmentBean();
            bean.activityName=""+i;
            activityList.add(bean);
        }
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
    }

    private void inits() {
        lv_activity_main = (XListView) findViewById(R.id.lv_bussiness_main);
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("公司企业");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_right_head:
                break;
            case R.id.ll_zx_company:
            case R.id.ll_tj_company:
            case R.id.ll_rm_company:
            case R.id.ll_lkzlj_company:
            case R.id.ll_xhsgj_company:
            case R.id.ll_xhckj_company:
                Intent intent8=new Intent(this,CompanyItemActivity.class);
                intent8.putExtra("title_name","最新企业");
                startActivity(intent8);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    class BussinessFragmentAdapter extends CommonAdapter<BussinessFragmentBean> implements AbsListView.OnScrollListener {
        //        private String type;
        //屏幕宽
        int widthScreen;
        //下方多图的高的值取屏幕宽的1/3，
        int collectionImgSize;
        int itemSpace;

        BussinessFragmentAdapter(List<BussinessFragmentBean> data) {
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
        public AdapterItem<BussinessFragmentBean> getItemView(int itemViewType) {
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

    class ItemFeed extends AdapterItem<BussinessFragmentBean> implements View.OnClickListener {

        @Override
        public int getLayoutResId() {
            return R.layout.item_company_enterprise;
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
        public void onUpdateViews(final BussinessFragmentBean auctionBean, final int position) {
//            ((TextView)getView(R.id.tv_user_name)).setText(auctionBean.user.nick);

        }
    }
}
