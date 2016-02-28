package com.empty.mapcannon.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class LoadingDlg extends Dialog
{
  Context context;

  public LoadingDlg(Context paramContext)
  {
    this(paramContext, 2131296582);
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

/* Location:           /home/xshengh/trash/classes_dex2jar.jar
 * Qualified Name:     com.miaotu.view.LoadingDlg
 * JD-Core Version:    0.6.2
 */