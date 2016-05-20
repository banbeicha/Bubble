package com.wz.bubble.bubble.lister;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.wz.bubble.bubble.MyApp;
import com.wz.bubble.bubble.data.UserInfoData;
import com.wz.bubble.bubble.model.User;

import java.util.logging.Logger;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by BANBEICHAS on 2016/5/12.
 */
public class UserSavelister extends SaveListener{
    Activity context;
    User user;

    public UserSavelister(Activity context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onSuccess() {
        MyApp.TX_ID=user.getObjectId();
        UserInfoData.getToken(context,MyApp.TX_ID,MyApp.TX_NAME,MyApp.TX_HEADPIC);
        Log.i("用户信息","保存成功");
    }

    @Override
    public void onFailure(int i, String s) {
        Log.i("用户信息","保存失败"+i+"&&"+s);
    }
}
