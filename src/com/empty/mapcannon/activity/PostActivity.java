package com.empty.mapcannon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.empty.mapcannon.R;
import com.empty.mapcannon.db.PostDBHandler;
import com.empty.mapcannon.model.PostInfo;
import com.empty.mapcannon.util.Util;

/**
 * Created by Nick on 16/2/28.
 */
public class PostActivity extends Activity implements View.OnClickListener {
    private View mBack;
    private TextView mPost;
    private TextView mTitleView;

    private EditText mStartView;
    private EditText mEndView;
    private EditText mStartTimeView;
    private EditText mContentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_together_new_step1);
        mBack = findViewById(R.id.tv_left);
        mPost = (TextView) findViewById(R.id.tv_right);
        mTitleView = (TextView) findViewById(R.id.tv_title);
        mStartView = (EditText) findViewById(R.id.et_start_city);
        mEndView = (EditText) findViewById(R.id.et_des_city);
        mStartTimeView = (EditText) findViewById(R.id.tv_round_date);
        mContentView = (EditText) findViewById(R.id.et_remark);

        mBack.setOnClickListener(this);
        mPost.setOnClickListener(this);
        mPost.setText("发布");
        mTitleView.setText("约起来");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                if (postSuccess()) {
                    finish();
                    Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean postSuccess() {
        String start = mStartView.getText().toString();
        String end = mEndView.getText().toString();
        String startTime = mStartTimeView.getText().toString();
        String content = mContentView.getText().toString();
        String nickNmae = Util.getNickName(this);

        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(content)) {
            PostInfo postInfo = new PostInfo();
            postInfo.setContent(content);
            postInfo.setDeparture(start);
            postInfo.setDeparttime(startTime);
            postInfo.setDestination(end);
            postInfo.setPosttime(String.valueOf(System.currentTimeMillis()));
            postInfo.setNickname(nickNmae);
            return PostDBHandler.getInstance().post(postInfo);
        }

        return false;
    }
}
