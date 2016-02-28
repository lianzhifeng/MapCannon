
package com.empty.mapcannon.app;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class App extends Application {
    public static final String NAME_COMMON = "COMMON";
    public static float fDensity;
    public static App instance;
    public static String packageName = "";
    public static float screenH;
    public static float screenW;
    private boolean bToContact;
    public Handler handler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            String str = (String) paramAnonymousMessage.obj;
            App.this.showTextToast(str);
        }
    };
    private SharedPreferences preferences;
    private String tempChatTime = "";
    private Toast toast;

    public static void cancelNotification() {
        try {
            ((NotificationManager) instance.getSystemService("notification")).cancelAll();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public static boolean checkNetwork() {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) instance
                .getSystemService("connectivity")).getActiveNetworkInfo();
        return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
    }

    public static App getInstance() {
        return instance;
    }

    public static String getTimeStamp() {
        return new Date().getTime() + "";
    }

    public static boolean isApplicationBroughtToBackground() {
        if (instance == null)
            return false;
        List localList = ((ActivityManager) instance.getSystemService("activity"))
                .getRunningTasks(1);
        return (!localList.isEmpty())
                && (!((ActivityManager.RunningTaskInfo) localList.get(0)).topActivity
                        .getPackageName().equals(instance.getPackageName()));
    }


    private void showTextToast(int paramInt) {
        showTextToast(getString(paramInt));
    }

    private void showTextToast(String paramString) {
        if (this.toast == null)
            this.toast = Toast.makeText(getApplicationContext(), paramString, 0);
        while (true) {
            this.toast.show();
            return;
        }
    }

    public static void showToast(int paramInt) {
        if (instance != null)
            instance.showTextToast(paramInt);
    }

    public static void showToast(String paramString) {
        if (instance != null)
            instance.showTextToast(paramString);
    }

    public void deleteAllPreference(String paramString) {
        SharedPreferences.Editor localEditor = getSharedPreferences(paramString, 0).edit();
        localEditor.clear();
        localEditor.commit();
    }

    public void deletePreference(String paramString) {
        SharedPreferences.Editor localEditor = getSharedPreferences("COMMON", 0).edit();
        localEditor.remove(paramString);
        localEditor.commit();
    }

    public void deletePreference(String paramString1, String paramString2) {
        SharedPreferences.Editor localEditor = getSharedPreferences(paramString1, 0).edit();
        localEditor.remove(paramString2);
        localEditor.commit();
    }

    public String getDeviceNo() {
        try {
            TelephonyManager localTelephonyManager = (TelephonyManager) getBaseContext()
                    .getSystemService("phone");
            String str1 = "" + localTelephonyManager.getDeviceId();
            String str2 = "" + localTelephonyManager.getSimSerialNumber();
            String str3 = new UUID(
                    ("" + Settings.Secure.getString(getContentResolver(), "android_id")).hashCode(),
                    str1.hashCode() << 32 | str2.hashCode()).toString();
            return str3;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public void onCreate() {
        super.onCreate();
        this.preferences = getSharedPreferences("COMMON", 0);
        fDensity = getResources().getDisplayMetrics().density;
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;
        instance = this;
        packageName = getPackageName();
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.exit(0);
        ((ActivityManager) getSystemService("activity")).killBackgroundProcesses(getPackageName());
    }

    public void onProviderDisabled(String paramString) {
    }

    public void onProviderEnabled(String paramString) {
    }

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {
    }

    public void onTerminate() {
        super.onTerminate();
        try {
            ((ActivityManager) getSystemService("activity"))
                    .killBackgroundProcesses(getPackageName());
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public String readPreference(String paramString) {
        return getSharedPreferences("COMMON", 0).getString(paramString, "");
    }

    public String readPreference(String paramString1, String paramString2) {
        return getSharedPreferences(paramString1, 0).getString(paramString2, "");
    }

    public void showToastAsyn(String paramString) {
        Message localMessage = new Message();
        localMessage.obj = paramString;
        this.handler.sendMessage(localMessage);
    }

    public void writePreference(String paramString1, String paramString2) {
        SharedPreferences.Editor localEditor = getSharedPreferences("COMMON", 0).edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }

    public void writePreference(String paramString1, String paramString2, String paramString3) {
        SharedPreferences.Editor localEditor = getSharedPreferences(paramString1, 0).edit();
        localEditor.putString(paramString2, paramString3);
        localEditor.commit();
    }
}

/*
 * Location: /home/xshengh/trash/classes_dex2jar.jar Qualified Name:
 * com.miaotu.app.App JD-Core Version: 0.6.2
 */
