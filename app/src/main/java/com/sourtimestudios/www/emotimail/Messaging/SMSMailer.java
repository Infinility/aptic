package com.sourtimestudios.www.emotimail.Messaging;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by user on 28/10/15.
 */
public class SMSMailer {
    private static final String TAG = "SMSMailer";
    private Context mAppContext;


    public SMSMailer(Context c){
        mAppContext = c;
    }

    public void sendMessage(String number, int position, String sender, String date){

        String toSend = "This is an Aptic message from user " + "\""+sender+"\"" +
                ". They have sent Emoticon " + position+1 + ".";

        SmsManager sms = SmsManager.getDefault();
        Log.i(TAG,"Number: " + number);
        sms.sendTextMessage(number, null, toSend, null, null);
    }
}
