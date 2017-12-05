package com.sourtimestudios.www.emotimail.Messaging;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;

import java.io.File;

/**
 * Created by user on 28/10/15.
 */
public class MMSMailer {
    private static final String TAG = "MMSMailer";
    private Context mAppContext;


    public MMSMailer(Context c){
        mAppContext = c;
    }

    public void sendMessage(String number, int position, String sender, String date){

        String toSend = "This is an Aptic message from user " + "\""+sender+"\".";

//        SmsManager sms = SmsManager.getDefault();
//        Log.i(TAG, "Number: " + number);
//        sms.sendTextMessage(number, null, toSend, null, null);

        File sdCard = Environment.getExternalStorageDirectory();
        String basePath = "file://" + sdCard.getAbsolutePath() + "/aptic/emoticons";

        String fileExtension = "/emo" + (position+1) + ".png";
        String filePath = basePath + fileExtension;

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra("address", number);
        sendIntent.putExtra("sms_body", toSend);
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filePath));
        sendIntent.setType("image/png");

        mAppContext.startActivity(sendIntent);
    }
}
