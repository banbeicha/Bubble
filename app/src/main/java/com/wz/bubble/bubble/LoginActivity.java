package com.wz.bubble.bubble;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.wz.bubble.bubble.CadLoading.CatLoadingView;
import com.wz.bubble.bubble.data.UserInfoData;
import com.wz.bubble.bubble.lister.BaseUIListener;
import com.wz.bubble.bubble.lister.MyFindCallBack;
import com.wz.bubble.bubble.model.User;
import com.wz.bubble.bubble.util.SnackbarUtil;
import com.wz.bubble.bubble.util.TelephoneUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.bmob.v3.BmobQuery;

public class LoginActivity extends AppCompatActivity  {
    @ViewInject(R.id.btn_sign_in_button) Button btnsigninbutton;
    @ViewInject(R.id.et_login_pwd)EditText etloginpwd;
    @ViewInject(R.id.et_login_phone) EditText etloginphone;
    private Tencent mTencent;
    private IUiListener listener;
    private String openid;
    private String acess_token;
    private String expires;
    List<User> list;
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyFindCallBack.XIAO_YAN_PWD:
                    list= (List<User>) msg.obj;
                    if (list.size()!=0){
                        User user=list.get(0);
                        MyApp.TX_NAME=user.getName();
                        MyApp.TX_HEADPIC=user.getHeadPic();
                        UserInfoData.getToken(LoginActivity.this,user.getObjectId(),user.getName(),user.getHeadPic());
                    }
                    else{
                        SnackbarUtil.show(LoginActivity.this,"用户名或者密码错误");
                    }
                    break;
                case BaseUIListener.AUTHORIZE_SUCCESS:
                    CatLoadingView catLoadingView=new CatLoadingView();
                    catLoadingView.show(getSupportFragmentManager(),"");
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        TelephoneUtil.getThisPhone(this,etloginphone);
    }

   @Event(value = {R.id.tv_login_foundpwd,R.id.tv_QQ_login,R.id.btn_sign_in_button})
    private void click(View view){
      switch (view.getId()){
          case R.id.tv_login_foundpwd:startActivity(new Intent(this,RegisterActivity.class).putExtra("flag",false));break;
          case R.id.tv_QQ_login: doLogin();break;
          case R.id.btn_sign_in_button:
              if(fkyz()){
                  try {
                      BmobQuery<User> userBmobQuery=new BmobQuery<User>();
                      userBmobQuery.addWhereEqualTo("phone",etloginphone.getText().toString());
                      userBmobQuery.addWhereEqualTo("pwd",etloginpwd.getText().toString());
                      userBmobQuery.findObjects(this, new MyFindCallBack(handler));
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
              break;
      }
  }

    private boolean fkyz() {
        String phone = etloginphone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            etloginphone.setError("请输入手机号");
            return false;
        }
        else if(phone.length()<11||!phone.startsWith("1")){
            etloginphone.setError("请输入正确的手机号");
            return false;
        }
        else if(TextUtils.isEmpty(etloginpwd.getText().toString())){
            etloginpwd.setError("请输入密码");
            return false;
        }

        return true;
    }

    private void doLogin() {
        mTencent = Tencent.createInstance(MyApp.TX_APPID, this);
        listener = new BaseUIListener(this) {
            @Override
            public void onComplete(Object response) {
                try {
                    JSONObject jsonObjec = new JSONObject(response.toString());
                    openid = jsonObjec.optString("openid");
                    acess_token = jsonObjec.optString("access_token");
                    expires = jsonObjec.optString("expires_in");
                    getUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        mTencent.login(this, "all", listener);

    }
    public void getUserInfo() {
        mTencent.setAccessToken(acess_token, expires);
        mTencent.setOpenId(openid);
        UserInfo info = new UserInfo(this, mTencent.getQQToken());
        info.getUserInfo(new BaseUIListener(this, "all",handler));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

