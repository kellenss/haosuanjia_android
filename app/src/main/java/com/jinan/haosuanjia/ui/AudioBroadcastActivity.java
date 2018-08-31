package com.jinan.haosuanjia.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.AgentListBean;
import com.jinan.haosuanjia.bean.AudioBroadCastBean;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.utils.BitmapUtil;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.view.MusicController;
import com.jinan.haosuanjia.view.MusicPlayer;
import com.jinan.haosuanjia.view.adapter.AdapterItem;
import com.jinan.haosuanjia.view.adapter.CommonAdapter;
import com.jinan.haosuanjia.view.listviewload.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * y音频播报
 * create by gc on 2018/06/12
 */
public class AudioBroadcastActivity extends StatisticsActivity implements  View.OnClickListener, XListView.IXListViewListener {
    private XListView lv_activity_main;
    List<AudioBroadCastBean> activityList; // 动态数组
    BussinessFragmentAdapter feedAdapter;
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;

    View view_head_sublist;
    LayoutInflater inflater;
    int Width;
    MusicPlayer musicPlayer;

    int page=1;
    String zone ="1729";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        initUI();
        initdata();
        musicPlayer=new MusicPlayer(context);
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
            AudioBroadCastBean bean=new AudioBroadCastBean();
            bean.user_nickname=""+i;
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
        tv_title_head.setText("新闻播报");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }
    private void initDataPost(final boolean needclear) {

        AuctionModule.getInstance().getAudioBroadcastList(context,zone,page, new BaseHandlerJsonObject() {
            @Override
            public void onGotJson(JSONObject result) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                    if(jsonObject.getInteger("status")==1){
                        page++;
                        try{
                             activityList = ParseJson.parseGetResultCollection(result.getJSONObject("data"), "data", AudioBroadCastBean.class);
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
    @Override
    public void onClick(View v) {
        String titleNama="";
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

    class BussinessFragmentAdapter extends CommonAdapter<AudioBroadCastBean> implements AbsListView.OnScrollListener {
        //        private String type;
        //屏幕宽
        int widthScreen;
        //下方多图的高的值取屏幕宽的1/3，
        int collectionImgSize;
        int itemSpace;

        BussinessFragmentAdapter(List<AudioBroadCastBean> data) {
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
        public AdapterItem<AudioBroadCastBean> getItemView(int itemViewType) {
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

    class ItemFeed extends AdapterItem<AudioBroadCastBean> implements View.OnClickListener {

        @Override
        public int getLayoutResId() {
            return R.layout.audio_broadcast_list_item;
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
            getView(R.id.iv_user_photo).setOnClickListener(this);

        }


        @Override
        public void onUpdateViews(final AudioBroadCastBean auctionBean, final int position) {
            ((TextView)getView(R.id.tv_name_phone_num)).setText(auctionBean.title);
            if (!TextUtils.isEmpty(auctionBean.content)){
                String htmlData = auctionBean.content;
                htmlData = htmlData.replaceAll("&amp;", "")
                        .replaceAll("&quot;", "\"")
                        .replaceAll("&lt;", "<")
                        .replaceAll("&gt;", ">");
                ((TextView)getView(R.id.tv_qq_num)).setText(Html.fromHtml(htmlData));
            }else {
                ((TextView)getView(R.id.tv_qq_num)).setText("");
            }
//            if (!TextUtils.isEmpty(auctionBean.address_name)) {
//                ((TextView) getView(R.id.rv_address)).setText("地区 ：" + auctionBean.address_name);
//            }else{
//                ((TextView) getView(R.id.rv_address)).setText("地区 ：" );
//            }
            final MusicPlayer musicPlayer = new MusicPlayer(context,  (MusicController)getView(R.id.music_controller));
//            musicPlayer=new MusicPlayer(context,)
              musicPlayer.setVideoPath(HMApplication.KP_BASE_URL_FILE +auctionBean.music_url);
            getView(R.id.iv_user_photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(musicPlayer!=null){
                        musicPlayer.pause();
                    }else{
                        musicPlayer.start();
                    }

                }
            });
//            musicPlayer.
//            BitmapUtil.loadImageUrl(((ImageView) getView(R.id.iv_user_photo)), R.drawable.ic_launcher_background, HMApplication.KP_BASE_URL_YU + auctionBean.agent_avatar);
//            ((WebView)getView(R.id.wv_shoucang)).l("公司简介："+auctionBean.title+"  公司地址："+ Html.fromHtml(auctionBean.content));
//            ((WebView)getView(R.id.wv_shoucang)).loadDataWithBaseURL(null, auctionBean.content, "text/html", "UTF-8", null);
        }
    }
    private void onLoad() {
        if (lv_activity_main != null) {
            lv_activity_main.stopRefresh();
            lv_activity_main.stopLoadMore();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (musicPlayer!=null)
        musicPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (musicPlayer!=null&&musicPlayer.isPlaying())
        musicPlayer.resume();
    }

    @Override
    protected void onDestroy() {
        if (musicPlayer!=null)
        musicPlayer.stopPlayback();
        super.onDestroy();
//        if(musicPlayer!=null){
//        MusicPlayer.stopPlayback();

//            musicPlayer.pause();
//            musicPlayer.closePlayer();
//            musicPlayer=null;
//        }
    }
}
