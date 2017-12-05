package com.sourtimestudios.www.emotimail.Database;

/**
 * Created by user on 03/10/15.
 */
public class SentLog {

    private long mId;
    private String mRecipient;
    private long mSentTime;
    private long mUserId;

    public SentLog(){

    }

    public SentLog(String recipient, long sentTime){
        mRecipient = recipient;
        mSentTime = sentTime;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmRecipient() {
        return mRecipient;
    }

    public void setmRecipient(String mRecipient) {
        this.mRecipient = mRecipient;
    }

    public long getmSentTime() {
        return mSentTime;
    }

    public void setmSentTime(long mSentTime) {
        this.mSentTime = mSentTime;
    }

    public long getmUserId() {
        return mUserId;
    }

    public void setmUserId(long mUserId) {
        this.mUserId = mUserId;
    }
}
