package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.empty.mapcannon.R;
import com.empty.mapcannon.db.CommentDBHandler;
import com.empty.mapcannon.db.PostDBHandler;
import com.empty.mapcannon.model.CommentInfo;
import com.empty.mapcannon.model.PostInfo;
import com.empty.mapcannon.util.Util;

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
    private TextView mCommentCount;
    private boolean mIsBtnTogether;
    private ListView mListView;
    private List<CommentInfo> mCommentList;
    private List<CommentInfo> mTogetherList;
    private MyAdapter mAdapter;
    private int mPostId;

    private void setContent() {
        PostInfo postInfo = PostDBHandler.getInstance().getPostInfo(CommentDBHandler.Key._ID + "=" + mPostId).get(0);
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        TextView tvDestination = (TextView) findViewById(R.id.tv_way);
        TextView tvDeparture = (TextView) findViewById(R.id.tv_start);
        TextView tvDeparttime = (TextView) findViewById(R.id.tv_start_time);
        TextView tvContent = (TextView) findViewById(R.id.tv_introduce);
        TextView tvPostTime = (TextView) findViewById(R.id.tv_date);
        tvName.setText(postInfo.getNickname());
        tvDestination.setText("目的地 : " + postInfo.getDestination());
        tvDeparture.setText("出发地  : " + postInfo.getDeparture());
        tvDeparttime.setText("出发时间: " + postInfo.getDeparttime());
        tvContent.setText(postInfo.getContent());
        showIsJoin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataList();
        mCommentCount.setText(mCommentList.size() + "");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_detail);
        mBtnComment = findViewById(R.id.layout_comment_count);
        mBtnTogether = findViewById(R.id.layout_joined);
        mBottemComment = findViewById(R.id.layout_comment);
        mBottemChat = findViewById(R.id.layout_chat);
        mBottemTogether = findViewById(R.id.layout_join);
        mCommentCount = (TextView) findViewById(R.id.tv_com_count);
        mBack = findViewById(R.id.tv_left);
        mPostId = getIntent().getIntExtra("POSTID", 0);

        updateDataList();
        mCommentCount.setText(mCommentList.size() + "");

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
        setContent();
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
            convertView = View.inflate(mContext, R.layout.item_topic_comment, null);
            updateView(convertView, position);
            return convertView;
        }

        void updateView(View root, int position) {
            TextView tvName = (TextView) root.findViewById(R.id.tv_nickname);
            TextView tvDate = (TextView) root.findViewById(R.id.tv_date);
            TextView tvContent = (TextView) root.findViewById(R.id.tv_content);
            CommentInfo commentInfo = mIsBtnTogether ? mTogetherList.get(position) : mCommentList.get(position);
            tvName.setText(commentInfo.getCommentName());
            tvDate.setText(commentInfo.getTime());
            tvContent.setText(commentInfo.getContent());
            tvContent.setVisibility(mIsBtnTogether ? View.GONE : View.VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_comment_count:
                updateDataList();
                resetBtnColor();
                mCommentCount.setText(mCommentList.size() + "");
                mBtnComment.setBackgroundColor(0xffffffff);
                mIsBtnTogether = false;
                mAdapter.notifyDataSetInvalidated();
                break;
            case R.id.layout_joined:
                updateDataList();
                resetBtnColor();
                mCommentCount.setText(mCommentList.size() + "");
                mBtnTogether.setBackgroundColor(0xffffffff);
                mIsBtnTogether = true;
                mAdapter.notifyDataSetInvalidated();
                break;
            case R.id.tv_left:
                finish();
                break;
            case R.id.layout_join:
                if (getNickName().isEmpty()) {
                    Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
                } else {
                    joinOrLeave();
                    showIsJoin();
                }
                break;
            case R.id.layout_chat:
                Intent intent_chat = new Intent(this, ChatActivity.class);
                intent_chat.putExtra("POSTID", mPostId);
                startActivity(intent_chat);
                break;
            case R.id.layout_comment:
                Intent intent_comment = new Intent(this, CommentActivity.class);
                intent_comment.putExtra("POSTID", mPostId);
                startActivity(intent_comment);
                break;
        }
    }

    private String selectIsJoinString() {
        return CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_JOINED +
                "' AND " + CommentDBHandler.Key.COMMENTNAME + "='" + getNickName() + "'" +
                " AND " + CommentDBHandler.Key.POSTID + "=" + mPostId;
    }

    private void updateDataList() {
        mCommentList = CommentDBHandler.getInstance().getCommentInfo(CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_COMMENT + "' AND " + CommentDBHandler.Key.POSTID + "=" + mPostId);
        mTogetherList = CommentDBHandler.getInstance().getCommentInfo(CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_JOINED + "' AND " + CommentDBHandler.Key.POSTID + "=" + mPostId);
    }

    private boolean isJoined() {
        return CommentDBHandler.getInstance().getCommentInfo(selectIsJoinString()).size() > 0;
    }

    private String getNickName() {
        return Util.getNickName(this);
    }

    private void joinOrLeave() {
        if (isJoined()) {
            CommentDBHandler.getInstance().deleteComment(selectIsJoinString());
            Toast.makeText(TogetherDetailActivity.this, "已经取消入伙", Toast.LENGTH_SHORT).show();
        } else {
            CommentInfo commentInfo = new CommentInfo();
            commentInfo.setContent("");
            commentInfo.setType(CommentInfo.TYPE_JOINED);
            commentInfo.setCommentName(getNickName());
            commentInfo.setPostId(mPostId);
            commentInfo.setTime(getCurrentTime());
            CommentDBHandler.getInstance().comment(commentInfo);
            Toast.makeText(TogetherDetailActivity.this, "成功入伙", Toast.LENGTH_SHORT).show();
        }
        updateDataList();
        mCommentCount.setText(mCommentList.size() + "");
        mAdapter.notifyDataSetInvalidated();
    }

    private String getCurrentTime() {
        return System.currentTimeMillis() + "";
    }

    private void showIsJoin() {
        TextView textView = (TextView) mBottemTogether.findViewById(R.id.tv_join);
        if (isJoined()) {
            textView.setText("取消入伙");
        } else {
            textView.setText("入伙");
        }
    }
}
