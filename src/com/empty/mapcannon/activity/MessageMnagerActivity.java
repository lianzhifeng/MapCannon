package com.empty.mapcannon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.empty.mapcannon.R;
import com.empty.mapcannon.db.CommentDBHandler;
import com.empty.mapcannon.model.CommentInfo;
import com.empty.mapcannon.util.Util;

import java.util.List;

/**
 * Created by Nick on 16/4/3.
 */
public class MessageMnagerActivity extends Activity{
    private ListView mListView;
    private MyAdapter mAdapter;
    private List<CommentInfo> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_manager);
        mListView = (ListView) findViewById(R.id.message_listview);
        mList = CommentDBHandler.getInstance().getCommentInfo(CommentDBHandler.Key.TYPE + "='" + CommentInfo.TYPE_JOINED + "'"
                + " AND " + CommentDBHandler.Key.COMMENTNAME + "='" + Util.getNickName(this) + "'");
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MessageMnagerActivity.this, ChatActivity.class);
                intent.putExtra("POSTID", mList.get(position).getPostId());
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends BaseAdapter {

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
            if (convertView == null) {
                convertView = View.inflate(MessageMnagerActivity.this, R.layout.item_message_manager, null);
            }
            TextView tl = (TextView) convertView.findViewById(R.id.text);
            tl.setText(String.format("您参加的由%s组织的活动消息", mList.get(position).getCommentName()));
            return convertView;
        }
    }
}
