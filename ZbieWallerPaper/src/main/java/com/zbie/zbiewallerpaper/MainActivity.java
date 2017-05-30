package com.zbie.zbiewallerpaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CheckBox) findViewById(R.id.id_cb_voice)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    VideoLiveWallpaper.voiceSilence(getApplicationContext());
                } else {
                    VideoLiveWallpaper.voiceNormal(getApplicationContext());
                }
            }
        });
    }

    public void setWallerPaper(View v) {
        VideoLiveWallpaper.setToWallPaper(this);
    }
}
