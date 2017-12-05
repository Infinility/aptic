package com.sourtimestudios.www.emotimail.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sourtimestudios.www.emotimail.Database.EmotiDatabaseHelper;
import com.sourtimestudios.www.emotimail.Database.SentLog;
import com.sourtimestudios.www.emotimail.Database.User;
import com.sourtimestudios.www.emotimail.Database.UserManager;
import com.sourtimestudios.www.emotimail.Messaging.GMailer;
import com.sourtimestudios.www.emotimail.Messaging.MMSMailer;
import com.sourtimestudios.www.emotimail.Messaging.SMSMailer;
import com.sourtimestudios.www.emotimail.R;

/**
 * Created by user on 06/10/15.
 */
public class ConfirmSendFragment extends DialogFragment {
    public static final String TAG = "ConfirmSendFragment";

    public static final String EXTRA_POS = "com.sourtimestudios.www.emotimail";

    private String rec;
    private int position;
    private String sender;
    private UserManager mUserManager;
    private String method;
    private String phoneNumber;

    private static ConfirmSendFragment mInstance;

    private TextView tvConfirm;

    public static ConfirmSendFragment newInstance(int position){

        if(mInstance != null){
            return mInstance;
        }else{

            Bundle args = new Bundle();
            args.putInt(EXTRA_POS, position);
            ConfirmSendFragment fragment = new ConfirmSendFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }

    private void sendResult(int resultCode){
        if (getTargetFragment() == null){
            return;
        }
        Intent i = new Intent();
        getTargetFragment().onActivityResult(EmotisFragment.REQUEST_CONFIRM, resultCode, i);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserManager = UserManager.getInstance(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v =  getActivity().getLayoutInflater().inflate(R.
        layout.fragment_dialog_confirm,null);

        final Spinner spinner = (Spinner) v.findViewById(R.id.spinMethod);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.method_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return new AlertDialog.Builder(getActivity()).setView(v)
                .setTitle(R.string.confirm_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        method = spinner.getSelectedItem().toString();
                        sendMessage();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    private void sendMessage(){
        position = getArguments().getInt(EXTRA_POS);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

//        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
//        if (isNetworkAvailable == false){
//            Toast.makeText(getActivity(),R.string.enable_network,Toast.LENGTH_SHORT);
//        }else {
            EmotiDatabaseHelper.UserCursor userCursor = UserManager.getInstance(getActivity()).queryCurrentUser();

//            rec;
//            sender;
            while(userCursor.moveToNext()){
                User user = userCursor.getUser();
                rec = user.getmRecipient();
                sender = user.getmUsername();
            }

//            rec = prefs.getString(String.valueOf(R.string.pref_recipient), "");

//            sender = prefs.getString(String.valueOf(R.string.pref_user), "");

            rec = prefs.getString("prefRecipient","");
            sender = prefs.getString("prefUsername","");
            phoneNumber = prefs.getString("prefNumber","");
            //        Log.d(TAG, recipient);

            //        long currentTime = Calendar.getInstance().getTimeInMillis();
            long currentTime = System.currentTimeMillis();
//            SentLog log = new SentLog(rec, currentTime);

//            EmotiDatabaseHelper helper = EmotiDatabaseHelper.getInstance(getActivity());
//            helper.updateEmoticonCount(position + 1);
//
//            helper.insertLog(position + 1, log);
//            mUserManager.incrementEmoticonCount(position);
//            mUserManager.insertLog(log, position + 1);

            if (method.equals("E-Mail") && checkNetworkAvailable() == false){
                Toast.makeText(getActivity(), R.string.enable_network, Toast.LENGTH_SHORT).show();
            }else {

                try {
                    SendTask task = new SendTask();
                    task.execute();

                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

    }

    private class SendTask extends AsyncTask<Void,Void,Void> {

        boolean succeeded = false;

        @Override
        protected Void doInBackground(Void... params) {
//            sendMessage(recipient, emoPosition);
            String date = LogFragment.getDate(System.currentTimeMillis(),"dd/MM/yyyy hh:mm:ss");

            Log.i(TAG, method);

            if (method.equals("SMS")){
                Log.i(TAG,"SMS selected");
                SMSMailer mailer = new SMSMailer(getActivity());
                mailer.sendMessage(phoneNumber,position,sender,date);

            }else if(method.equals("E-Mail")) {
//                if (checkNetworkAvailable() == true) {
                    try {
                        GMailer.sendMessage(getActivity(), position, rec, sender, date);
                        addSentLog(position, rec);
                        Log.i(TAG, "E-mail selected");
//                        sendResult(Activity.RESULT_OK);
                        succeeded = true;
//                        Toast.makeText(getActivity(), R.string.message_success, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG,e.toString());
                        succeeded = false;
//                        Toast.makeText(getActivity(), R.string.message_failed, Toast.LENGTH_SHORT).show();
                    }

            } else if(method.equals("MMS")){
                try {
                    MMSMailer mmsMailer = new MMSMailer(getActivity());
                    mmsMailer.sendMessage(phoneNumber, position, sender, date);
                    addSentLog(position, rec);
//                    sendResult(Activity.RESULT_OK);
                }catch (Exception e){
//                    Toast.makeText(getActivity(), R.string.message_failed, Toast.LENGTH_SHORT).show();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (succeeded){
                sendResult(Activity.RESULT_OK);
            }
        }
    }

    private void addSentLog(int position, String rec){
        long currentTime = System.currentTimeMillis();
        SentLog log = new SentLog(rec, currentTime);

        mUserManager.incrementEmoticonCount(position);
        mUserManager.insertLog(log, position + 1);
    }

    private boolean checkNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
        if (isNetworkAvailable == false) {
//            Toast.makeText(getActivity(), R.string.enable_network, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}
