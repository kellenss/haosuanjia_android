package suanhang.jinan.com.suannihen.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.dialog.CustomDialogBack;

/**
 * Created by admin on 2016/11/28.
 * Function：
 * Modify by admin on 2016/11/28.
 * Modify Reason：
 */

public class PermissionUtil {


    //    以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    public static boolean checkCameraPermission(final Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本是否是6.0
            int camera_permission = activity.checkSelfPermission(Manifest.permission.CAMERA);//相机权限
            int granted = PackageManager.PERMISSION_GRANTED;
            if (camera_permission != granted) {
//                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 100);
                showPemissionConfirmDialog(activity, R.string.open_permission_camera);
                return false;
            }
        }
        return true;
    }

    /**
     * 判断日历权限是否获取到
     */
    public static boolean checkCalendarPermission(final Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本是否是6.0
            int read = activity.checkSelfPermission(Manifest.permission.READ_CALENDAR);
            int write = activity.checkSelfPermission(Manifest.permission.WRITE_CALENDAR);
            int granted = PackageManager.PERMISSION_GRANTED;
            if (read != granted || write != granted) {
//                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 100);
                showPemissionConfirmDialog(activity, R.string.open_permission_calendar);
                return false;
            }
        }
        return true;
    }

    /**
     * 判断内存卡读写权限是否获取到
     */
    public static boolean checkStoragePermission(final Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本是否是6.0
            int read = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int write = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int granted = PackageManager.PERMISSION_GRANTED;
            if (read != granted || write!=granted) {
//                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                showPemissionConfirmDialog(activity, R.string.open_permission_storage);
                return false;
            }
        }
        return true;
    }

    /**
     * 判断联系人权限是否获取到
     */
    public static boolean checkContactPermission(final Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本是否是6.0
            int read = activity.checkSelfPermission(Manifest.permission.READ_CONTACTS);
            int granted = PackageManager.PERMISSION_GRANTED;
            if (read != granted) {
//                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS}, 100);
                showPemissionConfirmDialog(activity, R.string.open_permission_contact);
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限对话框
     */
    private static void showPemissionConfirmDialog(final Activity activity, int message) {
        new CustomDialogBack.Builder(activity).
                setCancelable(true)
                .setCancelableTouchOutside(false)// 设置点击屏幕Dialog不消失
                .setMessage(message)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getAppDetailSettingIntent(activity);
                    }
                }).show();
    }
}
