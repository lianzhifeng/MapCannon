
package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empty.mapcannon.R;
import com.empty.mapcannon.db.UserInfoDBHandler.Key;

/**
 * Created by Nick on 16/2/27.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MineFragment extends Fragment implements OnClickListener {
    private View root;
    private RelativeLayout rl_userinfo;
    private Button btn_exit;
    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_title;
    private TextView tv_nickname;
    private TextView tv_city;
    private BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_fourth_page, null, false);
        activity = (BaseActivity) getActivity();
        findView();
        bindView();
        return root;
    }

    private void bindView() {
        rl_userinfo.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        tv_left.setVisibility(View.GONE);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("我的");
    }

    private void findView() {
        rl_userinfo = (RelativeLayout) root.findViewById(R.id.rl_userinfo);
        btn_exit = (Button) root.findViewById(R.id.btn_exit);
        tv_left = (TextView) root.findViewById(R.id.tv_left);
        tv_right = (TextView) root.findViewById(R.id.tv_right);
        tv_title = (TextView) root.findViewById(R.id.tv_title);
        tv_nickname = (TextView) root.findViewById(R.id.tv_nickname);
        tv_city = (TextView) root.findViewById(R.id.tv_city);
    }

    @Override
    public void onResume() {
        super.onResume();
        String nickname = activity.readPreference(BaseActivity.NAME_USERINFO, Key.NICKNAME);
        String city = activity.readPreference(BaseActivity.NAME_USERINFO, Key.CITY);
        if (!TextUtils.isEmpty(nickname)) {
            tv_nickname.setText(nickname);
        }
        if (!TextUtils.isEmpty(city)) {
            tv_city.setText(city);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                logout();
                break;
            case R.id.rl_userinfo:
                editUserInfo();
                break;
        }
    }

    private void logout() {
        activity.deleteAllPreference(BaseActivity.NAME_USERINFO);
        activity.writePreference("login", "false");
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void editUserInfo() {
        Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
        startActivity(intent);
    }

}
