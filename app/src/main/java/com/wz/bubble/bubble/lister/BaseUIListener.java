package com.wz.bubble.bubble.lister;


import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.wz.bubble.bubble.MyApp;
import com.wz.bubble.bubble.model.User;
import com.wz.bubble.bubble.util.SnackbarUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseUIListener implements IUiListener {
    private Activity mContext;
    private String mScope;
    Handler handler;
    public static final int AUTHORIZE_SUCCESS=99;
    public BaseUIListener(Activity mContext) {
        super();
        this.mContext = mContext;
    }
    public BaseUIListener(Activity mContext, String mScope,Handler handler) {
        super();
        this.mContext = mContext;
        this.mScope = mScope;
        this.handler=handler;
    }

    @Override
    public void onComplete(Object response) {
        try {
            handler.obtainMessage(AUTHORIZE_SUCCESS).sendToTarget();
            JSONObject jsonObject = new JSONObject(response.toString());
            MyApp.TX_NAME = jsonObject.optString("nickname");
            MyApp.TX_HEADPIC = jsonObject.optString("figureurl_qq_1");
            User user = new User(MyApp.TX_NAME, "", jsonObject.optString("gender"), "", MyApp.TX_HEADPIC, jsonObject.optString("province"), jsonObject.optString("city"));
            user.save(mContext, new UserSavelister(mContext, user));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError e) {
        SnackbarUtil.show(mContext, "获取信息错误");
    }

    @Override
    public void onCancel() {

    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Activity mContext) {
        this.mContext = mContext;
    }

}
