package suanhang.jinan.com.suannihen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.bean.BussinessFragmentBean;
import suanhang.jinan.com.suannihen.bean.CompanyEnterpriseBean;
import suanhang.jinan.com.suannihen.bean.LabourServicesBean;
import suanhang.jinan.com.suannihen.bean.NewsCompanyBean;
import suanhang.jinan.com.suannihen.request.BaseHandlerJsonObject;
import suanhang.jinan.com.suannihen.request.module.AuctionModule;
import suanhang.jinan.com.suannihen.utils.ParseJson;
import suanhang.jinan.com.suannihen.utils.ShowToastUtil;
import suanhang.jinan.com.suannihen.view.adapter.AdapterItem;
import suanhang.jinan.com.suannihen.view.adapter.CommonAdapter;
import suanhang.jinan.com.suannihen.view.listviewload.XListView;

/**
 * 公司企业
 * create by gc on 2018/05/30
 */
public class CompanyEnterpriseActivity extends StatisticsActivity implements  View.OnClickListener, XListView.IXListViewListener {
    private XListView lv_activity_main;
    List<NewsCompanyBean> activityList; // 动态数组
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
    int page=0;
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
            NewsCompanyBean bean=new NewsCompanyBean();
            bean.title=""+i;
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
        initDataPost(true);
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
        tv_title_head.setText("公司企业");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void initDataPost(final boolean needclear) {

        AuctionModule.getInstance().getNewsCommpanyList(context,class_id,page, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
//											Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
//													Toast.LENGTH_SHORT).show();
//						dialogtools.dismissDialog();
//                        ShowToastUtil.Short(jsonObject.getString("msg"));
//						finish();
                    }else{
//											Toast.makeText(ZhuCeActivity.this, jsonObject.getString("msg"),
//													Toast.LENGTH_SHORT).show();
                        ShowToastUtil.Short(jsonObject.getString("msg"));
//						dialogtools.dismissDialog();
                    }
                    activityList = ParseJson.parseGetResultCollection(result.getJSONObject("data"), "data", NewsCompanyBean.class);
                    if (needclear) {
                        lv_activity_main.stopRefresh();
                        feedAdapter.updateData(activityList);
                    } else {
                        feedAdapter.addListData(activityList);
                        lv_activity_main.stopLoadMore();
                    }

//                if (activityEntities.size() >= limit) {
//                    lv_activity_main.setPullLoadEnable(true);
//                } else {
//                    lv_activity_main.setPullLoadEnable(false);
//                }
                    activityList = feedAdapter.getDataList();
                    if(activityList.size()>0){
//                    v_default.setVisibility(View.GONE);
//                    viewEmpty.setVisibility(View.GONE);
                    }else{
//                    v_default.setVisibility(View.VISIBLE);
//                    viewEmpty.setVisibility(View.VISIBLE);
//                    viewEmpty.setText(getString(R.string.no_content_activity));
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    ShowToastUtil.Short("解析异常！");
//										Toast.makeText(ZhuCeActivity.this, "未知异常！", Toast.LENGTH_LONG).show();
//					dialogtools.dismissDialog();
                }
                onLoad();
            }

            @Override
            public void onGotError(String code, String error) {
                onLoad();
            }

//            @Override
//            public void success(String result, String method) {
////                List<ActivityListBean> activityEntities = null;
////                try {
//                    JSONObject jSONObject;
//                try {
//                    jSONObject = new JSONObject(result).getJSONObject("data");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                    activityEntities = ParseJson.parseGetResultCollection(jSONObject.getJSONObject("pagedData"), "data", LabourServicesBean.class);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (next == 0) {
//                    lv_activity_main.stopRefresh();
//                    feedAdapter.updateData(activityEntities);
//                } else {
//                    feedAdapter.addListData(activityEntities);
//                    lv_activity_main.stopLoadMore();
//                }
//
//                if (activityEntities.size() >= limit) {
//                    lv_activity_main.setPullLoadEnable(true);
//                } else {
//                    lv_activity_main.setPullLoadEnable(false);
//                }
//                activityList = feedAdapter.getDataList();
//                if(activityList.size()>0){
//                    v_default.setVisibility(View.GONE);
//                    viewEmpty.setVisibility(View.GONE);
//                }else{
//                    v_default.setVisibility(View.VISIBLE);
//                    viewEmpty.setVisibility(View.VISIBLE);
//                    viewEmpty.setText(getString(R.string.no_content_activity));
//                }
//                onLoad();
//            }

//            @Override
//            public void failure(String error, String method, int type) {
////                onLoad();
//            }
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
            case R.id.ll_xhsgj_company:
                Intent intent5=new Intent(this,CompanyItemActivity.class);
                intent5.putExtra("title_name","现货收购价");
                startActivity(intent5);
                break;
            case R.id.ll_xhckj_company:
                Intent intent6=new Intent(this,CompanyItemActivity.class);
                intent6.putExtra("title_name","现货出库价");
                startActivity(intent6);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
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
        public void onUpdateViews(final NewsCompanyBean auctionBean, final int position) {
            ((TextView)getView(R.id.tv_shoucang)).setText("公司简介："+auctionBean.title+"  公司地址："+ Html.fromHtml(auctionBean.content));
//            ((WebView)getView(R.id.wv_shoucang)).l("公司简介："+auctionBean.title+"  公司地址："+ Html.fromHtml(auctionBean.content));
            ((WebView)getView(R.id.wv_shoucang)).loadDataWithBaseURL(null, auctionBean.content, "text/html", "UTF-8", null);
        }
    }
    private void onLoad() {
        if (lv_activity_main != null) {
            lv_activity_main.stopRefresh();
            lv_activity_main.stopLoadMore();
        }
    }
}
