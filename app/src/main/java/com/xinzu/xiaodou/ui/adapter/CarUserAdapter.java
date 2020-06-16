package com.xinzu.xiaodou.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.bean.CarUserBean;

import java.util.List;

public class CarUserAdapter extends BaseQuickAdapter<CarUserBean.ConsumersBean, BaseViewHolder> {
    int selectItem = 0;
    private static String name;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public CarUserAdapter() {
        super(R.layout.item_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarUserBean.ConsumersBean item) {
        helper.setText(R.id.tv_name, item.getUserName())
                .setText(R.id.tv_card, item.getIdNo());
        helper.addOnClickListener(R.id.iv_delcect)
                .addOnClickListener(R.id.iv_edit).addOnClickListener(R.id.ll_slecet_user);
    }


}
