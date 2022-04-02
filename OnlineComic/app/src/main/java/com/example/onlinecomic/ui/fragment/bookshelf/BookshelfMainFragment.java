package com.example.onlinecomic.ui.fragment.bookshelf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentMainBookshelfBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import me.yokeyword.fragmentation.SupportFragment;

public class BookshelfMainFragment extends BaseFragment<FragmentMainBookshelfBinding, BaseViewModel> {

    private SupportFragment[] fragments = new SupportFragment[2];
    private final String[] tabs = new String[]{"书架", "历史"};

    public static BookshelfMainFragment newInstance() {
        return new BookshelfMainFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_main_bookshelf;
    }

    @Override
    protected void initView() {
        Logger.i("initView");
        viewDataBinding.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), getLifecycle()));
        viewDataBinding.viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        new TabLayoutMediator(viewDataBinding.tabLayout, viewDataBinding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();
        viewDataBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Logger.i("onLazyInitView");
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    public class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            fragments[0] = BookShelfFragment.newInstance();
            fragments[1] = HistoryFragment.newInstance();
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
}
