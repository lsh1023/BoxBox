package com.lsh.boxbox.module.news.news_category.draghelper;

/**
 * Created by LSH on 2017/9/15.
 * ViewHolder 被选中 以及 拖拽释放 触发监听器
 */

public interface OnDragVHListener {

    /**
     * Item被选中时触发
     */
    void onItemSelected();


    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();

}
