package com.wz.bubble.bubble.extendsclass;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BANBEICHAS on 2016/5/13.
 */
public class MyContentObserver extends ContentObserver {

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    Handler handler;
    Context context;
    public static final int GET_YAN_ZHENG_MA=0;

    public MyContentObserver(Context context, Handler handler) {
        super(handler);
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (uri.toString().equals("content://sms/raw")) {

            return;

        }
        else {
            Uri uriinbox = Uri.parse("content://sms/inbox");
            Cursor cursor = context.getContentResolver().query(uriinbox, null, null, null, "date desc");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String smsbody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    Matcher matcher = Pattern.compile("(\\d{4})").matcher(smsbody);
                    if (matcher.find()) {
                        handler.obtainMessage(GET_YAN_ZHENG_MA, matcher.group(0)).sendToTarget();

                    }
                }

            }

        }
    }
}
