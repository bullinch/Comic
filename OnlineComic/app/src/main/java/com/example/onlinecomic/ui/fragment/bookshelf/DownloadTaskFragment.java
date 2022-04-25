package com.example.onlinecomic.ui.fragment.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.library_base.fragment.BaseFragment;
import com.example.library_base.util.log.Logger;
import com.example.library_base.viewmodel.BaseViewModel;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.onlinecomic.R;
import com.example.onlinecomic.constant.Constants;
import com.example.onlinecomic.databinding.FragmentDownloadTaskBinding;
import com.example.onlinecomic.downolad.DiffCallback;
import com.example.onlinecomic.downolad.DownloadDatabase;
import com.example.onlinecomic.downolad.DownloadManager;
import com.example.onlinecomic.downolad.bean.Task;
import com.example.onlinecomic.downolad.bean.Worker;
import com.example.onlinecomic.ui.activity.ComicReadActivity;
import com.example.onlinecomic.util.TaskUtils;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class DownloadTaskFragment extends BaseFragment<FragmentDownloadTaskBinding, BaseViewModel> {

    private DownloadTaskAdapter mDownloadTaskAdapter;

    public static DownloadTaskFragment newInstance(Comic comic) {
        Bundle bundle = new Bundle();
        DownloadTaskFragment fragment = new DownloadTaskFragment();
        bundle.putParcelable("comic", comic);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.fragment_download_task;
    }

    @Override
    protected void initView() {
        Comic comic = getArguments().getParcelable("comic");
        String comicUrl = comic.comicUrl;
        mDownloadTaskAdapter = new DownloadTaskAdapter();
        mDownloadTaskAdapter.setDiffCallback(new DiffCallback());
        viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        viewDataBinding.recycler.setAdapter(mDownloadTaskAdapter);
        List<Worker> list = DownloadDatabase.getInstance().query(comicUrl);
        mDownloadTaskAdapter.setList(list);
        mDownloadTaskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Worker worker = (Worker) adapter.getData().get(position);
                Logger.i(worker.toString());
                if(worker.state == Constants.DOWNLOAD.ING) {
                    //IntentUtils.intentServiceWithTask(_mActivity, DownloadService.PAUSE, task);
                    DownloadManager.getInstance().pauseDownload(worker);
                } else if(worker.state == Constants.DOWNLOAD.PAUSE) {
                    //IntentUtils.intentServiceWithTask(_mActivity, DownloadService.START, task);
                    DownloadManager.getInstance().startDownload(worker);
                } else if(worker.state == Constants.DOWNLOAD.COMPLETE) {
                    Intent intent = new Intent(_mActivity, ComicReadActivity.class);
                    ArrayList<Page> list = new ArrayList<>();
                    for(Task task : worker.taskList) {
                        list.add(task.getPage());
                    }
                    intent.putParcelableArrayListExtra("pages", list);
                    _mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    class DownloadTaskAdapter extends BaseQuickAdapter<Worker, BaseViewHolder> {

        public DownloadTaskAdapter() {
            super(R.layout.item_download_task);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Worker worker) {
            baseViewHolder.setText(R.id.tv_title, worker.getChapter().getTitle());
            baseViewHolder.setText(R.id.tv_size,
                    worker.loadSize+"/"+ worker.allSize);
            baseViewHolder.setText(R.id.tv_state, TaskUtils.parseDownloadState(worker.state));
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Worker worker, List<?> payloads) {
            for (Object p : payloads) {
                int payload = (int) p;
                if (payload == 100) {
                    baseViewHolder.setText(R.id.tv_state, TaskUtils.parseDownloadState(worker.state));
                    baseViewHolder.setText(R.id.tv_size, worker.loadSize+"/"+ worker.allSize);
                }
            }
        }
    }

    @Subscriber
    private void updateDownload(Worker worker) {
        List<Worker> list = mDownloadTaskAdapter.getData();
        int position = -1;
        for(int i=0;i<mDownloadTaskAdapter.getData().size();i++) {
            if(worker.id.longValue() == mDownloadTaskAdapter.getData().get(i).id.longValue()) {
                position = i;
                break;
            }
        }
        if(position == -1) {
            // 说明下载章节不在列表里面
            mDownloadTaskAdapter.addData(worker);
        } else {
            mDownloadTaskAdapter.getData().set(position, worker);
            mDownloadTaskAdapter.notifyItemChanged(position, 100);
        }
    }
}
