package com.example.onlinecomic.ui.fragment.browser;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.onlinecomic.R;

public class RankingFragment extends BaseFragment {

    private static final String TAG = "RankingFragment";

    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


}
