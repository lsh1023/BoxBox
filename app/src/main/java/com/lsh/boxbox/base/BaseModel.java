package com.lsh.boxbox.base;

import android.content.Context;

/**
 * Created by LSH on 2017/9/1.
 * 公共Model,所有Model继承自此类
 */

public abstract class BaseModel {

    Context mContext;

    public BaseModel(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

}
