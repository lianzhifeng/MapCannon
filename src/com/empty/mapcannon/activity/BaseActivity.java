package com.empty.mapcannon.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empty.mapcannon.R;
import com.empty.mapcannon.util.Util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends Activity
{
  public static final String NAME_COMMON = "COMMON";

  public static String getCurrentTime()
  {
    String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    System.out.println("CurrentTime:" + str);
    return str;
  }

  public static String getNameByTime()
  {
    String str = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    System.out.println("CurrentTime:" + str);
    return str;
  }

  public void changeBackground(float paramFloat)
  {
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.alpha = paramFloat;
    getWindow().setAttributes(localLayoutParams);
  }

  public void deleteAllPreference(String paramString)
  {
    SharedPreferences.Editor localEditor = getSharedPreferences(paramString, 0).edit();
    localEditor.clear();
    localEditor.commit();
  }

  public void deletePreference(String paramString)
  {
    SharedPreferences.Editor localEditor = getSharedPreferences("COMMON", 0).edit();
    localEditor.remove(paramString);
    localEditor.commit();
  }

  public void deletePreference(String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = getSharedPreferences(paramString1, 0).edit();
    localEditor.remove(paramString2);
    localEditor.commit();
  }

  public void onPause()
  {
    super.onPause();
  }

  protected void onResume()
  {
    super.onResume();
  }

  public String readPreference(String paramString)
  {
    return getSharedPreferences("COMMON", 0).getString(paramString, "");
  }

  public String readPreference(String paramString1, String paramString2)
  {
    return getSharedPreferences(paramString1, 0).getString(paramString2, "");
  }

  public void showMyToast(int paramInt, String paramString)
  {
    View localView = LayoutInflater.from(this).inflate(2130968854, null);
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(localDisplayMetrics.widthPixels, -1);
    TextView localTextView = (TextView)localView.findViewById(2131624487);
    localTextView.setLayoutParams(localLayoutParams);
    localTextView.setText(paramString);
    Toast localToast = new Toast(this);
    localToast.setGravity(48, 0, Util.dip2px(this, paramInt));
    localTextView.setAlpha(0.8F);
    localToast.setDuration(0);
    localToast.setView(localView);
    localToast.show();
  }

  public void showMyToast(String paramString)
  {
    View localView = LayoutInflater.from(this).inflate(R.layout.toast_like, null);
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(localDisplayMetrics.widthPixels, -1);
    TextView localTextView = (TextView)localView.findViewById(R.id.tv_content);
    localTextView.setLayoutParams(localLayoutParams);
    localTextView.setText(paramString);
    Toast localToast = new Toast(this);
    localToast.setGravity(48, 0, Util.dip2px(this, 44.0F));
    localTextView.setAlpha(0.8F);
    localToast.setDuration(0);
    localToast.setView(localView);
    localToast.show();
  }

  public void showToastMsg(String paramString)
  {
    showToastMsg(paramString, 0);
  }

  protected void showToastMsg(String paramString, int paramInt)
  {
    Toast.makeText(this, paramString, paramInt).show();
  }

  public void writePreference(String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = getSharedPreferences("COMMON", 0).edit();
    localEditor.putString(paramString1, paramString2);
    localEditor.commit();
  }

  public void writePreference(String paramString1, String paramString2, String paramString3)
  {
    SharedPreferences.Editor localEditor = getSharedPreferences(paramString1, 0).edit();
    localEditor.putString(paramString2, paramString3);
    localEditor.commit();
  }
}