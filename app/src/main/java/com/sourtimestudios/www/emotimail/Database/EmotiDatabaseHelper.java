package com.sourtimestudios.www.emotimail.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by user on 01/10/15.
 */
public class EmotiDatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "EmotiDatabaseHelper";

    private static final String DB_NAME = "emoticons.sqlite";
    private static final int VERSION = 1;

    private static final String TABLE_USER = "users";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_PIN = "pin";
    private static final String COLUMN_USER_RECIPIENT = "recipient";

    private static final String TABLE_EMOTICON = "emoticons";
    private static final String COLUMN_EMO_NAME = "name";
    private static final String COLUMN_EMO_COUNT = "count";
    private static final String COLUMN_EMO_EMO_ID = "emo_id";

    private static final String COLUMN_USER_ID = "user_id";

    private static final String TABLE_LOGS = "logs";
    private static final String COLUMN_LOG_TIMESTAMP = "timestamp";
    private static final String COLUMN_LOG_RECIPIENT = "recipient";
    private static final String COLUMN_LOG_ID = "emo_id";


    private static EmotiDatabaseHelper mInstance;

    public EmotiDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static EmotiDatabaseHelper getInstance(Context c){
        if (mInstance != null){
            return mInstance;
        }else{
            mInstance = new EmotiDatabaseHelper(c);
            return mInstance;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table users (" +
                "_id integer primary key autoincrement, name varchar(100), pin varchar(100), recipient varchar(100))");

        db.execSQL("create table emoticons (" +
            "_id integer primary key autoincrement, name varchar(100), count integer, emo_id integer, user_id integer references users(_id))");

        db.execSQL("create table logs (" +
            "_id integer primary key autoincrement, timestamp integer, recipient varchar(100), " +
                "emo_id integer references emoticons(_id), user_id integer references users(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /*
    *
    * USERS
    *
    *
    * */
    public long insertUser(User user){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME,user.getmUsername());
        cv.put(COLUMN_USER_PIN, user.getmPin());
        cv.put(COLUMN_USER_RECIPIENT, user.getmRecipient());
        return getWritableDatabase().insert(TABLE_USER,null,cv);
    }

    public UserCursor queryUsers(){
        Cursor wrapped = getReadableDatabase().query(TABLE_USER,null,null,null,null,null,null);
        return new UserCursor(wrapped);
    }

    public UserCursor queryUser(long id){
        Cursor wrapped = getReadableDatabase().query(TABLE_USER,null,"_id = ?",new String[]{String.valueOf(id)},null,null,null,"1");
        return new UserCursor(wrapped);
    }

    public static class UserCursor extends CursorWrapper {

        public UserCursor(Cursor cursor) {
            super(cursor);
        }

        public User getUser(){
            if(isBeforeFirst() || isAfterLast()) return null;

            User user = new User();
            user.setmId(getPosition());
            user.setmUsername(getString(getColumnIndex(COLUMN_USER_NAME)));
            user.setmPin(getString(getColumnIndex(COLUMN_USER_PIN)));
            user.setmRecipient(getString(getColumnIndex(COLUMN_USER_RECIPIENT)));
            return user;
        }
    }




    /*
    *
    * EMOTICONS
    *
    * */
    public long insertEmoticon(Emoticon emo, long userId){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMO_NAME,emo.getmName());
        cv.put(COLUMN_EMO_COUNT,emo.getmCount());
        cv.put(COLUMN_EMO_EMO_ID, emo.getEmo_id());
        cv.put(COLUMN_USER_ID,userId);
        return getWritableDatabase().insert(TABLE_EMOTICON,null,cv);
    }

    public EmoticonCursor queryEmoticons(){
        Cursor wrapped = getReadableDatabase().query(TABLE_EMOTICON,null,null,null,null,null,null);
        return new EmoticonCursor(wrapped);
    }

    public EmoticonCursor queryEmoticonsByFrequency(){
        Cursor wrapped = getReadableDatabase().query(TABLE_EMOTICON,null,null,null,null,null,COLUMN_EMO_COUNT + " desc");
        return new EmoticonCursor(wrapped);
    }

    public EmoticonCursor queryUsersEmoticonsByFrequency(long id){
        Cursor wrapped = getReadableDatabase().query(TABLE_EMOTICON,null,COLUMN_USER_ID + " = ?",new String[]{String.valueOf(id)},null,null,COLUMN_EMO_COUNT + " desc");
        return new EmoticonCursor(wrapped);
    }

    public void updateUserEmoticonCount(long userId, long emoId){

        String sql = "UPDATE " + TABLE_EMOTICON +
                " SET " + COLUMN_EMO_COUNT + "=" + COLUMN_EMO_COUNT + "+1" +
                " WHERE " + COLUMN_EMO_EMO_ID + " == " + emoId + " AND " + COLUMN_USER_ID + " == " + userId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public void updateEmoticonCount(long id){
        String[] whereArgs = new String[] { String.valueOf(id) };

        String sql = "UPDATE " + TABLE_EMOTICON +
                " SET " + COLUMN_EMO_COUNT + "=" + COLUMN_EMO_COUNT + "+1" +
                " WHERE " + "_id" + " == " + id;

        mInstance.getWritableDatabase().execSQL(sql);
//        mInstance.getWritableDatabase().update(TABLE_EMOTICON,,"_id=?",whereArgs);
    }

    public static class EmoticonCursor extends CursorWrapper {

        public EmoticonCursor(Cursor cursor) {
            super(cursor);
        }

        public Emoticon getEmoticon(){
            if(isBeforeFirst() || isAfterLast()) return null;

            Emoticon emo = new Emoticon();
            emo.setmId(getPosition());
            emo.setmName(getString(getColumnIndex(COLUMN_EMO_NAME)));
            emo.setmCount(getInt(getColumnIndex(COLUMN_EMO_COUNT)));
            emo.setEmo_id(getLong(getColumnIndex(COLUMN_EMO_EMO_ID)));
            emo.setUser_id(getLong(getColumnIndex(COLUMN_USER_ID)));
            return emo;
        }
    }





     /*
    *
    * LOGS
    *
    * */
    public long insertLog(long id, SentLog log){
        ContentValues cv = new ContentValues();
        Log.i(TAG,""+log.getmSentTime());
        cv.put(COLUMN_LOG_TIMESTAMP,log.getmSentTime());
        cv.put(COLUMN_LOG_RECIPIENT,log.getmRecipient());
        cv.put(COLUMN_LOG_ID,id);
        return getWritableDatabase().insert(TABLE_LOGS,null,cv);
    }

    public long insertUserLog(long userId, long emoId, SentLog log){
        ContentValues cv = new ContentValues();
        Log.i(TAG,""+log.getmSentTime());
        cv.put(COLUMN_LOG_TIMESTAMP,log.getmSentTime());
        cv.put(COLUMN_LOG_RECIPIENT,log.getmRecipient());
        cv.put(COLUMN_LOG_ID,emoId);
        cv.put(COLUMN_USER_ID,userId);
        return getWritableDatabase().insert(TABLE_LOGS,null,cv);
    }

    public LogsCursor queryLlogs(){
        Cursor wrapped = getReadableDatabase().query(TABLE_LOGS,null,null,null,null,null,COLUMN_LOG_TIMESTAMP + " desc");
        return new LogsCursor(wrapped);
    }

    public LogsCursor queryUserLogs(long userId){
        Cursor wrapped = getReadableDatabase().query(TABLE_LOGS,null,COLUMN_USER_ID + " = ?",new String[]{String.valueOf(userId)},null,null,COLUMN_LOG_TIMESTAMP + " desc");
        return new LogsCursor(wrapped);
    }


    public static class LogsCursor extends CursorWrapper {

        public LogsCursor(Cursor cursor) {
            super(cursor);
        }

        public SentLog getLog(){
            if(isBeforeFirst() || isAfterLast()) return null;

            SentLog log = new SentLog();
            log.setmSentTime(getLong(getColumnIndex(COLUMN_LOG_TIMESTAMP)));
            log.setmRecipient(getString(getColumnIndex(COLUMN_LOG_RECIPIENT)));
            log.setmId(getInt(getColumnIndex(COLUMN_LOG_ID)));
            log.setmUserId(getLong(getColumnIndex(COLUMN_USER_ID)));
            return log;
        }
    }



}
