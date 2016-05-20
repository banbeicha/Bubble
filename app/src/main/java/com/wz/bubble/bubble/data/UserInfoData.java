package com.wz.bubble.bubble.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wz.bubble.bubble.MyApp;
import com.wz.bubble.bubble.lister.xCallback;
import com.wz.bubble.bubble.util.SHA1Utils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Random;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by BANBEICHAS on 2016/5/6.
 */
public class UserInfoData {
    private static UserInfo userInfo;

    public static void getToken(final Activity context, String userId, final String name, final String headPic) {
        RequestParams request = new RequestParams(MyApp.RY_URI);
        request.addBodyParameter("userId", userId);
        request.addBodyParameter("name", name);
        request.addBodyParameter("portraitUri", headPic);
        request.addHeader("App-Key", MyApp.RY_APP_KEY);
        String Timestamp = System.currentTimeMillis() + "";
        String Nonce = new Random().nextInt(1000000) + "";
        request.addHeader("Timestamp", Timestamp);
        request.addHeader("Nonce", Nonce);
        request.addHeader("Signature", SHA1Utils.hex_sha1(MyApp.RY_APP_SECRET + Nonce + Timestamp));
        x.http().post(request, new xCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("RY_USERINFO", Context.MODE_PRIVATE);
                if (jsonObject.optInt("code") == 200) {
                    String token = jsonObject.optString("token");
                    sharedPreferences.edit().putString("token", token).putString("userid", jsonObject.optString("userId"))
                            .putString("name",name).putString("headpic",headPic)
                            .commit();
                    connectRY(context, token);
                }
            }
        });
    }

    public static void connectRY(final Activity  context, String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                UserInfoData.getToken(context, MyApp.TX_ID, MyApp.TX_NAME, MyApp.TX_HEADPIC);
            }

            @Override
            public void onSuccess(String s) {


                if (RongIM.getInstance() != null) {
                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                        @Override
                        public UserInfo getUserInfo(String s) {
                            getUserInfos(s);
                            return userInfo;

                        }
                    }, true);
                    //启动会话列表界面
                    RongIM.getInstance().startConversationList(context);
                    context.finish();

                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }

    private static void getUserInfos(final String userid) {
        RequestParams request = new RequestParams(MyApp.RY_USERINFO);
        request.addBodyParameter("userId", userid);
        request.addHeader("App-Key", MyApp.RY_APP_KEY);
        String Timestamp = System.currentTimeMillis() + "";
        String Nonce = new Random().nextInt(1000000) + "";
        request.addHeader("Timestamp", Timestamp);
        request.addHeader("Nonce", Nonce);
        request.addHeader("Signature", SHA1Utils.hex_sha1(MyApp.RY_APP_SECRET + Nonce + Timestamp));
        x.http().post(request, new xCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                userInfo = new UserInfo(userid, jsonObject.optString("userName"), Uri.parse(jsonObject.optString("userPortrait")));

            }
        });

    }

}
