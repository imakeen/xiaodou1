package com.xinzu.xiaodou.util.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.xinzu.xiaodou.R;


public class MyHouseListFooter extends RelativeLayout implements RefreshFooter {


    private Context context;
    private final TextView mTvState;
    private final ProgressBar mPb;
    private final RelativeLayout rlRefresh;

    protected boolean mNoMoreData = false;

    public MyHouseListFooter(Context context, ViewGroup viewGroup) {
        super(context);
        this.context = context;

        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(context).inflate(R.layout.view_footer, viewGroup,false);

        mTvState = view.findViewById(R.id.tv_state);
        mPb = view.findViewById(R.id.pb_refresh);
        rlRefresh = view.findViewById(R.id.rl_refresh);
        addView(view);
//        setMinimumHeight(RxImageTool.dp2px(50));
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        if (!mNoMoreData) {

        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (!mNoMoreData) {
            mTvState.setText(success?"加载完成":"加载失败");
        }
        return 500;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            final View arrowView = mPb;
            if (noMoreData) {
                mTvState.setText("没有更多数据了");
                arrowView.setVisibility(GONE);
            } else {
                mTvState.setText("上拉加载更多");
                arrowView.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        if (!mNoMoreData) {
            switch (newState) {
                case None:
                case PullUpToLoad:
                    rlRefresh.setVisibility(GONE);
                    mPb.setVisibility(INVISIBLE);
                    mTvState.setText("上拉加载更多");
                    break;
                case Loading:
                    rlRefresh.setVisibility(GONE);
                    mPb.setVisibility(VISIBLE);
                    mTvState.setText("正在加载");
                    break;
                case ReleaseToLoad:
                    rlRefresh.setVisibility(GONE);
                    mPb.setVisibility(INVISIBLE);
                    mTvState.setText("释放立即加载");
                    break;
                default:
                    break;
            }
        }
    }
}
