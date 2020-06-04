package com.xinzu.xiaodou.pro.fragment.home.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.xinzu.xiaodou.R;
import com.xinzu.xiaodou.bean.backTimeBean;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PickerDailog extends Dialog {
    String TAG = "PickerDailog";
    Activity activity;
    private callback callback;
    private backTimeBean timeBean;
    private NumberPicker mDateSpinner;
    private NumberPicker mHourSpinner;
    private NumberPicker mMinuteSpinner;
    private TextView mDailogOk;
    private TextView mDailogCancel;
    private TextView mSelectTv;
    private TextView mQuTimeTv;
    private TextView mHuanTimeTv;
    private View mHuanLy;
    private View mQuLy;
    private TextView mHuanStatuTv;
    private TextView mQuStatuTv;

    private String[] mDateDisplayValues = new String[60];
    private boolean isSelectQuCar = true;
    String[] hours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] mini = {"00", "30"};
    String[] day;

    private TextView tv_huan_day;
    private TextView tv_qu_day;
    private StringBuffer qu_day = new StringBuffer();

    private String start_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datedialog);
        if (day == null) {
            day = Day.getTitles();
        }
        init();
        initLayout();

    }

    public PickerDailog(@NonNull Context context, backTimeBean timeBean, Activity activity) {
        super(context, R.style.DialogTheme);

        this.activity = activity;
        this.timeBean = timeBean;

    }


    private void init() {
        setCanceledOnTouchOutside(true);
        Window mDialogWindow = getWindow();
        mDialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialogWindow.setAttributes(lp);
    }

    private void initLayout() {
        mDailogOk = this.findViewById(R.id.dailog_ok);
        mDailogCancel = this.findViewById(R.id.dailog_cancel);
        mSelectTv = this.findViewById(R.id.dailog_select_tv);
        mQuTimeTv = this.findViewById(R.id.dailog_qu_time);
        tv_huan_day = this.findViewById(R.id.dailog_huan_day);
        tv_qu_day = this.findViewById(R.id.dailog_qu_day);
        mHuanTimeTv = this.findViewById(R.id.dailog_huan_time);
        mHuanLy = this.findViewById(R.id.dailog_huan_ly);
        mQuLy = this.findViewById(R.id.dailog_qu_ly);
        mHuanStatuTv = this.findViewById(R.id.dailog_huan_statu);
        mQuStatuTv = this.findViewById(R.id.dailog_qu_statu);
        mDateSpinner = this.findViewById(R.id.np_date);
        mHourSpinner = this.findViewById(R.id.np_hour);
        mMinuteSpinner = this.findViewById(R.id.np_minute);

        setPickerMargin(mDateSpinner);
        setPickerMargin(mHourSpinner);
        setPickerMargin(mMinuteSpinner);


        mHourSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mHourSpinner.setDisplayedValues(hours);
        mHourSpinner.setMaxValue(hours.length - 1);
        mHourSpinner.setMinValue(0);


        mMinuteSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMinuteSpinner.setDisplayedValues(mini);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setMaxValue(mini.length - 1);
        mMinuteSpinner.setValue(0);
        initData();

        LiseningButton();
    }

    private void initData() {
        String day = timeBean.getQu_day().substring(8, 10);
        String month = timeBean.getQu_day().substring(5, 7);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM月dd日");
        String format = simpleDateFormat1.format(calendar.getTime());
        tv_qu_day.setText(format);
        mQuTimeTv.setText(Integer.parseInt(timeBean.getBack_week_time().substring(3, 5)) + ":00");
        updateQuDateControl();
    }

    private void updateQuDateControl() {

        mDateSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mDateSpinner.setMinValue(0);
        mDateSpinner.setMaxValue(day.length - 1);
        mDateSpinner.setDisplayedValues(Day.getTitles());
        mDateSpinner.setValue(0);
        mDateSpinner.invalidate();
        mDateSpinner.setWrapSelectorWheel(false);


        for (int i = 0; i < day.length; i++) {
            if (tv_qu_day.getText().toString().equals(day[i].substring(0, day[i].length() - 4))) {
                mDateSpinner.setValue(i);
            }
        }

        mHourSpinner.setValue(Integer.parseInt(timeBean.getBack_week_time().substring(3, 5)));

        qu_day.append(tv_qu_day.getText().toString() + mQuTimeTv.getText().toString());
        mDateSpinner.setOnValueChangedListener((picker, oldVal, newVal) -> {
            LogUtils.e("===================>" + newVal);
            start_day = day[newVal].substring(0, day[newVal].length() - 4);
            tv_qu_day.setText(start_day);
            qu_day.setLength(0);
            qu_day.append(day[newVal] + mQuTimeTv.getText().toString());
        });
    }


    /**
     * 设置picker之间的间距
     */
    private void setPickerMargin(NumberPicker picker) {
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) picker.getLayoutParams();
        p.setMargins(0, 0, 0, 0);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            p.setMarginStart(0);
            p.setMarginEnd(0);
        }
        setDividerColor(picker);
        setNumberPickerDivider(picker);
    }

    /**
     * 设置picker分割线的颜色
     */
    private void setDividerColor(NumberPicker picker) {

        try {
            Field field = NumberPicker.class.getDeclaredField("mSelectionDivider");
            if (field != null) {
                field.setAccessible(true);
                field.set(picker, new ColorDrawable(0xffcccccc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置picker分割线的宽度
     */
    private void setNumberPickerDivider(NumberPicker picker) {

        Field[] fields = NumberPicker.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals("mSelectionDividerHeight")) {
                f.setAccessible(true);
                try {
                    f.set(picker, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void LiseningButton() {
        mDailogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String day = qu_day.substring(3, 5);
                String month = qu_day.substring(0, 2);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                String format = simpleDateFormat1.format(calendar.getTime());
                callback.getTime(format, null, null, null);
                dismiss();
            }
        });


        mDailogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mHuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        mQuLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectQuCar = true;
                //  updateQuDateControl();


            }
        });
    }

    public void setOnDateSelectFinished(callback callback) {
        this.callback = callback;
    }

    public interface callback {
        void getTime(String startTime_day, String startTime_week, String endTime_day, String endTime_week);
    }

}
