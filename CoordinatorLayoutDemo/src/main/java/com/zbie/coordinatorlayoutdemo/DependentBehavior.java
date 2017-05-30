package com.zbie.coordinatorlayoutdemo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 涛 on 2017/5/12 0012.
 * 项目名           Practice03
 * 包名             com.zbie.coordinatorlayoutdemo
 * 创建时间         2017/05/12 00:53
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            http://www.jianshu.com/p/6394d738713c TODO 第二种behavior还没弄
 */
public class DependentBehavior extends CoordinatorLayout.Behavior<View> {

    private int initDisX = 0;

    public DependentBehavior() {
    }

    public DependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInitDisX(int initDisX) {
        this.initDisX = initDisX;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //如果dependency的类型是ImageView，则就可以被child依赖
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //当dependency发生移动时，计算出child应该偏移的距离，然后让child进行偏移
        int offsetX = (dependency.getLeft() - child.getLeft()) - initDisX;
        int offsetY = dependency.getTop() - child.getTop();
        child.offsetLeftAndRight(offsetX);
        child.offsetTopAndBottom(offsetY);
        return true;
    }
}
