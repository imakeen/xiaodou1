package com.radish.framelibrary.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.radish.baselibrary.utils.LogUtils;
import com.radish.framelibrary.R;


/**
 * @作者 radish
 * @创建日期 2019/1/17 3:40 PM
 * @邮箱 15703379121@163.com
 * @描述 颜色跟踪变化的textView
 */
public class ColorTrackTextView extends AppCompatTextView {

    private final Context mContext;
    // 原始字体颜色画笔
    private Paint mOriginPaint;

    // 原始字体颜色
    private int mOriginColor = Color.parseColor("#333333");

    // 原始字体大小
    private float mOriginTextSize = getTextSize();

    // 变色字体颜色画笔
    private Paint mChangePaint;

    // 变色字体颜色
    private int mChangeColor = Color.RED;

    // 变色字体大小
    private float mChangeTextSize = getTextSize();

    // 当前进度
    private float mCurrentProgress = 0f;

    // 实现两种朝向 - 从左到右还是从右到左
    private Direction mDirection = Direction.LEFT_TO_RIGHT;
    private Resources mResources;
    private int mTextGravity = TextGravity.BOTTOM;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint(attrs);

    }

    /**
     * 获取画笔
     *
     * @param attrs
     * @return
     */
    private void initPaint(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        mOriginColor = array.getColor(R.styleable.ColorTrackTextView_originColorTrack, Color.BLACK);
        mChangeColor = array.getColor(R.styleable.ColorTrackTextView_changeColorTrack, Color.RED);

        mOriginTextSize = array.getDimension(R.styleable.ColorTrackTextView_originSizeTrack, 46);
        mChangeTextSize = array.getDimension(R.styleable.ColorTrackTextView_changeSizeTrack, 50);

        array.recycle();

        mOriginPaint = getPaint(mOriginColor, mOriginTextSize);
        mChangePaint = getPaint(mChangeColor, mChangeTextSize);
    }

    private Paint getPaint(int color, float textSize) {
        Paint paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        // 防抖动
        paint.setDither(true);
        // 设置颜色
        paint.setColor(color);
        // 设置字体大小
        paint.setTextSize(textSize);
        return paint;

    }

    /**
     * 设置当前进度
     *
     * @param currentProgress
     */
    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        // 重新绘制
        invalidate();
    }

    private void drawText(String text, Canvas canvas, Paint paint, int start, int end) {
        // 保存画布状态
        canvas.save();

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        if (getHeight() < textHeight)
            setHeight(textHeight);

        if (getWidth() < bounds.width()) {
            setWidth(bounds.width());
        }

        // 只绘制截取部分
        canvas.clipRect(new Rect(start, 0, end, getHeight()));

        // x 绘制的开始部分，不考虑左右padding不相等的情况下, = 控件宽度的一半 - 字体宽度的一半
        int textWidth = bounds.width();
        int x = (getWidth() - textWidth) / 2;
        int textDistance = 0;
        if (mTextGravity == TextGravity.BOTTOM) {
            textDistance = getHeight() - textHeight;
        } else if (mTextGravity == TextGravity.CENTER) {
            textDistance = (getHeight() - textHeight) / 2;
        }
        // y 代表基线 Baseline
        canvas.drawText(text, x, -fontMetricsInt.top + textDistance, paint);
        // 释放画布
        canvas.restore();

    }

    public class TextGravity {
        public static final int TOP = 0x0011;
        public static final int CENTER = 0x0022;
        public static final int BOTTOM = 0x0033;
    }

    /**
     * 设置文本gravity
     *
     * @param gravity
     */
    public void setTextGravity(int gravity) {
        this.mTextGravity = gravity;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

//        实现不同的颜色
//        1.1 计算中间的位置 = 当前的速度*控件宽度
        int width = getWidth();
        int middle = (int) (mCurrentProgress * width);
//        1.2 根据中间的位置去绘制 两边不同文字颜色 截取绘制文字的范围
        String text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }

        //绘制文字
        drawOriginText(canvas, text, middle);
        drawChangeText(canvas, text, middle);

    }

    /**
     * 绘制变色文字
     *
     * @param canvas
     * @param text
     * @param middle
     */
    private void drawChangeText(Canvas canvas, String text, int middle) {
        int start = 0;
        int end = middle;
        if (mDirection == Direction.RIGHT_TO_LEFT) {
            start = getWidth() - middle;
            end = getWidth();
        }
        drawText(text, canvas, mChangePaint, start, end);
    }

    /**
     * 绘制原始文字
     *
     * @param canvas
     * @param text
     * @param middle
     */
    private void drawOriginText(Canvas canvas, String text, int middle) {
        int start = middle;
        int end = getWidth();
        if (mDirection == Direction.RIGHT_TO_LEFT) {
            start = 0;
            end = getWidth() - middle;
        }
        drawText(text, canvas, mOriginPaint, start, end);

    }

    /**
     * 设置颜色渐变的朝向
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    /**
     * 颜色渐变的两种朝向
     */
    public enum Direction {
        RIGHT_TO_LEFT,
        LEFT_TO_RIGHT
    }

    public void setOriginColor(int originColor) {
        this.mOriginColor = originColor;
        if (mOriginPaint != null)
            mOriginPaint.setColor(mOriginColor);
    }

    public void setChangeColor(int changeColor) {
        this.mChangeColor = changeColor;
        if (mChangePaint != null)
            mChangePaint.setColor(mChangeColor);
    }

    public void setOriginTextSize(float originTextSize) {
        if (mOriginPaint != null) {
            if (mResources == null)
                mResources = this.mContext == null ? Resources.getSystem() : this.mContext.getResources();
            mOriginTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, originTextSize, mResources.getDisplayMetrics());
            mOriginPaint.setTextSize(mOriginTextSize);
        }

//        setTextSize(10);
////            setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
////            mOriginPaint.setTextSize(TypedValue.COMPLEX_UNIT_SP,mOriginTextSize);
    }

    public void setChangeTextSize(float changeTextSize) {
        if (mChangePaint != null) {
            if (mResources == null)
                mResources = this.mContext == null ? Resources.getSystem() : this.mContext.getResources();
            mChangeTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, changeTextSize, mResources.getDisplayMetrics());
            mChangePaint.setTextSize(mChangeTextSize);
        }
//            mChangePaint.setTextSize(mChangeTextSize);
    }
}
