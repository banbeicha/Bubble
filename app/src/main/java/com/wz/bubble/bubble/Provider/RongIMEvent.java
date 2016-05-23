package com.wz.bubble.bubble.Provider;

import android.content.Context;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imkit.widget.provider.VoIPInputProvider;
import io.rong.imlib.model.Conversation;

/**
 * Created by BANBEICHAS on 2016/5/19.
 */
public class RongIMEvent implements RongIM.LocationProvider {
 public static void  init(){
    InputProvider.ExtendProvider[] singleProvider = {
            new VoIPInputProvider(RongContext.getInstance()),// 语音通话
            new ImageInputProvider(RongContext.getInstance()),
            new CameraInputProvider(RongContext.getInstance()),//相机

    };

    InputProvider.ExtendProvider[] muiltiProvider = {
            new ImageInputProvider(RongContext.getInstance()),

            new CameraInputProvider(RongContext.getInstance()),//相机


    };

    RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, singleProvider);

    RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.DISCUSSION, muiltiProvider);

    RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.CUSTOMER_SERVICE, muiltiProvider);

    RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.GROUP, muiltiProvider);
    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {

    }
}
