
package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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

/**
 * Created by Nick on 16/2/27.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MineFragment extends Fragment implements OnClickListener {
    private Button btnLogin;
    private AutoCompleteTextView tvAuto;
    private EditText etPassword;
    private TextView tvRegister;
    private TextView tvFindPassword;
    private View root;
    private BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_login, null, false);
        activity = (BaseActivity) getActivity();
        findView(root);
        bindView();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void bindView() {
        this.btnLogin.setOnClickListener(this);
        this.tvRegister.setOnClickListener(this);
        this.tvFindPassword.setOnClickListener(this);
    }

    private void findView(View root) {
        this.btnLogin = (Button) root.findViewById(R.id.btn_login);
        this.tvAuto = ((AutoCompleteTextView) root.findViewById(R.id.et_tel));
        this.tvRegister = ((TextView) root.findViewById(R.id.tv_register));
        this.etPassword = ((EditText) root.findViewById(R.id.et_password));
        this.tvFindPassword = ((TextView) root.findViewById(R.id.tv_find_password));
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
        }
    }

    private void findPassword() {
        startActivity(new Intent(getActivity(), FindPasswordActivity.class));
    }

    private void register() {
        startActivity(new Intent(getActivity(), Register2Activity.class));
    }

    private boolean validate() {
        if (StringUtil.isBlank(tvAuto.getText().toString())) {
            activity.showMyToast("请输入您的手机号");
            return false;
        }
        if (!StringUtil.isPhoneNumber(this.tvAuto.getText().toString())) {
            activity.showMyToast("账号填写有误，请检查后重新填写");
            return false;
        }
        if (StringUtil.isBlank(this.etPassword.getText().toString())) {
            activity.showMyToast("请输入您的登录密码！");
            return false;
        }
        return true;
    }

    private void login() {
        if (!validate()) {
            final RegisterInfo info = new RegisterInfo();
            info.setPhone(this.tvAuto.getText().toString());
            info.setPassword(this.etPassword.getText().toString());
            new BaseHttpAsyncTask<Object, Object, Object>(getActivity(), false) {

                @Override
                protected void onCompleteTask(Object paramResult) {
                    if (paramResult != null) {
                        RegisterInfo info = (RegisterInfo) paramResult;
                        MineFragment.this.activity.deleteAllPreference("COMMON");
                        MineFragment.this.activity.writePreference(Constants.STR_LOGIN, "true");
                        MineFragment.this.activity.writePreference(Key.GENDER, info.getGender());
                        MineFragment.this.activity.writePreference(Key.PHONE, info.getPhone());
                        MineFragment.this.activity.writePreference(Key.PROVINCE,
                                info.getProvince());
                        MineFragment.this.activity.writePreference(Key.CITY, info.getCity());
                        MineFragment.this.activity.writePreference(Key.NICKNAME,
                                info.getNickname());
                        MineFragment.this.activity.showMyToast("登录成功");
                    } else {
                        MineFragment.this.activity.showMyToast("账号填写有误,请检查后重新填写");
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
