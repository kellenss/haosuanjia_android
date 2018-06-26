package com.jinan.haosuanjia.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * s
 * 保存图片工具类
 */
public class SaveImageUtils {
    public static void saveImageToSDkard(Context context, int pic) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), pic);
            saveImageToSDkard(context, bmp);
            bmp.recycle();
            bmp = null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveImageToSDkard(Context context, Bitmap bmp) throws Exception {
        if (Build.MODEL.equals("MX4 Pro")) {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!dir.exists())
                dir.mkdirs();
            File f = new File(dir, System.currentTimeMillis() + ".JPG");
            FileOutputStream out = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
            Toast.makeText(context, "二维码保存成功!", Toast.LENGTH_SHORT)
                    .show();
            out.flush();
            out.close();
        } else {
            savePic(context, bmp);
        }
    }

    /**
     * 保存图片
     */
    private static void savePic(Context context, Bitmap bmp) {
        try {
            String path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString();
            System.out.println("相册路径：" + path);
            String photoPath = path;// 获取相册路径

            File dir = new File(photoPath);
            if (!dir.exists())
                dir.mkdirs();
            File f = new File(dir, System.currentTimeMillis() + ".JPG");
            FileOutputStream out = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f); // out is your output
                // file
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);
                System.out.println("小米 ");
            } else {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                System.out.println("魅族");
            }
            ShowToastUtil.toastShow("保存成功!");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public static void saveImageToSDkard(Context context, String url) {
        try {
            URL picUrl = new URL(url);
            InputStream inputStream = picUrl.openStream();
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            saveImageToSDkard(context, bmp);
            bmp.recycle();
            bmp = null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "KupaiImage");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

}
