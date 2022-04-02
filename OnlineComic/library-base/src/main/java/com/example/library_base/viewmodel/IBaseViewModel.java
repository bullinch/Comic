package com.example.library_base.viewmodel;

public interface IBaseViewModel<V> {
    /**
     * 关联View
     * */
    void attachUi(V view);

    /**
     * 获取View
     * */
    V getView();

    /**
     * 是否已经关联View
     * */
    boolean isUiAttach();

    /**
     * 解除关联
     * */
    void detachUi();
}
