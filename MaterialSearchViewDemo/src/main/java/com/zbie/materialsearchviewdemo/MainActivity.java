package com.zbie.materialsearchviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zbie.materialsearchviewdemo.activity.ColoredActivity;
import com.zbie.materialsearchviewdemo.activity.DefaultActivity;
import com.zbie.materialsearchviewdemo.activity.InputTypeActivity;
import com.zbie.materialsearchviewdemo.activity.StickyActivity;
import com.zbie.materialsearchviewdemo.activity.TabActivity;
import com.zbie.materialsearchviewdemo.activity.VoiceActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button defaultButton;
    private Button themedButton;
    private Button voiceButton;
    private Button stickyButton;
    private Button tabButton;
    private Button inputTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultButton = (Button) findViewById(R.id.button_default);
        themedButton = (Button) findViewById(R.id.button_themed);
        voiceButton = (Button) findViewById(R.id.button_voice);
        stickyButton = (Button) findViewById(R.id.button_sticky);
        tabButton = (Button) findViewById(R.id.button_tab);
        inputTypeButton = (Button) findViewById(R.id.button_input_type);

        defaultButton.setOnClickListener(this);
        themedButton.setOnClickListener(this);
        voiceButton.setOnClickListener(this);
        stickyButton.setOnClickListener(this);
        tabButton.setOnClickListener(this);
        inputTypeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button_default:
                intent = new Intent(this, DefaultActivity.class);
                break;
            case R.id.button_themed:
                intent = new Intent(this, ColoredActivity.class);
                break;
            case R.id.button_voice:
                intent = new Intent(this, VoiceActivity.class);
                break;
            case R.id.button_sticky:
                intent = new Intent(this, StickyActivity.class);
                break;
            case R.id.button_tab:
                intent = new Intent(this, TabActivity.class);
                break;
            case R.id.button_input_type:
                intent = new Intent(this, InputTypeActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
