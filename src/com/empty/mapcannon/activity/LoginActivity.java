
package com.empty.mapcannon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.empty.mapcannon.Constants;
import com.empty.mapcannon.R;
import com.empty.mapcannon.async.BaseHttpAsyncTask;
import com.empty.mapcannon.db.UserInfoDBHandler;
import com.empty.mapcannon.db.UserInfoDBHandler.Key;
import com.empty.mapcannon.model.RegisterInfo;
import com.empty.mapcannon.util.StringUtil;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private Button btnLogin;
    private EditText tvAuto;
    private EditText etPassword;
    private TextView tvRegister;
    private TextView tvFindPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        bindView();
    }

    private void bindView() {
        this.btnLogin.setOnClickListener(this);
        this.tvRegister.setOnClickListener(this);
        this.tvFindPassword.setOnClickListener(this);
    }

    private void findView() {
        this.btnLogin = (Button) findViewById(R.id.btn_login);
        this.tvAuto = ((EditText) findViewById(R.id.tv_auto));
        this.tvRegister = ((TextView) findViewById(R.id.tv_register));
        this.etPassword = ((EditText) findViewById(R.id.et_password));
        this.tvFindPassword = ((TextView) findViewById(R.id.tv_find_password));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                register();
                break;
            case R.id.tv_find_password:
                findPassword();
                break;
        }
    }

    private void findPassword() {
        startActivity(new Intent(this, FindPasswordActivity.class));
    }

    private void register() {
        startActivity(new Intent(this, Register2Activity.class));
    }

    private boolean validate() {
        if (StringUtil.isBlank(tvAuto.getText().toString())) {
            showMyToast("请输入您的手机号");
            return false;
        }
        if (!StringUtil.isPhoneNumber(this.tvAuto.getText().toString())) {
            showMyToast("账号填写有误，请检查后重新填写");
            return false;
        }
        if (StringUtil.isBlank(this.etPassword.getText().toString())) {
            showMyToast("请输入您的登录密码！");
            return false;
        }
        return true;
    }

    private void login() {
        if (validate()) {
            final RegisterInfo info = new RegisterInfo();
            info.setPhone(this.tvAuto.getText().toString());
            info.setPassword(this.etPassword.getText().toString());
            new BaseHttpAsyncTask<Object, Object, Object>(this, false) {

                @Override
                protected void onCompleteTask(Object paramResult) {
                    if (paramResult != null) {
                        RegisterInfo info = (RegisterInfo) paramResult;
                        deleteAllPreference(NAME_USERINFO);
                        writePreference(Constants.STR_LOGIN, "true");
                        writePreference(NAME_USERINFO, Key.GENDER, info.getGender());
                        writePreference(NAME_USERINFO, Key.PHONE, info.getPhone());
                        writePreference(NAME_USERINFO, Key.PROVINCE, info.getProvince());
                        writePreference(NAME_USERINFO, Key.CITY, info.getCity());
                        writePreference(NAME_USERINFO, Key.NICKNAME, info.getNickname());
                        showMyToast("登录成功");
                        finish();
                    } else {
                        showMyToast("账号填写有误,请检查后重新填写");
                    }
                }

                @Override
                protected Object run(Object[] paramArrayOfParams) {
                    return UserInfoDBHandler.getInstance().login(info);
                }
            }.execute();
        }
    }
}
