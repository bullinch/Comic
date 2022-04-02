package com.example.library_base.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel {

    protected CompositeDisposable compositeDisposable;

    /**
     * 订阅对象管理
     */
    public void addDisposable(Disposable disposable) {
        if (null == disposable) {
            return;
        }
        if (null == compositeDisposable) {
            compositeDisposable = new CompositeDisposable();
        }
        if(!compositeDisposable.isDisposed()) {
            compositeDisposable.add(disposable);
        }
    }

    /**
     * 取消所有订阅
     */
    public void cancel() {
        if (null != compositeDisposable) {
            compositeDisposable.clear();
        }
    }
}
