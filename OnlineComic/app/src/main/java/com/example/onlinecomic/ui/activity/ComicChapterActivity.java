package com.example.onlinecomic.ui.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.activity.BaseActivity;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.constant.Constants;
import com.example.onlinecomic.databinding.ActivityComicChapterBinding;
import com.example.onlinecomic.util.IntentUtils;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.viewmodel.ChapterViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;

public class ComicChapterActivity extends BaseActivity<ActivityComicChapterBinding, ChapterViewModel> {

    private BookShelfViewModel mBookShelfViewModel;
    private ChapterAdapter mChapterAdapter;
    private Comic mComic;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_comic_chapter;
    }

    @Override
    protected void initView() {
        try {
            Comic comic = getIntent().getParcelableExtra("comic");
            mBookShelfViewModel = new ViewModelProvider((MyApplication) getApplication()).get(BookShelfViewModel.class);
            Logger.i(comic.toString());
            mComic = comic;
            mBookShelfViewModel.mCurrentReadComic.setValue(mComic);
            viewDataBinding.setComic(mComic);
            Glide.with(this).load(mComic.getCoverUrl()).into(viewDataBinding.ivCover);
            viewDataBinding.setClickProxy(new ClickProxy());
            viewModel.requestChapters(mComic, null);
            mChapterAdapter = new ChapterAdapter();
            viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
            viewDataBinding.recycler.addItemDecoration(new RecyclerItemDecoration(15));
            viewDataBinding.recycler.setAdapter(mChapterAdapter);
            mChapterAdapter.setEmptyView(R.layout.base_layout_loading);
            mChapterAdapter.setOnItemClickListener((adapter, view, position) -> {
                IntentUtils.intentReadActivity(this, (Chapter) adapter.getData().get(position));
            });
            mChapterAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                Chapter chapter = (Chapter) adapter.getData().get(position);
                mComic.setDownloadStamp(System.currentTimeMillis());
                chapter.setDownloadState(Constants.DOWNLOAD.ING);
                mChapterAdapter.setData(position, chapter);
                mBookShelfViewModel.download(mComic, true);
//                mBookShelfViewModel.download(chapter);
                IntentUtils.intentDownloadService(this, chapter);
            });
            addObserveLiveData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void addObserveLiveData() {
        viewModel.chapterMutableLiveData.observe(this, chapters -> {
            Logger.i("chapterMutableLiveData-->"+chapters.toString());
            //showContent();
            mChapterAdapter.setList(chapters);
        });
    }

    public class ClickProxy {
        public void click(View view) {
            if(view.getId() == R.id.iv_favorite) {
                mBookShelfViewModel.favorite(mComic);
            }
        }
    }

    class ChapterAdapter extends BaseQuickAdapter<Chapter, BaseViewHolder> {

        public ChapterAdapter() {
            super(R.layout.item_comic_chapter);
            addChildClickViewIds(R.id.iv_download);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Chapter chapter) {
            baseViewHolder.setText(R.id.tv_title, chapter.getName());
            ImageView ivDownload = baseViewHolder.findView(R.id.iv_download);
            if(chapter.getDownloadState() == Constants.DOWNLOAD.IDLE) {
                ivDownload.setImageResource(R.mipmap.icon_download);
            } else if(chapter.getDownloadState() == Constants.DOWNLOAD.ING) {
                ivDownload.setImageResource(R.mipmap.icon_loading);
            }
        }
    }
}
