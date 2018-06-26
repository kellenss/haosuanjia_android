/**
 * @description
 * @author hu
 * @time 2015-11-12
 */
package suanhang.jinan.com.suannihen.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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

/**
 * @author hu
 * @time 2015-11-12
 */
public class FileUtils_new {

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
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(HMApplication.application.getContentResolver(),
                    fileName, file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HMApplication.application.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
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

    /**
     * 获取内置内存，一般存储小文件，如json等
     */
    public static File getFilesDirSystemInside(){
        return HMApplication.application.getFilesDir();
    }


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
            folder = HMApplication.application.getExternalCacheDir();
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
                FileUtils_new.saveBitmapRunnabler(fileName, bitmap));
    }
}
