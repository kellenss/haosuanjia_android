package suanhang.jinan.com.suannihen.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;

@SuppressLint({ "DefaultLocale", "SimpleDateFormat" })
public class Function_Utility {	
	/**
	 * 缓存数据目录
	 */
	public static String getCatchDataRootPath() {
		String filePath = getAppRootPath() + "/catch/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		file = null;
		return filePath;
	}

	/**
	 * 缓存关注流数据目录
	 */
	public static String getCatchAttentionRootPath(String type) {
		String filePath = "";
		if (type.equals("attention")) {
			filePath = getCatchDataRootPath() + "attention/";
		}
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		file = null;
		return filePath;
	}

	public static String getImageRootPath() {
		String filePath = getAppRootPath() + "/image/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		file = null;
		return filePath;
	}

	public static String getCompressImageRootPath() {
		String filePath = getImageRootPath() + "compressImage/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		file = null;
		return filePath;
	}

	public static String getCompressImagePath(String filename) {
		String path = getCompressImageRootPath() + "compress_" + filename;
		return path;
	}

	public static String getAppRootPath() {
		String filePath = FileUtils.getFilesDirSystem()+"/baoku";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = null;
		return filePath;
	}

	public static Bitmap reduce(Bitmap bitmap, int width, int height,
			boolean isAdjust) {
		// 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
		if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
			return bitmap;
		}
		// 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor,
		// int scale, int roundingMode);
		// scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
		float sx = new BigDecimal(width).divide(
				new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		float sy = new BigDecimal(height).divide(
				new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
			sx = (sx < sy ? sx : sy);
			sy = sx;// 哪个比例小一点，就用哪个比例
		}
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
		Bitmap scaleBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		scaleBitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		return BitmapFactory.decodeStream(new ByteArrayInputStream(baos
				.toByteArray()));
	}

	
}
