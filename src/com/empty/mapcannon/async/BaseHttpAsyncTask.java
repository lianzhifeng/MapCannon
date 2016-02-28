
package com.empty.mapcannon.async;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empty.mapcannon.R;
import com.empty.mapcannon.util.Util;
import com.empty.mapcannon.view.LoadingDlg;

@SuppressLint({
        "NewApi"
})
public abstract class BaseHttpAsyncTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {
    protected Activity activity = null;
    protected Dialog mLoadingDlg;
    private boolean onPostExcuted = false;
    private boolean onPreExcuted = false;
    private boolean showDlg = true;
    private boolean showTip = true;
    private boolean success;
    protected Throwable throwable;

    public BaseHttpAsyncTask(Activity paramActivity) {
        this.activity = paramActivity;
    }

    public BaseHttpAsyncTask(Activity paramActivity, boolean paramBoolean) {
        this.activity = paramActivity;
        this.showDlg = paramBoolean;
    }

    public BaseHttpAsyncTask(Activity paramActivity, boolean paramBoolean1, boolean paramBoolean2) {
        this.activity = paramActivity;
        this.showDlg = paramBoolean1;
        this.showTip = paramBoolean2;
    }

    private void dismissDlg() {
        if ((this.activity != null) && (!this.activity.isFinishing()) && (this.mLoadingDlg != null)
                && (this.mLoadingDlg.isShowing()))
            this.mLoadingDlg.dismiss();
    }

    protected final Result doInBackground(Params[] paramArrayOfParams) {
        try {
            Object localObject = run(paramArrayOfParams);
            return (Result) localObject;
        } catch (Exception localException) {
            localException.printStackTrace();
            this.throwable = localException;
        }
        return null;
    }

    protected void finallyRun() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    protected void onCancelled() {
        super.onCancelled();
        dismissDlg();
        finallyRun();
    }

    protected abstract void onCompleteTask(Result paramResult);

    protected void onConnectFailed() {
    }

    protected final void onPostExecute(Result paramResult) {
        this.onPostExcuted = true;
        dismissDlg();
        if ((this.throwable != null) || (paramResult == null)) {
            if (this.showTip) {
                showToast("服务器响应超时");
            }
        } else {
            onCompleteTask(paramResult);
            this.success = true;
        }
        finallyRun();
    }

    protected final void onPreExecute() {
        this.success = false;
        this.onPreExcuted = true;
        super.onPreExecute();
        if (!Util.isNetworkConnected(this.activity)) {
            showToast("没有网络连接");
            onConnectFailed();
            cancel(true);
            return;
        }
        if (this.showDlg)
            this.mLoadingDlg = LoadingDlg.show(this.activity);
        preRun();
    }

    protected void preRun() {
    }

    protected abstract Result run(Params[] paramArrayOfParams);

    public void showToast(String paramString) {
        View localView = LayoutInflater.from(this.activity).inflate(R.layout.toast_like, null);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
                localDisplayMetrics.widthPixels, -1);
        TextView localTextView = (TextView) localView.findViewById(R.id.tv_content);
        localTextView.setLayoutParams(localLayoutParams);
        localTextView.setText(paramString);
        Toast localToast = new Toast(this.activity);
        localToast.setGravity(48, 0, Util.dip2px(this.activity, 44.0F));
        localTextView.setAlpha(0.8F);
        localToast.setDuration(0);
        localToast.setView(localView);
        localToast.show();
    }
}
