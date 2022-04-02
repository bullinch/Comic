package com.example.onlinecomic.ui.activity;

import android.util.Log;

import com.example.library_base.activity.BaseActivity;
import com.example.library_base.viewmodel.IBaseViewModel;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.ui.fragment.comic.ComicChapterFragment;

public class ComicReadActivity extends BaseActivity {
    private static final String TAG = "ComicReadActivity";

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_comic_read;
    }

    @Override
    protected void initView() {
        Comic comic = getIntent().getParcelableExtra("comic");
        Log.i(TAG, "initView: "+comic.toString());
        if (findFragment(ComicChapterFragment.class) == null) {
            loadRootFragment(R.id.fl_container, ComicChapterFragment.newInstance(comic));
        }
    }

    @Override
    protected IBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }
}
