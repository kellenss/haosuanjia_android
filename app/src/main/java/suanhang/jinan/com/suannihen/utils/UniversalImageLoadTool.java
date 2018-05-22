package suanhang.jinan.com.suannihen.utils;

import android.graphics.Bitmap;

import suanhang.jinan.com.suannihen.commons.LogX;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class UniversalImageLoadTool {
	private static ImageLoader imageLoader = ImageLoader.getInstance();
	public static ImageLoader getImageLoader(){
		return imageLoader;
	}
	public static boolean checkImageLoader(){
		return imageLoader.isInited();
	}


    public static void disPlayNoDefault(String uri, ImageAware imageAware){
		disPlay(uri, imageAware, null);
    }

	public static void disPlay(String uri, ImageAware imageAware,
                               SimpleImageLoadingListener loadingListener){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
		.cacheOnDisk(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new SimpleBitmapDisplayer())
		.build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(HMApplication.application));
        imageLoader.displayImage(uri, imageAware, options, loadingListener);
	}

	public static void disPlay(String uri, ImageAware imageAware,int pic, boolean cache){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(pic)
		.showImageForEmptyUri(pic)
		.showImageOnFail(pic)
		.cacheInMemory(cache)
		.cacheOnDisk(cache)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new SimpleBitmapDisplayer())
		.build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(HMApplication.application));
		imageLoader.displayImage(uri, imageAware, options);
	}

	public static void disPlay(String uri, ImageAware imageAware,int pic){
		imageLoader.init(ImageLoaderConfiguration.createDefault(HMApplication.application));
		disPlay(uri, imageAware, null, pic);
	}

    public static void disPlay(String uri, ImageAware imageAware,
                               SimpleImageLoadingListener loadingListener,int pic){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(pic)
                .showImageForEmptyUri(pic)
                .showImageOnFail(pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(HMApplication.application));
        imageLoader.displayImage(uri, imageAware, options, loadingListener);
    }

	public static void clear(){
//		imageLoader.clearMemoryCache();		
//		imageLoader.clearDiskCache();
	}

	public static void resume(){
		imageLoader.resume();
		LogX.i("img","imageLoader.resume();");
	}
	/**
	 * 暂停加载
	 */
	public static void pause(){
		imageLoader.pause();
		LogX.i("img", "imageLoader.pause();");
	}
	/**
	 * 停止加载
	 */
	public static void stop(){
		imageLoader.stop();
		LogX.i("img", "imageLoader.stop();");
	}
	/**
	 * 销毁加载
	 */
	public static void destroy() {
		imageLoader.destroy();
		LogX.i("img", "imageLoader.destroy();");
	}
}
