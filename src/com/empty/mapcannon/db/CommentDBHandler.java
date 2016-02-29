
package com.empty.mapcannon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.empty.mapcannon.app.App;
import com.empty.mapcannon.model.CommentInfo;

import java.util.ArrayList;
import java.util.List;

public class CommentDBHandler {

    private static CommentDBHandler sDBHandler;
    private SQLiteDatabase mDataBase;

    private CommentDBHandler(Context context) {
        mDataBase = new DbOpenHelper(context).getWritableDatabase();
    }

    public synchronized static CommentDBHandler getInstance() {
        if (sDBHandler == null) {
            sDBHandler = new CommentDBHandler(App.getInstance());
        }
        return sDBHandler;
    }

    public boolean comment(CommentInfo info) {
        ContentValues values = new ContentValues();
        values.put(Key.TYPE, info.getType());
        values.put(Key.POSTID, info.getPostId());
        values.put(Key.TIME, info.getTime());
        values.put(Key.CONTENT, info.getContent());
        values.put(Key.COMMENTNAME, info.getCommentName());
        return mDataBase.insert(Key.TABLE_NAME, null, values) != -1;
    }

    public void deleteComment(String selction) {
        mDataBase.delete(Key.TABLE_NAME, selction, null);
    }

    public List<CommentInfo> getCommentInfo(String selection) {
        List<CommentInfo> infoList = new ArrayList<CommentInfo>();
        Cursor cursor = mDataBase.query(Key.TABLE_NAME, null, selection, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    CommentInfo info = new CommentInfo();
                    info.setType(cursor.getString(cursor.getColumnIndex(Key.TYPE)));
                    info.setPostId(cursor.getInt(cursor.getColumnIndex(Key.POSTID)));
                    info.setContent(cursor.getString(cursor.getColumnIndex(Key.CONTENT)));
                    info.setTime(cursor.getString(cursor.getColumnIndex(Key.TIME)));
                    info.setCommentName(cursor.getString(cursor.getColumnIndex(Key.COMMENTNAME)));
                    infoList.add(info);
                }
            }
            cursor.close();
        }
        return infoList;
    }

    private static class DbOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "commentinfo.db";
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
                    + Key.COMMENTNAME + " TEXT, "
                    + Key.POSTID + " INTEGER, "
                    + Key.CONTENT + " TEXT, "
                    + Key.TYPE + " TEXT, "
                    + Key.TIME + " TEXT);");
        }

    }

    public static final class Key implements BaseColumns {
        public static final String TABLE_NAME = "comment";
        public static final String COMMENTNAME = "commentname";
        public static final String POSTID = "postid";
        public static final String TIME = "time";
        public static final String CONTENT = "content";
        public static final String TYPE = "type";
    }
}
