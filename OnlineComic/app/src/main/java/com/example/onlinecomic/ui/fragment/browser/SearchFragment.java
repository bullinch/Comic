package com.example.onlinecomic.ui.fragment.browser;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentSearchBinding;
import com.example.onlinecomic.databinding.ItemFavoriteBinding;
import com.example.onlinecomic.ui.activity.ComicReadActivity;
import com.example.onlinecomic.viewmodel.BrowserViewModel;
import com.example.onlinecomic.weight.RecyclerItemDecoration;

import java.util.List;

public class SearchFragment extends BaseFragment<FragmentSearchBinding, BrowserViewModel> {

    private MyAdapter myAdapter;

    public static SearchFragment newInstance(String keyWord) {
        Bundle bundle = new Bundle();
        SearchFragment fragment = new SearchFragment();
        bundle.putString("KeyWord", keyWord);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        assert getArguments() != null;
        initAdapter();
        viewModel.requestSearchData(getArguments().getString("KeyWord"));
        viewDataBinding.etSearch.setText(getArguments().getString("KeyWord"));
        viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        viewDataBinding.recycler.addItemDecoration(new RecyclerItemDecoration(3));
        viewDataBinding.recycler.setAdapter(myAdapter);
        viewModel.searchComicList.observe(this, new Observer<List<Comic>>() {
            @Override
            public void onChanged(List<Comic> comics) {
                Logger.i("comics-->"+comics.size());
                myAdapter.setList(comics);
            }
        });
    }

    @Override
    protected BrowserViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

    private void initAdapter() {
        myAdapter = new MyAdapter();
        myAdapter.setOnItemClickListener((adapter, view, position) -> {
            Comic comic = (Comic) adapter.getData().get(position);
            Intent intent = new Intent(_mActivity, ComicReadActivity.class);
            intent.putExtra("comic", comic);
            _mActivity.startActivity(intent);
        });
        myAdapter.setOnItemLongClickListener((adapter, view, position) -> {
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
            //binding.tvUpdateTime.setText(TimeUtils.millis2String(System.currentTimeMillis()));
            Glide.with(binding.ivCover.getContext()).load(comic.getCoverUrl()).into(binding.ivCover);
        }
    }



}
