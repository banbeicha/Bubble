package com.wz.bubble.bubble;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import org.xutils.x;
import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;
import io.rong.imkit.RongIM;
/**
 * Created by BANBEICHAS on 2016/5/5.
 */
public class MyApp extends Application {
    static final String TX_APPID = "1105305763";
    public static final String RY_APP_KEY = "25wehl3uwkkxw";
    public static final String RY_URI = "https://api.cn.ronghub.com/user/getToken.json";
    public static final String RY_APP_SECRET = "mzBq0tIrbKz41";
    public static String TX_NAME;
    public static String TX_HEADPIC;
    public static String TX_ID;
    public static final String RY_USERINFO="https://api.cn.ronghub.com/user/info.json";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }
        Bmob.initialize(this,"b0f712131c2b3285097ba4324fb91ccf");
        SMSSDK.initSDK(this, "12b3bbd270557", "f35359b3009904f997c1172c0975d367");
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
