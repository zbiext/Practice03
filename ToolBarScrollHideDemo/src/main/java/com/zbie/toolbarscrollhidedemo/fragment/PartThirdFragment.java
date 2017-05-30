package com.zbie.toolbarscrollhidedemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbie.toolbarscrollhidedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 涛 on 2017/5/29 0029.
 * 项目名           AppStore
 * 包名             com.zbie.toolbarscrollhidedemo.fragment
 * 创建时间         2017/05/29 03:13
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            ThirdActivity 所需要的 fragment
 */
public class PartThirdFragment extends Fragment {

    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";

    public static PartThirdFragment createInstance(int itemsCount) {
        PartThirdFragment partThreeFragment = new PartThirdFragment();
        Bundle            bundle            = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_part_three, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewApadter recyclerAdapter = new RecyclerViewApadter(createItemList());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        Bundle       bundle   = getArguments();
        if (bundle != null) {
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            for (int i = 0; i < itemsCount; i++) {
                itemList.add("Item " + i);
            }
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
}
