package com.example.library_base.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.library_base.base.IBaseView;
import com.example.library_base.loadsir.EmptyCallback;
import com.example.library_base.loadsir.ErrorCallback;
import com.example.library_base.loadsir.LoadingCallback;
import com.example.library_base.viewmodel.IBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends IBaseViewModel> extends SupportActivity
        implements IBaseView {

    protected V viewDataBinding;
    protected VM viewModel;

    @LayoutRes
    protected abstract int getLayoutResourceID();

    protected abstract void initView();

    protected abstract VM getViewModel();

    protected abstract int getBindingVariable();

    /**
     * 失败重试,重新加载事件
     */
//    protected abstract void onRetryBtnClick();

    protected LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        performDataBinding();
        initView();
    }

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceID());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if (getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
    }

    private void initViewModel() {
        viewModel = getViewModel();
        if (null != viewModel) {
            viewModel.attachUi(this);
        }
    }

    /**
     * 注册LoadSir
     *
     * @param view 替换视图
     */
    public void setLoadSir(View view) {
        if (mLoadService == null){
            mLoadService = LoadSir.getDefault()
                    .register(view, (Callback.OnReloadListener) v -> {
//                        onRetryBtnClick();
                    });
        }

    }

    @Override
    public void showContent() {
        if (null != mLoadService) {
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (null != mLoadService) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showEmpty() {
        if (null != mLoadService) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void showFailure(String message) {
        if (null != mLoadService) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != viewModel && viewModel.isUiAttach()) {
            viewModel.detachUi();
        }
    }

    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }
}
