package com.example.onlinecomic.ui.fragment.more;



import android.view.View;

import androidx.lifecycle.AndroidViewModel;
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
import com.example.onlinecomic.app.MyApplication;
import com.example.onlinecomic.databinding.FragmentTabUserBinding;
import com.example.onlinecomic.databinding.FragmentTabUserBindingImpl;
import com.example.onlinecomic.databinding.ItemPeopleBinding;
import com.example.onlinecomic.viewmodel.TestViewModel;

import org.greenrobot.greendao.rx.RxDao;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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

        TestViewModel testViewModel = new ViewModelProvider(_mActivity).get(TestViewModel.class);
        testViewModel.a();
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    private void getData() {
        long time = System.currentTimeMillis();
        List<People> peopleList = new ArrayList<>();
        for(int i=0;i<20000;i++) {
            peopleList.add(new People(i, "XZB_"+i, "Student_"+i));
        }
        List<People> comics = mDao.queryBuilder().where(PeopleDao.Properties.Name.eq("XZB_4567"))
                .where(PeopleDao.Properties.Name.eq("XZB_4867")).list();
        Logger.i("time3-->"+(System.currentTimeMillis()-time)+"____"+comics.size());

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
            People people = new People(4, "XZB_"+6, "Student_"+5);
            long id = mDao.insertOrReplace(people);
            Logger.i("insert: "+id);
            List<People> list = mDao.queryBuilder().list();
            myAdapter.setList(list);
        }
        public void update(View v) {
            People people = new People(5, "XZB_"+5, "Student_"+5);
            Long id = mDao.getKey(people);
            Logger.i("insert: "+id);
//            List<People> list = mDao.queryBuilder().list();
//            myAdapter.setList(list);
        }
        public void delete(View v) {
//            Logger.i("delete: "+mDao.queryBuilder().where
//                    (PeopleDao.Properties.Name.eq("XZB_"+5)).list().size());
            for(People p :
                    mDao.queryBuilder().where(PeopleDao.Properties.Name.eq("XZB_"+5)).list()) {
                mDao.delete(p);
            }
            List<People> list = mDao.queryBuilder().list();
            myAdapter.setList(list);
            Logger.i("delete: "+list.size());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy");
    }
}
