package com.wz.bubble.bubble;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.wz.bubble.bubble.extendsclass.MyContentObserver;
import com.wz.bubble.bubble.extendsclass.MyCountDownTimer;
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
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {

    @ViewInject(R.id.et_phone)
    EditText etPhone;
    @ViewInject(R.id.et_yanzhengma)
    EditText etYanzhengma;
    @ViewInject(R.id.tv_send)
    TextView tvSend;
    boolean flag;
    List<User> list;
    private EventHandler eventHandler;
    private Handler handle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyContentObserver.GET_YAN_ZHENG_MA:
                    etYanzhengma.setText(msg.obj.toString());
                    break;
                case MyFindCallBack.XIAO_YAN_PHONE:
                    list = (List<User>) msg.obj;
                    for (User user : list
                            ) {
                        if (TextUtils.equals(user.getPhone(), etPhone.getText().toString())) {
                            TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content), "此手机号已经注册过", TSnackbar.LENGTH_LONG).setAction("去登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }).setActionTextColor(Color.parseColor("#96FFF2"));
                            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.mz_theme_color_seagreen));
                            snackbar.show();
                            etPhone.setError("手机号码已被注册，请重新输入");
                            etPhone.setText("");
                        }

                    }
                    break;
            }
        }
    };
    private MyContentObserver contentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        x.view().inject(this);
        flag = getIntent().getBooleanExtra("flag", true);
        sms();
    }

    private void sms() {
        TelephoneUtil.getThisPhone(this, etPhone);
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    if ("false".equals(data.toString())) {
                        SnackbarUtil.show(RegisterActivity.this, "发送成功");
                        getContentResolver().registerContentObserver(Uri.parse("context://sms"), true, contentObserver);
                    }
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        String json = data.toString().substring(21);
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.optInt("status") == 603) {
                            SnackbarUtil.show(RegisterActivity.this, "手机号无效");
                        } else if (jsonObject.optInt("status") == 468) {
                            SnackbarUtil.show(RegisterActivity.this, "验证码错误");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    try {
                        JSONObject jsonObject = new JSONObject(data.toString());
                        if (etPhone.getText().toString().equals(jsonObject.optLong("phone") + "")) {
                            if (flag) {
                                startActivity(new Intent(RegisterActivity.this, NextRegisterActivity.class).putExtra("phone", etPhone.getText().toString()));
                                finish();
                            } else {
                                startActivity(new Intent(RegisterActivity.this, FoundPwdActivity.class).putExtra("phone", etPhone.getText().toString()));
                                finish();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        contentObserver = new MyContentObserver(this, handle);
        if (flag) {
            etPhone.addTextChangedListener(this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        SMSSDK.unregisterEventHandler(eventHandler);
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    @Event(value = {R.id.tv_send, R.id.btn_next})
    private void onClick(View v) {
        String phone = etPhone.getText().toString();
        switch (v.getId()) {
            case R.id.tv_send:
                if (TextUtils.isEmpty(phone)) {

                    etPhone.setError("请输入手机号");
                } else if (phone.length() < 11 || !phone.startsWith("1")) {
                    etPhone.setError("请输入正确的手机号");
                } else {
                    SMSSDK.getVerificationCode("86", phone);
                    tvSend.setEnabled(false);
                    new MyCountDownTimer(60 * 1000, 1000, tvSend).start();
                }
                break;
            case R.id.btn_next:
                if (TextUtils.isEmpty(phone)) {
                    etPhone.setError("请输入手机号");
                }
                if (TextUtils.isEmpty(etYanzhengma.getText().toString())) {
                    etYanzhengma.setError("请输入验证码");
                } else {
                    SMSSDK.submitVerificationCode("86", phone, etYanzhengma.getText().toString());
                }
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 11) {
            BmobQuery<User> bmobQuery = new BmobQuery<User>();
            bmobQuery.addWhereEqualTo("phone", s);
            bmobQuery.findObjects(this, new MyFindCallBack(handle));
        }
    }
}
