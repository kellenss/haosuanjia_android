/*
* Copyright (C) 2015 Author <dictfb#gmail.com>
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.jinan.haosuanjia.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.jinan.haosuanjia.commons.LogX;

import java.io.IOException;
import java.util.Map;

/**
 * description 音频播放
 * <p>
 * author hanlei
 * version 1.0
 * created at 2017/7/31.
 * <p>
 * 两种使用方式
 * <p>
 * 1、配合自定义控件MusicController使用,播放暂停进度控制都可以
 * 详见cn.bcbook.pad.teacher.ui.activity.lesson.AudioListenTextFragment
 *
 * @BindView(R.id.media_controller) MusicController mMusicController;
 * <p>
 * musicPlayer = new MusicPlayer(context, mMusicController);
 * musicPlayer.setVideoPath(musicUrl);
 * <p>
 * 2、单独使用，常用于简单的音乐播放，无播放器
 * musicPlayer = new MusicPlayer(context);
 * musicPlayer.setVideoPath(musicUrl);
 * <p>
 * 常用方法
 * musicPlayer.start(); // 播放
 * musicPlayer.puase(); // 暂停
 * musicPlayer.setVideoPath(String url); // 设置音频源url
 * musicPlayer.closePlayer(); // 关闭播放器
 * <p>
 * 回调方法(调用本控件的视图，想要获取音乐播放的状态，做一些业务上的操作)
 * <p>
 * public interface MusicCallback {
 * void onPause(final MediaPlayer mediaPlayer);  //  暂停时
 * void onStart(final MediaPlayer mediaPlayer); // 播放时
 * void onBufferingStart(final MediaPlayer mediaPlayer); // 缓冲开始
 * void onBufferingEnd(final MediaPlayer mediaPlayer); // 加载完成
 * }
 */

public class MusicPlayer implements MusicController.MediaPlayerControl {

    private String TAG = MusicPlayer.class.getSimpleName();
    // settable by the client
    private Uri mUri;

    // all possible internal states
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;

    // mCurrentState is a VideoView object's current state.
    // mTargetState is the state that a method caller intends to reach.
    // For instance, regardless the VideoView object's current state,
    // calling pause() intends to bring the object to a target state
    // of STATE_PAUSED.
    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;

    private MediaPlayer mMediaPlayer = null;
    private int mAudioSession;

    private MusicController mMediaController;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private int mCurrentBufferPercentage;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private MediaPlayer.OnInfoListener mOnInfoListener;
    private int mSeekWhenPrepared;  // recording the seek position while preparing
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private boolean mPreparedBeforeStart;
    private Context mContext;


    private MusicCallback videoViewCallback;

    public MusicPlayer(Context context) {
        mContext = context;
        initVideoView();
    }

    public MusicPlayer(Context context, MusicController musicController) {
        mContext = context;
        initVideoView();
        setMediaController(musicController);
    }


    private void initVideoView() {

        mCurrentState = STATE_IDLE;
        mTargetState = STATE_IDLE;
    }

    /**
     * Sets video path.
     *
     * @param path the path of the video.
     */
    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    /**
     * Sets video URI.
     *
     * @param uri the URI of the video.
     */
    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    /**
     * Sets video URI using specific headers.
     *
     * @param uri     the URI of the video.
     * @param headers the headers for the URI request.
     *                Note that the cross domain redirection is allowed by default, but that can be
     *                changed with key/value pairs through the headers parameter with
     *                "android-allow-cross-domain-redirect" as the key and "0" or "1" as the value
     *                to disallow or allow cross domain redirection.
     */
    public void setVideoURI(Uri uri, Map<String, String> headers) {
        mUri = uri;
        mSeekWhenPrepared = 0;
        openAudio();
        if (null != mMediaController) {
            mMediaController.setMsgText(null);
        }

    }


    public void stopPlayback() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;
        }
    }

    private void openAudio() {

        if (mUri == null) {
            // not ready for playback just yet, will try again later
            return;
        }

        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        // we shouldn't clear the target state, because somebody might have
        // called start() previously
        release(false);
        try {
            mMediaPlayer = new MediaPlayer();

            if (mAudioSession != 0) {
                mMediaPlayer.setAudioSessionId(mAudioSession);
            } else {
                mAudioSession = mMediaPlayer.getAudioSessionId();
            }
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mCurrentBufferPercentage = 0;
            mMediaPlayer.setDataSource(mContext, mUri);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.prepareAsync();


            // we don't set the target state here either, but preserve the
            // target state that was there before.
            mCurrentState = STATE_PREPARING;
            attachMediaController();
        } catch (IOException ex) {
            Log.w(TAG, "Unable to open content: " + mUri, ex);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
        }
    }

    public void setMediaController(MusicController controller) {
        mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (mMediaPlayer != null && mMediaController != null) {
            mMediaController.setMediaPlayer(this);
            mMediaController.setEnabled(isInPlaybackState());
            mMediaController.show();
        }
    }


    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            mCurrentState = STATE_PREPARED;

            mCanPause = mCanSeekBack = mCanSeekForward = true;

            mPreparedBeforeStart = true;
            if (mMediaController != null) {
                mMediaController.hideLoading();
            }

            if (mOnPreparedListener != null) {
                mOnPreparedListener.onPrepared(mMediaPlayer);
            }
            if (mMediaController != null) {
                mMediaController.setEnabled(true);
            }


            int seekToPosition = mSeekWhenPrepared;  // mSeekWhenPrepared may be changed after seekTo() call
            if (seekToPosition != 0) {
                seekTo(seekToPosition);
            }

            if (mTargetState == STATE_PLAYING) {
                start();
                if (mMediaController != null) {
                    mMediaController.show();
                }
            } else if (!isPlaying() &&
                    (seekToPosition != 0 || getCurrentPosition() > 0)) {
                if (mMediaController != null) {
                    // Show the media controls when we're paused into a video and make 'em stick.
                    mMediaController.show();
                }
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mCurrentState = STATE_PLAYBACK_COMPLETED;
                    mTargetState = STATE_PLAYBACK_COMPLETED;
                    if (mMediaController != null) {
                        boolean a = mMediaPlayer.isPlaying();
                        int b = mCurrentState;
                        mMediaController.showComplete();
                        // 播放完成后,视频中央会显示一个播放按钮,点击播放按钮会调用start重播,
                        // 但start后竟然又回调到这里,导致第一次点击按钮不会播放视频,需要点击第二次.
                        LogX.d(TAG, String.format("a=%s,b=%d", a, b));
                    }
                    if (mOnCompletionListener != null) {
                        mOnCompletionListener.onCompletion(mMediaPlayer);
                    }
                }
            };

    private MediaPlayer.OnInfoListener mInfoListener =
            new MediaPlayer.OnInfoListener() {
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    boolean handled = false;
                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            LogX.d(TAG, "onInfo MediaPlayer.MEDIA_INFO_BUFFERING_START");
                            if (videoViewCallback != null) {
                                videoViewCallback.onBufferingStart(mMediaPlayer);
                            }
                            if (mMediaController != null) {
                                mMediaController.showLoading();
                            }
                            handled = true;
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            LogX.d(TAG, "onInfo MediaPlayer.MEDIA_INFO_BUFFERING_END");
                            if (videoViewCallback != null) {
                                videoViewCallback.onBufferingEnd(mMediaPlayer);
                            }
                            if (mMediaController != null) {
                                mMediaController.hideLoading();
                            }
                            handled = true;
                            break;
                    }
                    if (mOnInfoListener != null) {
                        return mOnInfoListener.onInfo(mp, what, extra) || handled;
                    }
                    return handled;
                }
            };

    private MediaPlayer.OnErrorListener mErrorListener =
            new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
                    LogX.d(TAG, "Error: " + framework_err + "," + impl_err);
                    mCurrentState = STATE_ERROR;
                    mTargetState = STATE_ERROR;
                    if (mMediaController != null) {
                        mMediaController.showError();
                    }

            /* If an error handler has been supplied, use it and finish. */
                    if (mOnErrorListener != null) {
                        if (mOnErrorListener.onError(mMediaPlayer, framework_err, impl_err)) {
                            return true;
                        }
                    }

            /* Otherwise, pop up an error dialog so the user knows that
             * something bad has happened. Only try and pop up the dialog
             * if we're attached to a window. When we're going away and no
             * longer have a window, don't bother showing the user an error.
             */
//                    if (getWindowToken() != null) {
//                        Resources r = mContext.getResources();
//                        int messageId;
//
//                        if (framework_err == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
//                            messageId = com.android.internal.R.string.VideoView_error_text_invalid_progressive_playback;
//                        } else {
//                            messageId = com.android.internal.R.string.VideoView_error_text_unknown;
//                        }
//
//                        new AlertDialog.Builder(mContext)
//                                .setMessage(messageId)
//                                .setPositiveButton(com.android.internal.R.string.VideoView_error_button,
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int whichButton) {
//                                        /* If we get here, there is no onError listener, so
//                                         * at least inform them that the video is over.
//                                         */
//                                                if (mOnCompletionListener != null) {
//                                                    mOnCompletionListener.onCompletion(mMediaPlayer);
//                                                }
//                                            }
//                                        })
//                                .setCancelable(false)
//                                .show();
//                    }
                    return true;
                }
            };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener =
            new MediaPlayer.OnBufferingUpdateListener() {
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    mCurrentBufferPercentage = percent;
                }
            };

    /**
     * Register a callback to be invoked when the media file
     * is loaded and ready to go.
     *
     * @param l The callback that will be run
     */
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        mOnPreparedListener = l;
    }

    /**
     * Register a callback to be invoked when the end of a media file
     * has been reached during playback.
     *
     * @param l The callback that will be run
     */
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener l) {
        mOnCompletionListener = l;
    }

    /**
     * Register a callback to be invoked when an error occurs
     * during playback or setup.  If no listener is specified,
     * or if the listener returned false, VideoView will inform
     * the user of any errors.
     *
     * @param l The callback that will be run
     */
    public void setOnErrorListener(MediaPlayer.OnErrorListener l) {
        mOnErrorListener = l;
    }

    /**
     * Register a callback to be invoked when an informational event
     * occurs during playback or setup.
     *
     * @param l The callback that will be run
     */
    public void setOnInfoListener(MediaPlayer.OnInfoListener l) {
        mOnInfoListener = l;
    }

    /*
     * release the media player in any state
     */
    private void release(boolean cleartargetstate) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            if (cleartargetstate) {
                mTargetState = STATE_IDLE;
            }
        }
    }

    @Override
    public void start() {
        if (!mPreparedBeforeStart && mMediaController != null) {
            mMediaController.showLoading();
        }

        if (isInPlaybackState()) {
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
            if (this.videoViewCallback != null) {
                this.videoViewCallback.onStart(mMediaPlayer);
            }
        }
        mTargetState = STATE_PLAYING;
    }

    @Override
    public void pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mCurrentState = STATE_PAUSED;
                if (this.videoViewCallback != null) {
                    this.videoViewCallback.onPause(mMediaPlayer);
                }
            }
        }
        mTargetState = STATE_PAUSED;
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        openAudio();
    }

    @Override
    public int getDuration() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getDuration();
        }

        return -1;
    }

    @Override
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            mMediaPlayer.seekTo(msec);
            mSeekWhenPrepared = 0;
        } else {
            mSeekWhenPrepared = msec;
        }
    }

    @Override
    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }

    private boolean isInPlaybackState() {
        return (mMediaPlayer != null &&
                mCurrentState != STATE_ERROR &&
                mCurrentState != STATE_IDLE &&
                mCurrentState != STATE_PREPARING);
    }

    @Override
    public boolean canPause() {
        return mCanPause;
    }

    @Override
    public boolean canSeekBackward() {
        return mCanSeekBack;
    }

    @Override
    public boolean canSeekForward() {
        return mCanSeekForward;
    }

    @Override
    public void closePlayer() {
        release(true);
    }



/*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void switchTitleBar(boolean show) {
        if (mContext instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity)mContext;
            android.support.v7.app.ActionBar supportActionBar = activity.getSupportActionBar();
            if (supportActionBar != null) {
                if (show) {
                    supportActionBar.show();
                } else {
                    supportActionBar.hide();
                }
            }
        }else if (mContext instanceof Activity) {
            Activity activity = (Activity)mContext;
            if(activity.getActionBar() != null) {
                if (show) {
                    activity.getActionBar().show();
                } else {
                    activity.getActionBar().hide();
                }
            }
        }
    }
*/


    public interface MusicCallback {
        void onPause(final MediaPlayer mediaPlayer);

        void onStart(final MediaPlayer mediaPlayer);

        void onBufferingStart(final MediaPlayer mediaPlayer);

        void onBufferingEnd(final MediaPlayer mediaPlayer);
    }

    public void setVideoViewCallback(MusicCallback callback) {
        this.videoViewCallback = callback;
    }
}
