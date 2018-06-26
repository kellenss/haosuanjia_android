package suanhang.jinan.com.suannihen.ui.framgment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.bean.BussinessFragmentBean;
import suanhang.jinan.com.suannihen.bean.LabourServicesBean;
import suanhang.jinan.com.suannihen.commons.LogX;
import suanhang.jinan.com.suannihen.dialog.CustomDialogEditText;
import suanhang.jinan.com.suannihen.request.BaseHandlerJsonObject;
import suanhang.jinan.com.suannihen.request.module.AuctionModule;
import suanhang.jinan.com.suannihen.ui.AddDemandActivity;
import suanhang.jinan.com.suannihen.ui.AddSupplyActivity;
import suanhang.jinan.com.suannihen.ui.base.BaseFragment;
import suanhang.jinan.com.suannihen.utils.ConstantString;
import suanhang.jinan.com.suannihen.utils.ParseJson;
import suanhang.jinan.com.suannihen.utils.SPUtil;
import suanhang.jinan.com.suannihen.utils.ShowToastUtil;
import suanhang.jinan.com.suannihen.view.adapter.AdapterItem;
import suanhang.jinan.com.suannihen.view.adapter.CommonAdapter;
import suanhang.jinan.com.suannihen.view.listviewload.XListView;


/**
 * Created by Administrator on 2018/5/9.
 * 劳务需求
 */

public class LabourServicesOneFragment extends BaseFragment implements View.OnClickListener, XListView.IXListViewListener {
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
    private int page;
//    String longitude="";
//    String latitude="";
//    TextView viewEmpty;
//    View v_default;
    public static LabourServicesOneFragment newInstance(String param1, String classId) {
        LabourServicesOneFragment fragment = new LabourServicesOneFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            classId = getArguments().getString(ARG_CLASSID);
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
                    Intent intent=new Intent(getActivity(), AddDemandActivity.class);
                    startActivity(intent);
//                }else{
//                    Intent intent=new Intent(getActivity(), AddSupplyActivity.class);
//                    startActivity(intent);
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
//                                     ShowToastUtil.toastShow(content);
                                        getDemandOffer(getModel().id+"", SPUtil.get(ConstantString.USERID),content);
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
                    public void setActivityText(String content) {
                        ShowToastUtil.toastShow(content);
                        getDemandComment(getModel().id+"", SPUtil.get(ConstantString.USERID),content);
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
//            getView(R.id.tv_user_name).setOnClickListener(this);

        }


        @Override
        public void onUpdateViews(final LabourServicesBean auctionBean, final int position) {
            ((TextView)getView(R.id.tv_title_price)).setText(auctionBean.unitName);
            ((TextView)getView(R.id.tv_name_phone)).setText("电话："+auctionBean.phone);
            ((TextView)getView(R.id.tv_work_time)).setText("工作时间："+auctionBean.startDate+" 下午"+auctionBean.endDate);
            ((TextView)getView(R.id.tv_address_text)).setText("工作描述： "+auctionBean.workContent);
            ((TextView)getView(R.id.tv_desc_text)).setText("招工：挖蒜工人"+auctionBean.amount+"人，工资"+auctionBean.price+"元/天");
            ((TextView)getView(R.id.tv_baojia)).setText("我要报价 ( "+auctionBean.comments_count+" )");
            ((TextView)getView(R.id.tv_desc_text)).setVisibility(View.VISIBLE);
        }
    }

    private void initData(final boolean needclear) {
        AuctionModule.getInstance().getDemandList(context, page,new BaseHandlerJsonObject() {
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
                    activityList = feedAdapter.getDataList();
                    lv_activity_main.stopRefresh();
                    lv_activity_main.stopLoadMore();
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
    private void getDemandComment(String demand_id,String user_id,String content) {
        AuctionModule.getInstance().getAddDemandComment(context,demand_id,user_id,content, new BaseHandlerJsonObject() {
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
    private void getDemandOffer(String demand_id,String user_id,String price) {
//        longitude=SPUtil.get("longitude");
//        latitude=SPUtil.get("latitude");
//        cityId= SPUtil.get("cityId");
        AuctionModule.getInstance().getAddDemandOffer(context, demand_id,user_id,price,new BaseHandlerJsonObject() {
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

//						dialogtools.dismissDialog();
                    }
                    ShowToastUtil.Short(jsonObject.getString("msg"));
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
