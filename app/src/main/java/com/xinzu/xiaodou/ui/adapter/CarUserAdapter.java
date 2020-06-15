package com.xinzu.xiaodou.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.bean.CarUserBean;

import java.util.List;

public class CarUserAdapter extends BaseQuickAdapter<CarUserBean.ConsumersBean, BaseViewHolder> {
    public CarUserAdapter() {
        super(R.layout.item_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarUserBean.ConsumersBean item) {
        helper.setText(R.id.tv_name, item.getUserName())
                .setText(R.id.tv_card, item.getIdNo());

        helper.addOnClickListener(R.id.iv_delcect)
                .addOnClickListener(R.id.iv_edit);

    }
}
