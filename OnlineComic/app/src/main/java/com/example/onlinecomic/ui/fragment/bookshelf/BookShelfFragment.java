package com.example.onlinecomic.ui.fragment.bookshelf;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.example.library_base.base.BaseApplication;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.callback.DialogClickListener;
import com.example.onlinecomic.databinding.FragmentBookshelfBinding;
import com.example.onlinecomic.databinding.ItemFavoriteBinding;
import com.example.onlinecomic.ui.activity.ComicReadActivity;
import com.example.onlinecomic.util.ActivityUtils;
import com.example.onlinecomic.util.DialogUtils;
import com.example.onlinecomic.util.TimeUtils;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;

import java.util.List;

public class BookShelfFragment extends BaseFragment<FragmentBookshelfBinding, BookShelfViewModel> {

    private MyAdapter myAdapter;

    public static BookShelfFragment newInstance() {
        BookShelfFragment fragment = new BookShelfFragment();
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
        viewModel.favComicsMLD.observe(this, new Observer<List<Comic>>() {
            @Override
            public void onChanged(List<Comic> comics) {
                Logger.i("Comic.size()-->" + comics.size());
                myAdapter.setList(comics);
                viewDataBinding.swipeLayout.setRefreshing(false);
            }
        });
        viewDataBinding.swipeLayout.setRefreshing(true);
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
            ActivityUtils.intentActivity(_mActivity, ComicReadActivity.class, comic);
        });
        myAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            DialogUtils.showDialog(_mActivity, "提示", "确认要取消收藏", new DialogClickListener() {
                @Override
                public void onPositive() {
                    viewModel.favorite((Comic) adapter.getData().get(position));
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
