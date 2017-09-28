package com.lsh.boxbox.module.find.joke;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lsh.boxbox.R;
import com.lsh.boxbox.model.TextJokeBean;

import java.util.List;

/**
 * Created by LSH on 2017/9/28.
 */

public class TextJokeAdapter extends BaseMultiItemQuickAdapter<TextJokeBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TextJokeAdapter(List<TextJokeBean> data) {
        super(data);
        addItemType(TextJokeBean.JOKE, R.layout.item_textjoke_joke);
        addItemType(TextJokeBean.MORE, R.layout.item_textjoke_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextJokeBean item) {
        if (helper.getItemViewType() == TextJokeBean.JOKE) {
            helper.setText(R.id.tv_item_textjoke, item.getContent());
        } else if (TextJokeBean.MORE == helper.getItemViewType()) {
            Glide.with(mContext)
                    .load(R.drawable.loadingjoke)
                    .into((ImageView) helper.getView(R.id.img_item_morejoke));
        }
    }
}
