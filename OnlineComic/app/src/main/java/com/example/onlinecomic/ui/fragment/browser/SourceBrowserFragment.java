package com.example.onlinecomic.ui.fragment.browser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_comic.bean.Comic;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentComicSourceBinding;
import com.example.onlinecomic.viewmodel.BrowserViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class SourceBrowserFragment extends BaseFragment<FragmentComicSourceBinding, BrowserViewModel> {

    private SupportFragment[] fragments = new SupportFragment[3];
    private final String[] tabs = new String[]{"最新", "排行", "分类"};

    public static SourceBrowserFragment newInstance(String comicUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", comicUrl);
        SourceBrowserFragment fragment = new SourceBrowserFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_comic_source;
    }


    @Override
    protected void initView() {
        Logger.i("initView");
        viewDataBinding.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), getLifecycle()));
        viewDataBinding.viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        new TabLayoutMediator(viewDataBinding.tabLayout, viewDataBinding.viewPager, (tab, position) -> tab.setText(tabs[position])).attach();

        assert getArguments() != null;
        String comicUrl = getArguments().getString("URL");
        viewModel.requestHostUrl(comicUrl);
        viewModel.recentUpdateList.observe(getViewLifecycleOwner(), new Observer<List<Comic>>() {
            @Override
            public void onChanged(List<Comic> comics) {
            }
        });
    }

    @Override
    protected BrowserViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

    public class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            fragments[0] = RecentUpdateFragment.newInstance();
            fragments[1] = RankingFragment.newInstance();
            fragments[2] = ClassificationFragment.newInstance();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments[position];
        }

        @Override
        public int getItemCount() {
            return fragments.length;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

}
