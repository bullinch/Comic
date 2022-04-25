package com.example.onlinecomic.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentMainBinding;
import com.example.onlinecomic.ui.fragment.bookshelf.BookshelfMainFragment;
import com.example.onlinecomic.ui.fragment.browser.BrowserMainFragment;
import com.example.onlinecomic.ui.fragment.more.MoreMainFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;
import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends BaseFragment<FragmentMainBinding, BaseViewModel> {

    private final SupportFragment[] mFragments = new SupportFragment[3];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(BookshelfMainFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = BookshelfMainFragment.newInstance();
            mFragments[SECOND] = BrowserMainFragment.newInstance();
            mFragments[THIRD] = MoreMainFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(BrowserMainFragment.class);
            mFragments[THIRD] = findChildFragment(MoreMainFragment.class);
        }
    }

    @Override
    protected void initView() {
        viewDataBinding.pagerTab.material().addItem(R.mipmap.icon_home, "书架")
                .addItem(R.mipmap.icon_search, "浏览")
                .addItem(R.mipmap.icon_user, "用户")
                .build().addSimpleTabItemSelectedListener(new SimpleTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                showHideFragment(mFragments[index]);
            }
        });
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }
}
