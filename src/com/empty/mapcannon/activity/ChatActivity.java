package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import com.empty.mapcannon.R;

/**
 * Created by Nick on 16/2/28.
 */
public class ChatActivity extends Activity {
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_comment_list);
    }
}
