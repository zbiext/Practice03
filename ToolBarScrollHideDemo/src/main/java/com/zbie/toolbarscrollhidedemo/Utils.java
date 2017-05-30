package com.zbie.toolbarscrollhidedemo;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by 涛 on 2017/5/28 0028.
 * 项目名           AppStore
 * 包名             com.zbie.toolbarscrollhidedemo
 * 创建时间         2017/05/28 17:25
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int              toolbarHeight    = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }
}
