package com.wz.bubble.bubble.lister;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.androidadvance.topsnackbar.TSnackbar;
import com.wz.bubble.bubble.util.SnackbarUtil;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by BANBEICHAS on 2016/5/16.
 */
public class MyUpdateLister extends UpdateListener{
    public static final int MODIFY_PWD_SUCCESS = 88;
    Activity context; Handler handler;

    public MyUpdateLister(Activity context, Handler handler) {
        this.context=context;
        this.handler=handler;
    }
    @Override
    public void onSuccess() {
        SnackbarUtil.show(context,"修改成功");
        handler.obtainMessage(MODIFY_PWD_SUCCESS).sendToTarget();

    }

    @Override
    public void onFailure(int i, String s) {
        SnackbarUtil.show(context,"修改失败");
    }
}
