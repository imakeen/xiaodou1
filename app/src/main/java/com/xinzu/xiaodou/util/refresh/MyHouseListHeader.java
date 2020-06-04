package com.xinzu.xiaodou.util.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.xinzu.xiaodou.R;


public class MyHouseListHeader extends RelativeLayout implements RefreshHeader {


    private Context context;
    private final ImageView mIvArrow;
    private final TextView mTvState;
    private final ProgressBar mPb;
    private final RelativeLayout rlRefresh;

    public MyHouseListHeader(Context context, ViewGroup viewGroup) {
        super(context);
        this.context = context;
        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(context).inflate(R.layout.view_header, viewGroup,false);
        mIvArrow = view.findViewById(R.id.iv_arrow);
        mTvState = view.findViewById(R.id.tv_state);
        mPb = view.findViewById(R.id.pb_refresh);
        rlRefresh = view.findViewById(R.id.rl_refresh);

        addView(view);
       // setMinimumHeight(RxImageTool.dp2px(50));
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
//        mPb.setVisibility(VISIBLE);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (success){
            mTvState.setText("刷新成功");
        }else {
            mTvState.setText("刷新失败");
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
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                rlRefresh.setVisibility(VISIBLE);
                mPb.setVisibility(INVISIBLE);
                mIvArrow.setVisibility(VISIBLE);
                mTvState.setText("下拉开始刷新");
                mIvArrow.animate().rotation(0);
                break;
            case Refreshing:
                rlRefresh.setVisibility(GONE);
                mPb.setVisibility(VISIBLE);
                mIvArrow.setVisibility(INVISIBLE);
                mTvState.setText("正在刷新");
                break;
            case ReleaseToRefresh:
                rlRefresh.setVisibility(VISIBLE);
                mPb.setVisibility(INVISIBLE);
                mIvArrow.setVisibility(VISIBLE);
                mTvState.setText("释放立即刷新");
                mIvArrow.animate().rotation(180);
                break;
            default:
                break;
        }
    }
}
