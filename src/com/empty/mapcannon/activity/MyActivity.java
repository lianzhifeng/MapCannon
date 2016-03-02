
package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.empty.mapcannon.R;

public class MyActivity extends BaseActivity implements View.OnClickListener {

    private DiscoveryFragment mDiscoveryFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private LinearLayout mBtnDiscovery;
    private LinearLayout mBtnMessage;
    private LinearLayout mBtnMine;
    private ImageView mImageDiscovery;
    private ImageView mImageMessage;
    private ImageView mImageMine;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newFragment();
        setDefaultFragment();
        initView();
    }

    private void initView() {
        mBtnDiscovery = (LinearLayout) findViewById(R.id.btn_discovery);
        mBtnMessage = (LinearLayout) findViewById(R.id.btn_message);
        mBtnMine = (LinearLayout) findViewById(R.id.btn_mine);
        mBtnDiscovery.setOnClickListener(this);
        mBtnMessage.setOnClickListener(this);
        mBtnMine.setOnClickListener(this);
        mImageDiscovery = (ImageView) findViewById(R.id.image_discovery);
        mImageMessage = (ImageView) findViewById(R.id.image_message);
        mImageMine = (ImageView) findViewById(R.id.image_mine);
    }

    private void newFragment() {
        mDiscoveryFragment = new DiscoveryFragment();
        mMineFragment = new MineFragment();
        mMessageFragment = new MessageFragment();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void setFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.id_content, fragment);
        transaction.commit();
    }

    private void setDefaultFragment() {
        setFragment(mDiscoveryFragment);
    }

    private void resetImageView() {
        mImageDiscovery.setImageResource(R.drawable.icon_tab_first_unselected);
        mImageMessage.setImageResource(R.drawable.icon_tab_message_unselected);
        mImageMine.setImageResource(R.drawable.icon_tab_mine_unselected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_discovery:
                setFragment(mDiscoveryFragment);
                resetImageView();
                mImageDiscovery.setImageResource(R.drawable.icon_tab_first_selected);
                break;
            case R.id.btn_message:
                setFragment(mMessageFragment);
                resetImageView();
                mImageMessage.setImageResource(R.drawable.icon_tab_message_selected);
                break;
            case R.id.btn_mine:
                String login = readPreference("login");
                if (TextUtils.isEmpty(login) || !login.equals("true")) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    setFragment(mMineFragment);
                    resetImageView();
                    mImageMine.setImageResource(R.drawable.icon_tab_mine_selected);
                }
                break;
            default:
                break;
        }
    }
}
