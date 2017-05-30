package com.zbie.toolbarscrollhidedemo.part;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbie.toolbarscrollhidedemo.R;
import com.zbie.toolbarscrollhidedemo.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.zbie.toolbarscrollhidedemo.R.id.recycler_view;

/**
 * Created by 涛 on 2017/05/29.
 * 包名             com.zbie.toolbarscrollhidedemo.part
 * 创建时间         2017/05/29 1:31
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            ToolBar 滑动隐藏系列二
 * 列表滚动的时候显示或者隐藏Toolbar(第二部分)
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0319/2618.html
 */
public class PartSecondActivity extends AppCompatActivity {

    private static final String TAG = "PartSecondActivity";

    private LinearLayout mToolbarContainer;
    private Toolbar      mToolbar;
    private int mToolbarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_second);

        mToolbarContainer = (LinearLayout) findViewById(R.id.toolbar_Container);
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbarHeight = Utils.getToolbarHeight(this);
        Log.d(TAG, "initToolbar: mToolbarHeight = " + mToolbarHeight);
        Log.d(TAG, "initToolbar: mToolbar.getHeight = " + mToolbar.getHeight());
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(recycler_view);

        int paddingTop = Utils.getToolbarHeight(this) + Utils.getTabsHeight(this);
        recyclerView.setPadding(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3/*, StaggeredGridLayoutManager.VERTICAL*/));
        recyclerView.setAdapter(new RecyclerViewApadter(initDataList()));
        recyclerView.addOnScrollListener(new HidingScrollListener(this) {
            @Override
            public void onMoved(int distance) {
                mToolbarContainer.setTranslationY(-distance);
            }

            @Override
            public void onShow() {
                mToolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void onHide() {
                Log.d(TAG, "initToolbar: mToolbar.getHeight = " + mToolbar.getHeight());
                mToolbarContainer.animate().translationY(-mToolbarHeight).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });
    }

    private List<String> initDataList() {
        List<String> itemList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            itemList.add("Item " + i);
        }
        return itemList;
    }

    private class RecyclerViewApadter extends RecyclerView.Adapter<RecyclerItemViewHolder> {

        private List<String> mItemList;

        public RecyclerViewApadter(List<String> itemList) {
            mItemList = itemList;
        }

        @Override
        public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {
            holder.setItemText(mItemList.get(position));
        }

        @Override
        public int getItemCount() {
            if (mItemList != null) {
                return mItemList.size();
            }
            return 0;
        }
    }

    private class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTextView;

        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            mItemTextView = (TextView) parent.findViewById(R.id.itemTextView);
        }

        public void setItemText(CharSequence text) {
            mItemTextView.setText(text);
        }
    }

    private abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

        private static final float HIDE_THRESHOLD = 10;
        private static final float SHOW_THRESHOLD = 70;

        private int mToolbarOffset = 0;
        private boolean mControlsVisible = true;
        private int mToolbarHeight;
        private int mTotalScrolledDistance;

        public HidingScrollListener(Context context) {
            mToolbarHeight = Utils.getToolbarHeight(context);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                if(mTotalScrolledDistance < mToolbarHeight) {
                    setVisible();
                } else {
                    if (mControlsVisible) {
                        if (mToolbarOffset > HIDE_THRESHOLD) {
                            setInvisible();
                        } else {
                            setVisible();
                        }
                    } else {
                        if ((mToolbarHeight - mToolbarOffset) > SHOW_THRESHOLD) {
                            setVisible();
                        } else {
                            setInvisible();
                        }
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            clipToolbarOffset();
            onMoved(mToolbarOffset);

            if ((mToolbarOffset < mToolbarHeight && dy > 0) || (mToolbarOffset > 0 && dy < 0)) {
                mToolbarOffset += dy;
            }

            if (mTotalScrolledDistance < 0) {
                mTotalScrolledDistance = 0;
            } else {
                mTotalScrolledDistance += dy;
            }
        }

        private void clipToolbarOffset() {
            if (mToolbarOffset > mToolbarHeight) {
                mToolbarOffset = mToolbarHeight;
            } else if (mToolbarOffset < 0) {
                mToolbarOffset = 0;
            }
        }

        private void setVisible() {
            if(mToolbarOffset > 0) {
                onShow();
                mToolbarOffset = 0;
            }
            mControlsVisible = true;
        }

        private void setInvisible() {
            if(mToolbarOffset < mToolbarHeight) {
                onHide();
                mToolbarOffset = mToolbarHeight;
            }
            mControlsVisible = false;
        }

        public abstract void onMoved(int distance);
        public abstract void onShow();
        public abstract void onHide();
    }
}
