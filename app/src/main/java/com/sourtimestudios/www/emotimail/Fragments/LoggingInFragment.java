package com.sourtimestudios.www.emotimail.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sourtimestudios.www.emotimail.Activities.HomeActivity;
import com.sourtimestudios.www.emotimail.Activities.LoggingInActivity;
import com.sourtimestudios.www.emotimail.Activities.WelcomeActivity;
import com.sourtimestudios.www.emotimail.Database.UserManager;
import com.sourtimestudios.www.emotimail.R;

/**
 * Created by user on 01/10/15.
 */
public class LoggingInFragment extends Fragment {

    private static final String TAG = "LoggingInFragment";

    public static final String PREFERENCE_REMEMBER_USERNAME = "prefRememberUsername";
    public static final String PREFERENCE_REMEMBERED_USERNAME = "prefRememberedUsername";


    private EditText etUsername;
    private EditText etPin;
    private CheckBox cbRemember;
    private Button bLogin;
    private UserManager mUserManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserManager = UserManager.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final boolean rememberUsername = prefs.getBoolean(PREFERENCE_REMEMBER_USERNAME,false);

        etUsername = (EditText) v.findViewById(R.id.etUsername_login);
        etPin = (EditText) v.findViewById(R.id.etPin_login);
        bLogin = (Button) v.findViewById(R.id.bLogin);
        cbRemember = (CheckBox) v.findViewById(R.id.checkboxRemember);

        cbRemember.setChecked(rememberUsername);

        if (rememberUsername){
            String username = prefs.getString(PREFERENCE_REMEMBERED_USERNAME,"");
            etUsername.setText(username);
        }

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u = etUsername.getText().toString();
                String p = etPin.getText().toString();

                if (cbRemember.isChecked()){
                    prefs.edit().putString(PREFERENCE_REMEMBERED_USERNAME,u).apply();
                    prefs.edit().putBoolean(PREFERENCE_REMEMBER_USERNAME, true).apply();
                }else{
                    prefs.edit().putBoolean(PREFERENCE_REMEMBER_USERNAME, false).apply();
                }

                etUsername.setText("");
                etPin.setText("");

                mUserManager.login(u,p,false);

//                logIn(u,p);
            }
        });

        return v;
    }


//    private void logIn(String u, String p){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//        String username = prefs.getString(String.valueOf(R.string.pref_user),"");
//        String pin = prefs.getString(String.valueOf(R.string.pref_pin),"");
//
//        if (!username.equals(String.valueOf(u)) || !pin.equals(String.valueOf(p))){
//            Toast.makeText(getActivity(),R.string.incorrect,Toast.LENGTH_SHORT).show();
//            Log.d(TAG,username + " " + u);
//            Log.d(TAG,"prefs: "+ pin + " textview: " + p);
//        }else{
//            prefs.edit().putBoolean(LoggingInActivity.PREF_FIRST_LOGIN,false).apply();
//            Intent i = new Intent(getActivity(), WelcomeActivity.class);
//            startActivity(i);
//            getActivity().finish();
//        }
//    }

}
