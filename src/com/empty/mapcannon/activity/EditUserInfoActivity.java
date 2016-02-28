
package com.empty.mapcannon.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empty.mapcannon.R;
import com.empty.mapcannon.util.StringUtil;

public class EditUserInfoActivity extends BaseActivity implements OnClickListener {
    private EditText et_nickname;
    private TextView tv_gender;
    private RelativeLayout rl_home;
    private RelativeLayout rl_life;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        findView();
        initData();
    }

    private void initData() {
    }

    private void findView() {
        this.tv_gender = ((TextView) findViewById(R.id.tv_gender));
        this.et_nickname = ((EditText) findViewById(R.id.et_nickname));
        this.rl_home = ((RelativeLayout) findViewById(R.id.rl_home));
        this.rl_life = ((RelativeLayout) findViewById(R.id.rl_life));
    }

    @Override
    public void onClick(View v) {
    }

}
