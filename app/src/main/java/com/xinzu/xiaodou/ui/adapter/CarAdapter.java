package com.xinzu.xiaodou.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.bean.CarBean;
import com.xinzu.xiaodou.util.GlideUtils;

import java.util.ArrayList;

/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class CarAdapter extends BaseQuickAdapter<CarBean.StoreListBean, BaseViewHolder> {


    public CarAdapter(ArrayList<CarBean.StoreListBean> data) {
        super(R.layout.item_car, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CarBean.StoreListBean item) {

        //芝麻双免(0.不支持，1.免租车押金、2.免租车和违章押金)3.免违章押金
        switch (item.getSesametype()){
            case 0:
                helper.getView(R.id.tv_sesametype).setVisibility(View.INVISIBLE);
                break;
            case 1:
                helper.setText(R.id.tv_sesametype,"免租车押金");
                break;
            case 2:
                helper.setText(R.id.tv_sesametype,"免租车和违章押金");
                break;
            case 3:
                helper.setText(R.id.tv_sesametype,"免违章押金");
                break;
        }
        helper.setText(R.id.tv_pickupStoreName, item.getPickupStoreName())
                .setText(R.id.tv_vehicleName, item.getVehicleName())
                .setText(R.id.tv_displacement, item.getDisplacement())
                .setText(R.id.amount, "￥" + item.getAmount() + "元")
                .setText(R.id.distance, "距离您当前所在位置" + item.getDistance() + "公里")
                .setText(R.id.tv_basicInsuranceFee, "￥" + item.getBasicInsuranceFee() + "元");
        ImageView imageView = helper.getView(R.id.car_image);
        GlideUtils.getInstance().loadPathImage(mContext, item.getImage(), imageView);


        helper.addOnClickListener(R.id.ll_car);
    }
}
