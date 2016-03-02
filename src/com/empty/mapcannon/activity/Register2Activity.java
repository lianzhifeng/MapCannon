
package com.empty.mapcannon.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.empty.mapcannon.Constants;
import com.empty.mapcannon.R;
import com.empty.mapcannon.async.BaseHttpAsyncTask;
import com.empty.mapcannon.db.UserInfoDBHandler;
import com.empty.mapcannon.db.UserInfoDBHandler.Key;
import com.empty.mapcannon.model.RegisterInfo;
import com.empty.mapcannon.util.StringUtil;

public class Register2Activity extends BaseActivity implements OnClickListener {

    private Dialog dialog;
    private EditText etPassword;
    private EditText etPasswordRe;
    private EditText etTel;
    private RegisterInfo registerInfo;
    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        findView();
        bindView();
        init();
    }

    private void init() {
        this.tvTitle.setText("手机号注册");
        this.tvRight.setText("下一步");
        this.registerInfo = new RegisterInfo();
    }

    private void bindView() {
        this.tvLeft.setOnClickListener(this);
        this.tvRight.setOnClickListener(this);
    }

    private void findView() {
        this.tvRight = ((TextView) findViewById(R.id.tv_right));
        this.tvLeft = ((TextView) findViewById(R.id.tv_left));
        this.tvTitle = ((TextView) findViewById(R.id.tv_title));
        this.etTel = ((EditText) findViewById(R.id.et_tel));
        this.etPassword = ((EditText) findViewById(R.id.et_password));
        this.etPasswordRe = ((EditText) findViewById(R.id.et_password_re));
    }

    private boolean validate() {
        if (StringUtil.isEmpty(this.etTel.getText().toString())) {
            showMyToast("请输入手机号！");
            return false;
        }
        if (!StringUtil.isPhoneNumber(this.etTel.getText().toString())) {
            showMyToast("请输入正确的手机号！");
            return false;
        }
        if (StringUtil.isEmpty(this.etPassword.getText().toString())) {
            showMyToast("请输入密码！");
            return false;
        }
        if (this.etPassword.getText().toString().length() < 6) {
            showMyToast("请输入6-8位密码！");
            return false;
        }
        if (StringUtil.isEmpty(this.etPasswordRe.getText().toString())) {
            showMyToast("请输入重复密码！");
            return false;
        }
        if (!this.etPasswordRe.getText().toString().equals(this.etPassword.getText().toString())) {
            showMyToast("两次密码输入不一致，请重新输入！");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                nextStep();
                break;
            case R.id.tv_left:
                finish();
                break;
            default:
                break;
        }
    }

    private void nextStep() {
        if (validate()) {
            new BaseHttpAsyncTask<Void, Void, Boolean>(this) {

                @Override
                protected void onCompleteTask(Boolean paramResult) {
                    if (paramResult) {
                        writePreference(Constants.STR_LOGIN, "true");
                        writePreference(NAME_USERINFO, Key.PHONE, registerInfo.getPhone());
                        Intent intent = new Intent(Register2Activity.this,
                                EditUserInfoActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                protected Boolean run(Void[] paramArrayOfParams) {
                    registerInfo.setPhone(etTel.getText().toString());
                    registerInfo.setPassword(etPassword.getText().toString());
                    return UserInfoDBHandler.getInstance().register(registerInfo);
                }
            }.execute();
        }
    }
}
