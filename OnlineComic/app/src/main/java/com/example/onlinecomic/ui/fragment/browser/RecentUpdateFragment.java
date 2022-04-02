package com.example.onlinecomic.ui.fragment.browser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.callback.DiffComicCallback;
import com.example.onlinecomic.databinding.FragmentRecentUpdateBinding;
import com.example.onlinecomic.ui.activity.ComicReadActivity;
import com.example.onlinecomic.ui.fragment.comic.ComicChapterFragment;
import com.example.onlinecomic.util.ActivityUtils;
import com.example.onlinecomic.viewmodel.BrowserViewModel;

import java.util.List;

public class RecentUpdateFragment extends BaseFragment<FragmentRecentUpdateBinding, BrowserViewModel> {
    private MyAdapter mAdapter;

    public static RecentUpdateFragment newInstance() {
        return new RecentUpdateFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_recent_update;
    }

    @Override
    protected void initView() {
        setLoadSir(viewDataBinding.recyclerView);
        showLoading();
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new MyAdapter();
        viewDataBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Comic comic = (Comic) adapter.getData().get(position);
                ActivityUtils.intentActivity(_mActivity, ComicReadActivity.class, comic);
            }
        });

        viewModel.recentUpdateList.observe(getViewLifecycleOwner(), comics -> {
            Logger.i("recentUpdateList: "+comics.size());
            showContent();
            mAdapter.setDiffNewData(comics);
        });
        mAdapter.setDiffCallback(new DiffComicCallback());
    }

    @Override
    protected BrowserViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    class MyAdapter extends BaseQuickAdapter<Comic, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_recent_udapte);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Comic comic) {
            baseViewHolder.setText(R.id.tv_title, comic.getTitle());
//            baseViewHolder.setText(R.id.tv_update_time, comic.getComicUrl());
            Glide.with(_mActivity).load(comic.getCoverUrl()).into((ImageView) baseViewHolder.getView(R.id.iv_cover));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
