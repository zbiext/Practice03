package com.zbie.zbiewallerpaper;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.io.IOException;

import static android.app.WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER;
import static android.app.WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT;

/**
 * Created by 涛 on 2017/05/25.
 * 包名             com.zbie.zbiewallerpaper
 * 创建时间         2017/05/25 20:22
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class VideoLiveWallpaper extends WallpaperService {

    private static final String TAG                  = "VideoLiveWallpaper";
    public static final  String KEY_ACTION           = "voice_action";
    public static final  int    ACTION_VOICE_SILENCE = 110;
    public static final  int    ACTION_VOICE_NORMAL  = 111;

    public static String VIDEO_PARAMS_CONTROL_ACTION = "";

    @Override
    public Engine onCreateEngine() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "到群里发个红包吧~\r\n      ^ 0 ^", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
        return new VideoEngine();
    }

    public static void voiceSilence(Context context) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, VideoLiveWallpaper.ACTION_VOICE_SILENCE);
        context.sendBroadcast(intent);
    }

    public static void voiceNormal(Context context) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, VideoLiveWallpaper.ACTION_VOICE_NORMAL);
        context.sendBroadcast(intent);
    }

    public static void setToWallPaper(Context context) {
        final Intent intent = new Intent(ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(context, VideoLiveWallpaper.class));
        context.startActivity(intent);
    }

    private class VideoEngine extends Engine {

        private MediaPlayer       mMediaPlayer;
        private BroadcastReceiver mVideoParamsControlReceiver;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.d(TAG, "VideoEngine#onCreate");
            VIDEO_PARAMS_CONTROL_ACTION = getApplicationContext().getPackageName();
            registerReceiver(mVideoParamsControlReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d(TAG, "BroadcastReceiver#onReceive");
                    switch (intent.getIntExtra(KEY_ACTION, -1)) {
                        case ACTION_VOICE_NORMAL:
                            mMediaPlayer.setVolume(1.0f, 1.0f);
                            break;
                        case ACTION_VOICE_SILENCE:
                            mMediaPlayer.setVolume(0, 0);
                            break;

                    }
                }
            }, new IntentFilter(VIDEO_PARAMS_CONTROL_ACTION));
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy");
            unregisterReceiver(mVideoParamsControlReceiver);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.d(TAG, "VideoEngine#onVisibilityChanged visible = " + visible);
            if (visible) {
                mMediaPlayer.start();
            } else {
                mMediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "VideoEngine#onSurfaceCreated");
            super.onSurfaceCreated(holder);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setSurface(holder.getSurface());
            try {
                AssetManager        assetMg        = getApplicationContext().getAssets();
                AssetFileDescriptor fileDescriptor = assetMg.openFd("meinv.mp4");
                mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(0, 0);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "VideoEngine#onSurfaceChanged");
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "VideoEngine#onSurfaceDestroyed");
            super.onSurfaceDestroyed(holder);
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
