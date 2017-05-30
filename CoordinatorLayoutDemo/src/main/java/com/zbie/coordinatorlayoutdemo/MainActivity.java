package com.zbie.coordinatorlayoutdemo;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivDependency;
    private ImageView ivChild;

    private int mLastX;
    private int mLastY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ivDependency = (ImageView) findViewById(R.id.iv_dependency);
        ivDependency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 记录触摸点坐标
                        mLastX = x;
                        mLastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 计算偏移量
                        int offsetX = x - mLastX;
                        int offsetY = y - mLastY;
                        ivDependency.offsetLeftAndRight(offsetX);
                        ivDependency.offsetTopAndBottom(offsetY);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
        ivChild = (ImageView) findViewById(R.id.iv_child);
        ivChild.postDelayed(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams layoutParams      = (CoordinatorLayout.LayoutParams)ivChild.getLayoutParams();
                final DependentBehavior        dependentBehavior = (DependentBehavior) layoutParams.getBehavior();
                dependentBehavior.setInitDisX(ivDependency.getLeft() - ivChild.getLeft());
            }
        }, 100);
    }
}
