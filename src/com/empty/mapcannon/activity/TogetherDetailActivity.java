package com.empty.mapcannon.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.empty.mapcannon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 16/2/28.
 */
public class TogetherDetailActivity extends Activity implements View.OnClickListener {
    private View mBtnComment;
    private View mBtnTogether;
    private View mBottemComment;
    private View mBottemChat;
    private View mBottemTogether;
    private View mBack;
    private boolean mIsBtnTogether;
    private ListView mListView;
    private List<Object> mCommentList;
    private List<Object> mTogetherList;
    private MyAdapter mAdapter;
    private boolean isJoined = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_detail);
        mBtnComment = findViewById(R.id.layout_comment_count);
        mBtnTogether = findViewById(R.id.layout_joined);
        mBottemComment = findViewById(R.id.layout_comment);
        mBottemChat = findViewById(R.id.layout_chat);
        mBottemTogether = findViewById(R.id.layout_join);
        mBack = findViewById(R.id.tv_left);

        mCommentList = new ArrayList<Object>();
        mTogetherList = new ArrayList<Object>();
        mCommentList.add(null);
        mCommentList.add(null);
        mCommentList.add(null);
        mTogetherList.add(null);
        mTogetherList.add(null);

        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(this);
        mListView.setAdapter(mAdapter);
        mBtnComment.setOnClickListener(this);
        mBtnTogether.setOnClickListener(this);
        mBottemComment.setOnClickListener(this);
        mBottemChat.setOnClickListener(this);
        mBottemTogether.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mIsBtnTogether = false;
    }

    private void resetBtnColor() {
        mBtnComment.setBackgroundColor(0xffe6e6e6);
        mBtnTogether.setBackgroundColor(0xffe6e6e6);
    }

    class MyAdapter extends BaseAdapter {
        Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mIsBtnTogether ? mTogetherList.size() : mCommentList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext, R.layout.item_topic_comment, null);
            if (mIsBtnTogether) {
                view.findViewById(R.id.tv_content).setVisibility(View.GONE);
            }
            return view;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_comment_count:
                resetBtnColor();
                mBtnComment.setBackgroundColor(0xffffffff);
                mIsBtnTogether = false;
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.layout_joined:
                resetBtnColor();
                mBtnTogether.setBackgroundColor(0xffffffff);
                mIsBtnTogether = true;
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_left:
                finish();
                break;
            case R.id.layout_join:
                joinOrLeave();
                break;
            case R.id.layout_chat:
                startActivity(new Intent(this, ChatActivity.class));
                break;
            case R.id.layout_comment:
                startActivity(new Intent(this, CommentActivity.class));
                break;
        }
    }

    private void joinOrLeave() {
        TextView textView = (TextView) mBottemTogether.findViewById(R.id.tv_join);
        if (isJoined) {
            isJoined = false;
            textView.setText("入伙");
            Toast.makeText(TogetherDetailActivity.this, "已经取消入伙", Toast.LENGTH_SHORT).show();
        } else {
            isJoined = true;
            Toast.makeText(TogetherDetailActivity.this, "成功入伙", Toast.LENGTH_SHORT).show();
            textView.setText("取消入伙");
        }
    }
}
