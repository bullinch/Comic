package com.example.onlinecomic.ui.fragment.comic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.base.BaseApplication;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.library_comic.bean.People;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.databinding.FragmentComicReadBinding;
import com.example.onlinecomic.databinding.ItemComicViewPagerBinding;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.viewmodel.ComicDetailsViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class ComicReadFragment extends BaseFragment<FragmentComicReadBinding, ComicDetailsViewModel> {
    private static final String TAG = "ComicReadFragment";

    private MyAdapter myAdapter;
    private MyPagerAdapter mPagerAdapter;
    private BookShelfViewModel mBookShelfViewModel;
    private Comic mComic;
    private Chapter mChapter;

    public static ComicReadFragment newInstance(Chapter chapter) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Chapter", chapter);
        ComicReadFragment fragment = new ComicReadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_comic_read;
    }

    @Override
    protected void initView() {
        try {
            mBookShelfViewModel = new ViewModelProvider((MyApplication) _mActivity.getApplication()).get(BookShelfViewModel.class);
            myAdapter = new MyAdapter();
            viewDataBinding.setClick(new ClickProxy());
            viewDataBinding.recyclerComicPage.setLayoutManager(new LinearLayoutManager(_mActivity));
            viewDataBinding.recyclerComicPage.addItemDecoration(new RecyclerItemDecoration(5));
            viewDataBinding.recyclerComicPage.setAdapter(myAdapter);
            viewDataBinding.recyclerComicPage.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if(layoutManager instanceof LinearLayoutManager) {
                        int firstItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        int lastItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        Logger.i(firstItemPosition+"_"+lastItemPosition);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                            mComic.setReadPage(firstItemPosition);
                            mBookShelfViewModel.history(mComic);
                        }
                    }
                }
            });

            mPagerAdapter = new MyPagerAdapter();
            viewDataBinding.viewpagerPage.setVisibility(View.GONE);
            viewDataBinding.viewpagerPage.setAdapter(mPagerAdapter);
            viewDataBinding.viewpagerPage.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
            viewDataBinding.viewpagerPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    Logger.i(position+"");
                }
            });

            mChapter = (Chapter) getArguments().getParcelable("Chapter");
            viewModel.requestPages(mChapter.getHrefUrl(), null);
            addObserveLiveData();
            handleComicData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ComicDetailsViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

    private void handleComicData() {
        mComic = viewModel.readComicMutableLiveData.getValue();
        mComic.setHistoryStamp(System.currentTimeMillis());
        if(mChapter != null) {
            mComic.setReadChapterUrl(mChapter.getHrefUrl());
            mComic.setReadChapter(mChapter.getName());
        }
        mBookShelfViewModel.history(mComic);
    }

    private void addObserveLiveData() {
        viewModel.pageMutableLiveData.observe(this, pages -> {
            myAdapter.setList(pages);
            mPagerAdapter.setList(pages);
        });
    }

    class MyAdapter extends BaseQuickAdapter<Page, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_comic_page);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Page page) {
            ImageView ivPage = baseViewHolder.findView(R.id.iv_item_page);
            RequestOptions options = new RequestOptions().fitCenter().placeholder(R.drawable.loading)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(_mActivity).load(page.getImgUrl()).apply(options).into(ivPage);
        }
    }

    public class MyPagerAdapter extends BaseQuickAdapter<Page, BaseViewHolder> {

        public MyPagerAdapter() {
            super(R.layout.item_comic_view_pager);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Page page) {
            PhotoView photoView = baseViewHolder.findView(R.id.pv_comic);
            RequestOptions options = new RequestOptions().fitCenter().placeholder(R.drawable.loading)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(_mActivity)
                    .load(page.getImgUrl()).apply(options)
                    .into(photoView);
        }
    }

    public class ClickProxy {
        public void expandController(View v) {
            viewDataBinding.viewpagerPage.setVisibility(View.VISIBLE);
            viewDataBinding.recyclerComicPage.setVisibility(View.GONE);
        }
    }
}
