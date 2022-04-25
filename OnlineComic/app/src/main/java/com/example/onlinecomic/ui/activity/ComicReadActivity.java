package com.example.onlinecomic.ui.activity;

import android.os.Parcelable;
import android.text.TextUtils;
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
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.activity.BaseActivity;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.databinding.ActivityComicReadBinding;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.viewmodel.ReadViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ComicReadActivity extends BaseActivity<ActivityComicReadBinding, ReadViewModel> {

    private ReadAdapter mReadAdapter;
    private ReadPagerAdapter mReadPagerAdapter;
    private BookShelfViewModel mBookShelfViewModel;
    private Comic mComic;
    private Chapter mChapter;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_comic_read;
    }

    @Override
    protected void initView() {
        try {
            mBookShelfViewModel = new ViewModelProvider(MyApplication.getApp()).get(BookShelfViewModel.class);
            mReadAdapter = new ReadAdapter();
            viewDataBinding.setClick(new ClickProxy());
            viewDataBinding.recyclerComicPage.setLayoutManager(new LinearLayoutManager(this));
            viewDataBinding.recyclerComicPage.addItemDecoration(new RecyclerItemDecoration(5));
            viewDataBinding.recyclerComicPage.setAdapter(mReadAdapter);
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

            mReadPagerAdapter = new ReadPagerAdapter();
            viewDataBinding.viewpagerPage.setVisibility(View.GONE);
            viewDataBinding.viewpagerPage.setAdapter(mReadPagerAdapter);
            viewDataBinding.viewpagerPage.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
            viewDataBinding.viewpagerPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    Logger.i(position+"");
                }
            });

            mChapter = (Chapter) getIntent().getParcelableExtra("chapter");
            if(mChapter != null) {
                viewModel.requestPages(mChapter.getHrefUrl(), null);
                mBookShelfViewModel.mCurrentReadChapter.setValue(mChapter);
            } else {
                List<Page> pages = getIntent().getParcelableArrayListExtra("pages");
                mReadAdapter.setList(pages);
                mReadPagerAdapter.setList(pages);
            }
            addObserveLiveData();
            handleComicData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void handleComicData() {
        mComic = mBookShelfViewModel.mCurrentReadComic.getValue();
        if(mComic != null) {
            Logger.i(mComic.toString());
            if (mChapter != null) {
                mComic.setReadChapterUrl(mChapter.getHrefUrl());
                mComic.setReadChapter(mChapter.getName());
            }
            mBookShelfViewModel.history(mComic);
        }
    }

    private void addObserveLiveData() {
        viewModel.pageMutableLiveData.observe(this, pages -> {
            mReadAdapter.setList(pages);
            mReadPagerAdapter.setList(pages);
        });
    }

    class ReadAdapter extends BaseQuickAdapter<Page, BaseViewHolder> {

        public ReadAdapter() {
            super(R.layout.item_comic_page);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Page page) {
            ImageView ivPage = baseViewHolder.findView(R.id.iv_item_page);
            String URL = "";
            if(!TextUtils.isEmpty(page.imgLocalPath) && new File(page.imgLocalPath).exists()) {
                URL = page.imgLocalPath;
            } else {
                URL = page.imgUrl;
            }
            Logger.i("pageUrl-->"+URL);
            RequestOptions options = new RequestOptions().fitCenter().placeholder(R.drawable.loading)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(this.getContext()).load(URL).apply(options).into(ivPage);
        }
    }

    public class ReadPagerAdapter extends BaseQuickAdapter<Page, BaseViewHolder> {

        public ReadPagerAdapter() {
            super(R.layout.item_comic_view_pager);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Page page) {
            PhotoView photoView = baseViewHolder.findView(R.id.pv_comic);
            RequestOptions options = new RequestOptions().placeholder(R.drawable.loading).
                    skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            String URL = "";
            if(!TextUtils.isEmpty(page.imgLocalPath) && new File(page.imgLocalPath).exists()) {
                URL = page.imgLocalPath;
            } else {
                URL = page.imgUrl;
            }
            Logger.i("pageUrl-->"+URL);
            Glide.with(this.getContext()).load(URL).apply(options).into(photoView);
        }
    }

    public class ClickProxy {
        public void expandController(View v) {
            viewDataBinding.viewpagerPage.setVisibility(View.VISIBLE);
            viewDataBinding.recyclerComicPage.setVisibility(View.GONE);
        }
    }

}
