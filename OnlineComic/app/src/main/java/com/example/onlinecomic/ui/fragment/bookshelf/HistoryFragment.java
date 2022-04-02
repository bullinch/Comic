package com.example.onlinecomic.ui.fragment.bookshelf;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.base.BaseApplication;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.bean.HistoryComic;
import com.example.onlinecomic.callback.DialogClickListener;
import com.example.onlinecomic.databinding.FragmentHistoryBinding;
import com.example.onlinecomic.ui.activity.ComicReadActivity;
import com.example.onlinecomic.util.ActivityUtils;
import com.example.onlinecomic.util.DialogUtils;
import com.example.onlinecomic.util.TimeUtils;
import com.example.onlinecomic.viewmodel.BookShelfViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;

import java.util.List;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding, BookShelfViewModel> {

    private MyAdapter myAdapter;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView() {
        Logger.i("initView");
        initAdapter();
        viewDataBinding.swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        viewDataBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshHistoryComics();
            }
        });
        viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        viewDataBinding.recycler.addItemDecoration(new RecyclerItemDecoration(10));
        viewDataBinding.recycler.setAdapter(myAdapter);
        viewDataBinding.swipeLayout.setRefreshing(true);
        viewModel.historyComicsMLD.observe(this, new Observer<List<HistoryComic>>() {
            @Override
            public void onChanged(List<HistoryComic> comics) {
                Logger.i("onChanged: " + comics.size());
                myAdapter.setList(comics);
                viewDataBinding.swipeLayout.setRefreshing(false);
            }
        });
        viewModel.refreshHistoryComics();
    }

    @Override
    protected BookShelfViewModel getViewModel() {
        return getStoreViewModel((MyApplication) _mActivity.getApplication());
    }

    private void initAdapter() {
        myAdapter = new MyAdapter();
        myAdapter.setOnItemClickListener((adapter, view, position) -> {
            HistoryComic historyComic = (HistoryComic) adapter.getData().get(position);
            if (historyComic.getObject() instanceof Comic) {
                Comic comic = (Comic) historyComic.getObject();
                ActivityUtils.intentActivity(_mActivity, ComicReadActivity.class, comic);
            }
        });
        myAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HistoryComic historyComic = (HistoryComic) adapter.getData().get(position);
                if (historyComic.getObject() instanceof Comic) {
                    Comic comic = (Comic) historyComic.getObject();
                    if (view.getId() == R.id.iv_delete) {
                        DialogUtils.showDialog(_mActivity, "提示", "确认要清除此阅读历史记录", new DialogClickListener() {
                            @Override
                            public void onPositive() {
                                viewModel.deleteHistory(comic);
                            }
                        });
                    } else if (view.getId() == R.id.iv_play) {
                        ActivityUtils.intentActivity(_mActivity, ComicReadActivity.class, comic, 1);
                    }
                }
            }
        });
    }

    private class MyAdapter extends BaseSectionQuickAdapter<HistoryComic, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_history_header);
            setNormalLayout(R.layout.item_history);
            addChildClickViewIds(R.id.iv_play);
            addChildClickViewIds(R.id.iv_delete);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, HistoryComic historyComic) {
            if (historyComic.getObject() instanceof Comic) {
                Comic comic = ((Comic) historyComic.getObject());
                baseViewHolder.setText(R.id.tv_title, comic.getTitle());
                String history = String.format(getString(R.string.txt_history), comic.getReadChapter(),
                        TimeUtils.millis2StringHHMM(comic.getHistoryStamp()));
                baseViewHolder.setText(R.id.tv_history, history);
                Glide.with(_mActivity).load(comic.getCoverUrl()).into((ImageView)
                        baseViewHolder.getView(R.id.iv_cover));
            }
        }

        @Override
        protected void convertHeader(BaseViewHolder baseViewHolder, HistoryComic historyComic) {
            if (historyComic.getObject() instanceof String) {
                baseViewHolder.setText(R.id.header, (String) historyComic.getObject());
            }
        }
    }
}