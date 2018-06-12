package suanhang.jinan.com.suannihen.ui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.utils.Constant;
import suanhang.jinan.com.suannihen.utils.ConstantString;

public class MainActivity extends TabActivity  {
    private TabHost tabHost;
    private RadioButton main_tab_main, main_tab_quotation, main_tab_business,  main_tab_mine;// main_tab_forum,
    RadioGroup radioGroup;

    private Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        init();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        registBoscast();
        initMainInfo();
    }

    private void registBoscast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION.ACTION_CHANGE_TAB);
        filter.addAction(ConstantString.MINE_LOGIN_ACTION);
        registerReceiver(commonReceiver, filter);
    }
    /**
     * 统一注册广播
     */
    private BroadcastReceiver commonReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constant.ACTION.ACTION_CHANGE_TAB))
                changeTabsReceiver(ConstantString.index_tab);
            else if(intent.getAction().equals(ConstantString.MINE_LOGIN_ACTION))
                changeTabsReceiver(ConstantString.index_tab);
//            else if(intent.getAction().equals(Constants_umeng.CANCAL))
//                cancalLoginReceiver();
//            else if(intent.getAction().equals(Constants_umeng.CLEAR_PAIMAI_MSG_ACTION))
//                clearpaimaiReceiver();
//            else if(intent.getAction().equals(Constants_umeng.MINE_LOGIN_ACTION))
//                changeTabsReceiver(BaokuStatic.index_tab);
        }
    };
    private void init() {

        main_tab_main = (RadioButton) findViewById(R.id.main_tab_main);
        main_tab_quotation = (RadioButton) findViewById(R.id.main_tab_quotation);
        main_tab_business = (RadioButton) findViewById(R.id.main_tab_business);
//        main_tab_forum = (RadioButton) findViewById(R.id.main_tab_forum);
        main_tab_mine = (RadioButton) findViewById(R.id.main_tab_mine);

    }
    private void initMainInfo() {
        try{
//            if (!TextUtils.isEmpty(BaokuStatic.extraFromsplsh)) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        SkipUrlUtil.toNextByBaoku(SuperMainActivity.this,
//                                BaokuStatic.extraFromsplsh);
//                        BaokuStatic.extraFromsplsh = null;
//                    }
//                }, 200);
//            }
//            registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
//            registerReceiver(mybroadcast,
//                    new IntentFilter(Intent.ACTION_SCREEN_OFF));
//            registerReceiver(mybroadcast, new IntentFilter(
//                    Intent.ACTION_USER_PRESENT));

            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // setTranslucentStatus(true);
            // }

            // SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // tintManager.setStatusBarTintEnabled(true);
            // tintManager.setStatusBarTintResource(R.color.gray_head);

            tabHost = this.getTabHost();
            TabHost.TabSpec spec;
            Intent intent;

            intent = new Intent().setClass(this, HomeActivity.class);
            spec = tabHost.newTabSpec("首页").setIndicator("首页").setContent(intent);
            tabHost.addTab(spec);

//        intent = new Intent().setClass(this, ThemeFeedWebviewActivity.class);//ThemeFeedActivity
            intent = new Intent().setClass(this, QuotationActivity.class);//ThemeFeedActivity
            spec = tabHost.newTabSpec("大蒜行情").setIndicator("大蒜行情").setContent(intent);
            tabHost.addTab(spec);

            intent = new Intent().setClass(this, BusinessActivity.class);//ThemeFeedActivity
            spec = tabHost.newTabSpec("买卖需求").setIndicator("买卖需求").setContent(intent);
            tabHost.addTab(spec);

//            intent = new Intent().setClass(this, ForumActivity.class);
//            spec = tabHost.newTabSpec("论坛").setIndicator("论坛").setContent(intent);
//            tabHost.addTab(spec);

            intent = new Intent().setClass(this, MineActivity.class);
            spec = tabHost.newTabSpec("我的").setIndicator("我的").setContent(intent);
            tabHost.addTab(spec);
            tabHost.setCurrentTab(0);

            radioGroup = (RadioGroup) this.findViewById(R.id.main_tab_group);
            radioGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.main_tab_main:// 首页
                            changeTabsReceiver(0);
                            break;
                        case R.id.main_tab_quotation:// 行情
                            changeTabsReceiver(1);
                            break;
                        case R.id.main_tab_business:// 买卖
                            changeTabsReceiver(2);
                            break;
//                        case R.id.main_tab_forum:// 论坛
//                            changeTabsReceiver(3);
////                            main_tab_exhibition_message.setVisibility(View.GONE);
//                            break;
                        case R.id.main_tab_mine:// 我的
                            changeTabsReceiver(3);
                            break;
                    }
                }
            });
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.main_tab_main://首页
                            changeTabsReceiver(0);
                            break;
                        case R.id.main_tab_quotation:// 行情
                            changeTabsReceiver(1);
                            break;
                        case R.id.main_tab_business:// 买卖
                            changeTabsReceiver(2);
//                            main_tab_exhibition_message.setVisibility(View.GONE);
                            break;
//                        case R.id.main_tab_forum:// 论坛
//                            changeTabsReceiver(3);
//                            main_tab_exhibition_message.setVisibility(View.GONE);
//                            break;
                        case R.id.main_tab_mine:// 我的
//                            if (TextUtils.isEmpty(SPUtil.get(ConstantString.USERID))) {
//                                Constants.ACTIONID = 1;
//                                Intent intent = new Intent(SuperMainActivity.this, LoginWindActivity.class);
//                                intent.putExtra("isMineActivity", true);
//                                startActivity(intent);
//                            } else {
                                changeTabsReceiver(3);
//                            }
                            break;
                        default:
                            changeTabsReceiver(0);
                            break;
                    }

                }
            });
//            requestUpdate();
//            String apkName = "/patch_signed_7zip.apk1";
//            String apkPath = FileUtils.getFilesDir("download") + apkName;
//            String apkTempPath = apkPath.substring(0, apkPath.lastIndexOf('.')) + "_temp";
////            ShowToastUtil.Short(apkPath);
//
//            File apkFile = new File(apkPath);
//            if (apkFile.exists()){
//                ShowToastUtil.Short("receiveUpgradePatch");
//                BaokuStatic.receiveUpgradePatch(apkPath);
////                return;
//            }

//从通知消息点击进入相关页面
//            String mMessage = getIntent().getStringExtra("mMessage");
//            if (!TextUtils.isEmpty(mMessage)) {
//                SkipUrlUtil.toNextByBaoku(context, mMessage);
//            }
            //立即登录共一处修改
//            if(BaokuStatic.isMustLogin) {
//                String isopenFromSplash = getIntent().getStringExtra("isopenFromSplash");
//                if (!TextUtils.isEmpty(isopenFromSplash) && isopenFromSplash.equals("1")) {
//                    if (TextUtils.isEmpty(SPUtil.get(context,
//                            ConstantString.USERID))) {
//                        Intent intent1 = new Intent(context, LoginWindActivity.class);
//                        intent1.putExtra("openinSuperMain", "1");
//                        Constants.ACTIONID = 1;
//                        context.startActivity(intent1);
//                    }
//                }
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void changeTabsReceiver(int tabIndex) {
        try {
//            if(tabIndex == 3 && TextUtils.isEmpty(SPUtil.get(ConstantString.USERID)))
//                tabIndex = 0;

//            BaokuStatic.index_tab = tabIndex;
            textColor();
            int selectColor = ContextCompat.getColor(context, R.color.color_main);
            tabHost.setCurrentTab(tabIndex);
            switch (tabIndex) {
                case 0://首页
                    main_tab_main.setChecked(true);
                    main_tab_main.setTextColor(selectColor);
                    break;
                case 1://大蒜行情
                    main_tab_quotation.setChecked(true);
                    main_tab_quotation.setTextColor(selectColor);
                    break;
                case 2://买卖
                    main_tab_business.setChecked(true);
                    main_tab_business.setTextColor(selectColor);
                    break;
//                case 3://论坛
//                    main_tab_forum.setChecked(true);
//                    main_tab_forum.setTextColor(selectColor);
//                    break;
                case 3://我的
                    main_tab_mine.setChecked(true);
                    main_tab_mine.setTextColor(selectColor);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void textColor() {
        int color = getResources().getColor(R.color.color_666666);
        main_tab_main.setTextColor(color);
        main_tab_quotation.setTextColor(color);
        main_tab_business.setTextColor(color);
//        main_tab_forum.setTextColor(color);
        main_tab_mine.setTextColor(color);
    }
}
