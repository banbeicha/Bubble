package com.wz.bubble.bubble;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Collection;
import java.util.Iterator;

import io.rong.imkit.RongIM;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class ConversationActivity extends AppCompatActivity {

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_DeviceDefault_Light_Color_SeaGreen);
        setContentView(R.layout.activity_conversation);
       title= getIntent().getData().getQueryParameter("title");
        getActionBar().setTitle(title);
        showTitleType();
    }

    private void showTitleType() {
        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType conversationType, String s, Collection<TypingStatus> collection) {
                int count = collection.size();
                if (count > 0) {
                    Iterator iterator = collection.iterator();
                    TypingStatus status = (TypingStatus) iterator.next();
                    String objectName = status.getTypingContentType();
                    MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                    MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                    //匹配对方正在输入的是文本消息还是语音消息
                    if (objectName.equals(textTag.value())) {
                        //显示“对方正在输入”
                        getActionBar().setTitle("对方正在输入");
                    } else if (objectName.equals(voiceTag.value())) {
                        //显示"对方正在讲话"
                        getActionBar().setTitle("对方正在讲话");
                    }
                }
                    else{
                        getActionBar().setTitle(title);
                    }

            }
        });
    }
}
