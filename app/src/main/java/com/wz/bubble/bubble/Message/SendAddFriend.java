package com.wz.bubble.bubble.Message;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.sea_monster.common.ParcelUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by BANBEICHAS on 2016/5/20.
 */
@MessageTag(value = "app:SendAddFriend", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class SendAddFriend extends MessageContent implements Parcelable {

    private  String content;

    public String getContent() {
        return content;
    }

    //给消息赋值。
    public SendAddFriend(Parcel in) {
        content= ParcelUtils.readFromParcel(in);//该类为工具类，消息属性

        //这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<SendAddFriend> CREATOR = new Creator<SendAddFriend>() {

        @Override
        public SendAddFriend createFromParcel(Parcel source) {
            return new SendAddFriend(source);
        }

        @Override
        public SendAddFriend[] newArray(int size) {
            return new SendAddFriend[size];
        }
    };

    public SendAddFriend(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("content"))
                content = jsonObj.optString("content");

        } catch (JSONException e) {
            RLog.e(this, "JSONException", e.getMessage());
        }

    }
    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", "这是一条消息内容");
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
    }
}
