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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.empty.mapcannon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 16/2/27.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DiscoveryFragment extends Fragment implements View.OnClickListener {
    private ListView list;
    private View mBtnComment;
    private View mBtnTogether;
    private View root;
    private MyAdapter mAdapter;
    private List<Object> mContentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_first_page, container, false);
        list = (ListView) root.findViewById(R.id.listview);
        mContentList = new ArrayList<Object>();
        mContentList.add(new Object());
        mContentList.add(new Object());
        mContentList.add(new Object());
        mContentList.add(new Object());
        mAdapter = new MyAdapter(getActivity());
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), TogetherDetailActivity.class));
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext, R.layout.item_together, null);
            return view;
        }
    }
}
