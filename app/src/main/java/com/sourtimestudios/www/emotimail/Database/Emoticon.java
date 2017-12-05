package com.sourtimestudios.www.emotimail.Database;

/**
 * Created by user on 03/10/15.
 */
public class Emoticon {

    private long mId;
    private String mName;
    private int mCount;
    private long emo_id;
    private long user_id;

    public Emoticon(){
        mId = -1;
        mCount = 0;
    }

    public Emoticon(long id){
        mName = "";
        mId = -1;
        mCount = 0;
        emo_id = id;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public long getEmo_id() {
        return emo_id;
    }

    public void setEmo_id(long emo_id) {
        this.emo_id = emo_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
