package com.xinzu.xiaodou.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.bean.CarBean;
import com.xinzu.xiaodou.bean.CarGroupBean;
import com.xinzu.xiaodou.util.GlideUtils;

/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class CarAdapter extends BaseQuickAdapter<CarBean.StoreListBean, BaseViewHolder> {


    public CarAdapter() {
        super(R.layout.item_car);
    }


    @Override
    protected void convert(BaseViewHolder helper, CarBean.StoreListBean item) {
        helper.setText(R.id.tv_pickupStoreName, item.getPickupStoreName())
                .setText(R.id.tv_vehicleName, item.getVehicleName())
                .setText(R.id.tv_displacement, item.getDisplacement())
                .setText(R.id.amount, "￥" + item.getAmount() + "元")
                .setText(R.id.distance, "距离您当前所在位置" + item.getDistance() + "公里")
                .setText(R.id.tv_basicInsuranceFee, "￥" + item.getBasicInsuranceFee() + "元");
        ImageView imageView = helper.getView(R.id.car_image);
        GlideUtils.getInstance().loadPathImage(mContext, item.getImage(), imageView);

    }
}
