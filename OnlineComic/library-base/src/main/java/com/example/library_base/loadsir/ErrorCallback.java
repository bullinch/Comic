package com.example.library_base.loadsir;

import com.example.library_base.R;
import com.kingja.loadsir.callback.Callback;

/**
 * 应用模块:
 * <p>
 * 类描述: 错误页面
 * <p>
 *
 */
public class ErrorCallback extends Callback
{
    @Override
    protected int onCreateView()
    {
        return R.layout.base_layout_error;
    }
}
