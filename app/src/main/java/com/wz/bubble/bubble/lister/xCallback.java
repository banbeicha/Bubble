package com.wz.bubble.bubble.lister;

import android.util.Log;

import org.json.JSONObject;
import org.xutils.common.Callback;

/**
 * Created by BANBEICHAS on 2016/5/12.
 */
public class xCallback implements Callback.CommonCallback<JSONObject> {
    @Override
    public void onSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onError(Throwable throwable, boolean b) {
        Log.i("请求出错",throwable.toString());
    }

    @Override
    public void onCancelled(CancelledException e) {
        Log.i("请求取消",e.toString());
    }

    @Override
    public void onFinished() {

    }
}
