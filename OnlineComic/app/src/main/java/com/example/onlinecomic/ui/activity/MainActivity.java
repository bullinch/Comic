package com.example.onlinecomic.ui.activity;

import android.content.Intent;

import com.example.library_base.activity.BaseActivity;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.IBaseViewModel;
import com.example.onlinecomic.R;
import com.example.onlinecomic.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private MainFragment mainFragment;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        Logger.i("initView__"+(findFragment(MainFragment.class) == null));
        if (findFragment(MainFragment.class) == null) {
            mainFragment = MainFragment.newInstance();
            loadRootFragment(R.id.fl_container, mainFragment);
        } else {
            mainFragment = findFragment(MainFragment.class);
        }
    }

    @Override
    protected IBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("onNewIntent");
        setIntent(intent);
        mainFragment.showHideFragment();
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy");
    }
}