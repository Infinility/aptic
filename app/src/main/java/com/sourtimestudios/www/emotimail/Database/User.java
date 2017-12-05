package com.sourtimestudios.www.emotimail.Database;

/**
 * Created by user on 09/10/15.
 */
public class User {

    private long mId;
    private String mUsername;
    private String mPin;
    private String mRecipient;
    private String mNumber;

    public User(){
        mId = -1;

    }

    public User(String username, String pin, String recipient, String number){
        mId = -1;
        mUsername = username;
        mPin = pin;
        mRecipient = recipient;
        mNumber = number;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPin() {
        return mPin;
    }

    public void setmPin(String mPin) {
        this.mPin = mPin;
    }

    public String getmRecipient() {
        return mRecipient;
    }

    public void setmRecipient(String mRecipient) {
        this.mRecipient = mRecipient;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}
