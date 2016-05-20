package com.wz.bubble.bubble;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wz.bubble.bubble.data.UserInfoData;
import com.wz.bubble.bubble.model.User;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=getSharedPreferences("RY_USERINFO",MODE_PRIVATE);
       name= sharedPreferences.getString("name","");
        if(!"".equals(name)){
            MyApp.TX_NAME=name;
            MyApp.TX_ID=sharedPreferences.getString("userid","");
            MyApp.TX_HEADPIC=sharedPreferences.getString("headpic","");
            UserInfoData.connectRY(this,sharedPreferences.getString("token",""));
        }
        x.view().inject(this);
    }
    @Event(value = {R.id.tv_login,R.id.tv_register})
    private void click(View view){
        Intent it=null;
        switch (view.getId()){
            case R.id.tv_login:
                it=new Intent(this,LoginActivity.class);
                break;
            case R.id.tv_register:
                it=new Intent(this,RegisterActivity.class);
                it.putExtra("flag",true);
                break;
        }
        startActivity(it);
        finish();
    }
}
