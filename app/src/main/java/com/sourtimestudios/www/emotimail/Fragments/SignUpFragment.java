package com.sourtimestudios.www.emotimail.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sourtimestudios.www.emotimail.Activities.HomeActivity;
import com.sourtimestudios.www.emotimail.Activities.LoggingInActivity;
import com.sourtimestudios.www.emotimail.Activities.WelcomeActivity;
import com.sourtimestudios.www.emotimail.R;

/**
 * Created by user on 01/10/15.
 */
public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";

    private EditText etUsername;
    private EditText etPin;
    private EditText etEmail;
    private Button bSignup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup,container,false);

        etUsername = (EditText) v.findViewById(R.id.etUsername);
        etPin = (EditText) v.findViewById(R.id.etPin);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        bSignup = (Button) v.findViewById(R.id.bSignup);


        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = etUsername.getText().toString();
                String p = etPin.getText().toString();
                String e = etEmail.getText().toString();

                signUp(u,p,e);
            }
        });

        return v;
    }

    private void signUp(String user, String pin, String email){

        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(pin) || (!TextUtils.isEmpty(pin) && !isPasswordValid(pin))) {
            etPin.setError(getString(R.string.error_invalid_password));
            focusView = etPin;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user) || (!TextUtils.isEmpty(user) && !isUsernameValid(user))) {
            etUsername.setError(getString(R.string.error_too_short));
            focusView = etUsername;
            cancel = true;
        }
        if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

            prefs.edit().putString(String.valueOf(R.string.pref_user),String.valueOf(user)).commit();
            prefs.edit().putString(String.valueOf(R.string.pref_pin),String.valueOf(pin)).commit();
            prefs.edit().putString(String.valueOf(R.string.pref_recipient),String.valueOf(email)).commit();
            prefs.edit().putBoolean(LoggingInActivity.PREF_FIRST_LOGIN,true).commit();
            prefs.edit().putBoolean(String.valueOf(R.string.pref_signed_up), true).commit();

            String u = prefs.getString(String.valueOf(R.string.pref_user),"");
            Log.d(TAG,"u = " + u);

            Intent i = new Intent(getActivity(), WelcomeActivity.class);
            startActivity(i);
            getActivity().finish();

            Log.i(TAG,"Login successful");
        }


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.length() >= 6;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() == 4;
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() >= 4 && username.length() <=12;
    }
}
