package com.sourtimestudios.www.emotimail.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import com.sourtimestudios.www.emotimail.Fragments.SlidingTabsFragment;
import com.sourtimestudios.www.emotimail.R;

/**
 * Created by user on 01/10/15.
 */
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            SlidingTabsFragment fragment = new SlidingTabsFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"onbackpressed");
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
        this.finish();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK){
////            Intent i = new Intent(this,LoggingInActivity.class);
////            startActivity(i);
//            System.exit(0);
//        }
//        return true;
//    }
}
