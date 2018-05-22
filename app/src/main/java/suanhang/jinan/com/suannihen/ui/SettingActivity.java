package suanhang.jinan.com.suannihen.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import suanhang.jinan.com.suannihen.R;

/**
 * 设置界面activity
 *
 * @author admin
 */
public class SettingActivity extends StatisticsActivity implements
        OnClickListener {
    private static final String TAG = "SettingActivity";
    private TextView tv_clear_memory;
    private Gson gson;
    TextView tv_page_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        init();
    }

//    private void init() {
//        gson = new Gson();
//        tv_page_title = (TextView) findViewById(R.id.tv_page_title);
//        tv_clear_memory = (TextView) findViewById(R.id.tv_clear_memory);
//        try {
//            tv_clear_memory
//                    .setText(DataCleanManager.getTotalCacheSize(context));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 设置字体
//        tv_page_title.setText(getString(R.string.setting));
//        ((TextView) findViewById(R.id.tv_text10)).setText("当前版本：v"
//                + JinChaoUtils.getAppVerson(context));
//
//        findViewById(R.id.iv_left_img).setOnClickListener(this);
//        findViewById(R.id.tv_supplier_send).setOnClickListener(this);
//        findViewById(R.id.tv_edit_info).setOnClickListener(this);
//        findViewById(R.id.tv_glfl).setOnClickListener(this);
//        findViewById(R.id.tv_address).setOnClickListener(this);
//        findViewById(R.id.tv_kefu).setOnClickListener(this);
//        findViewById(R.id.layout_notify).setOnClickListener(this);
//        findViewById(R.id.layout_clear_cache).setOnClickListener(this);
//        findViewById(R.id.tv_aboutus).setOnClickListener(this);
//        findViewById(R.id.layout_update).setOnClickListener(this);
//        findViewById(R.id.layout_top_5).setOnClickListener(this);
//        findViewById(R.id.tv_invitation).setOnClickListener(this);
//        TextView tv_input_code = (TextView) findViewById(R.id.tv_input_code);
//        tv_input_code.setOnClickListener(this);
//        String supplierType = SPUtil.get(this, "supplierType");
//        if (!TextUtils.isEmpty(supplierType) && supplierType.equals("1")) {//机构账号
//            findViewById(R.id.tv_account_safety).setVisibility(View.GONE);
//        } else {//普通账号
//            findViewById(R.id.tv_account_safety).setVisibility(View.VISIBLE);
//            findViewById(R.id.tv_account_safety).setOnClickListener(this);
//        }
//        sbt_ispush = (SwitchButton) findViewById(R.id.sbt_ispush);
//        sbt_ispush.setOnCheckedChangeWidgetListener(onCheckedChangeListener);
//        String isPush = SPUtil.getPush(this, "isPush");
//        if (isPush.equals("1")) {
//            sbt_ispush.setChecked(false);
//        } else {
//            sbt_ispush.setChecked(true);
//        }
//    }

//    /****
//     * 滑动按钮监听事件
//     */
//    OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
//
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView,
//                                     boolean isChecked) {
//            LogX.e("isChecked===>", isChecked + ConstantString.TEXT_EMPTY);
//            if (isChecked) {
//                ShowToastUtil.Short("通知已关闭");
//                SPUtil.set(SettingActivity.this, "isPush", "0");
//                nateworkIspush("0");
//            } else {
//                ShowToastUtil.Short("通知已打开");
//                SPUtil.set(SettingActivity.this, "isPush", "1");
//                nateworkIspush("1");
//            }
//        }
//    };

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库 * * @param context * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 清除本应用所有的数据 * * @param context * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_input_code://邀请好友
////                Intent codeIntent = new Intent(this, InvitationCodeInputActivity.class);
////                startActivityForResult(codeIntent, 0);
//
//                break;
//            case R.id.iv_left_img:
//                onBack();
//                break;
//            case R.id.tv_edit_info:
//                if (userInfo == null) {
//                    requestServerPostThread();
//                    return;
//                }
//                Intent intent2 = new Intent(getBaseContext(),
//                        UserInfoActivity.class);
//                intent2.putExtra(ConstantString.PROFILEUSERINFO, userInfo);
//                startActivityForResult(intent2, 0);
//                break;
//            case R.id.tv_account_safety:
//                Intent intent = new Intent(getApplicationContext(),
//                        AccountActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.layout_clear_cache:
//                try {
//                    DataCleanManager.clearAllCache(context);
//                    tv_clear_memory.setText(DataCleanManager
//                            .getTotalCacheSize(context));
//                    ShowToastUtil.Short("清除缓存成功");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.tv_supplier_send:
//                String supplierTypes = SPUtil.get(this, "supplierType");
//
//                if (!TextUtils.isEmpty(supplierTypes) && supplierTypes.equals("1")) {
//                    ShowToastUtil.Short("已是合作机构");
//                } else if (SPUtil.getInt(ConstantString.ISBINDSUPPLIER) == 1) {
//                    ShowToastUtil.Short("已是合作机构");
//                } else {
//                    Intent i = new Intent(this, PolicyActivity.class);
//                    i.putExtra("type", 5);
//                    i.putExtra("titleName", "合作机构申请");
//                    startActivity(i);
//                }
//                break;
//            case R.id.tv_aboutus:
//                Intent intent4 = new Intent(getApplicationContext(),
//                        AboutUsActivity.class);
//                startActivity(intent4);
//
//                break;
//            case R.id.layout_top_5:
//                new CustomDialogBack.Builder(SettingActivity.this)
//                        .setMessage("您是否确认退出当前账号？")
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.confirm,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int id) {
//                                        LogX.d("setPagePath",
//                                                "登出Setingctivity");
//                                        Constants.ISEXIT_MAIN = true;
//                                        Constants.ISEXIT_YSQ = true;
//                                        WeiMiCountUtil.recordLogout(false);
//
//                                        logout();
//                                        doExit();
//                                        WeiMiCountUtil.recordClientEvent("Logout",
//                                                "{\"describe\":\"点击退出登录"+"\"}");
//                                        Constants.EXIT_STIT = true;
//                                    }
//                                })
//                        .setNegativeButton(R.string.cancel,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int id) {
//                                        dialog.cancel();
//                                    }
//                                })
//                        .show();
//
//                break;
//            case R.id.layout_update:
//                requestUpdate();
//                break;
//            case R.id.tv_address://收货地址列表
//                Intent lacationIntent = new Intent(this, LocationListActivity.class);
//                lacationIntent.putExtra("from", 1);//1是从设置进入，2是从物流界面进入
//                startActivity(lacationIntent);
////
////                Intent lacationIntent = new Intent(this, AuctionMapActivity.class);
////                lacationIntent.putExtra(ConstantString.SUPPLIERID,"4133066448643712");
////                startActivity(lacationIntent);
//
//                break;
//            case R.id.tv_kefu://客服
//                if(TextUtils.isEmpty(SPUtil.get(getApplicationContext(),
//                        ConstantString.USERID))){
//                    JinChaoUtils.goLoginPage(this, 1);
//                }else {
//                    String fromData = "queryId=" + SPUtil.get(this, ConstantString.USERID) + "&type=1";
//                    CallServiceActivity.startActivity(this,fromData,"3");
////                    CsChatActivity.startActivity(this,fromData);
//                }
//                break;
            default:
                break;

        }
    }


//    private void logout() {
//        if (!BaokuStatic.isLogin) {
//            ShowToastUtil.Short(R.string.not_login_yet);
//            return;
//        }
//
//        LiteHttp.getInstence().executeAsync(new Runnable() {
//
//            @Override
//            public void run() {
//
//                if (Looper.myLooper() == null)
//                    Looper.prepare();
//                boolean logoutSuccess = BaokuStatic.getInstance().logout();
//
//                if (logoutSuccess) {
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            ShowToastUtil.Short(getResources().getString(R.string.log_off_success));
//                        }
//                    });
//                } else {
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            ShowToastUtil.Short(getResources().getString(R.string.log_off_failed));
//                        }
//                    });
//                }
//            }
//        });
//    }

    

//    private void doExit() {
//        LoginOutUtil.doExit(this);
//
//        BaokuStatic.index_tab = 0;
//        Intent intent = new Intent();
//        intent.setAction(Constants_umeng.MINE_LOGIN_ACTION);
//        context.sendBroadcast(intent);
//
//        setResult(RESULT_OK);
//        finish();
//    }

    /**
     * 开启线程请求“我的状态”的列表数据
     */
//    private void requestUpdate() {
//
//        Map<String, String> params = new HashMap<String, String>();
//
//        VolleyUtils.sendPostMethod(URLUtil.checkUpdate(), params,
//                volleyCallBack, true, this);
//    }

//    private void checkUPdate(String json) throws JSONException {
//        // 成功
//        JSONObject jsonObject = new JSONObject(json);
//        String data = jsonObject.getString("data");
//        final AppVersionDomain versionDomain = gson.fromJson(data,
//                AppVersionDomain.class);
//        if (versionDomain.getNeedUpdate() == 1) { // 0不需要更新，1需要更新
//            new CustomDialog.Builder(SettingActivity.this)
//                    .setTitle(getString(R.string.version_refresh_message, versionDomain.getAppVersion()))
//                    .setMessage(versionDomain.getAppDescription())
//                    // .setMessage("宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦宝库发布新版本啦")
//                    .setPositiveButton(R.string.version_refresh_rightnow,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//                                    if (TextUtils.isEmpty(versionDomain
//                                            .getDownloadUrl())) {
//                                        ShowToastUtil.Short(getString(R.string.version_refresh_urlerror));
//                                        return;
//                                    }
//                                    if (!NetworkUtils
//                                            .isNetworkConnected(context)) {
//                                        ShowToastUtil.Short(getString(R.string.network_broken));
//                                        return;
//                                    }
//                                    if (NetworkUtils.isWifi(context)) {
//                                        String chanleNum = JinChaoUtils.getChanelNom(context);
//                                        if (BaokuStatic.isBelongList(chanleNum)) {
//                                            startDownloadService(BaokuStatic.baseStaticUrl+"/app/" + chanleNum + ".apk",
//                                                    versionDomain.getAppVersion());
//                                        } else {
//                                            startDownloadService(
//                                                    versionDomain.getDownloadUrl(),
//                                                    versionDomain.getAppVersion());
//                                        }
//                                    } else {
//                                        // TODO 测试
//                                        showDownloadDialog(versionDomain);
//                                    }
//                                }
//                            }).setNegativeButton(R.string.version_refresh_keep, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if(!TextUtils.isEmpty(versionDomain.appPatchVersion)){
//                        BaokuStatic.startDownloadPatch(versionDomain.appPatchUrl, versionDomain.appPatchVersion);
//                    }
//                }
//            }).show();
//        } else if (versionDomain.getNeedUpdate() == 0) {
//            ShowToastUtil.Short("已是最新版本");
//            if(!TextUtils.isEmpty(versionDomain.appPatchVersion)){
//                BaokuStatic.startDownloadPatch(versionDomain.appPatchUrl, versionDomain.appPatchVersion);
//            }
//        }
//
//    }

//    private void showDownloadDialog(final AppVersionDomain versionDomain) {
//        new CustomDialogBack.Builder(SettingActivity.this)
//                .setMessage(R.string.download_message)
//                .setPositiveButton(
//                        R.string.download,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(
//                                    DialogInterface dialog,
//                                    int which) {
//                                String chanleNum = JinChaoUtils.getChanelNom(context);
//                                if (BaokuStatic.isBelongList(chanleNum)) {
//                                    startDownloadService(BaokuStatic.baseStaticUrl+"/app/" + chanleNum + ".apk",
//                                            versionDomain.getAppVersion());
//                                } else {
//                                    startDownloadService(
//                                            versionDomain.getDownloadUrl(),
//                                            versionDomain.getAppVersion());
//                                }
//                            }
//                        })
//                .setNegativeButton(R.string.cancel, null)
//                .show();
//    }

//    private void startDownloadService(String url, String versionName) {
//        Intent intent = new Intent();
//        intent.setAction(Constants_umeng.updateAction);
//        Bundle bundle = new Bundle();
//        bundle.putString("url", url);
//        bundle.putString("versionName", versionName);
//        intent.putExtras(bundle);
//        sendBroadcast(intent);
//    }

//    private void nateworkIspush(String isPush) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put(ConstantString.USERID,
//                SPUtil.get(context, ConstantString.USERID));
//        params.put("isPush", isPush);
//        VolleyUtils.sendPostMethod(URLUtil.getIfPush(), params, volleyCallBack,
//                true, this);
//    }

//    VolleyCallBack volleyCallBack = new VolleyCallBack() {
//        @Override
//        public void success(String result, String method) {
//            try {
//                if (method.equals(URLUtil.checkUpdate())) {
//                    checkUPdate(result);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void failure(String error, String method, int type) {
//
//        }
//    };

    @Override
    protected void onDestroy() {
//        sbt_ispush.setOnCheckedChangeWidgetListener(null);
//        sbt_ispush = null;
        super.onDestroy();
//        if (onCheckedChangeListener != null) {
//            onCheckedChangeListener = null;
//        }
    }

//    private void requestServerPostThread() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put(ConstantString.USERID, SPUtil.get(
//                context, ConstantString.USERID));
//        map.put(ConstantString.SCANNEDUSERID, SPUtil.get(context, ConstantString.USERID));
//
//        VolleyUtils.sendPostMethod(URLUtil.getProfileUserInfo(), map,
//                new VolleyCallBack() {
//                    @Override
//                    public void success(String result, String method) {
//                        doRequestSuccess(result);
//                    }
//
//                    @Override
//                    public void failure(String error, String method, int type) {
//
//                    }
//                }, true, this);
//    }

//    protected void doRequestSuccess(String result) {
//        BaseParser<UserVo> baseParser = BaseParser.fromJson(result, UserVo.class);
//        if(baseParser == null || baseParser.getData() == null){
//            return;
//        }
//        userInfo = baseParser.getData();
//        Intent intent2 = new Intent(getBaseContext(), UserInfoActivity.class);
//        intent2.putExtra(ConstantString.PROFILEUSERINFO, userInfo);
//        startActivity(intent2);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ConstantString.RESULTCODE_UPDATEUSERINFO
//                && data != null) {
//            if (data.getSerializableExtra(ConstantString.PROFILEUSERINFO) != null
//                    && data.getSerializableExtra(ConstantString.PROFILEUSERINFO) instanceof UserVo) {
//                userInfo = (UserVo) data
//                        .getSerializableExtra(ConstantString.PROFILEUSERINFO);
//            }
//        }
//    }

//    /**
//     * 返回操作
//     */
//    private void onBack() {
//        Intent intent = new Intent();
//        intent.putExtra(ConstantString.PROFILEUSERINFO, userInfo);
//        setResult(ConstantString.RESULTCODE_UPDATEUSERINFO, intent);
//        finish();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }
}
