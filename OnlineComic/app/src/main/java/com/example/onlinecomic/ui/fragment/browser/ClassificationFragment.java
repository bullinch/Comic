package com.example.onlinecomic.ui.fragment.browser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.library_base.fragment.BaseFragment;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentClassificationBinding;
import com.example.onlinecomic.viewmodel.BrowserViewModel;

public class ClassificationFragment extends BaseFragment<FragmentClassificationBinding, BrowserViewModel> {
    private static final String TAG = "ClassificationFragment";

    public static ClassificationFragment newInstance() {
        return new ClassificationFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_classification;
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        viewDataBinding.setClassificationFragment(this);
    }

    @Override
    protected BrowserViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Log.i(TAG, "onLazyInitView");
    }



    public void onTestClick(View view) {
    }

}
