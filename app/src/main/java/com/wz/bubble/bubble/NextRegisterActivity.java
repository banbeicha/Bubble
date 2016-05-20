package com.wz.bubble.bubble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wz.bubble.bubble.lister.UserSavelister;
import com.wz.bubble.bubble.model.User;
import com.wz.bubble.bubble.util.SnackbarUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class NextRegisterActivity extends AppCompatActivity {

    @ViewInject(R.id.et_name)
    EditText etName;
    @ViewInject(R.id.et_pwd)
    EditText etPwd;
    @ViewInject(R.id.et_pwdok)
    EditText etPwdok;
    @ViewInject(R.id.btn_done)
    Button btnNext;
    String phone;
    String headpic="http://www.jf258.com/uploads/2014-09-25/204355767.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_register);
        x.view().inject(this);
        phone=getIntent().getStringExtra("phone");
    }

    @Event(value = {R.id.btn_done})
    private void onClick(View view) {
       if(fkyz()){
           User user=new User(etName.getText().toString(),etPwdok.getText().toString(),"",phone,headpic,"","");
           MyApp.TX_HEADPIC=headpic;
           MyApp.TX_NAME=etName.getText().toString();
           user.save(this, new UserSavelister(this,user));
       }

    }

    private boolean fkyz() {
        if(TextUtils.isEmpty(etName.getText().toString())){
            etName.setError("请输入昵称");
            return false;

        }
      else if(TextUtils.isEmpty(etPwd.getText().toString())){
           etPwd.setError("请输入密码");
            return false;

        }
     else if (TextUtils.isEmpty(etPwdok.getText().toString())){
            etPwdok.setError("请输入密码");
            return false;
        }
      else  if(!TextUtils.equals(etPwd.getText().toString(),etPwdok.getText().toString())){
            SnackbarUtil.show(this,"两次输入的密码不一致");
            return false;
        }
    return true;
    }

}
