
package com.empty.mapcannon.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class DateArrayAdapter extends ArrayWheelAdapter<String> {
    int currentItem;
    int currentValue;

    public DateArrayAdapter(Context paramContext, String[] paramArrayOfString, int paramInt) {
        super(paramContext, paramArrayOfString);
        this.currentValue = paramInt;
        setTextSize(16);
    }

    protected void configureTextView(TextView paramTextView) {
        super.configureTextView(paramTextView);
        if (this.currentItem == this.currentValue)
            paramTextView.setTextColor(-16776976);
        paramTextView.setTypeface(Typeface.SANS_SERIF);
    }

    public View getItem(int paramInt, View paramView, ViewGroup paramViewGroup) {
        this.currentItem = paramInt;
        return super.getItem(paramInt, paramView, paramViewGroup);
    }
}

/*
 * Location: /home/xshengh/trash/classes_dex2jar.jar Qualified Name:
 * com.miaotu.adapter.DateArrayAdapter JD-Core Version: 0.6.2
 */
