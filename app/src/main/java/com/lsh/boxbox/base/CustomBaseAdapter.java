package com.lsh.boxbox.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by LSH on 2017/9/4.
 * 自定义BaseAdapter
 */

public abstract class CustomBaseAdapter<T> extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<T> mDatas;
    private Context mContext;


    public CustomBaseAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 获取上下文对象
     *
     * @return
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 获取item布局
     *
     * @param resource
     * @param parent
     * @return
     */
    public View getItemView(int resource, ViewGroup parent) {
        return mInflater.inflate(resource, parent, false);
    }

    /**
     * 获取指定item数据
     *
     * @param position
     * @return
     */
    public T getItemData(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<T> getData() {
        return mDatas;
    }

    /**
     * 设置数据集合
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.mDatas = data;
    }

    /**
     * 移除指定item的数据
     *
     * @param position
     */
    public void removeData(int position) {
        this.mDatas.remove(position);
    }


    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
