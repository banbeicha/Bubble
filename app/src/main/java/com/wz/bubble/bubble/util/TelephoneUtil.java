package com.wz.bubble.bubble.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.EditText;

/**
 * Created by BANBEICHAS on 2016/5/18.
 */
public class TelephoneUtil {
    public static void getThisPhone(Context context, EditText editText) {
        TelephonyManager telephonyManager = (TelephonyManager)context. getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            editText.setText(telephonyManager.getLine1Number());
        }
    }
}
