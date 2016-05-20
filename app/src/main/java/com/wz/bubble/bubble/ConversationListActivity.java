package com.wz.bubble.bubble;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import io.rong.imkit.widget.AsyncImageView;

public class ConversationListActivity extends AppCompatActivity {
    @ViewInject(R.id.aiv_ConversationList_headpic)
    AsyncImageView asyncImageView;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        x.view().inject(this);
        x.image().bind(asyncImageView, MyApp.TX_HEADPIC);
        initPopupWindow();
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
