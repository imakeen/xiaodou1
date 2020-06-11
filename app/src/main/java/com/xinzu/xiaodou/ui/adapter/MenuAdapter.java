//package com.xinzu.xiaodou.ui.adapter;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.xinzu.xiaodou.R;
//import com.xinzu.xiaodou.bean.CarGroupBean;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 左主界面ListView的适配器
// *
// * @author Administrator
// */
//public class MenuAdapter extends BaseQuickAdapter<CarGroupBean.CarGroupListBean, BaseViewHolder> {
//    int selectItem = 0;
//
//    public MenuAdapter() {
//        super(R.layout.item_car_type);
//    }
//
//    public void setSelectItem(int selectItem) {
//        this.selectItem = selectItem;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, CarGroupBean.CarGroupListBean item) {
//        TextView textVie = helper.getView(R.id.item_name);
//        View view = helper.getView(R.id.view_ce);
//        if (helper.getAdapterPosition() == selectItem) {
//
//            textVie.setBackgroundColor(Color.parseColor("#ffffff"));
//            textVie.setTextColor(Color.parseColor("#DB5414"));
//
//
//            view.setVisibility(View.VISIBLE);
//        } else {
//            textVie.setBackgroundColor(Color.parseColor("#fff7f7f7"));
//            textVie.setTextColor(Color.BLACK);
//            view.setVisibility(View.INVISIBLE);
//        }
//        helper.setText(R.id.item_name, item.getCarGroupName());
//        helper.addOnClickListener(R.id.item_name);
//    }
//}
