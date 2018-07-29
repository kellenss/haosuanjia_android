package com.jinan.haosuanjia.ui;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.jpushdemo.ExampleUtil;
import com.example.jpushdemo.LocalBroadcastManager;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.AppVersionDomain;
import com.jinan.haosuanjia.commons.LogX;
import com.jinan.haosuanjia.dialog.CustomDialog;
import com.jinan.haosuanjia.dialog.CustomDialogBack;
import com.jinan.haosuanjia.interfac.VolleyCallBack;
import com.jinan.haosuanjia.utils.Constant;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.FileUtils_new;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.NetworkUtils;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.ShowToastUtil;
import com.jinan.haosuanjia.utils.UrlUtils;
import com.jinan.haosuanjia.utils.VolleyUtils;
import com.jinan.haosuanjia.utils.okgo.OKhttpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends TabActivity  {
    private TabHost tabHost;
    private RadioButton main_tab_main, main_tab_quotation, main_tab_business,  main_tab_mine;// main_tab_forum,
    RadioGroup radioGroup;

    private NotificationManager nManager = null;
    private Notification mNotification = null;
    AppVersionDomain versionDomain;
    private final int NOTIFY_ID = 0;
    private boolean isDownloadFinished = true;
    private Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        init();
        registerMessageReceiver();  // used for receive msg
        initJpush();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        registBoscast();
        initMainInfo();
    }

    private void registBoscast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantString.updateAction);
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
            if(intent.getAction().equals(ConstantString.updateAction))
                if (!isDownloadFinished) {
                    Toast.makeText(context, "正在下载", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = intent.getExtras();
                    String url = UrlUtils.getBaseUrlYu()+bundle.getString("url");
                    String versionName = bundle.getString("versionName");
                    startDownloadService(url, versionName);
                }
            else if(intent.getAction().equals(Constant.ACTION.ACTION_CHANGE_TAB))
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
    /**
     * 开启线程请求“我的状态”的列表数据
     */
    private void requestUpdate() {

        Map<String, String> params = new HashMap<String, String>();

        VolleyUtils.sendPostMethod(UrlUtils.getAndroidVersiont(), params,
                volleyCallBack, true, this);
    }

    private void checkUPdate(String json) throws JSONException {
        try {
            // 成功
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            versionDomain=new AppVersionDomain();
            versionDomain = ParseJson.parseConvertResultObject(jsonObject.optJSONObject("data"),
                    AppVersionDomain.class);
            if (HMApplication.version_code<versionDomain.version_code) { // 0不需要更新，1需要更新
                new CustomDialog.Builder(context)
                        .setTitle(getString(R.string.version_refresh_message, versionDomain.version_code+""))
                        .setMessage(versionDomain.version_desc)
                        // .setMessage("宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦")
                        .setPositiveButton(R.string.version_refresh_rightnow,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if (TextUtils.isEmpty(versionDomain
                                                .url)) {
                                            ShowToastUtil.Short(getString(R.string.version_refresh_urlerror));
                                            return;
                                        }
                                        if (!NetworkUtils
                                                .isNetworkConnected(context)) {
                                            ShowToastUtil.Short(getString(R.string.network_broken));
                                            return;
                                        }
                                        if (NetworkUtils.isWifi(context)) {

                                            startDownloadService(
                                                    UrlUtils.getBaseUrlYu()+versionDomain.url,
                                                    versionDomain.version_name);
                                        } else {
                                            // TODO 测试
                                            showDownloadDialog(versionDomain);
                                        }
                                    }
                                }).setNegativeButton(R.string.version_refresh_keep, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                    if(!TextUtils.isEmpty(versionDomain.appPatchVersion)){
//                        BaokuStatic.startDownloadPatch(versionDomain.appPatchUrl, versionDomain.appPatchVersion);
//                    }
                    }
                }).show();
            }
//            else if (versionDomain.flag == 0) {
//                ShowToastUtil.Short("已是最新版本");
////            if(!TextUtils.isEmpty(versionDomain.appPatchVersion)){
////                BaokuStatic.startDownloadPatch(versionDomain.appPatchUrl, versionDomain.appPatchVersion);
////            }
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showDownloadDialog(final AppVersionDomain versionDomain) {
        new CustomDialogBack.Builder(context)
                .setMessage(R.string.download_message)
                .setPositiveButton(
                        R.string.download,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
//                                String chanleNum = JinChaoUtils.getChanelNom(context);
//                                if (BaokuStatic.isBelongList(chanleNum)) {
//                                    startDownloadService(BaokuStatic.baseStaticUrl+"/app/" + chanleNum + ".apk",
//                                            versionDomain.getAppVersion());
//                                } else {
                                startDownloadService(
                                        versionDomain.url,
                                        versionDomain.version_name);
//                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

//    private void startDownloadService(String url, String versionName) {
//        Intent intent = new Intent();
//        intent.setAction(ConstantString.updateAction);
//        Bundle bundle = new Bundle();
//        bundle.putString("url", url);
//        bundle.putString("versionName", versionName);
//        intent.putExtras(bundle);
//        sendBroadcast(intent);
//    }

    private void startDownloadService(String url, String versionName) {
        String apkName = url.substring(url.lastIndexOf('/') + 1,
                url.lastIndexOf('.'))
                + "_v" + versionName + ".apk";

        if (apkName.substring(apkName.lastIndexOf('.')).equals(".apk")) {
            startDownload(apkName, url);
        } else {
            Uri uri = Uri.parse(url);
            Intent uriIntent = new Intent(Intent.ACTION_VIEW, uri);
            uriIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(uriIntent);
        }

    }

    private void startDownload(String apkName, String url) {
        String apkPath = FileUtils_new.getFilesDir("download/") + apkName;
        String apkTempPath = apkPath.substring(0, apkPath.lastIndexOf('.')) + "_temp";

        final File apkFile = new File(apkPath);
        final File apkTempFile = new File(apkTempPath);
        if (apkFile.exists()) {
            installApk(apkFile);
        } else {
            isDownloadFinished = false;
            startDownload(apkTempPath, apkFile, apkTempFile, url);
        }
    }

    private void startDownload(final String apkTempPath, final File apkFile, final File apkTempFile, String url) {
        OKhttpUtil.initOkGo();
        OkGo.<File>get(url)//
                .tag(this)//
                .execute(new FileCallback(apkTempPath) {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        LogX.e("download", "onStart");
                        if (apkFile.exists()) {
                            LogX.e("download", "onStartExist");
                        } else {
                            LogX.e("download", "onStartNotExist");
                            initNotification();
                            Toast.makeText(context, "开始下载",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        LogX.e("download", "onSuccess");
                        if (mNotification != null) {
                            mNotification.contentView.setTextViewText(
                                    R.id.tvStatus, "app下载完成");
                            mNotification.contentView.setTextViewText(
                                    R.id.tvProgress,
                                    String.format("%s%%", 100));
                            mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                            nManager.notify(NOTIFY_ID, mNotification);
                            nManager.cancel(NOTIFY_ID);
                            apkTempFile.renameTo(apkFile);
                            installApk(apkFile);
                            ShowToastUtil.toastShow("下载完成: "
                                    + response.message());
                            isDownloadFinished = true;
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        LogX.e("download", "onFailure");
                        if (mNotification != null) {
                            mNotification.contentView.setTextViewText(
                                    R.id.tvStatus, "下载失败");
                            mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                            nManager.notify(NOTIFY_ID, mNotification);
                            nManager.cancel(NOTIFY_ID);
                            isDownloadFinished = true;
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        LogX.e("download", "onLoading");
                        if (mNotification == null) {
                            initNotification();
                        }
                        int pro = (int) (100 * progress.currentSize / progress.totalSize);
                        mNotification.contentView.setTextViewText(
                                R.id.tvProgress,
                                String.format("%s%%", pro));
                        mNotification.contentView.setProgressBar(
                                R.id.proBar, 100, pro, false);
                        nManager.notify(NOTIFY_ID, mNotification);
                    }
                });
    }

    private void initNotification() {
        mNotification = new Notification(R.mipmap.ic_launcher, "库拍正在下载...",
                System.currentTimeMillis());
        mNotification.contentView = new RemoteViews(getPackageName(),
                R.layout.notification_download);

        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        nManager.notify(NOTIFY_ID, mNotification);
    }

    /**
     * 安装apk
     */
    private void installApk(File apkFile) {
        if (!apkFile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, getPackageName() + ".provider", apkFile);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
        }
        startActivity(i);
    }
    VolleyCallBack volleyCallBack = new VolleyCallBack() {
        @Override
        public void success(String result, String method) {
            try {
                if (method.equals(UrlUtils.getAndroidVersiont())) {
                    checkUPdate(result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(String error, String method, int type) {

        }
    };
    private void init() {

        nManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
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
            requestUpdate();
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(commonReceiver);
            OkGo.getInstance().cancelTag(this);
            nManager.cancel(NOTIFY_ID);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void initJpush(){
        JPushInterface.init(getApplicationContext());
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
//        if (null != msgText) {
//            msgText.setText(msg);
//            msgText.setVisibility(View.VISIBLE);
//        }
    }
}
