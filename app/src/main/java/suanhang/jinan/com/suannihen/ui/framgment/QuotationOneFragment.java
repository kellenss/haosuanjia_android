package suanhang.jinan.com.suannihen.ui.framgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.ui.base.BaseFragment;


/**
 * 行情-大蒜价格
 * Created by Administrator on 2018/1/15.
 */

public class QuotationOneFragment extends BaseFragment implements View.OnClickListener {
//    private XListView lv_activity_main;
    private ViewPager view_pager;
    private TextView tv_zt_more;
//    List<ActivityListBean> activityList; // 动态数组
//    ActivityListAdapter feedAdapter;
//    private int next=0;
//    private int limit=20;
//    private String cityId="1";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_CLASSID = "classId";
    private String mParam1;
    private String classId;
//    String longitude="";
//    String latitude="";
//    TextView viewEmpty;
//    View v_default;
    public static QuotationOneFragment newInstance(String param1,String classId) {
        QuotationOneFragment fragment = new QuotationOneFragment();
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
        View view = inflater.inflate(R.layout.activity_home, container, false);
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
//        lv_activity_main = (XListView) view.findViewById(R.id.lv_activity_main);

//        lv_activity_main.setPullLoadEnable(true);
//        lv_activity_main.setXListViewListener((XListView.IXListViewListener) this);
//        activityList=new ArrayList<>();
//        viewEmpty = (TextView) view.findViewById(R.id.tv_discribe);
//        v_default = view.findViewById(R.id.v_default);
//        feedAdapter = new ActivityListAdapter(activityList,context,3,mParam1);;//type复用adapter传2为服务列表3活动
//        lv_activity_main.setAdapter(feedAdapter);
//        lv_activity_main.setPullLoadEnable(true);
//        lv_activity_main.setXListViewListener(this);


//        lv_activity_main.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            int firstVisibleItem, lastVisibleItem;
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
////                firstVisibleItem = lv_community_main.getFirstVisiblePosition();
////                lastVisibleItem = lv_community_main.getLastVisiblePosition();
//            }
//        });
    }

    @Override
    protected void initData() {
//        onRefresh();
    }
//    @Override
//    public void onRefresh() {
//        next=0;
//        initData(true);
//    }

    private void initData(boolean needclear) {
//        longitude=SPUtil.get("longitude");
//        latitude=SPUtil.get("latitude");
//        cityId= SPUtil.get("cityId");
//        NetWorkModule.getInstance().getCityActivityList(context,cityId,classId,longitude,latitude,next,limit,mParam1, new VolleyCallBack() {
//            @Override
//            public void success(String result, String method) {
//                List<ActivityListBean> activityEntities = null;
//                try {
//                    JSONObject jSONObject=new JSONObject(result).getJSONObject("data");
//                    activityEntities = ParseJson.parseGetResultCollection(jSONObject.getJSONObject("pagedData"), "data", ActivityListBean.class);
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
//
//            @Override
//            public void failure(String error, String method, int type) {
//                onLoad();
//            }
//        });
    }

    @Override
    public void onClick(View view) {

    }
//    @Override
//    public void onLoadMore() {
//        initData(false);
//    }
//    private void onLoad() {
//        if (lv_activity_main != null) {
//            lv_activity_main.stopRefresh();
//            lv_activity_main.stopLoadMore();
//        }
//    }
}
