package com.wz.bubble.bubble.lister;

import android.os.Handler;
import android.util.Log;

import com.wz.bubble.bubble.model.User;

import org.json.JSONArray;

import java.util.List;

import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by BANBEICHAS on 2016/5/16.
 */
public class MyFindCallBack extends FindListener<User> {
    Handler handler;
    public final static int GET_USERID = 1;
    public final static int XIAO_YAN_PWD=66;
    public final static int XIAO_YAN_PHONE=2;
    public MyFindCallBack(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onSuccess(List<User> list) {
        if (list.size() != 0) {
            handler.obtainMessage(GET_USERID, list.get(0).getObjectId()).sendToTarget();
            handler.obtainMessage(XIAO_YAN_PHONE, list).sendToTarget();
        }

        handler.obtainMessage(XIAO_YAN_PWD, list).sendToTarget();

    }

    @Override
    public void onError(int i, String s) {
        Log.e("onError: ", s);
    }
}
