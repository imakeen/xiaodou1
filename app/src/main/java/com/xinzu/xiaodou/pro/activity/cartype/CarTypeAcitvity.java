package com.xinzu.xiaodou.pro.activity.cartype;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.adapter.HomeAdapter;
import com.xinzu.xiaodou.base.mvp.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CarTypeAcitvity extends BaseMvpActivity<CarTypePresenter> implements CarTypeContract.View {
    @BindView(R.id.lv_menu)
    ListView lvMenu;
    @BindView(R.id.lv_home)
    ListView lvHome;

    private HomeAdapter homeAdapter;
   // private MenuAdapter menuAdapter;
    private int currentItem;
    private List<Integer> showTitle = new ArrayList<>();

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initBundle() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_cartype;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("");
            showTitle.add(i);
        }

        homeAdapter = new HomeAdapter(this, stringList);
        lvHome.setAdapter(homeAdapter);
//        menuAdapter = new MenuAdapter(this, stringList);
//        lvMenu.setAdapter(menuAdapter);
//        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                menuAdapter.setSelectItem(position);
//                menuAdapter.notifyDataSetInvalidated();
//                lvHome.setSelection(showTitle.get(position));
//            }
//        });

        lvHome.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
//                    menuAdapter.setSelectItem(currentItem);
//                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }
}
