package com.example.onlinecomic.ui.fragment.comic;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.base.BaseApplication;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.databinding.FragmentComicChapterBinding;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.viewmodel.ComicDetailsViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;

import java.util.List;

public class ComicChapterFragment extends BaseFragment<FragmentComicChapterBinding, ComicDetailsViewModel> {

    private MyAdapter myAdapter;
    private Comic mComic;
    private BookShelfViewModel mBookShelfViewModel;

    public static ComicChapterFragment newInstance(Comic comic) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("comic", comic);
        ComicChapterFragment fragment = new ComicChapterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_comic_chapter;
    }

    @Override
    protected void initView() {
        try {
//            setLoadSir(viewDataBinding.recycler);
//            showLoading();
            mBookShelfViewModel = new ViewModelProvider((MyApplication) _mActivity.getApplication()).get(BookShelfViewModel.class);
            mComic = getArguments().getParcelable("comic");
            viewModel.readComicMutableLiveData.setValue(mComic);
            Logger.i("initView: " + mComic.toString());
            viewDataBinding.setComic(mComic);
            Glide.with(_mActivity).load(mComic.getCoverUrl()).into(viewDataBinding.ivCover);
            viewDataBinding.setClickProxy(new ClickProxy());
            viewModel.requestChapters(mComic, null);
            myAdapter = new MyAdapter();
            viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
            viewDataBinding.recycler.addItemDecoration(new RecyclerItemDecoration(15));
            viewDataBinding.recycler.setAdapter(myAdapter);
            myAdapter.setOnItemClickListener((adapter, view, position) -> {
                start(ComicReadFragment.newInstance((Chapter) adapter.getData().get(position)));
            });
            addObserveLiveData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ComicDetailsViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

    private void addObserveLiveData() {
        viewModel.chapterMutableLiveData.observe(this, chapters -> {
            Logger.i("chapterMutableLiveData-->"+chapters.toString());
//            showContent();
            myAdapter.setList(chapters);
        });
    }

    public class ClickProxy {
        public void click(View view) {
            if(view.getId() == R.id.iv_favorite) {
                mComic.setFavoriteStamp(System.currentTimeMillis());
                mBookShelfViewModel.favorite(mComic);
            }
        }
    }

    class MyAdapter extends BaseQuickAdapter<Chapter, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_comic_chapter);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Chapter chapter) {
            baseViewHolder.setText(R.id.tv_title, chapter.getName());
        }
    }
}
