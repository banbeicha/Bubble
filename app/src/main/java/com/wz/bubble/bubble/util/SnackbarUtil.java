package com.wz.bubble.bubble.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.wz.bubble.bubble.R;

/**
 * Created by BANBEICHAS on 2016/5/6.
 */
public class SnackbarUtil {
    private static TSnackbar snackbar;
    public static void show(Activity context, String msg) {
                snackbar = TSnackbar.make(context.findViewById(android.R.id.content), msg, TSnackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.mz_theme_color_seagreen));
                snackbar.show();

    }

}
