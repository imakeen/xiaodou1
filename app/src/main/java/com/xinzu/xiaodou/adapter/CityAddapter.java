package com.xinzu.xiaodou.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzu.xiaodou.R;

import java.util.List;

public class CityAddapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    public CityAddapter() {
        super(R.layout.location_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.name, item.getTitle()).
                setText(R.id.sub, item.getSnippet());
        helper.addOnClickListener(R.id.ll_diqu);
    }

}
