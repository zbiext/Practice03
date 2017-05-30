package com.zbie.toolbarscrollhidedemo.part;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zbie.toolbarscrollhidedemo.R;
import com.zbie.toolbarscrollhidedemo.fragment.PartThirdFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 涛 on 2017/05/29.
 * 包名             com.zbie.toolbarscrollhidedemo.part
 * 创建时间         2017/05/29 2:19
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            ToolBar 滑动隐藏系列三
 * 列表滚动的时候显示或者隐藏Toolbar(第三部分)，使用 CoordinatorLayout + Behavior
 * https://mzgreen.github.io/2015/06/23/How-to-hideshow-Toolbar-when-list-is-scrolling%28part3%29/
 */
public class PartThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_third);

        initToolbar();
        initViewPagerAndTabs();
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initViewPagerAndTabs() {
        ViewPager    viewPager    = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(PartThirdFragment.createInstance(20), getString(R.string.tab_1));
        pagerAdapter.addFragment(PartThirdFragment.createInstance(4), getString(R.string.tab_2));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList      = new ArrayList<>();
        private final List<String>   fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
