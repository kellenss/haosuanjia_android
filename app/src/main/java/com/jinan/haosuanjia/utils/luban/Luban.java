package com.jinan.haosuanjia.utils.luban;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;

import com.jinan.haosuanjia.utils.FileUtils;
import com.jinan.haosuanjia.utils.LiteHttp;

import java.io.File;

public class Luban implements Handler.Callback {
    private static final String TAG = "Luban";
    private static final String DEFAULT_DISK_CACHE_DIR = "compress";

    private static final int MSG_COMPRESS_SUCCESS = 0;
    private static final int MSG_COMPRESS_START = 1;
    private static final int MSG_COMPRESS_ERROR = 2;

    private File file;
    private OnCompressListener onCompressListener;

    private Handler mHandler;

    private Luban(Builder builder) {
        this.file = builder.file;
        this.onCompressListener = builder.onCompressListener;
        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    public static Builder get(Context context) {
        return new Builder(context);
    }

    /**
     * Returns a file with a cache audio name in the private cache directory.
     *
     * @param context A context.
     */
    private File getImageCacheFile(Context context) {
        if (getImageCacheDir(context) != null) {
            return new File(getImageCacheDir(context) + "/" + System.currentTimeMillis());
        }
        return null;
    }

    /**
     * Returns a directory with a default name in the private cache directory of the application to
     * use to store retrieved audio.
     *
     * @param context A context.
     * @see #getImageCacheDir(Context, String)
     */
    @Nullable
    private File getImageCacheDir(Context context) {
        return getImageCacheDir(context, DEFAULT_DISK_CACHE_DIR);
    }

    /**
     * Returns a directory with the given name in the private cache directory of the application to
     * use to store retrieved media and thumbnails.
     *
     * @param context   A context.
     * @param cacheName The name of the subdirectory in which to store the cache.
     * @see #getImageCacheDir(Context)
     */
    @Nullable
    private File getImageCacheDir(Context context, String cacheName) {
        File cacheDir = FileUtils.getFilesDirSystem();
        if (cacheDir != null) {
            File result = new File(cacheDir, cacheName);
            if (!result.mkdirs() && (!result.exists() || !result.isDirectory())) {
                // File wasn't able to create a directory, or the result exists but not a directory
                return null;
            }
            return result;
        }
        if (Log.isLoggable(TAG, Log.ERROR)) {
            Log.e(TAG, "default disk cache dir is null");
        }
        return null;
    }

    @UiThread
    private void launch(final Context context) {
        if (file == null && onCompressListener != null) {
            onCompressListener.onError(new NullPointerException("image file cannot be null"));
        }

        LiteHttp.getInstence().executeAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_START));

                    File dir = getImageCacheFile(context);
                    if(dir == null)
                        dir = file.getParentFile();
                    File result = new Engine(file, dir).compress();
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_SUCCESS, result));
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_COMPRESS_ERROR, e));
                }
            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (onCompressListener == null) return false;

        switch (msg.what) {
            case MSG_COMPRESS_START:
                onCompressListener.onStart();
                break;
            case MSG_COMPRESS_SUCCESS:
                onCompressListener.onSuccess((File) msg.obj);
                break;
            case MSG_COMPRESS_ERROR:
                onCompressListener.onError((Throwable) msg.obj);
                break;
        }
        return false;
    }

    public static class Builder {
        private Context context;
        private File file;
        private OnCompressListener onCompressListener;

        Builder(Context context) {
            this.context = context;
        }

        private Luban build() {
            return new Luban(this);
        }

        public Builder load(File file) {
            this.file = file;
            return this;
        }

        public Builder putGear(int gear) {
            return this;
        }

        public Builder setCompressListener(OnCompressListener listener) {
            this.onCompressListener = listener;
            return this;
        }

        public Luban launch() {
            Luban luban = build();
            luban.launch(context);
            return luban;
        }
    }
}