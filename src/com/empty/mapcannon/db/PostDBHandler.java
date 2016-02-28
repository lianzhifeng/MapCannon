
package com.empty.mapcannon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.empty.mapcannon.app.App;
import com.empty.mapcannon.model.PostInfo;

import java.util.ArrayList;
import java.util.List;

public class PostDBHandler {

    private static PostDBHandler sDBHandler;
    private SQLiteDatabase mDataBase;

    private PostDBHandler(Context context) {
        mDataBase = new DbOpenHelper(context).getWritableDatabase();
    }

    public synchronized static PostDBHandler getInstance() {
        if (sDBHandler == null) {
            sDBHandler = new PostDBHandler(App.getInstance());
        }
        return sDBHandler;
    }

    public boolean post(PostInfo info) {
        ContentValues values = new ContentValues();
        values.put(Key.NICKNAME, info.getNickname());
        values.put(Key.POSTTIME, info.getPosttime());
        values.put(Key.CONTENT, info.getContent());
        values.put(Key.DESTNATION, info.getDestination());
        values.put(Key.DEPARTURE, info.getDeparture());
        values.put(Key.DEPARTTIME, info.getDeparttime());
        return mDataBase.insert(Key.TABLE_NAME, null, values) != -1;
    }

    public List<PostInfo> getPostInfo(String selection) {
        List<PostInfo> infoList = new ArrayList<PostInfo>();
        Cursor cursor = mDataBase.query(Key.TABLE_NAME, null, selection, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    PostInfo info = new PostInfo();
                    info.setId(cursor.getInt(cursor.getColumnIndex(Key._ID)));
                    info.setNickname(cursor.getString(cursor.getColumnIndex(Key.NICKNAME)));
                    info.setPosttime(cursor.getString(cursor.getColumnIndex(Key.POSTTIME)));
                    info.setContent(cursor.getString(cursor.getColumnIndex(Key.CONTENT)));
                    info.setDestination(cursor.getString(cursor.getColumnIndex(Key.DESTNATION)));
                    info.setDeparture(cursor.getString(cursor.getColumnIndex(Key.DEPARTURE)));
                    info.setDeparttime(cursor.getString(cursor.getColumnIndex(Key.DEPARTTIME)));
                    infoList.add(info);
                }
            }
            cursor.close();
        }
        return infoList;
    }

    private static class DbOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "userinfo.db";
        private static final int DATABASE_VERSION = 1;

        public DbOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE " + Key.TABLE_NAME + ";");
            onCreate(db);
        }

        private void createTable(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + Key.TABLE_NAME + " ("
                    + Key._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Key.NICKNAME + " TEXT, "
                    + Key.POSTTIME + " TEXT, "
                    + Key.CONTENT + " TEXT, "
                    + Key.DESTNATION + " TEXT, "
                    + Key.DEPARTURE + " TEXT, "
                    + Key.DEPARTTIME + " TEXT);");
        }

    }

    public static final class Key implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String NICKNAME = "nickname";
        public static final String POSTTIME = "posttime";
        public static final String CONTENT = "content";
        public static final String DESTNATION = "destination";
        public static final String DEPARTURE = "departure";
        public static final String DEPARTTIME = "departtime";
    }
}
