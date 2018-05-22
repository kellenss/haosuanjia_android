package suanhang.jinan.com.suannihen.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.ui.base.BaseFragmentPagerAdapter;
import suanhang.jinan.com.suannihen.ui.framgment.BussinessOneFragment;
import suanhang.jinan.com.suannihen.ui.framgment.BussinessTwoFragment;
import suanhang.jinan.com.suannihen.ui.framgment.LabourServicesOneFragment;
import suanhang.jinan.com.suannihen.ui.framgment.LabourServicesTwoFragment;

/**
 *劳务供需
 */
public class LabourServicesActivity extends StatisticsActivity implements  View.OnClickListener {
    private TextView tv_recommend;
    private ImageView iv_recommend;
    private TextView tv_attention;
    private ImageView iv_attention;
    public ViewPager view_pager;
    private FragmentManager mFragMgr;
    private ArrayList<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_services);
        tv_recommend = (TextView) findViewById(R.id.tv_recommend);
        iv_recommend = (ImageView) findViewById(R.id.iv_recommend);
        tv_attention = (TextView) findViewById(R.id.tv_attention);
        iv_attention = (ImageView) findViewById(R.id.iv_attention);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        mFragMgr = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        fragmentList.add(LabourServicesOneFragment.newInstance("0",""));
//        if (!TextUtils.isEmpty(SPUtil.get(Constants.USERID))) {
        fragmentList.add(LabourServicesTwoFragment.newInstance("1",""));
//        }
        findViewById(R.id.ll_recommend).setOnClickListener(this);
        findViewById(R.id.ll_attention).setOnClickListener(this);
        findViewById(R.id.iv_right_img).setOnClickListener(this);
        initViewPager();
    }
    private void initViewPager() {
        // 给ViewPager设置适配器
        view_pager
                .setAdapter(new BaseFragmentPagerAdapter<>(mFragMgr, fragmentList));
        view_pager.setCurrentItem(0);// 设置当前显示标签页为第一页
        view_pager.addOnPageChangeListener(new MyOnPageChangeListener());// 页面变化时的监听器
    }
    public class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        // private int one = offset *2 +bmpW;//两个相邻页面的偏移量

        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == 0) {
                tv_attention.setTextColor(ContextCompat.getColor(context, R.color.black));
                tv_recommend.setTextColor(ContextCompat.getColor(context, R.color.white));
                iv_attention.setVisibility(View.INVISIBLE);
                iv_recommend.setVisibility(View.VISIBLE);

            } else {
                tv_attention.setTextColor(ContextCompat.getColor(context, R.color.white));
                tv_recommend.setTextColor(ContextCompat.getColor(context, R.color.black));
                iv_recommend.setVisibility(View.INVISIBLE);
                iv_attention.setVisibility(View.VISIBLE);

            }
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_recommend) {
            tv_attention.setTextColor(ContextCompat.getColor(context, R.color.black));
            tv_recommend.setTextColor(ContextCompat.getColor(context, R.color.white));
            iv_attention.setVisibility(View.INVISIBLE);
            iv_recommend.setVisibility(View.VISIBLE);
            view_pager.setCurrentItem(0);
        } else if (i == R.id.ll_attention) {
//            if(!ARouterUtils.IsLogin()) return;
            tv_attention.setTextColor(ContextCompat.getColor(context, R.color.white));
            tv_recommend.setTextColor(ContextCompat.getColor(context, R.color.black));
            iv_recommend.setVisibility(View.INVISIBLE);
            iv_attention.setVisibility(View.VISIBLE);
            view_pager.setCurrentItem(1);
        }/*else if (i == R.id.iv_right_img) {//                String userId2 = SPUtil.get(ConstantString.USERID);
            Intent intent=new Intent(context,FabuSelectActivity.class);
            startActivity(intent);
        }*/ else if (i == R.id.iv_right_img){
//            showPublishChoosePopup();
        } else {
        }
    }

//    private void showPublishChoosePopup() {
////        if(!ARouterUtils.IsLogin()) return;
//        SPUtil.set("activityId", "");// 设置默认活动id为空
//        SPUtil.set("activityName", "");// 设置默认活动name为空
//        SPUtil.set("topicId", "");// 设置默认话题id为空
//        SPUtil.set("topicName", "");// 设置默认话题name为空
//        Intent intent = new Intent(CommunityActivity.this, PublishChooseActivity.class);
//        startActivity(intent);
//    }

//    public void backgroundAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().setAttributes(lp);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        //userID不为空，并且fragmentList不包含AttentionFragment
//        if (!TextUtils.isEmpty(SPUtil.get(Constants.USERID)) && fragmentList.size() == 1) {
//            fragmentList.add(AttentionFragment.newInstance("1",""));
//            initViewPager();
//        } else if (TextUtils.isEmpty(SPUtil.get(Constants.USERID)) && fragmentList.size() == 2) {
//            fragmentList.remove(1);
//            initViewPager();
//            tv_attention.setTextColor(ContextCompat.getColor(context, R.color.color_orange_main));
//            tv_recommend.setTextColor(ContextCompat.getColor(context, R.color.black));
//            iv_attention.setVisibility(View.INVISIBLE);
//            iv_recommend.setVisibility(View.VISIBLE);
//        }
    }
}
