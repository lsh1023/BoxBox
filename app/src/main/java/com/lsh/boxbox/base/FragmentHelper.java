package com.lsh.boxbox.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by LSH on 2017/9/1.
 * Fragment 帮助类
 */

public class FragmentHelper {

    /**
     * 使用replace方法每次替换新的碎片 有刷新
     *
     * @param manager  碎片管理器
     * @param list     存放碎片的集合
     * @param tabIndex 碎片索引下标
     * @param layoutId layout_container
     * @param enter    进出动画参数
     * @param exit     进出动画参数
     */
    public static void replaceFragment(FragmentManager manager, List<Fragment> list, int tabIndex, int layoutId, int enter, int exit) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (enter != 0 && exit != 0) {
            transaction.setCustomAnimations(enter, exit);
        }
        transaction.replace(layoutId, list.get(tabIndex));
        transaction.commit();
    }


    /**
     * 使用hide、show、add方法展示，不必每次执行碎片的生命周期方法
     *
     * @param manager
     * @param list
     * @param tabIndex
     * @param layoutId
     * @param enter
     * @param exit
     */
    public static void switchFragment(FragmentManager manager, List<Fragment> list, int tabIndex, int layoutId, int enter, int exit) {
        FragmentTransaction transaction = manager.beginTransaction();

        /**
         * 先将所有碎片hide
         */
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isVisible()) {
                transaction.hide(list.get(i));
            }
        }

        /**
         *自定义进出场动画
         */
        if (enter != 0 && exit != 0) {
            transaction.setCustomAnimations(enter, exit);
        }

        /**
         * 判断碎片是否被添加，已添加则直接show，未添加则show
         */
        if (list.get(tabIndex).isAdded()) {
            transaction.show(list.get(tabIndex));
        } else {
            transaction.add(layoutId, list.get(tabIndex));
        }
        transaction.commit();

    }

}
