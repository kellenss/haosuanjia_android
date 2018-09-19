package com.jinan.haosuanjia.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.ForumListBean;
import com.jinan.haosuanjia.commons.LogX;
import com.jinan.haosuanjia.dialog.CustomDialogEditText;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.HMApplication;
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
 * 论坛
 * create by gc on 2018/05/23
 */
public class ForumActivity extends StatisticsActivity implements  View.OnClickListener, XListView.IXListViewListener {
    private XListView lv_activity_main;
    List<ForumListBean> activityList; // 动态数组
    BussinessFragmentAdapter feedAdapter;
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        inits();
        initdata();
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
//            ForumListBean bean=new ForumListBean();
//            bean.user_nickname=""+i;
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
        lv_activity_main = (XListView) findViewById(R.id.lv_bussiness_main);
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        tv_title_head.setText("论坛");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.VISIBLE);
        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void initDataPost(final boolean needclear) {

        AuctionModule.getInstance().getCircleList(context,page, new BaseHandlerJsonObject() {
            @Override            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        page++;
                        try{
                            activityList = ParseJson.parseGetResultCollection(result.getJSONObject("data"), "data", ForumListBean.class);
                        }catch (Exception e){
                            e.printStackTrace();
                            ShowToastUtil.toastShow("没有更多数据");
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
    private void getAddComments(String circle_id,String user_id,String content) {

        AuctionModule.getInstance().getAddComments(context, circle_id ,user_id ,content ,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        feedAdapter.notifyDataSetChanged();
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
    private void getAddCircleCollection(String circle_id,String user_id,String status) {

        AuctionModule.getInstance().getAddCircleCollection(context, circle_id ,user_id ,status ,new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                    }else{
                    }
                    feedAdapter.notifyDataSetChanged();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_right_head:
                Intent intent=new Intent(ForumActivity.this,AddCircleActivity.class);
                startActivity(intent);
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

    class BussinessFragmentAdapter extends CommonAdapter<ForumListBean> implements AbsListView.OnScrollListener {
        //        private String type;
        //屏幕宽
        int widthScreen;
        //下方多图的高的值取屏幕宽的1/3，
        int collectionImgSize;
        int itemSpace;

        BussinessFragmentAdapter(List<ForumListBean> data) {
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
        public AdapterItem<ForumListBean> getItemView(int itemViewType) {
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
            return itemType;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    class ItemFeed extends AdapterItem<ForumListBean> implements View.OnClickListener {

        @Override
        public int getLayoutResId() {
            return R.layout.rorum_list_item;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_pinglun:
                    new CustomDialogEditText.Builder(context, new CustomDialogEditText.Builder.PriorityListener() {
                        @Override
                        public void setActivityText(String content) {
                            getAddComments(getModel().id+"", SPUtil.get(ConstantString.USERID),content);
//                            if(getModel().status==1){
                                getModel().comments=getModel().comments+1;
//                                getModel().status=2;
//                            }else{
//                                getModel().comments=getModel().comments-1;
//                                getModel().status=1;
//                            }

                        }
                    })
                            .setMessage("我要评论")
                            .setCancelable(false)
                            .setPositiveButton(R.string.confirm,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            LogX.d("setPagePath",
                                                    "" +
                                                            "评论");

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
//                    getAddComments();
                    break;
                case R.id.iv_shoucang_icon:
                case R.id.tv_shoucang:
                    getAddCircleCollection(getModel().id+"", SPUtil.get(ConstantString.USERID),"1");
                    if(getModel().is_collection==2){
                        getModel().collection=getModel().collection+1;
                        getModel().is_collection=1;
                    }else{
                        getModel().collection=getModel().collection-1;
                        getModel().is_collection=2;
                    }
//                    getModel().collection++;
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onSetViews() {
            getView(R.id.tv_pinglun).setOnClickListener(this);
            getView(R.id.tv_shoucang).setOnClickListener(this);
            getView(R.id.iv_shoucang_icon).setOnClickListener(this);

        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onUpdateViews(final ForumListBean auctionBean, final int position) {
            ((TextView)getView(R.id.tv_username)).setText(auctionBean.user_nickname);
            ((TextView)getView(R.id.tv_create_time)).setText(auctionBean.createtime);
            ((TextView)getView(R.id.tv_item_title)).setText(auctionBean.title);
            ((TextView)getView(R.id.tv_item_content)).setText(auctionBean.content);
            ((TextView)getView(R.id.tv_shoucang)).setText(auctionBean.collection+"");
            ((TextView)getView(R.id.tv_pinglun)).setText(auctionBean.comments+"");
//            ((ImageView)getView(R.id.iv_user_photo)).setText(auctionBean.collection+"");
//            String headPhotoUrl = ImageLoaderUtil.getPhotoUrl(auctionBean.bidInfo.bidGoods.supplier.supplierPic, 200);
            if (auctionBean.is_collection==1){
                (getView(R.id.iv_shoucang_icon)).setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_scccccccccc));
            }else{
                (getView(R.id.iv_shoucang_icon)).setBackground(getApplicationContext().getResources().getDrawable(R.mipmap.icon_shoucang));
            }
            BitmapUtil.loadImageUrl(((ImageView) getView(R.id.iv_user_photo)), R.mipmap.icon_my_head_img, HMApplication.KP_BASE_URL_YU+auctionBean.avatar);

            ((LinearLayout) getView(R.id.ll_mainline1)).removeAllViews();

            TextView tv_comments_username;
            TextView tv_comments_content;
            TextView tv_comments_time;
            for (int i = 0; i < auctionBean.comments_list.size(); i++) {
                final int num = i;
                View itemview1 = View.inflate(context, R.layout.list_comments_item, null);
                // 给item布局添加ID,此ID为int类型任意值
                ((LinearLayout) getView(R.id.ll_mainline1)).addView(itemview1);
                tv_comments_username = (TextView) itemview1
                        .findViewById(R.id.tv_comments_username);
                tv_comments_content = (TextView) itemview1
                        .findViewById(R.id.tv_comments_content);
                tv_comments_time = (TextView) itemview1
                        .findViewById(R.id.tv_comments_time);

                try {
                    tv_comments_username.setText(auctionBean.comments_list.get(num).user_nickname );
                    tv_comments_time.setText("  评论于 "+auctionBean.comments_list.get(num).createtime);
                    tv_comments_content.setText(auctionBean.comments_list.get(num).content);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                itemview1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                            changeOnce(auctionBean.themeInfo.bidsList.get(num).saleId + "","");
                    }
                });
            }

        }
    }
    private void onLoad() {
        if (lv_activity_main != null) {
            lv_activity_main.stopRefresh();
            lv_activity_main.stopLoadMore();
        }
    }
}
