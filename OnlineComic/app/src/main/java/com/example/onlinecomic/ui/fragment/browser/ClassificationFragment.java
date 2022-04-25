package com.example.onlinecomic.ui.fragment.browser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.example.library_base.fragment.BaseFragment;
import com.example.library_comic.bean.Source;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentClassificationBinding;
import com.example.onlinecomic.viewmodel.BrowserViewModel;

public class ClassificationFragment extends BaseFragment<FragmentClassificationBinding, BrowserViewModel> {
    private static final String TAG = "ClassificationFragment";

    public static ClassificationFragment newInstance(Source source) {
        Bundle bundle = new Bundle();
        ClassificationFragment fragment = new ClassificationFragment();
        bundle.putParcelable("source", source);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_classification;
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        web = viewDataBinding.web;
        webSettings = web.getSettings();
        viewDataBinding.setClassificationFragment(this);
    }

    @Override
    protected BrowserViewModel getViewModel() {
        return getStoreViewModel(_mActivity);
    }

    WebView web;
    WebSettings webSettings;
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Log.i(TAG, "onLazyInitView");
        //支持javascript
        web.getSettings().setJavaScriptEnabled(true);
//不使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
// 设置可以支持缩放
        web.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        web.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        web.getSettings().setUseWideViewPort(true);
        // 缩放至屏幕的大小 ,自由缩放 ，隐藏缩放提示
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
//设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
//自适应屏幕
        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setLoadWithOverviewMode(true);

        web.loadUrl("/sdcard/Pictures/go-wild.htm");
    }



    public void onTestClick(View view) {
    }

}
