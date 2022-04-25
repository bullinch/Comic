package com.example.onlinecomic.ui.fragment.bookshelf;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.callback.DialogClickListener;
import com.example.onlinecomic.databinding.FragmentBookshelfBinding;
import com.example.onlinecomic.databinding.ItemFavoriteBinding;
import com.example.onlinecomic.util.IntentUtils;
import com.example.onlinecomic.util.DialogUtils;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;

import java.util.List;

public class FavoriteFragment extends BaseFragment<FragmentBookshelfBinding, BookShelfViewModel> {

    private MyAdapter myAdapter;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    protected void initView() {
        Logger.i("initView");
        initAdapter();
        viewDataBinding.swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        viewDataBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshFavoriteComics();
            }
        });
        viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        viewDataBinding.recycler.addItemDecoration(new RecyclerItemDecoration(5));
        viewDataBinding.recycler.setAdapter(myAdapter);
        viewModel.favComicsMLD.observe(this, comics -> {
            Logger.i("Comic.size()-->" + comics.size());
            myAdapter.setList(comics);
            viewDataBinding.swipeLayout.setRefreshing(false);
        });
        viewDataBinding.swipeLayout.setRefreshing(true);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        Logger.i("onLazyInitView");
        viewModel.refreshFavoriteComics();
    }

    @Override
    protected BookShelfViewModel getViewModel() {
        return getStoreViewModel((MyApplication) _mActivity.getApplication());
    }

    private void initAdapter() {
        myAdapter = new MyAdapter();
        myAdapter.setOnItemClickListener((adapter, view, position) -> {
            Comic comic = (Comic) adapter.getData().get(position);
            IntentUtils.intentChapterActivity(_mActivity,  comic);
        });
        myAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            DialogUtils.showDialog(_mActivity, "提示", "确认要取消收藏", new DialogClickListener() {
                @Override
                public void onPositive() {
                    viewModel.deleteFavorite((Comic) adapter.getData().get(position));
                }
            });
            return false;
        });
    }

    class MyAdapter extends BaseQuickAdapter<Comic, BaseDataBindingHolder<ItemFavoriteBinding>> {

        public MyAdapter() {
            super(R.layout.item_favorite);
        }

        @Override
        protected void convert(BaseDataBindingHolder<ItemFavoriteBinding> baseDataBindingHolder, Comic comic) {
            ItemFavoriteBinding binding = baseDataBindingHolder.getDataBinding();
            binding.setComic(comic);
//            binding.tvUpdateTime.setText(TimeUtils.millis2String(comic.getFavoriteStamp()));
            Glide.with(binding.ivCover.getContext()).load(comic.getCoverUrl()).into(binding.ivCover);
        }
    }

}
