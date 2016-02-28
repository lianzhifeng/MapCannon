
package com.empty.mapcannon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.empty.mapcannon.app.App;
import com.empty.mapcannon.model.RegisterInfo;

public class UserInfoDBHandler {

    private static UserInfoDBHandler sDBHandler;
    private SQLiteDatabase mDataBase;

    private UserInfoDBHandler(Context context) {
        mDataBase = new DbOpenHelper(context).getWritableDatabase();
    }

    public synchronized static UserInfoDBHandler getInstance() {
        if (sDBHandler == null) {
            sDBHandler = new UserInfoDBHandler(App.getInstance());
        }
        return sDBHandler;
    }

    public RegisterInfo login(RegisterInfo info) {
        Cursor cursor = mDataBase.query(Key.TABLE_NAME, null, "username=? and password=?",
                new String[] {
                        info.getPhone(), info.getPassword()
                }, null, null, null);
        if (cursor != null) {
            return generateInfo(cursor);
        }
        return null;
    }

    private static RegisterInfo generateInfo(Cursor cursor) {
        RegisterInfo info = null;
        if (cursor != null) {
            info = new RegisterInfo();
            info.setCity(cursor.getString(cursor.getColumnIndex(Key.CITY)));
            info.setNickname(cursor.getString(cursor.getColumnIndex(Key.NICKNAME)));
            info.setPhone(cursor.getString(cursor.getColumnIndex(Key.PHONE)));
            info.setPassword(cursor.getString(cursor.getColumnIndex(Key.PASSWORD)));
            info.setProvince(cursor.getString(cursor.getColumnIndex(Key.PROVINCE)));
            info.setGender(cursor.getString(cursor.getColumnIndex(Key.GENDER)));
        }
        return info;
    }

    public boolean register(RegisterInfo info) {
        ContentValues values = new ContentValues();
        String phone = info.getPhone();
        String password = info.getPassword();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
            values.put(Key.PHONE, phone);
            values.put(Key.PASSWORD, password);
            return mDataBase.insert(Key.TABLE_NAME, null, values) != -1;
        }
        return false;
    }

    public boolean editInfo(RegisterInfo info) {
        String phone = info.getPhone();
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        ContentValues values = new ContentValues();
        String nickName = info.getNickname();
        if (!TextUtils.isEmpty(nickName)) {
            values.put(Key.NICKNAME, nickName);
        }
        String password = info.getPassword();
        if (!TextUtils.isEmpty(password)) {
            values.put(Key.PASSWORD, password);
        }
        String gender = info.getGender();
        if (!TextUtils.isEmpty(gender)) {
            values.put(Key.GENDER, gender);
        }
        String city = info.getCity();
        if (!TextUtils.isEmpty(city)) {
            values.put(Key.CITY, city);
        }
        String province = info.getProvince();
        if (!TextUtils.isEmpty(province)) {
            values.put(Key.PROVINCE, province);
        }
        if (values.size() != 0) {
            return mDataBase.update(Key.TABLE_NAME, values, "phone = ?", new String[] {
                    phone
            }) > 0;
        }
        return false;
    }

    private class DbOpenHelper extends SQLiteOpenHelper {
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
                    + Key.PHONE + " TEXT, "
                    + Key.PASSWORD + " TEXT, "
                    + Key.NICKNAME + " TEXT, "
                    + Key.PROVINCE + " TEXT, "
                    + Key.CITY + " TEXT, "
                    + Key.GENDER + " TEXT;");
        }

    }

    public static final class Key implements BaseColumns {
        public static final String TABLE_NAME = "login";
        public static final String PHONE = "phone";
        public static final String PASSWORD = "password";
        public static final String NICKNAME = "nickname";
        public static final String PROVINCE = "province";
        public static final String CITY = "city";
        public static final String GENDER = "gender";
    }
}
