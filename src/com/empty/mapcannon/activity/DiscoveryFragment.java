package com.empty.mapcannon.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.empty.mapcannon.R;
import com.empty.mapcannon.db.CommentDBHandler;
import com.empty.mapcannon.db.PostDBHandler;
import com.empty.mapcannon.model.CommentInfo;
import com.empty.mapcannon.model.PostInfo;
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
    private MyAdapter mAdapter;
    private List<PostInfo> mContentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_first_page, container, false);
        list = (ListView) root.findViewById(R.id.listview);
        mPost = root.findViewById(R.id.tv_right);
        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostActivity.class));
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
                    Toast.makeText(mContext, "成功入伙" + position, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
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
                tvName.setText(postInfo.getNickname());
                tvDestination.setText(postInfo.getDestination());
                tvDeparture.setText(postInfo.getDeparture());
                tvDeparttime.setText(postInfo.getDeparttime());
                tvContent.setText(postInfo.getContent());
                int comentCount = CommentDBHandler.getInstance().getCommentInfo(CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_COMMENT + "'").size();
                tvCount.setText(comentCount + "");
            }
        }
    }
}
