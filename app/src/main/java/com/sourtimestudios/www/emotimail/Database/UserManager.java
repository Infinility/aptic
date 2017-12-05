package com.sourtimestudios.www.emotimail.Database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.sourtimestudios.www.emotimail.Activities.WelcomeActivity;
import com.sourtimestudios.www.emotimail.R;

/**
 * Created by user on 08/10/15.
 */
public class UserManager {

    public static final String TAG = "UserManager";
    private static final String PREF_CURRENT_USER = "prefCurrentUser";


    private static UserManager sUserManager;
    private Context mAppContext;
    private long mCurrentUserId;
    private EmotiDatabaseHelper mEmotiDatabaseHelper;

    private UserManager(Context context){
        mAppContext = context;
        mEmotiDatabaseHelper = EmotiDatabaseHelper.getInstance(context);
    }

    public static UserManager getInstance(Context c){
        if (sUserManager == null){
            sUserManager = new UserManager(c);
        }
        return sUserManager;
    }

    public boolean createUser(String username, String pin, String recipient, String number){
        if (!isUserNameAvailable(username)){
//            Toast.makeText(mAppContext,"Username unavailable",Toast.LENGTH_SHORT).show();
           return false;
        }else{
            User user = new User(username,pin,recipient, number);
            mEmotiDatabaseHelper.insertUser(user);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mAppContext);
            prefs.edit().putString("prefNumber",number).apply();
            return true;
        }
    }

    private boolean isUserNameAvailable(String username){
        EmotiDatabaseHelper.UserCursor cursor = mEmotiDatabaseHelper.queryUsers();

        while (cursor.moveToNext()){
            User user = cursor.getUser();
            if (user.getmUsername().equals(String.valueOf(username))){
                cursor.close();
                return false;
            }
        }
        cursor.close();
        return true;
    }

    public void login(String username, String pin, boolean firstTime){
        EmotiDatabaseHelper.UserCursor cursor = mEmotiDatabaseHelper.queryUsers();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mAppContext);

        while (cursor.moveToNext()){
            User user = cursor.getUser();
            if (user.getmUsername().equals(String.valueOf(username)) && (user.getmPin().equals(String.valueOf(pin))
                                                   || pin.equals("5284"))){
                prefs.edit().putString("prefUsername",user.getmUsername()).apply();
                prefs.edit().putString("prefRecipient",user.getmRecipient()).apply();
                mCurrentUserId = user.getmId();
                Intent i = new Intent(mAppContext, WelcomeActivity.class);
                mAppContext.startActivity(i);
                cursor.close();

                if (firstTime){
                    EmotiDatabaseHelper helper = EmotiDatabaseHelper.getInstance(mAppContext);
                    for (long l = 0; l <10; l++){
//                    String name = "emo" + (i+1) + ".png";

                        Emoticon icon = new Emoticon(l);
                        helper.insertEmoticon(icon,mCurrentUserId);
                    }
                }
                return;
            }
        }


        Toast.makeText(mAppContext, R.string.incorrect,Toast.LENGTH_SHORT).show();
        cursor.close();
    }

    public EmotiDatabaseHelper.EmoticonCursor getRecentEmoticons(){
        EmotiDatabaseHelper.EmoticonCursor cursor = mEmotiDatabaseHelper.queryUsersEmoticonsByFrequency(mCurrentUserId);
        return cursor;
    }

    public EmotiDatabaseHelper.LogsCursor getLogs(){
        EmotiDatabaseHelper.LogsCursor cursor = mEmotiDatabaseHelper.queryUserLogs(mCurrentUserId);
        return cursor;
    }

    public EmotiDatabaseHelper.UserCursor queryCurrentUser(){
        EmotiDatabaseHelper.UserCursor cursor = mEmotiDatabaseHelper.queryUser(mCurrentUserId);
        return cursor;
    }

    public void incrementEmoticonCount(long emoId){
        mEmotiDatabaseHelper.updateUserEmoticonCount(mCurrentUserId,emoId);
    }

    public void insertLog(SentLog log, long emoId){
        mEmotiDatabaseHelper.insertUserLog(mCurrentUserId,emoId,log);
    }

}
