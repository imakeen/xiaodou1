package com.xinzu.xiaodou.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.bean.OrderBean;
import com.xinzu.xiaodou.util.GlideUtils;

public class OrderAdapter extends BaseQuickAdapter<OrderBean.OrderListBean, BaseViewHolder> {
    public OrderAdapter() {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.OrderListBean item) {
        int i = helper.getAdapterPosition();
        helper.
                setText(R.id.tv_order_pick_city, item.getPickupCityName())
                .setText(R.id.Zhou_ri, item.getPickupDate()).
                setText(R.id.Ri_qi, item.getReturnCityName()).
                setText(R.id.Zhao_ris, item.getReturnDate()).
                setText(R.id.tv_vehicleName, item.getVehicleOrInfoDTO().getVehicleName()).
                setText(R.id.tv_displacement, item.getVehicleOrInfoDTO().getDisplacement()).
                setText(R.id.amount, "￥" + item.getPayAmount() + "元").
                setText(R.id.Tian_Shu, item.getTotalDays())
        ;
        ImageView imageView = helper.getView(R.id.iv_url);
        GlideUtils.getInstance().loadPathImage(mContext, item.getVehicleOrInfoDTO().getImage(), imageView);

    }
}
