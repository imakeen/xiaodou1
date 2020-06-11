package com.xinzu.xiaodou.view;

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
import com.xinzu.xiaodou.util.BetweenUtil;
import com.xinzu.xiaodou.util.Day;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.amap.api.mapcore2d.q.i;

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

    String[] hours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] mini = {"00", "30"};
    String[] day;

    private TextView tv_huan_day;
    private TextView tv_qu_day;
    private StringBuffer qu_day = new StringBuffer();
    private StringBuffer huan_day = new StringBuffer();
    private String start_day;

    private boolean start_end = true;
    private String minute;
    private int now_hour;

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

    public PickerDailog(Boolean start_end, @NonNull Context context, backTimeBean timeBean, Activity activity) {
        super(context, R.style.DialogTheme);
        this.start_end = start_end;
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

        mHourSpinner.setWrapSelectorWheel(false);


        mMinuteSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMinuteSpinner.setDisplayedValues(mini);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setMaxValue(mini.length - 1);
        //判断start_end  如果为ture 实现选中取车时间样式  false选中还车样式
        if (start_end) {
            mQuLy.setSelected(true);
            mHuanLy.setSelected(false);
            selet_qu();
        } else {
            mHuanLy.setSelected(true);
            mQuLy.setSelected(false);
            setlet_back();
        }
        initData();
        LiseningButton();
    }

    private void initData() {
        //取车时间
        String qu_day = timeBean.getQu_day().substring(8, 10);
        String qu_month = timeBean.getQu_day().substring(5, 7);
        tv_qu_day.setText(Day.dialog_start(qu_month, qu_day));
        mQuTimeTv.setText(Integer.parseInt(timeBean.getQu_week_time().substring(3, 5)) + ":00");
        //还车时间
        String huan_day = timeBean.getBack_day().substring(8, 10);
        String huan_month = timeBean.getBack_day().substring(5, 7);
        tv_huan_day.setText(Day.dialog_start(huan_month, huan_day));
        mHuanTimeTv.setText(Integer.parseInt(timeBean.getBack_week_time().substring(3, 5)) + ":00");
        updateQuDateControl();
        try {
            tianshu();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateQuDateControl() {

        mDateSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mDateSpinner.setMinValue(0);
        mDateSpinner.setMaxValue(day.length - 1);
        mDateSpinner.setDisplayedValues(Day.getTitles());
        mDateSpinner.setValue(0);
        mDateSpinner.invalidate();
        mDateSpinner.setWrapSelectorWheel(false);

        if (start_end) {

            qu_value();

        } else {
            huan_value();
        }

        //监听滑动日期
        mDateSpinner.setOnValueChangedListener((picker, oldVal, newVal) -> {
            LogUtils.e("===================>" + newVal);
            start_day = day[newVal].substring(0, day[newVal].length() - 4);
            if (start_end) {
                tv_qu_day.setText(start_day);
                qu_day.setLength(0);
                qu_day.append(day[newVal] + mQuTimeTv.getText().toString());
            } else {
                tv_huan_day.setText(start_day);
                huan_day.setLength(0);
                huan_day.append(day[newVal] + mQuTimeTv.getText().toString());
            }
            try {
                tianshu();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        //监听滑动时间
        mHourSpinner.setOnValueChangedListener(((picker, oldVal, newVal) -> {
            String hour = hours[newVal];
            if (start_end) {
                yincang(hour);

            } else {
                mHuanTimeTv.setText(Day.dialog_start_hour(hour, mHuanTimeTv.getText().toString()));
            }
            try {
                tianshu();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }));
        //监听滑动分钟
        mMinuteSpinner.setOnValueChangedListener(((picker, oldVal, newVal) -> {
            if (start_end) {
                String mMinute = mQuTimeTv.getText().toString();
                mMinute = mMinute.substring(0, mMinute.length() - 3);
                mQuTimeTv.setText(mMinute + ":" + mini[newVal]);
                mMinute = "";
            } else {
                String mMinute = mHuanTimeTv.getText().toString();
                mMinute = mMinute.substring(0, mMinute.length() - 3);
                mHuanTimeTv.setText(mMinute + ":" + mini[newVal]);
                mMinute = "";
            }
        }));
        //取消按钮
        mDailogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    //设置取车的时间
    private void qu_value() {
        for (int i = 0; i < day.length; i++) {
            if (tv_qu_day.getText().toString().equals(day[i].substring(0, day[i].length() - 4))) {
                mDateSpinner.setValue(i);
            }
        }
        mHourSpinner.setValue(Integer.parseInt(timeBean.getQu_week_time().substring(3, 5)));
        minute = timeBean.getQu_week_time();
        mMinuteSpinner.setValue(Integer.parseInt(minute.substring(minute.length() - 2)));
        qu_day.append(tv_qu_day.getText().toString() + mQuTimeTv.getText().toString());
    }

    //设置还车的时间
    private void huan_value() {
        for (int i = 0; i < day.length; i++) {
            if (tv_huan_day.getText().toString().equals(day[i].substring(0, day[i].length() - 4))) {
                mDateSpinner.setValue(i);
            }
        }
        mHourSpinner.setValue(Integer.parseInt(timeBean.getBack_week_time().substring(3, 5)));

        minute = timeBean.getBack_week_time();
        mMinuteSpinner.setValue(Integer.parseInt(minute.substring(minute.length() - 2)));
        huan_day.append(tv_huan_day.getText().toString() + mHuanTimeTv.getText().toString());
    }

    //实现不能选择已过的时间
    private void yincang(String hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 3); //减填负数
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM月dd日HH");

        String format = simpleDateFormat1.format(calendar.getTime());
        now_hour = Integer.parseInt(format.substring(format.length() - 2, format.length()));
        String mitun = mQuTimeTv.getText().toString();
        if (tv_qu_day.getText().toString().equals(format.substring(0, format.length() - 2))) {
            if (Integer.parseInt(hour) < now_hour) {
                mHourSpinner.setValue(now_hour);
                mQuTimeTv.setText(Day.dialog_start_hour(now_hour + "", mitun.substring(mitun.length() - 2, mitun.length())));
            } else {
                mQuTimeTv.setText(Day.dialog_start_hour(hour, mitun.substring(mitun.length() - 2, mitun.length())))
                ;
            }
        } else {
            mQuTimeTv.setText(Day.dialog_start_hour(hour, mitun.substring(mitun.length() - 2, mitun.length())));
        }

    }

    /**
     * 实现天数
     *
     * @return
     * @parm
     */
    private void tianshu() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM月dd日HH:mm");
        String qutime = timeBean.getQu_day().substring(0, 4) + "-" + tv_qu_day.getText().toString() + mQuTimeTv.getText().toString();
        String huantime = timeBean.getBack_day().substring(0, 4) + "-" + tv_huan_day.getText().toString() + mHuanTimeTv.getText().toString();
        mSelectTv.setText(BetweenUtil.getDatePoor(simpleDateFormat.parse(huantime), simpleDateFormat.parse(qutime)));
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
                //  callback.getTime(format, null, format_huan, null);
                dismiss();
            }
        });
        mHuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHuanLy.setSelected(true);
                mQuLy.setSelected(false);
                setlet_back();
                start_end = false;
                for (int i = 0; i < day.length; i++) {
                    if (tv_huan_day.getText().toString().equals(day[i].substring(0, day[i].length() - 4))) {
                        mDateSpinner.setValue(i);
                    }
                }
                String hour = mHuanTimeTv.getText().toString();

                mHourSpinner.setValue(Integer.parseInt(hour.substring(0, 2)));
                mMinuteSpinner.setValue(Integer.parseInt(hour.substring(hour.length() - 2)));
            }
        });
        mQuLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuLy.setSelected(true);
                mHuanLy.setSelected(false);
                selet_qu();
                start_end = true;
                for (int i = 0; i < day.length; i++) {
                    if (tv_qu_day.getText().toString().equals(day[i].substring(0, day[i].length() - 4))) {
                        mDateSpinner.setValue(i);
                    }
                }
                String hour = mQuTimeTv.getText().toString();
                mHourSpinner.setValue(Integer.parseInt(hour.substring(0, 2)));
                mMinuteSpinner.setValue(Integer.parseInt(hour.substring(hour.length() - 2)));
            }
        });

    }


    private void selet_qu() {
        mQuStatuTv.setTextColor(0xffffffff);
        tv_qu_day.setTextColor(0xffffffff);
        mQuTimeTv.setTextColor(0xffffffff);

        mHuanStatuTv.setTextColor(0xff343434);
        tv_huan_day.setTextColor(0xff343434);
        mHuanTimeTv.setTextColor(0xff343434);
    }

    private void setlet_back() {
        mQuStatuTv.setTextColor(0xff343434);
        tv_qu_day.setTextColor(0xff343434);
        mQuTimeTv.setTextColor(0xff343434);

        mHuanStatuTv.setTextColor(0xffffffff);
        tv_huan_day.setTextColor(0xffffffff);
        mHuanTimeTv.setTextColor(0xffffffff);

    }

    public void setOnDateSelectFinished(callback callback) {
        this.callback = callback;
    }

    public interface callback {
        void getTime(String startTime_day, String startTime_week, String endTime_day, String endTime_week);
    }

}
