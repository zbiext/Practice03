package com.zbie.toolbarscrollhidedemo.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.zbie.toolbarscrollhidedemo.Utils;

/**
 * Created by 涛 on 2017/5/29 0029.
 * 项目名           AppStore
 * 包名             com.zbie.toolbarscrollhidedemo.behavior
 * 创建时间         2017/05/29 03:04
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class FABBehavior extends FloatingActionButton.Behavior {

    private int mToolbarHeight;

    public FABBehavior(Context context, AttributeSet attrs) {
        super();
        mToolbarHeight = Utils.getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        return super.layoutDependsOn(parent, fab, dependency) || (dependency instanceof AppBarLayout);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        boolean returnValue = super.onDependentViewChanged(parent, fab, dependency);
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp               = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            int                            fabBottomMargin  = lp.bottomMargin;
            int                            distanceToScroll = fab.getHeight() + fabBottomMargin;
            float                          ratio            = dependency.getY() / (float) mToolbarHeight;
            fab.setTranslationY(-distanceToScroll * ratio);
        }
        return returnValue;
    }
}
