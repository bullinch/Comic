package com.example.onlinecomic.ui.fragment.more;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.People;
import com.example.library_comic.bean.PeopleDao;
import com.example.library_comic.database.PeopleDatabase;
import com.example.onlinecomic.R;
import com.example.onlinecomic.databinding.FragmentTabUserBinding;
import com.example.onlinecomic.databinding.ItemPeopleBinding;
import com.example.onlinecomic.util.ComicUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MoreMainFragment extends BaseFragment<FragmentTabUserBinding, BaseViewModel> {

    public static MoreMainFragment newInstance() {
        return new MoreMainFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_tab_user;
    }

    private PeopleDao mDao;
    private MyAdapter myAdapter;
    @Override
    protected void initView() {
        Logger.i("initView");
        PeopleDatabase.getInstance().init(_mActivity.getApplicationContext());
        mDao = PeopleDatabase.getInstance().getDaoSession().getPeopleDao();
        viewDataBinding.setClick(new ClickProxy());
        viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        myAdapter = new MyAdapter();
        viewDataBinding.recycler.setAdapter(myAdapter);
        getData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        ComicUtils.start();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
        try {
            ComicUtils.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    private List<People> peopleList= new ArrayList<>();
    private void getData() {
        for(int i=0;i<20;i++) {
            peopleList.add(new People(i, "XZB_"+i, "Student_"+i));
        }
    }

    private class MyAdapter extends BaseQuickAdapter<People, BaseDataBindingHolder<ItemPeopleBinding>> {

        public MyAdapter() {
            super(R.layout.item_people);
        }

        @Override
        protected void convert(BaseDataBindingHolder baseDataBindingHolder, People people) {
            ItemPeopleBinding binding = (ItemPeopleBinding) baseDataBindingHolder.getDataBinding();
            if(binding != null) {
                binding.setPeople(people);
            }
        }
    }

    public class ClickProxy {
        public void insert(View v) {
            List<People> comics = mDao.queryBuilder().list();
            myAdapter.setList(comics);
        }

        public void update(View v) {
            People people = new People(100, "XZB_"+3, "Student_"+100);
            mDao.insertOrReplace(people);
            List<People> comics = mDao.queryBuilder().list();
            myAdapter.setList(comics);
        }

        public void delete(View v) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy");
    }
}
