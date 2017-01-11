package com.example.jayny.povertyalleviation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Intent intent;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("checkboxBoolean", false)) {
            mEmailView.setText(sp.getString("mEmail", null));
            mPasswordView.setText(sp.getString("mPassword", null));
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_email_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return "";
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("loginName", mEmail);
            map.put("password", mPassword);
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "login", "POST", map);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            mAuthTask = null;
            showProgress(false);

            if (msg.equals("") || msg.equals("error")) {
                mEmailView.requestFocus();
                Toast.makeText(LoginActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    if (dataJson.getString("status").equals("fail")) {
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    } else {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("mEmail", mEmail);
                        editor.putString("mPassword", mPassword);
                        editor.putBoolean("checkboxBoolean", true);
                        editor.commit();
                        Constant.statusTime = dataJson.getString("statusTime");
                        Constant.userid = dataJson.getString("userid");
                        Constant.usertype = dataJson.getString("usertype");
                        Constant.username = dataJson.getString("username");
                        Constant.pname = "";
                        Constant.aid = "";
                        if (Constant.usertype.equals("0")) {
                            Constant.fundstatus = dataJson.getString("fundstatus");
                            Constant.aid = dataJson.getString("aid");
                            intent = new Intent(LoginActivity.this, PoorListActivity.class);
                        } else if (Constant.usertype.equals("5")) {
                            Constant.fundstatus = dataJson.getString("fundstatus");
                            intent = new Intent(LoginActivity.this, PoorListActivity.class);
                        } else if (Constant.usertype.equals("1")) {
                            Constant.fundstatus = dataJson.getString("fundstatus");
                            Constant.pname = dataJson.getString("pname");
                            intent = new Intent(LoginActivity.this, AssistListActivity.class);
                        } else {
                            intent = new Intent(LoginActivity.this, ManagerListActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    mEmailView.requestFocus();
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

