package com.empty.mapcannon.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.empty.mapcannon.R;

public class LoadingDlg extends Dialog
{
  Context context;

  public LoadingDlg(Context paramContext)
  {
    this(paramContext, R.style.loading_dialog);
  }

  public LoadingDlg(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.context = paramContext;
    setCanceledOnTouchOutside(false);
    setCancelable(false);
  }

  public static LoadingDlg show(Context paramContext)
  {
    LoadingDlg localLoadingDlg = new LoadingDlg(paramContext);
    localLoadingDlg.show();
    return localLoadingDlg;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968706);
  }
}