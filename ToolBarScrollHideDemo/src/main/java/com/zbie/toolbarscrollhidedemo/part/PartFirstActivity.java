package com.zbie.toolbarscrollhidedemo.part;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zbie.toolbarscrollhidedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 涛 on 2017/05/28.
 * 包名             com.zbie.toolbarscrollhidedemo
 * 创建时间         2017/05/28 15:50
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            ToolBar 滑动隐藏系列一
 * 列表滚动的时候显示或者隐藏Toolbar(第一部分)
 * http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0317/2612.html
 */
public class PartFirstActivity extends AppCompatActivity {

    private static final String TAG = "PartFirstActivity";

    private ImageButton mFabButton;
    private Toolbar     mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_first);

        initToolBar();
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        initRecyclerView();
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(initDataList());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
    }

    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)); // mToolbar 这里为什么不调用start()？
        FrameLayout.LayoutParams lp              = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        // 需要将margin也计算进去，不然fab不能完全隐藏
        int                      fabBottomMargin = lp.bottomMargin;
        Log.d(TAG, "hideViews: fabBottomMargin-px = " + fabBottomMargin);
        Log.d(TAG, "hideViews: fabBottomMargin-dp = " + px2dp(fabBottomMargin));
        mFabButton.animate().translationY(mFabButton.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)); // mToolbar 这里为什么不调用start()？
        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private List<String> initDataList() {
        List<String> itemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            itemList.add("Item " + i);
        }
        return itemList;
    }

    /**
     * recyclerview 的数据适配器
     */
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_HEADER = 2;
        private static final int TYPE_ITEM   = 1;
        private List<String> mItemList;

        public RecyclerViewAdapter(List<String> itemList) {
            mItemList = itemList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            if (viewType == TYPE_ITEM) {
                return new RecyclerItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false));
            } else if (viewType == TYPE_HEADER) {
                return new RecyclerHeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false));
            }
            throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position != 0) {
                RecyclerItemViewHolder itemHolder = (RecyclerItemViewHolder) holder;
                String                 itemText   = mItemList.get(position - 1);
                //String                 itemText   = mItemList.get(position);
                itemHolder.setItemText(itemText);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }

        public int getBasicItemCount() {
            return mItemList == null ? 0 : mItemList.size();
        }

        @Override
        public int getItemCount() {
            if (mItemList != null) {
                return getBasicItemCount() + 1;
            }
            return 1;
        }
    }

    /**
     * Item 类型的 viewholder
     */
    private class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTextView;

        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            mItemTextView = (TextView) parent.findViewById(R.id.itemTextView);
        }

        //public RecyclerItemViewHolder newInstance(View parent) {
        //    TextView itemTextView = (TextView) parent.findViewById(R.id.itemTextView);
        //    return new RecyclerItemViewHolder(parent, itemTextView);
        //}

        public void setItemText(CharSequence text) {
            mItemTextView.setText(text);
        }
    }

    /**
     * header 类型的 viewholder
     */
    private class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {

        public RecyclerHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

        private static final int HIDE_THRESHOLD = 20;

        private int     mScrolledDistance = 0;
        private boolean mControlsVisible  = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //super.onScrolled(recyclerView, dx, dy);
            // 还有点bug-如果你的滑动距离的触发值太小，在隐藏Toolbar的时候会在列表的顶部留下一段空白区域（最开始，随着滚动空白区域会消失）
            // 解决办法 ： 只需检测第一个item是否可见，只有当不可见的时候才执行上面的逻辑 findFirstVisibleItemPosition
            // RecyclerView和LinearLayoutManager经常用到的几个方法
            // https://my.oschina.net/lengwei/blog/691370
            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if (firstVisibleItem == 0) {
                if (!mControlsVisible) {
                    onShow();
                    mControlsVisible = true;
                }
            } else {
                if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                    onHide();
                    mControlsVisible = false;
                    mScrolledDistance = 0;
                } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                    onShow();
                    mControlsVisible = true;
                    mScrolledDistance = 0;
                }
            }
            if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
                mScrolledDistance += dy;
            }
        }

        public abstract void onHide();

        public abstract void onShow();
    }

    private int px2dp(int pxValue) {
        Log.d(TAG, "density = " + getResources().getDisplayMetrics().density);
        return (int) (pxValue / (getResources().getDisplayMetrics().density) + .5f);
    }
}
