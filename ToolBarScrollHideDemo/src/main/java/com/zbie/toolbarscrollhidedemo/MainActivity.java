package com.zbie.toolbarscrollhidedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.zbie.toolbarscrollhidedemo.part.PartFirstActivity;
import com.zbie.toolbarscrollhidedemo.part.PartSecondActivity;
import com.zbie.toolbarscrollhidedemo.part.PartThirdActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button partFirstButton;
    private Button partSecondButton;
    private Button partThirdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        partFirstButton = (Button) findViewById(R.id.partFirstButton);
        partSecondButton = (Button) findViewById(R.id.partSecondButton);
        partThirdButton = (Button) findViewById(R.id.partThirdButton);

        partFirstButton.setOnClickListener(this);
        partSecondButton.setOnClickListener(this);
        partThirdButton.setOnClickListener(this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.partFirstButton:
                startActivity(PartFirstActivity.class);
                break;
            case R.id.partSecondButton:
                startActivity(PartSecondActivity.class);
                break;
            case R.id.partThirdButton:
                startActivity(PartThirdActivity.class);
                break;
        }
    }

    private void startActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
