package com.xinzu.xiaodou.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

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
        TextView textView = helper.getView(R.id.tv_zf_type);

        //0未支付 1已支付
        if (item.getPayAmount() == 0) {
            textView.setText("未支付");
        }
        //0待提车；1 订单完成；2未到店取车；3已取消；4延迟还车；6已取车
        else if (1==item.getPayAmount()){
            switch (item.getState()) {
                case 0:
                    textView.setText("待提车");
                    break;
                case 1:
                    textView.setText("订单完成");
                    break;
                case 2:
                    textView.setText("未到店取车");
                    break;
                case 3:
                    textView.setText("已取消");
                    break;
                case 4:
                    textView.setText("延迟还车");
                    break;
                case 6:
                    textView.setText("已取车");
                    break;
            }
        }
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


        helper.addOnClickListener(R.id.ll_order_details);

    }
}
