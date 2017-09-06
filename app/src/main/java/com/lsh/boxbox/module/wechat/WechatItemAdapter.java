package com.lsh.boxbox.module.wechat;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lsh.boxbox.R;
import com.lsh.boxbox.model.WechatItem;

import java.util.List;

/**
 * Created by LSH on 2017/9/4.
 */

public class WechatItemAdapter extends BaseMultiItemQuickAdapter<WechatItem.ResultBean.ListBean, BaseViewHolder> {


    public boolean isNotLoad;
    public int mImgWidth;
    public int mImgHeight;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WechatItemAdapter(List<WechatItem.ResultBean.ListBean> data) {
        super(data);
        addItemType(WechatItem.ResultBean.ListBean.STYLE_BIG, R.layout.item_wechat_style1);
        addItemType(WechatItem.ResultBean.ListBean.STYLE_SMALL, R.layout.item_wechat_style2);
    }

    public WechatItemAdapter(List<WechatItem.ResultBean.ListBean> data, boolean isNotLoadImg, int imgWidth, int imgHeight) {
        super(data);
        addItemType(WechatItem.ResultBean.ListBean.STYLE_BIG, R.layout.item_wechat_style1);
        addItemType(WechatItem.ResultBean.ListBean.STYLE_SMALL, R.layout.item_wechat_style2);
        isNotLoad = isNotLoadImg;
        mImgHeight = imgHeight;
        mImgWidth = imgWidth;
    }


    @Override
    protected void convert(BaseViewHolder helper, WechatItem.ResultBean.ListBean item) {
        switch (helper.getItemViewType()) {
            case WechatItem.ResultBean.ListBean.STYLE_BIG:
                helper.setText(R.id.title_wechat_style1, TextUtils.isEmpty(item.getTitle()) ? mContext.getString(R.string.tab_wechat) : item.getTitle());
                if (!isNotLoad) {
                    Glide.with(mContext.getApplicationContext())
                            .load(item.getFirstImg())
                            .override(mImgWidth, mImgHeight)
                            .placeholder(R.drawable.lodingview)
                            .error(R.drawable.errorview)
                            .crossFade(1000)
                            .into((ImageView) helper.getView(R.id.img_wechat_style));
                }
                break;
            case WechatItem.ResultBean.ListBean.STYLE_SMALL:
                helper.setText(R.id.title_wechat_style2, TextUtils.isEmpty(item.getTitle()) ? mContext.getString(R.string.tab_wechat) : item.getTitle());
                if (!isNotLoad) {
                    Glide.with(mContext.getApplicationContext())
                            .load(item.getFirstImg())
                            .placeholder(R.drawable.lodingview)
                            .error(R.drawable.errorview)
                            .override(mImgWidth / 2, mImgWidth / 2)
                            .crossFade(1000)
                            .into((ImageView) helper.getView(R.id.img_wechat_style));
                }
                break;
            default:
                break;
        }
    }
}
