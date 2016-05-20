package com.wz.bubble.bubble.extendsclass;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by BANBEICHAS on 2016/5/13.
 */
public class MyCountDownTimer extends CountDownTimer {
   TextView textView;
    public MyCountDownTimer(long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView=textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText("重新发送" + millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        textView.setEnabled(true);
        textView.setText("重新发送");
    }
}