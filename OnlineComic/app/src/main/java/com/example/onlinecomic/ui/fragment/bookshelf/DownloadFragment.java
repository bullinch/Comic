package com.example.onlinecomic.ui.fragment.bookshelf;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.callback.DialogClickListener;
import com.example.onlinecomic.databinding.FragmentDownloadBinding;
import com.example.onlinecomic.databinding.ItemDownloadBinding;
import com.example.onlinecomic.databinding.ItemFavoriteBinding;
import com.example.onlinecomic.ui.fragment.MainFragment;
import com.example.onlinecomic.ui.fragment.browser.SourceBrowserFragment;
import com.example.onlinecomic.util.DialogUtils;
import com.example.onlinecomic.util.IntentUtils;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;

import java.util.List;
import java.util.Objects;

public class DownloadFragment extends BaseFragment<FragmentDownloadBinding, BookShelfViewModel> {

    private DownloadAdapter mDownloadAdapter;

    public static DownloadFragment newInstance() {
        DownloadFragment fragment = new DownloadFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_download;
    }

    @Override
    protected void initView() {
        initAdapter();
        viewDataBinding.swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        viewDataBinding.swipeLayout.setOnRefreshListener(() -> {
            viewModel.refreshDownloadComics();
        });
        viewDataBinding.recycler.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        viewDataBinding.recycler.addItemDecoration(new RecyclerItemDecoration(5));
        viewDataBinding.recycler.setAdapter(mDownloadAdapter);
        viewModel.downloadComicsMLD.observe(this, comics -> {
            Logger.i("Comic.size()-->" + comics.size());
            mDownloadAdapter.setList(comics);
            viewDataBinding.swipeLayout.setRefreshing(false);
        });
        viewDataBinding.swipeLayout.setRefreshing(true);
        viewModel.refreshDownloadComics();
    }

    private void initAdapter() {
        mDownloadAdapter = new DownloadAdapter();
        mDownloadAdapter.setOnItemClickListener((adapter, view, position) -> {
            Comic comic = (Comic) adapter.getData().get(position);
            ((MainFragment) getParentFragment().getParentFragment()).
                    startBrotherFragment(DownloadTaskFragment.newInstance(comic));
        });
        mDownloadAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            DialogUtils.showDialog(_mActivity, "提示", "确认要删除此下载漫画", new DialogClickListener() {
                @Override
                public void onPositive() {
                    viewModel.download((Comic) adapter.getData().get(position), false);
                }
            });
            return false;
        });
    }

    @Override
    protected BookShelfViewModel getViewModel() {
        return getStoreViewModel(MyApplication.getApp());
    }

    class DownloadAdapter extends BaseQuickAdapter<Comic, BaseViewHolder> {

        public DownloadAdapter() {
            super(R.layout.item_download);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Comic comic) {
            Logger.i(comic.toString());
            baseViewHolder.setText(R.id.tv_title, comic.getTitle());
            baseViewHolder.setText(R.id.tv_source, comic.getSourceName());
            ImageView imageView = baseViewHolder.findView(R.id.iv_cover);
            RequestOptions options = new RequestOptions().fitCenter().placeholder(R.drawable.loading)
                    .override(MyApplication.mWidthPixels / 3, MyApplication.mHeightPixels / 4)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(this.getContext()).load(comic.getCoverUrl()).apply(options)
                    .into(imageView);
        }
    }
}
