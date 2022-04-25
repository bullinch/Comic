package com.example.onlinecomic.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;

import com.example.library_base.activity.BaseActivity;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.IBaseViewModel;
import com.example.onlinecomic.R;
import com.example.onlinecomic.ui.fragment.MainFragment;
import com.yanzhenjie.permission.AndPermission;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_main;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
        AndPermission.with(this).runtime().permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).onGranted(permissions ->
                Logger.i(permissions.toString())).start();
    }

}