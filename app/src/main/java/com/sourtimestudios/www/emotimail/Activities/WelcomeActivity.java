package com.sourtimestudios.www.emotimail.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sourtimestudios.www.emotimail.R;

/**
 * Created by user on 05/10/15.
 */
public class WelcomeActivity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

//        toolbar = (Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.btnWelcome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
