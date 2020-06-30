package com.xinzu.xiaodou.util;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CountDownTimerUtils extends CountDownTimer {

    WeakReference<Button> mTextView; //显示倒计时的文字  用弱引用 防止内存泄漏

    public CountDownTimerUtils(Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = new WeakReference(button);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //用弱引用 先判空 避免崩溃
        if (mTextView.get() == null) {
            cancle();
            return;
        }
        mTextView.get().setClickable(false); //设置不可点击
        mTextView.get().setText(millisUntilFinished / 1000 + "秒后重新发送"); //设置倒计时时间
        mTextView.get().setText(mTextView.get().getText().toString());
    }
    @Override
    public void onFinish() {
        //用软引用 先判空 避免崩溃
        if (mTextView.get() == null){
            cancle();
            return;
        }
        mTextView.get().setText("重新获取验证码");
        mTextView.get().setClickable(true);//重新获得点击
    }
    public void cancle() {
        if (this != null) {
            this.cancel();
        }
    }
}