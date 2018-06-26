package com.jinan.haosuanjia.ui.framgment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.LabourServicesBean;
import com.jinan.haosuanjia.commons.LogX;
import com.jinan.haosuanjia.dialog.CustomDialogEditText;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.ui.AddSupplyActivity;
import com.jinan.haosuanjia.ui.base.BaseFragment;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.SPUtil;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.view.adapter.AdapterItem;
import com.jinan.haosuanjia.view.adapter.CommonAdapter;
import com.jinan.haosuanjia.view.listviewload.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/5/9.
 * 劳务输出
 */

public class LabourServicesTwoFragment extends BaseFragment implements View.OnClickListener, XListView.IXListViewListener {
    private XListView lv_activity_main;
    private ViewPager view_pager;
    private TextView tv_zt_more;
    private TextView tv_send_labour_services;
    List<LabourServicesBean> activityList; // 动态数组
    BussinessFragmentAdapter feedAdapter;
    //    private int next=0;
//    private int limit=20;
//    private String cityId="1";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_CLASSID = "classId";
    private String mParam1;
    private String classId;
    private int page=1;
    //    String longitude="";
//    String latitude="";
//    TextView viewEmpty;
//    View v_default;
    public static LabourServicesTwoFragment newInstance(String param1, String classId) {
        LabourServicesTwoFragment fragment = new LabourServicesTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_CLASSID, classId);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            classId = getArguments().getString(ARG_CLASSID);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_business_one_framgment, container, false);
        this.inflater = inflater;
        init(view);
//        v_default.setVisibility(View.VISIBLE);
//        viewEmpty.setVisibility(View.VISIBLE);
//        viewEmpty.setText(getString(R.string.no_content_activity));
        return view;
    }
    private void init(View view) {
        context=getActivity();
        inflater = LayoutInflater.from(context);
        lv_activity_main = (XListView) view.findViewById(R.id.lv_bussiness_main);
        tv_send_labour_services = (TextView) view.findViewById(R.id.tv_send_labour_services);
        tv_send_labour_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(mParam1.equals("0")){
//                    Intent intent=new Intent(getActivity(), AddDemandActivity.class);
//                    startActivity(intent);
//                }else{
                    Intent intent=new Intent(getActivity(), AddSupplyActivity.class);
                    startActivity(intent);
//                }
            }
        });
//
        lv_activity_main.setPullLoadEnable(true);
        lv_activity_main.setXListViewListener((XListView.IXListViewListener) this);
//
        if (activityList!=null){
            activityList.clear();
        }
        activityList=new ArrayList<>();
        for (int i=0;i<10;i++){
            LabourServicesBean bean=new LabourServicesBean();
            bean.startDate=""+i;
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

    @Override
    protected void initData() {
        initData(true);
//        onRefresh();
    }

    @Override
    public void onRefresh() {
        page=1;
        initData(true);
    }

//    @Override
//    public void onRefresh() {
//        next=0;
//        initData(true);
//    }

    class BussinessFragmentAdapter extends CommonAdapter<LabourServicesBean> implements AbsListView.OnScrollListener {
        //        private String type;
        //屏幕宽
        int widthScreen;
        //下方多图的高的值取屏幕宽的1/3，
        int collectionImgSize;
        int itemSpace;

        BussinessFragmentAdapter(List<LabourServicesBean> data) {
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
        public AdapterItem<LabourServicesBean> getItemView(int itemViewType) {
            AdapterItem item = null;
//            if (itemViewType == VIEWTYPE_VEDIO) {
//                item = new ItemVedio();
//            } else {
            item = new ItemPicture();
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

    class ItemPicture extends AdapterItem<LabourServicesBean> implements View.OnClickListener {

        @Override
        public int getLayoutResId() {
            return R.layout.labour_services_list_item;
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if(i==R.id.tv_baojia){
                new CustomDialogEditText.Builder(context, new CustomDialogEditText.Builder.PriorityListener() {
                    @Override
                    public void setActivityText(String content) {
                        getAddSupplyOffer(getModel().id+"", SPUtil.get(ConstantString.USERID),content);
                    }
                })
                        .setMessage("向他报价")
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        LogX.d("setPagePath",
                                                "" +
                                                        "报价");

                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }else if(i==R.id.tv_liuyan){
                new CustomDialogEditText.Builder(context, new CustomDialogEditText.Builder.PriorityListener() {
                    @Override
                    public void setActivityText(String content) {//弹框回调
                        getAddSupplyComment(getModel().id+"", SPUtil.get(ConstantString.USERID),content);
                    }
                })
                        .setMessage("给他留言")
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        LogX.d("setPagePath",
                                                "" +
                                                        "报价");

                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }

        }

        @Override
        public void onSetViews() {
            getView(R.id.tv_baojia).setOnClickListener(this);
            getView(R.id.tv_liuyan).setOnClickListener(this);
        }


        @Override
        public void onUpdateViews(final LabourServicesBean auctionBean, final int position) {
            ((TextView)getView(R.id.tv_title_price)).setText(auctionBean.workType);
            ((TextView)getView(R.id.tv_name_phone)).setText(auctionBean.workType+" | 家庭住址："+auctionBean.address);
            ((TextView)getView(R.id.tv_work_time)).setText("工作时间："+auctionBean.startDate+" 下午"+auctionBean.endDate);
            ((TextView)getView(R.id.tv_address_text)).setText("工作描述： "+auctionBean.workContent);
            ((TextView)getView(R.id.tv_baojia)).setText("我要报价 ( "+auctionBean.comments_count+" )");
            ((TextView)getView(R.id.tv_desc_text)).setVisibility(View.GONE);
        }
    }

    private void initData(final boolean needclear) {
        AuctionModule.getInstance().getSupplyList(context,page, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        page++;
                        try{
                            activityList = ParseJson.parseGetResultCollection(result.getJSONObject("data"), "data", LabourServicesBean.class);
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
    private void getAddSupplyComment(String demand_id,String user_id,String content) {

        AuctionModule.getInstance().getAddSupplyComment(context,demand_id,user_id,content, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                    }else{
                    }
                    ShowToastUtil.Short(jsonObject.getString("msg"));
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
    private void getAddSupplyOffer(String demand_id,String user_id,String price) {

        AuctionModule.getInstance().getAddSupplyOffer(context, demand_id,user_id,price,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                    }else{
                    }
                    ShowToastUtil.Short(jsonObject.getString("msg"));
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
    public void onClick(View view) {

    }
    @Override
    public void onLoadMore() {
        initData(false);
    }
    private void onLoad() {
        if (lv_activity_main != null) {
            lv_activity_main.stopRefresh();
            lv_activity_main.stopLoadMore();
        }
    }
}
