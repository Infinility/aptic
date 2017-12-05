package com.sourtimestudios.www.emotimail.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourtimestudios.www.emotimail.Activities.WelcomeActivity;
import com.sourtimestudios.www.emotimail.Database.EmotiDatabaseHelper;
import com.sourtimestudios.www.emotimail.Database.Emoticon;
import com.sourtimestudios.www.emotimail.Database.SentLog;
import com.sourtimestudios.www.emotimail.Messaging.GMailer;
import com.sourtimestudios.www.emotimail.R;


import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 01/10/15.
 */
public class EmotisFragment extends ListFragment {

    static final String TAG = "EmotisFragment";
    private String recipient;
    private int emoPosition;

    public static final int REQUEST_CONFIRM = 1000;

    private int[] emoidarray = {R.drawable.emo1,R.drawable.emo2,R.drawable.emo3,
                        R.drawable.emo4,R.drawable.emo5,R.drawable.emo6, R.drawable.emo7,
                        R.drawable.emo8,R.drawable.emo9,R.drawable.emo10};

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = super.onCreateView(inflater, container, savedInstanceState);



        setListAdapter(new EmoticonAdapter());

//        ListView listView = v.findViewById(a)

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Toast.makeText(getActivity(), R.string.message_success, Toast.LENGTH_SHORT).show();

    }

    private class EmoticonAdapter extends ArrayAdapter<Emoticon>{

        public EmoticonAdapter() {
            super(getActivity(),0);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.emoticon_list_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.tvId);

            ImageView iv = (ImageView) convertView.findViewById(R.id.ivEmo);


                int pos = position + 1;
                String emoid = "drawable/emo" + pos + ".png";

//                Resources resources = getActivity().getResources();
//                final int resourceId = resources.getIdentifier(emoid,"drawable",
//                    getActivity().getPackageName());
//                Log.d(TAG,""+resourceId);
                iv.setImageResource(emoidarray[position]);
                tv.setText(""+pos);


//            Resources resources = getActivity().getResources();
//            final int resourceId = resources.getIdentifier("emo"+(position+1)+".png", "drawable",
//                    getActivity().getPackageName());
//
//            iv.setImageResource(resourceId);

            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

//        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
//        if (isNetworkAvailable == false){
//            Toast.makeText(getActivity(), R.string.enable_network, Toast.LENGTH_SHORT).show();
//        }else {
//
            FragmentManager fm = getActivity().getSupportFragmentManager();
            ConfirmSendFragment confirmSendFragment = ConfirmSendFragment.newInstance(position);
            confirmSendFragment.setTargetFragment(EmotisFragment.this, REQUEST_CONFIRM);
            confirmSendFragment.show(fm, String.valueOf(R.string.confirm_title));
//        }
//
//        recipient = prefs.getString(String.valueOf(R.string.pref_recipient),"");
//
//        Log.d(TAG,recipient);
//
//        emoPosition = position;
//
////        long currentTime = Calendar.getInstance().getTimeInMillis();
//        long currentTime = System.currentTimeMillis();
//        Log.i(TAG,""+currentTime);
//        String recipient = prefs.getString(String.valueOf(R.string.pref_recipient), "");
//        SentLog log = new SentLog(recipient,currentTime);
//
//        EmotiDatabaseHelper helper = EmotiDatabaseHelper.getInstance(getActivity());
//        helper.updateEmoticonCount(position+1);
//
//        helper.insertLog(position+1,log);
//
//        SendTask task = new SendTask();
//        task.execute();
    }

//    private void sendMessage(String recipient, int position){
//        GMailer.sendMessage(getActivity(),position,recipient);
//    }


//    private class SendTask extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            sendMessage(recipient, emoPosition);
//            return null;
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG,"onActivityResult called");

        Log.d(TAG,"requestcode: " + requestCode);
        Log.d(TAG,"resultcode: " + resultCode);


        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult result ok");
            Toast.makeText(getActivity(), R.string.message_success, Toast.LENGTH_SHORT).show();
//            Snackbar.make()
//            return;
//            Snackbar.make(v.findViewById(android.R.id.content), R.string.message_success, Snackbar.LENGTH_LONG).show();
        }



        if (requestCode == REQUEST_CONFIRM){
//            Toast.makeText(getActivity(),R.string.message_success,Toast.LENGTH_SHORT).show();
            Log.d(TAG,"onActivityResult request confirm");


            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                }
            }, 1000);

            Intent i = new Intent(getActivity(), WelcomeActivity.class);
            startActivity(i);
            getActivity().finish();
        }

    }
}
