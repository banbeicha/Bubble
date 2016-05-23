package com.wz.bubble.bubble;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.wz.bubble.bubble.Message.SendAddFriend;
import com.wz.bubble.bubble.Provider.RongIMEvent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class ConversationListActivity extends AppCompatActivity {
    @ViewInject(R.id.aiv_ConversationList_headpic)
    AsyncImageView asyncImageView;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        x.view().inject(this);
        RongIMEvent.init();
        x.image().bind(asyncImageView, MyApp.TX_HEADPIC);
        initPopupWindow();
        RongIM.getInstance().getRongIMClient().sendMessage(Conversation.ConversationType.PRIVATE, "f34898691d", new SendAddFriend("{content\":\"你好\"}".getBytes()), "", "", new RongIMClient.SendMessageCallback() {
            @Override
            public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                Log.e( "onError: ",errorCode+"" );
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.e( "onSuccess: ",integer+"" );

            }
        }, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Event(value = {R.id.rbt_more})
    private void click(View view){
        switch (view.getId()){
            case R.id.rbt_more:
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                else{
                popupWindow.showAsDropDown(view,-120,0);
                }
                break;

        }
    }

    private void initPopupWindow() {
        View v=getLayoutInflater().inflate(R.layout.popupwindow_view,null);
        popupWindow=new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.scale);

    }
}
