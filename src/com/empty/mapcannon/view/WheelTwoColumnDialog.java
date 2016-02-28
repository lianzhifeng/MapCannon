
package com.empty.mapcannon.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.empty.mapcannon.R;

public class WheelTwoColumnDialog extends Dialog {
    Button btnConfirm;

    public WheelTwoColumnDialog(Activity paramActivity, int paramInt, View paramView) {
        super(paramActivity, paramInt);
        setCanceledOnTouchOutside(true);
        this.btnConfirm = ((Button) paramView.findViewById(R.id.btn_confirm));
        setContentView(paramView);
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        localWindow.setGravity(80);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        localLayoutParams.width = i;
        localLayoutParams.height = ((int) (0.3D * j));
        localWindow.setAttributes(localLayoutParams);
        localWindow.setWindowAnimations(R.style.dialog_style);
        setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface paramAnonymousDialogInterface) {
            }
        });
        show();
    }

    public void setOnConfirmListener(View.OnClickListener paramOnClickListener) {
        this.btnConfirm.setOnClickListener(paramOnClickListener);
    }
}
