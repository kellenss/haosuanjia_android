/**
 * @description
 * @author hu
 * @time 2015-11-12
 */
package com.jinan.haosuanjia.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

//import com.sina.shihui.baoku.mipush.BaokuStatic;
//import com.sina.shihui.baoku.utils.http.LiteHttp;

/**
 * @author hu
 * @time 2015-11-12
 */
public class FileUtils {
    private static String APP_DIR_NAME = "honjane";
    private static String FILE_DIR_NAME = "files";
    private static String mRootDir;
    private static String mAppRootDir;
    private static String mFileDir;

    public static void init() {
        mRootDir = getRootPath();
        if (mRootDir != null && !"".equals(mRootDir)) {
            mAppRootDir = mRootDir + "/" + APP_DIR_NAME;
            mFileDir = mAppRootDir + "/" + FILE_DIR_NAME;
            File appDir = new File(mAppRootDir);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            File fileDir = new File(mAppRootDir + "/" + FILE_DIR_NAME);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

        } else {
            mRootDir = "";
            mAppRootDir = "";
            mFileDir = "";
        }
    }

    public static String getFileDir(){
        return mFileDir;
    }


    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath:  /data/data/
        }
    }

    /**
     * 打开文件
     * 兼容7.0
     *
     * @param context     activity
     * @param file        File
     * @param contentType 文件类型如：文本（text/html）
     *                    当手机中没有一个app可以打开file时会抛ActivityNotFoundException
     */
    public static void startActionFile(Context context, File file, String contentType) throws ActivityNotFoundException {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getUriForFile(context, file), contentType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param file        File
     * @param requestCode result requestCode
     */
    public static void startActionCapture(Activity activity, File file, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//ACTION_GET_CONTENT
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file));
        activity.startActivityForResult(intent, requestCode);
    }


    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.jinan.haosuanjia.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
    /**
     * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
     *
     * @param fileName
     * @param bitmap
     * @throws IOException
     */
    private static void saveBitmap(String fileName, Bitmap bitmap) throws IOException {
        if (bitmap == null || isBitmapExists(fileName)) {
            return;
        }

        File file = new File(fileName);
        if(!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    }
    /**
     * 使用系统时间获取文件名
     *
     * @param fileExtension
     *            文件后缀名
     * @return 文件名
     */
    public static String getFileNameForSystemTime(String fileExtension) {
        if (fileExtension.length() < 1 || !fileExtension.startsWith(".")) {
            return "";
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(Calendar.getInstance().getTime());
        long milliseconds = cl.getTimeInMillis();
        return String.valueOf(milliseconds) + fileExtension;
    }
    /**
     * 根据路径获取图片资源（已缩放）
     * @param url 图片存储路径
     * @param width 缩放的宽度
     * @param height 缩放的高度
     * @return
     */
    public static Bitmap getBitmapFromUrl(String url, double width, double height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;
//        try {
//            ExifInterface exif = new ExifInterface(url);
//            String model = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 按照固定宽高进行缩放
        // 这里希望知道照片是横屏拍摄还是竖屏拍摄
        // 因为两种方式宽高不同，缩放效果就会不同
        // 这里用了比较笨的方式
        if(mWidth <= mHeight) {
            scaleWidth = (float) (width/mWidth);
            scaleHeight = (float) (height/mHeight);
        } else {
            scaleWidth = (float) (height/mWidth);
            scaleHeight = (float) (width/mHeight);
        }
//        matrix.postRotate(90); /* 翻转90度 */
        // 按照固定大小对图片进行缩放
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);
        // 用完了记得回收
        bitmap.recycle();
        return newBitmap;
    }

    public static final String CACHE="log";
    public static final String ICON="icon";
    public static final String ROOT="Isale";

    /**
     * 获取图片缓存路径
     * @return
     */
    public static File getIconDir(Context context){
        return getDir(context,ICON);
    }
    /**
     * 获取json缓存路径
     * @param str
     * @return
     */
    public static File getDir(Context context,String str){
        StringBuilder path=new StringBuilder();
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            path.append(File.separator);//‘/’
            path.append(ROOT);//
            path.append(File.separator);//‘/’
            path.append(str);//
        }else{
            File filesDir = context.getCacheDir();
            path.append(filesDir.getAbsolutePath());//
            path.append(File.separator);
            path.append(str);
        }
        File file=new File(path.toString());
        if (!file.exists()||!file.isDirectory()) {
            file.mkdirs();//创建文件
        }
        return file;
    }
    /**
     * 保存图片至sd卡中
     * @param mBitmap
     */
    public static void setPicToView(Context context,Bitmap mBitmap,String pathName) {
        File iconDir = getIconDir(context);
        String absolutePath = iconDir.getAbsolutePath();
        FileOutputStream b = null;
        File file = new File(absolutePath);
        file.mkdirs();// 创建文件夹
        String fileName = absolutePath + pathName;// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(CompressFormat.JPEG, 70, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found exception");// 异常提示
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * 判断sd卡是否存在
     */
    private static boolean isSDAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }else{
            return false;
        }
    }
    public static File getCacheDir(Context context){
        return getDir(context,CACHE);
    }
    /**
     * 使用runnable保存图片
     * @param fileName  图片的完整路径名称
     * @param bitmap 图片
     */
    private static Runnable saveBitmapRunnabler(final String fileName, final Bitmap bitmap) {
        return new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    saveBitmap(fileName, bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 删除图片
     */
    public static void deleteBitmap(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isBitmapExists(String fileName) {
        File file = new File(fileName);
        return file.exists();

    }

//    /**
//     * 获取内置内存，一般存储小文件，如json等
//     */
//    public static File getFilesDirSystemInside(){
//        return BaokuStatic.application.getFilesDir();
//    }


    public static File getFilesDirSystem(){
        String externalStorageState;
        Context context = HMApplication.application;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException var5) {
            externalStorageState = "";
        } catch (IncompatibleClassChangeError var6) {
            externalStorageState = "";
        }
        File folder = null;
        if(Environment.MEDIA_MOUNTED.equals(externalStorageState) && context != null
                && PackageManager.PERMISSION_GRANTED == context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                && PackageManager.PERMISSION_GRANTED == context.checkCallingOrSelfPermission("android.permission.READ_EXTERNAL_STORAGE")){
            folder = context.getExternalCacheDir();
        }

        if (folder == null || !folder.exists()) {
            folder = HMApplication.application.getFilesDir();
        }
        return folder;
    }

    /**
     * 不带后缀的文件夹名字
     * @dirName 文件夹名称
     * @return 返回路径（全路径）
     */
    public static File getFilesDir(String dirName){
        File file = new File(getFilesDirSystem()+File.separator+dirName);
        if(!file.exists())
            file.mkdir();

        return file;
    }

    /**
     * 文件夹是否存在
     * @dirName 文件夹名称
     */
    public static boolean isDirExists(String dirName){
        return new File(getFilesDirSystem()+File.separator+dirName).exists();
    }

	public static Runnable writeOuterRunnabler(final String fileName, final String content) {
		return new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				writeOuterPrivateFile(fileName, content);
			}
		};
	}

	public static Runnable readOuterRunnabler(final String fileName, final Handler hanlder) {
		return new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				String content = readOuterPrivateFile(fileName);
				if (TextUtils.isEmpty(content)) {
					msg.what = -1;
				} else {
					msg.what = 0;
					msg.obj = content;
				}
				hanlder.sendMessage(msg);
			}
		};
	}

	/**
	 * 外部存储私有文件保存
	 * 
	 * @author hu
	 * @time 2015-11-13
	 * @decription
	 */
	private static void writeOuterPrivateFile(String fileName, String content) {
//		File extdir= Environment.getExternalStorageDirectory();
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
//                file.setWritable(Boolean.TRUE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	/**
	 * 外部存储私有文件读取
	 */
	private static String readOuterPrivateFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists())
			return null;

		FileInputStream istream = null;
		ByteArrayOutputStream ostream = null;
		try {
			istream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			ostream = new ByteArrayOutputStream();
			int len = 0;
			while ((len = istream.read(buffer)) != -1) {
				ostream.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭输入流和输出流
				istream.close();
				ostream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ostream.toString();
	}

	public static void readInnerPrivateFile(String fileName, Context activity) {
		try {
			OutputStream outputStream = activity.getApplicationContext()
					.openFileOutput("openFileOutput.txt", Context.MODE_PRIVATE);
			outputStream.write("openFileOutput".getBytes("UTF-8"));
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * read file
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param content
     * @param append is append, if true, write to the end of file, else clear content of file and write into it
     * @return return false if content is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }

    public static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 统一数据保存用的方法，方便重构
     * @param json  保存的数据
     * @param jsonFileName json文件的文件名
     */
    public static void saveJsonData(String json, String jsonFileName){
        LiteHttp.getInstence().executeAsync(FileUtils.writeOuterRunnabler(jsonFileName, json));
    }

    /**
     * 统一数据保存用的方法，方便重构
     * @param fileName 文件的文件名
     * @param callBack  回调
     */
    public static void readJsonData(String fileName, final ReadJsonSuccessCallBack callBack){
        LiteHttp.getInstence().executeAsync(
                FileUtils.readOuterRunnabler(fileName,
                        new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                try {
                                    if (msg.what == 0) {
                                        callBack.readJosnSuccess(msg.obj.toString());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }

    public interface ReadJsonSuccessCallBack{
        void readJosnSuccess(String json);
    }

    /**
     * 统一数据保存用的方法，方便重构
     * @param fileName 文件的文件名
     * @param bitmap  图片
     */
    public static void saveBitmapCommon(String fileName, Bitmap bitmap){
        LiteHttp.getInstence().executeAsync(
                FileUtils.saveBitmapRunnabler(fileName, bitmap));
    }
}
