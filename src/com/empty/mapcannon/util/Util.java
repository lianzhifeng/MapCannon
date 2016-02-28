package com.empty.mapcannon.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Util
{
  private static final int CHECK_INTERVAL = 30000;
  public static final int NETTYPE_CMNET = 3;
  public static final int NETTYPE_CMWAP = 2;
  public static final int NETTYPE_WIFI = 1;

  public static String MD5(String paramString)
  {
    MessageDigest localMessageDigest;
    byte[] arrayOfByte1;
    try
    {
      localMessageDigest = MessageDigest.getInstance("MD5");
      char[] arrayOfChar = paramString.toCharArray();
      arrayOfByte1 = new byte[arrayOfChar.length];
      for (int i = 0; i < arrayOfChar.length; i++)
        arrayOfByte1[i] = ((byte)arrayOfChar[i]);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return "";
    }
    byte[] arrayOfByte2 = localMessageDigest.digest(arrayOfByte1);
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < arrayOfByte2.length; j++)
    {
      int k = 0xFF & arrayOfByte2[j];
      if (k < 16)
        localStringBuffer.append("0");
      localStringBuffer.append(Integer.toHexString(k));
    }
    return localStringBuffer.toString();
  }

  public static int dip2px(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }

  @SuppressLint({"SimpleDateFormat"})
  public static String formatDate(Date paramDate, String paramString)
  {
    if (paramDate == null)
      return "";
    return new SimpleDateFormat(paramString).format(paramDate);
  }

  public static int getAppVersionCode(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo("cn.thumbworld.leihaowu", 0).versionCode;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public static String getCurrentTime()
  {
    String str = new SimpleDateFormat("MM-dd HH:mm").format(new Date());
    System.out.println("CurrentTime:" + str);
    return str;
  }

  public static String getExtName(File paramFile)
  {
    String str = paramFile.getName();
    return str.substring(1 + str.lastIndexOf("."));
  }

  public static String getImei(Context paramContext)
  {
    return ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
  }

  public static long getLastCallDuration(Activity paramActivity, String paramString)
  {
    Log.d("Debug", CallLog.Calls.CONTENT_URI.toString());
    Cursor localCursor = paramActivity.getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[] { "duration", "type", "date" }, "number=?", new String[] { "10086" }, "date DESC");
    paramActivity.startManagingCursor(localCursor);
    boolean bool = localCursor.moveToLast();
    long l1 = 0L;
    if (bool)
    {
      int i = localCursor.getInt(localCursor.getColumnIndex("type"));
      long l2 = localCursor.getLong(localCursor.getColumnIndex("duration"));
      switch (i)
      {
      default:
      case 2:
      }
      while (true)
      {
        bool = localCursor.moveToPrevious();
        break;
      }
    }
    return l1;
  }

  public static String getNameByTime()
  {
    String str = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    System.out.println("CurrentTime:" + str);
    return str;
  }

  public static String getNumber(Context paramContext)
  {
    String str = ((TelephonyManager)paramContext.getSystemService("phone")).getLine1Number();
    if (str == null)
      str = "";
    return str;
  }

  public static Drawable getPic(Context paramContext, String paramString)
  {
    String str = paramString.substring(1 + paramString.lastIndexOf("/"));
    return (BitmapDrawable)Drawable.createFromPath(new File(paramContext.getCacheDir(), str).toString());
  }

  public static List<Camera.Size> getResolutionList(Camera paramCamera)
  {
    return paramCamera.getParameters().getSupportedPreviewSizes();
  }

  public static String getWholeTime()
  {
    String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    System.out.println("CurrentTime:" + str);
    return str;
  }

  public static boolean hasFroyo()
  {
    return Build.VERSION.SDK_INT >= 8;
  }

  public static boolean hasGingerbread()
  {
    return Build.VERSION.SDK_INT >= 9;
  }

  public static boolean hasHoneycomb()
  {
    return Build.VERSION.SDK_INT >= 11;
  }

  public static boolean hasHoneycombMR1()
  {
    return Build.VERSION.SDK_INT >= 12;
  }

  public static boolean hasJellyBean()
  {
    return Build.VERSION.SDK_INT >= 16;
  }

  public static boolean hasKitKat()
  {
    return Build.VERSION.SDK_INT >= 19;
  }

  public static boolean isFlashVideo(String paramString)
  {
    if (paramString == null);
    while (!paramString.trim().startsWith("<embed"))
      return false;
    return true;
  }

  public static boolean isHtml5Video(String paramString)
  {
    if (paramString == null);
    while (!paramString.trim().startsWith("<div"))
      return false;
    return true;
  }

  public static boolean isInLimitTime()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    String str = localSimpleDateFormat.format(new Date());
    try
    {
      Date localDate1 = localSimpleDateFormat.parse(str);
      Date localDate2 = localSimpleDateFormat.parse("23:00:00");
      if (localDate1.after(localSimpleDateFormat.parse("09:00:00")))
      {
        boolean bool = localDate1.before(localDate2);
        if (bool)
          return false;
      }
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return true;
  }

  public static boolean isNetworkConnected(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        boolean bool = localNetworkInfo.isAvailable();
        return bool;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean isPicExists(Context paramContext, String paramString)
  {
    String str = paramString.substring(1 + paramString.lastIndexOf("/"));
    File localFile = new File(paramContext.getCacheDir(), str);
    return (localFile.exists()) || (localFile.isDirectory());
  }

  private static boolean isSameProvider(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return paramString2 == null;
    return paramString1.equals(paramString2);
  }

  public static boolean isUrl(String paramString)
  {
    if (paramString == null);
    while (!paramString.trim().startsWith("http://"))
      return false;
    return true;
  }

  public static String keepNDouble(double paramDouble, int paramInt)
  {
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
    localNumberFormat.setMaximumFractionDigits(paramInt);
    return localNumberFormat.format(paramDouble);
  }

  public static int px2dip(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
  }

  public static void saveBitmap(String paramString, Bitmap paramBitmap)
  {
    File localFile = new File(paramString);
    try
    {
      localFile.createNewFile();
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
      localFileOutputStream.flush();
      localFileOutputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void setListViewHeightBasedOnChildren(ListView paramListView)
  {
    ListAdapter localListAdapter = paramListView.getAdapter();
    if (localListAdapter == null)
      return;
    int i = 0;
    for (int j = 0; j < localListAdapter.getCount(); j++)
    {
      View localView = localListAdapter.getView(j, null, paramListView);
      localView.measure(0, 0);
      i += localView.getMeasuredHeight();
    }
    ViewGroup.LayoutParams localLayoutParams = paramListView.getLayoutParams();
    localLayoutParams.height = (i + paramListView.getDividerHeight() * (-1 + localListAdapter.getCount()));
    paramListView.setLayoutParams(localLayoutParams);
  }

  public boolean delAllFile(String paramString)
  {
    boolean bool = false;
    File localFile1 = new File(paramString);
    if (!localFile1.exists())
      return false;
    if (!localFile1.isDirectory())
      return false;
    String[] arrayOfString = localFile1.list();
    int i = 0;
    if (i < arrayOfString.length)
    {
      if (paramString.endsWith(File.separator));
      for (File localFile2 = new File(paramString + arrayOfString[i]); ; localFile2 = new File(paramString + File.separator + arrayOfString[i]))
      {
        if (localFile2.isFile())
          localFile2.delete();
        if (localFile2.isDirectory())
        {
          delAllFile(paramString + "/" + arrayOfString[i]);
          bool = true;
        }
        i++;
        break;
      }
    }
    return bool;
  }

  public String encryptmd5(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++)
      arrayOfChar[i] = ((char)(0x6C ^ arrayOfChar[i]));
    return new String(arrayOfChar);
  }

  public long getExceed(Date paramDate1, Date paramDate2)
  {
    try
    {
      long l = (paramDate1.getTime() - paramDate2.getTime()) / 3600000L;
      return l;
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  public String getMyUUID()
  {
    return UUID.randomUUID().toString();
  }

  public int isDateBefore(String paramString1, String paramString2)
  {
    return paramString1.compareTo(paramString2);
  }

  public static class ResolutionComparator
    implements Comparator<Camera.Size>
  {
    public int compare(Camera.Size paramSize1, Camera.Size paramSize2)
    {
      if (paramSize1.height != paramSize2.height)
        return paramSize1.height - paramSize2.height;
      return paramSize1.width - paramSize2.width;
    }
  }
}

/* Location:           /home/xshengh/trash/classes_dex2jar.jar
 * Qualified Name:     com.miaotu.util.Util
 * JD-Core Version:    0.6.2
 */