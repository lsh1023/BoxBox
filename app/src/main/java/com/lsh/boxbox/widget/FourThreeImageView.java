package com.lsh.boxbox.widget;

import android.widget.ImageView;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by LSH on 2017/9/4.
 */

public class FourThreeImageView extends ImageView {
    public FourThreeImageView(Context context) {
        super(context);
    }

    public FourThreeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec) * 3 / 4,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, fourThreeHeight);
    }

}
