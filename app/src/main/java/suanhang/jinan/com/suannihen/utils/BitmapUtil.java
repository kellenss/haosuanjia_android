package suanhang.jinan.com.suannihen.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.commons.LogX;

/**
 */
public class BitmapUtil {
    /**
     *
     * @param iv
     *            ImageView
     * @param url
     *            图片的网络路径
     * @param size
     *            需要的图片的大小
     * 不设置加载动画，默认显示图片背景的
     */
    public static void loadImageUrlNoDefault(ImageView iv, String url, int size) {
        iv.setImageDrawable(null);
        String path = url + File.separator + size;
        UniversalImageLoadTool.disPlayNoDefault(path,
                new RotateImageViewAware(iv, path));
    }
    /**
     * @param iv ImageView
     * @param path 图片的网络路径
     * 不设置加载动画，默认显示图片背景的
     */
    public static void loadImageUrlNoDefault(ImageView iv, String path) {
        iv.setImageDrawable(null);
        UniversalImageLoadTool.disPlayNoDefault(path,
                new RotateImageViewAware(iv, path));
    }
    /**
     * @param iv ImageView
     * @param url 图片的网络路径
     * @param defaultId 默认的图片
     */
    public static void loadImageUrl(ImageView iv, String url,
                                    SimpleImageLoadingListener loadingListener, int defaultId) {
        UniversalImageLoadTool.disPlay(url,
                new RotateImageViewAware(iv, url), loadingListener,
                defaultId);
    }

	/**
	 * 默认设置加载中图片和加载失败图片，设置从服务器获取图片的大小，默认设置缓存图片，
	 *
	 * @param iv
	 *            ImageView
	 * @param url
	 *            图片的网络路径
	 * @param size
	 *            需要的图片的大小
	 */
	public static void loadImageUrl(ImageView iv, String url, int size) {
		String path = url + File.separator + size;
		loadImageUrl(iv, R.mipmap.ic_default_loading, path, true);
	}

	/**
	 * 默认设置加载中图片和加载失败图片，设置从服务器获取图片的大小，手动设置是否缓存图片，
	 *
	 * @param iv ImageView
	 * @param path 图片的网络路径
	 */
	public static void loadImageUrl(ImageView iv, String path, boolean cache) {
		loadImageUrl(iv, R.mipmap.ic_default_loading, path, cache);
	}

	/**
	 * 手动传入加载中图片和加载失败图片， 默认缓存图片
	 *
	 * @param iv ImageView
	 * @param defaultId 加载中显示的图片
	 * @param url 图片的网络路径
	 */
	public static void loadImageUrl(ImageView iv, int defaultId, String url) {
		loadImageUrl(iv, defaultId, url, true);
	}
	/**
	 * 手动传入加载中图片和加载失败图片， 默认缓存图片
	 *
	 * @param iv ImageView
	 * @param defaultId 加载中显示的图片
	 * @param url 图片的网络路径
	 */
	public static void loadImageUrl(ImageView iv, int defaultId, String url, boolean cache) {
		UniversalImageLoadTool.disPlay(url, new RotateImageViewAware(iv, url),
				defaultId, cache);
	}

	/**
	 * 获取图片文件的信息，是否旋转了90度，如果是则反转
	 *
	 * @param bitmap
	 *            需要旋转的图片
	 * @param path
	 *            图片的路径
	 */
	public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
        if(path.startsWith("http:/")) return bitmap;

		int degree = getPicRotate(path);
		if (degree != 0) {
			Matrix m = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			m.setRotate(degree); // 旋转angle度
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
		}
		return bitmap;
	}

	/**
	 * 读取图片文件旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return 图片旋转的角度
	 */
	public static int getPicRotate(String path) {
        if(path.startsWith("http")) return 0;

		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/*
	 * 使用Bitmap加Matrix来缩放
	 */
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int newWidth = w;
		int newHeight = h;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		if (scaleWidth > scaleHeight) {
			matrix.postScale(scaleWidth, scaleWidth);
		} else {
			matrix.postScale(scaleHeight, scaleHeight);
		}
		// if you want to rotate the Bitmap matrix.postRotate(45);
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static byte[] path2Bytes(String filepath) {
		byte[] data = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filepath));
			int fileLength = fis.available();
			data = new byte[fileLength];
			fis.read(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static String compressImage(String path) {
		if (path == null) {
			return null;
		}
		String destPath = Function_Utility.getCompressImagePath(path
				.substring(path.lastIndexOf("/") + 1));
		System.out.println("图片路径：" + destPath + "--------图片名称："
				+ path.substring(path.lastIndexOf("/") + 1));
		return compressImage(path, destPath);
	}

	public static String compressImage(String path, String destPath) {
		try {
			// 获取源图片的大小
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// 当opts不为null时，但decodeFile返回空，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight
			BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长度
			/*
			 * if (srcWidth > srcHeight) { ratio = (double) srcWidth / (double)
			 * 1600; destWidth = 1600; destHeight = (int) (srcHeight / ratio); }
			 * else { ratio = (double) srcHeight / (double) 1200; destHeight =
			 * 1200; destWidth = (int) (srcWidth / ratio); }
			 */
			if ((srcHeight > 4000 && srcWidth < 1000)
					|| (srcWidth > 4000 && srcHeight < 1000))
				return path;
			int min = srcWidth > srcHeight ? srcHeight : srcWidth;
			if (min <= 720) {
				destHeight = srcHeight;
				destWidth = srcWidth;
			} else {
				if (min == srcHeight) {
					ratio = (double) srcHeight / (double) 720;
					destHeight = 720;
					destWidth = (int) (srcWidth / ratio);
				} else {
					ratio = (double) srcWidth / (double) 720;
					destWidth = 720;
					destHeight = (int) (srcHeight / ratio);
				}
			}
			// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 添加尺寸信息，
			// 获取缩放后图片
			Bitmap destBm = BitmapFactory.decodeFile(path, newOpts);
			/*
			 * if (srcWidth < srcHeight) { Matrix matrix = new Matrix(); //
			 * 将图像顺时针旋转90度 matrix.setRotate(270); // 生成旋转后的图像 destBm =
			 * Bitmap.createBitmap(destBm, 0, 0, destBm.getWidth(),
			 * destBm.getHeight(), matrix, false); }
			 */

			if (destBm == null) {
				return path;
			} else {
				try {
					ExifInterface exif = new ExifInterface(path);
					int orientation = exif.getAttributeInt(
							ExifInterface.TAG_ORIENTATION, 1);
					LogX.d("EXIF", "Exif: " + orientation);
					Matrix matrix = new Matrix();
					if (orientation == 6) {
						matrix.postRotate(90);
					} else if (orientation == 3) {
						matrix.postRotate(180);
					} else if (orientation == 8) {
						matrix.postRotate(270);
					}
					destBm = Bitmap
							.createBitmap(destBm, 0, 0, destBm.getWidth(),
									destBm.getHeight(), matrix, true); // rotating
					// bitmap
				} catch (Exception e) {
					LogX.e("error", e.toString());
				} catch (OutOfMemoryError e) {
					LogX.e("error", e.toString());
				}
				// String compressPath = Function_Utility
				// .getCompressImagePath(path.substring(path
				// .lastIndexOf("/") + 1));
				File destFile = new File(destPath);
				// 创建文件输出流
				OutputStream os = new FileOutputStream(destFile);
				// 存储
				destBm.compress(CompressFormat.JPEG, 90, os);
				// 关闭流
				os.close();
				destBm.recycle();
				return destPath;
			}
		} catch (Exception e) {
			LogX.e("error", e.toString());
		} catch (OutOfMemoryError e) {
			LogX.e("error", e.toString());
		}
		return null;
	}

	public static boolean saveBitmap(Bitmap bitmap, String folderPath, String fileName) {
		File folder = new File(folderPath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		File file = new File(folderPath + fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bitmap.recycle();
		}
		return false;
	}

    /**
     * 根据下载图片时设置的的大小获取下载的图片的宽度
     * @param scaleSize 下载图片时设置的的大小
     * @param imgRealWidth 图片实际宽度
     * @param imgRealHight 图片实际高度
     * @return
     */
    public static int getLoadImgWidth(int scaleSize, double imgRealWidth, double imgRealHight){
        return (int) (scaleSize/imgRealHight * imgRealWidth);
    }
    public static int getShowHeight(double showWidth, double imgRealWidth, double imgRealHight){
        return (int) (showWidth/imgRealWidth * imgRealHight);
    }
}
