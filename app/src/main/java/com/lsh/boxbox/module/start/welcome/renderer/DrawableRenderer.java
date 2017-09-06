package com.lsh.boxbox.module.start.welcome.renderer;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatDrawableManager;

import com.cleveroad.slidingtutorial.Renderer;
import com.lsh.boxbox.R;
import com.orhanobut.logger.Logger;

/**
 * Created by LSH on 2017/8/31.
 * 圆形  对号圆形 轮播图形
 */
@SuppressWarnings("WeakerAccess")
public class DrawableRenderer implements Renderer {

    private Drawable mDrawableActive;
    private Drawable mDrawable;


    public static DrawableRenderer create(@NonNull Context context) {
        return new DrawableRenderer(context);
    }

    private DrawableRenderer(@NonNull Context context) {
        //解决 vector “资源未找到” 错误
        try {
            mDrawableActive = AppCompatDrawableManager.get()
                    .getDrawable(context, R.drawable.vec_checkbox_fill_circle_outline);
            mDrawable = AppCompatDrawableManager.get()
                    .getDrawable(context, R.drawable.vec_checkbox_blank_circle_outline);
        } catch (Resources.NotFoundException notFoundException) {
            Logger.e(notFoundException.getMessage());
        } catch (Exception e) {
            Logger.e(e.getMessage());
        } finally {
            mDrawableActive = context.getResources().getDrawable(R.drawable.vec_checkbox_fill_circle);
            mDrawable = context.getResources().getDrawable(R.drawable.vec_checkbox_empty_circle);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive) {
        Drawable drawable = isActive ? mDrawableActive : mDrawable;
        drawable.setBounds((int) elementBounds.left, (int) elementBounds.top,
                (int) elementBounds.right, (int) elementBounds.bottom);
        drawable.draw(canvas);
    }
}
