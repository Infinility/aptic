package com.sourtimestudios.www.emotimail.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sourtimestudios.www.emotimail.Database.EmotiDatabaseHelper;
import com.sourtimestudios.www.emotimail.Database.Emoticon;
import com.sourtimestudios.www.emotimail.Fragments.LoggingInFragment;
import com.sourtimestudios.www.emotimail.Fragments.SignUpFragment;
import com.sourtimestudios.www.emotimail.Fragments.SigningupFragment;
import com.sourtimestudios.www.emotimail.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 09/10/15.
 */
public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "LogginInActivity";

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logging_in);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
                FragmentTransaction transaction = fm.beginTransaction();
                SigningupFragment fragment = new SigningupFragment();
                transaction.replace(R.id.fragmentContainer_login, fragment);
                transaction.commit();

        }

//        try {
////            writeImagesToDisk();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            writeImagesToFileSystem();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void writeImagesToDisk() throws IOException {

        for (int i = 0; i < 10; i++) {

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

    private void writeImagesToFileSystem() throws IOException{
        for (int i = 0; i < 10; i++) {

            String fileName1 = "emo" + (i + 1);
            String filename = "emo" + (i + 1) + ".png";

            InputStream input = getAssets().open(filename);

            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File (sdCard.getAbsolutePath() + "/aptic/emoticons");
                dir.mkdirs();
                File file = new File(dir, filename);

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
