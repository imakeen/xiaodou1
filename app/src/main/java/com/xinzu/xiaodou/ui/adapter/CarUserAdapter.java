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

    private static String name;

    public CarUserAdapter() {
        super(R.layout.item_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarUserBean.ConsumersBean item) {
        helper.setText(R.id.tv_name, item.getUserName())
                .setText(R.id.tv_card, item.getIdNo());

        helper.addOnClickListener(R.id.iv_delcect)
                .addOnClickListener(R.id.iv_edit);

        CheckBox checkBox = helper.getView(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(null);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub

                if (isChecked) {
                    item.setIfselect(true);
                    name = item.getUserName();
                    SPUtils.getInstance().put("user", item.getUserName());
                    SPUtils.getInstance().put("ConsumerId", item.getConsumerId());

                } else {
                    SPUtils.getInstance().put("user", "");
                    SPUtils.getInstance().put("ConsumerId", "");
                    item.setIfselect(false);
                }
            }
        });
        if (item.getIfselect()) {
            checkBox.setEnabled(true);
        } else {
            checkBox.setEnabled(false);
        }
    }

    public static String name() {
        if (name.isEmpty()) {
            return "";
        } else {
            return name;
        }
    }
}
