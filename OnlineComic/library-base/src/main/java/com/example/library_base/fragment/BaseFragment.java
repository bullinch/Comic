package com.example.library_base.fragment;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.library_base.base.IBaseView;
import com.example.library_base.util.ClassUtil;
import com.example.library_base.viewmodel.BaseViewModel;
import com.kingja.loadsir.core.LoadService;

import org.simple.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends SupportFragment
        implements IBaseView {
    private static final String TAG = "BaseFragment";

    protected V viewDataBinding;

    protected VM viewModel;

    protected LoadService mLoadService;

    @LayoutRes
    protected abstract int getLayoutResourceID();

    protected abstract void initView();

    protected abstract VM getViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewDataBinding =
                DataBindingUtil.inflate(inflater, getLayoutResourceID(), container, false);
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
//        if (viewModelClass != null) {
//            this.viewModel = new ViewModelProvider(_mActivity).get(viewModelClass);
//        }
        this.viewModel = getViewModel();
        EventBus.getDefault().register(this);
        initView();
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected VM getStoreViewModel(ViewModelStoreOwner owner) {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            return new ViewModelProvider(owner).get(viewModelClass);
        }
        return null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        if (null != viewModel) {
            viewModel.destroy();
        }
        super.onDestroyView();
    }
}
