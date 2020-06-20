package com.xinzu.xiaodou.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.bean.OrPriceDetailBean;

public class FeiyongAdapter extends BaseQuickAdapter<OrPriceDetailBean.AddedServiceListBean, BaseViewHolder> {
    public FeiyongAdapter() {
        super(R.layout.item_feiyong);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrPriceDetailBean.AddedServiceListBean item) {
        helper.setText(R.id.tv_payname, item.getServiceName())
                .setText(R.id.tv_paymoney, item.getQuantity() + "*" + item.getPrice());
    }
}
