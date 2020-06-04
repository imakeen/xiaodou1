package com.radish.framelibrary.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class IndicatorPointView extends AppCompatImageView {
    private Paint paint;

    public IndicatorPointView(Context context) {
        this(context, null);
    }

    public IndicatorPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        // 防抖动
        paint.setDither(true);
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public String int2Hex(int colorInt) {
        String hexCode = "";
        hexCode = String.format("#%06X", Integer.valueOf(16777215 & colorInt));
        return hexCode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable == null) {
            super.onDraw(canvas);
            return;
        }
        if (drawable instanceof ColorDrawable) {
            // 绘制圆
            paint.setColor(((ColorDrawable) drawable).getColor());
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
            return;
        }

        super.onDraw(canvas);
    }
}
