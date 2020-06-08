package com.xinzu.xiaodou.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xinzu.xiaodou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<String> foodDatas;

    public HomeAdapter(Context context, List<String> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }

    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        final GoodsCategoryBean dataBean = foodDatas.get(position);
//        List<GoodsCategoryBean.SubCatagoryBean> dataList = dataBean.getSubCatagory();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            viewHold = new ViewHold();
            viewHold.gridView =  convertView.findViewById(R.id.gridView);
            viewHold.blank = (TextView) convertView.findViewById(R.id.blank);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        List<String> strings = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            strings.add("");
        }
//        HomeItemAdapter adapter = new HomeItemAdapter(context, foodDatas.get(position).getSubCats());
//        HomeItemAdapter adapter = new HomeItemAdapter(context, strings);
////
////        viewHold.blank.setText(foodDatas.get(position).getCatName());
//        viewHold.blank.setText("名字");
//        viewHold.gridView.setAdapter(adapter);
//        viewHold.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
////                context.startActivity(new Intent(context,ShopListActivity2.class).putExtra("catId",foodDatas.get(position).getSubCats().get(position2).getCatId()+"").putExtra("catName",foodDatas.get(position).getSubCats().get(position2).getCatName()));
//            }
//        });
        return convertView;
    }

    private static class ViewHold {
        private TextView gridView;
        private TextView blank;
    }

}
