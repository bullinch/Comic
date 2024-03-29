package com.example.onlinecomic.ui.fragment.browser;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Source;
import com.example.library_comic.common.ComicSource;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentTabBrowserBinding;
import com.example.onlinecomic.ui.activity.MainActivity;
import com.example.onlinecomic.ui.fragment.MainFragment;
import com.example.onlinecomic.viewmodel.SourceViewModel;

public class BrowserMainFragment extends BaseFragment<FragmentTabBrowserBinding, SourceViewModel> {

    private MyAdapter mAdapter;

    public static BrowserMainFragment newInstance() {
        return new BrowserMainFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_tab_browser;
    }

    @Override
    protected void initView() {
        Logger.i("initView");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 4);
        mAdapter = new MyAdapter();
        viewDataBinding.recyclerSource.setLayoutManager(gridLayoutManager);
        viewDataBinding.recyclerSource.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Source source = ((Source)adapter.getData().get(position));
                ((MainFragment) getParentFragment()).startBrotherFragment(SourceBrowserFragment.newInstance(source));
            }
        });
        viewDataBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((MainFragment) getParentFragment()).startBrotherFragment(SearchFragment.newInstance
                            (textView.getText().toString()));
                    ((MainActivity)_mActivity).hideKeyBoard(viewDataBinding.etSearch);
                }
                return false;
            }
        });
        viewModel.sourceListLiveData.observe(this, comicSources -> {
            mAdapter.setList(comicSources);
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("onLazyInitView");
        viewModel.requestSourceData();
    }

    @Override
    protected SourceViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

   private class MyAdapter extends BaseQuickAdapter<Source, BaseViewHolder> {

       public MyAdapter() {
           super(R.layout.item_source);
       }

       @Override
       protected void convert(BaseViewHolder baseViewHolder, Source source) {
           baseViewHolder.setText(R.id.tv_title, source.name);
           baseViewHolder.setText(R.id.tv_cover, source.name.substring(0,1));
       }
   }

}
