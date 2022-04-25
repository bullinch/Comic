package com.example.library_base.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.example.library_base.base.IBaseView;
import com.example.library_base.util.ClassUtil;
import com.example.library_base.viewmodel.BaseViewModel;
import com.kingja.loadsir.core.LoadService;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends SupportActivity
        implements IBaseView {

    protected V viewDataBinding;
    protected VM viewModel;

    @LayoutRes
    protected abstract int getLayoutResourceID();

    protected abstract void initView();

//    protected abstract VM getViewModel();

    /**
     * 失败重试,重新加载事件
     */
//    protected abstract void onRetryBtnClick();

    protected LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceID());
        initViewModel();
        initView();
    }

    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.viewModel = new ViewModelProvider(this).get(viewModelClass);
        }
//        viewModel = getViewModel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }
}
