package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.empty.mapcannon.R;
import com.empty.mapcannon.db.CommentDBHandler;
import com.empty.mapcannon.db.PostDBHandler;
import com.empty.mapcannon.model.CommentInfo;
import com.empty.mapcannon.model.PostInfo;
import com.empty.mapcannon.util.Util;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 16/2/27.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DiscoveryFragment extends Fragment implements View.OnClickListener {
    private ListView list;
    private View root;
    private View mPost;
    private EditText mEditView;
    private MyAdapter mAdapter;
    private List<PostInfo> mContentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_first_page, container, false);
        list = (ListView) root.findViewById(R.id.listview);
        mPost = root.findViewById(R.id.tv_right);
        mEditView = (EditText) root.findViewById(R.id.search_text);
        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.getNickName(getActivity()).isEmpty()) {
                    Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT);
                } else {
                    startActivity(new Intent(getActivity(), PostActivity.class));
                }
            }
        });
        mContentList = PostDBHandler.getInstance().getPostInfo(null);
        mAdapter = new MyAdapter(getActivity());
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TogetherDetailActivity.class);
                intent.putExtra("POSTID", mContentList.get(position).getId());
                startActivity(intent);
            }
        });
        mEditView.clearFocus();
        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mContentList = PostDBHandler.getInstance().getPostInfo(null);
                } else {
                    List<PostInfo> contentList = PostDBHandler.getInstance().getPostInfo(null);
                    mContentList.clear();
                    for (PostInfo info : contentList)
                        if (info.getDestination().contains(s)) {
                            mContentList.add(info);
                        }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mContentList = PostDBHandler.getInstance().getPostInfo(null);
        mAdapter.notifyDataSetChanged();
        mEditView.setText("");
        mEditView.clearFocus();
    }

    public class MyAdapter extends BaseAdapter {
        Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mContentList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = View.inflate(mContext, R.layout.item_together, null);
                holder.tvComent = convertView.findViewById(R.id.ll_comment);
                holder.tvJoin = convertView.findViewById(R.id.ll_join);
                holder.position = position;
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.updateView(convertView, mContentList.get(holder.position));
            holder.tvComent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CommentActivity.class);
                    intent.putExtra("POSTID", mContentList.get(position).getId());
                    startActivity(intent);
                }
            });
            holder.tvJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(Util.getNickName(getActivity()))) {
                        joinOrLeave(mContentList.get(position).getId());
                    } else {
                        Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return convertView;
        }

        private String selectIsJoinString(int postId) {
            return CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_JOINED +
                    "' AND " + CommentDBHandler.Key.COMMENTNAME + "='" + Util.getNickName(getActivity()) + "'" +
                    " AND " + CommentDBHandler.Key.POSTID + "=" + postId;
        }

        private boolean isJoined(int postId) {
            return CommentDBHandler.getInstance().getCommentInfo(selectIsJoinString(postId)).size() > 0;
        }

        private void joinOrLeave(int postId) {
            if (isJoined(postId)) {
                CommentDBHandler.getInstance().deleteComment(selectIsJoinString(postId));
                Toast.makeText(getActivity(), "已经取消入伙", Toast.LENGTH_SHORT).show();
            } else {
                CommentInfo commentInfo = new CommentInfo();
                commentInfo.setContent("");
                commentInfo.setType(CommentInfo.TYPE_JOINED);
                commentInfo.setCommentName(Util.getNickName(getActivity()));
                commentInfo.setPostId(postId);
                commentInfo.setTime(System.currentTimeMillis() + "");
                CommentDBHandler.getInstance().comment(commentInfo);
                Toast.makeText(getActivity(), "成功入伙", Toast.LENGTH_SHORT).show();
            }
            mAdapter.notifyDataSetChanged();
        }

        class Holder {
            View tvComent;
            View tvJoin;
            int position;

            void updateView(View root, PostInfo postInfo) {
                TextView tvName = (TextView) root.findViewById(R.id.tv_name);
                TextView tvDestination = (TextView) root.findViewById(R.id.tv_way);
                TextView tvDeparture = (TextView) root.findViewById(R.id.tv_start);
                TextView tvDeparttime = (TextView) root.findViewById(R.id.tv_start_time);
                TextView tvContent = (TextView) root.findViewById(R.id.tv_introduce);
                TextView tvPostTime = (TextView) root.findViewById(R.id.tv_date);
                TextView tvCount = (TextView) root.findViewById(R.id.tv_comment_count);
                TextView joinedTextView = (TextView) root.findViewById(R.id.tv_join_count);
                joinedTextView.setText(isJoinedString());
                tvName.setText(postInfo.getNickname());
                tvDestination.setText("目的地 : " + postInfo.getDestination());
                tvDeparture.setText("出发地 : " + postInfo.getDeparture());
                tvDeparttime.setText("出发时间: " + postInfo.getDeparttime());
                tvContent.setText(postInfo.getContent());
                int comentCount = CommentDBHandler.getInstance().getCommentInfo(CommentDBHandler.Key.TYPE + "='" +
                        CommentInfo.TYPE_COMMENT + "' AND " + CommentDBHandler.Key.POSTID + "=" + mContentList.get(position).getId()).size();
                tvCount.setText(comentCount + "");
            }

            String isJoinedString() {
                return isJoined(mContentList.get(position).getId()) ? "取消入伙" : "入伙";
            }
        }
    }
}
