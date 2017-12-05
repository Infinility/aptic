package com.sourtimestudios.www.emotimail.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sourtimestudios.www.emotimail.Database.EmotiDatabaseHelper;
import com.sourtimestudios.www.emotimail.Database.Emoticon;
import com.sourtimestudios.www.emotimail.Fragments.LoggingInFragment;
import com.sourtimestudios.www.emotimail.Fragments.SignUpFragment;
import com.sourtimestudios.www.emotimail.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 01/10/15.
 */
public class LoggingInActivity extends AppCompatActivity {

    private static final String TAG = "LogginInActivity";

    public static final String PREF_FIRST_LOGIN = "prefIsFirstLogin";

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logging_in);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

//        if (savedInstanceState == null) {
//            boolean isSignedUp = prefs.getBoolean(String.valueOf(R.string.pref_signed_up), false);
//            if (isSignedUp) {
                FragmentTransaction transaction = fm.beginTransaction();
                LoggingInFragment fragment = new LoggingInFragment();
                transaction.replace(R.id.fragmentContainer_login, fragment);
                transaction.commit();
//            } else {
//
//
//                FragmentTransaction transaction = fm.beginTransaction();
//                SignUpFragment fragment = new SignUpFragment();
//                transaction.replace(R.id.fragmentContainer_login, fragment);
//                transaction.commit();

//                EmotiDatabaseHelper helper = EmotiDatabaseHelper.getInstance(this);
//                for (long i = 0; i <10; i++){
////                    String name = "emo" + (i+1) + ".png";
//
//                    Emoticon icon = new Emoticon(i);
//                    helper.insertEmoticon(icon);
//                }



//        try {
////            writeImagesToDisk();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.miSignup:
                Intent i = new Intent(LoggingInActivity.this,SignupActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void writeImagesToDisk() throws IOException {

//        InputStream input = getAssets().open("emo1.png");

//        FileOutputStream outputStream ;

//        try {
//            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            outputStream.
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        for (int i = 0; i < 10; i++) {

//            FileOutputStream outputStream;
            String fileName1 = "emo" + (i + 1);
            String filename = "emo" + (i + 1) + ".png";

            InputStream input = getAssets().open(filename);

            try {
                File file = new File(getFilesDir(), filename);
                OutputStream output = new FileOutputStream(file);
                try {
                    try {
                        byte[] buffer = new byte[30 * 1024]; // or other buffer size
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                        Log.i(TAG, "Output flushed");
                    } finally {
                        output.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // handle exception, define IOException and others
                    Log.e(TAG, "first catch");
                }
            } finally {
                input.close();
                Log.i(TAG, "input closed");
            }
        }
    }


}
