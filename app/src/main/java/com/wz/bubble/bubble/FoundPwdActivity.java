package com.wz.bubble.bubble;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tencent.open.utils.SystemUtils;
import com.wz.bubble.bubble.lister.MyFindCallBack;
import com.wz.bubble.bubble.lister.MyUpdateLister;
import com.wz.bubble.bubble.model.User;
import com.wz.bubble.bubble.util.SnackbarUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.update.BmobUpdateAgent;

public class FoundPwdActivity extends AppCompatActivity {
    @ViewInject(R.id.et_modify_pwd)
    EditText et_modify_pwd;
    @ViewInject(R.id.et_modify_pwdok)
    EditText et_modify_pwdok;
    String phone;
    String id;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyFindCallBack.GET_USERID:
                    id = msg.obj.toString();
                    break;
                case MyUpdateLister.MODIFY_PWD_SUCCESS:
                    startActivity(new Intent(FoundPwdActivity.this, LoginActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        phone = getIntent().getStringExtra("phone");
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("phone", phone);
        bmobQuery.findObjects(this, new MyFindCallBack(handler));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_pwd);
        x.view().inject(this);
    }

    @Event(value = {R.id.btn_done})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                if (fkyz() && id != null) {
                    User user = new User();
                    user.setPwd(et_modify_pwdok.getText().toString());
                    user.update(this, id, new MyUpdateLister(this,handler));

                }
                break;
        }
    }

    private boolean fkyz() {
        if (TextUtils.isEmpty(et_modify_pwd.getText().toString())) {
            et_modify_pwd.setError("请输入密码");
            return false;

        } else if (TextUtils.isEmpty(et_modify_pwdok.getText().toString())) {
            et_modify_pwdok.setError("请输入密码");
            return false;
        } else if (!TextUtils.equals(et_modify_pwd.getText().toString(), et_modify_pwdok.getText().toString())) {
            SnackbarUtil.show(this, "两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
