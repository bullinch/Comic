package com.example.onlinecomic.ui.fragment.browser;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Source;
import com.example.onlinecomic.R;
import com.example.onlinecomic.callback.DiffComicCallback;
import com.example.onlinecomic.databinding.FragmentRecentUpdateBinding;
import com.example.onlinecomic.model.SourceModel;
import com.example.onlinecomic.util.IntentUtils;
import com.example.onlinecomic.viewmodel.BrowserViewModel;

public class RecentUpdateFragment extends BaseFragment<FragmentRecentUpdateBinding, BrowserViewModel> {
    private MyAdapter mAdapter;

    public static RecentUpdateFragment newInstance(Source source) {
        Bundle bundle = new Bundle();
        RecentUpdateFragment fragment = new RecentUpdateFragment();
        bundle.putParcelable("source", source);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_recent_update;
    }

    @Override
    protected void initView() {
        Source source = getArguments().getParcelable("source");
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new MyAdapter();
        viewDataBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.base_layout_loading);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Comic comic = (Comic) adapter.getData().get(position);
                comic.sourceId = source.id;
                comic.sourceName = source.name;
                comic.sourceUrl = source.hostUrl;
                IntentUtils.intentChapterActivity(_mActivity, comic);
            }
        });

        viewModel.recentUpdateList.observe(getViewLifecycleOwner(), comics -> {
            Logger.i("recentUpdateList: "+comics.size());
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
