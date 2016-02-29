package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.empty.mapcannon.R;
import com.empty.mapcannon.db.CommentDBHandler;
import com.empty.mapcannon.model.CommentInfo;

import java.util.List;

/**
 * Created by Nick on 16/2/28.
 */
public class CommentActivity extends Activity implements View.OnClickListener{
    private MyAdapter mAdapter;
    private List<CommentInfo> mList;
    private EditText mEditView;
    private TextView mTitleView;
    private int mPostId;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_comment_list);
        mTitleView = (TextView) findViewById(R.id.tv_title);
        mTitleView.setText("评论");
        ListView listView = (ListView) findViewById(R.id.rv_comment);
        mEditView = (EditText) findViewById(R.id.et_comment);
        findViewById(R.id.tv_left).setOnClickListener(this);
        mPostId = getIntent().getIntExtra("POSTID", 0);
        mList = CommentDBHandler.getInstance().getCommentInfo(CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_COMMENT + "'");;

        findViewById(R.id.iv_publish).setOnClickListener(this);
        mAdapter = new MyAdapter(this);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_publish:
                String comment = mEditView.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    Toast.makeText(this, "评论不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    postComment(comment);
                    Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_left:
                finish();
                break;
        }
    }

    private String getCurrentTime() {
        return System.currentTimeMillis() + "";
    }

    private String getNickName() {
        return "Nick";
    }

    private void postComment(String comment) {
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setTime(getCurrentTime());
        commentInfo.setCommentName(getNickName());
        commentInfo.setContent(comment);
        commentInfo.setType(CommentInfo.TYPE_COMMENT);
        commentInfo.setPostId(mPostId);
        CommentDBHandler.getInstance().comment(commentInfo);
        mList = CommentDBHandler.getInstance().getCommentInfo(CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_COMMENT + "'");
        mEditView.setText("");
        mEditView.clearFocus();
        mAdapter.notifyDataSetChanged();
    }

    class MyAdapter extends BaseAdapter {
        Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
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
            Holder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_topic_comment, null);
                holder = new Holder();
                holder.position = position;
                holder.commentInfo = mList.get(position);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.updateView(convertView);
            return convertView;
        }

        class Holder {
            int position;
            CommentInfo commentInfo;
            void updateView(View root) {
                TextView tvName = (TextView) root.findViewById(R.id.tv_nickname);
                TextView tvDate = (TextView) root.findViewById(R.id.tv_date);
                TextView tvContent = (TextView) root.findViewById(R.id.tv_content);
                tvName.setText(commentInfo.getCommentName());
                tvDate.setText(commentInfo.getTime());
                tvContent.setText(commentInfo.getContent());
            }
        }
    }
}
