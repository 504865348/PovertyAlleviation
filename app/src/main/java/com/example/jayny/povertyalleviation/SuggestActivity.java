package com.example.jayny.povertyalleviation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SuggestActivity extends AppCompatActivity {

    ContentTask contentTask;
    SendContentTask sendContentTask;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        editText = (EditText) findViewById(R.id.suggestionContent);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.buttonForSuggest || id == EditorInfo.IME_ACTION_DONE) {
                    suggestSubmit();
                    return true;
                }
                return false;
            }
        });
        Button buttonForSuggest = (Button) findViewById(R.id.buttonForSuggest);
        buttonForSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestSubmit();
            }
        });
        contentTask = new ContentTask();
        contentTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
    }

    private void suggestSubmit() {
        editText.setError(null);
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(getString(R.string.error_p_suggest));
            focusView = editText;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            sendContentTask = new SendContentTask(editText.getText().toString());
            sendContentTask.execute();
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ContentTask extends AsyncTask<Void, Void, String> {

        ContentTask() {
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

            Map<String, String> temp = new HashMap<String, String>();
            temp.put("aid", Constant.userid);
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "getSuggestion", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("error")) {
                Toast.makeText(SuggestActivity.this, getString(R.string.error_server_res), Toast.LENGTH_LONG).show();
            } else {
                try {
                    if (!msg.equals("")) {
                        JSONObject dataJson = new JSONObject(msg);
                        TextView temp = (TextView) findViewById(R.id.suggestionContent);
                        temp.setText(dataJson.getString("suggestionContent"));
                    }
                } catch (Exception e) {
                    Log.d("getJosn:", e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SendContentTask extends AsyncTask<Void, Void, String> {
        private String suggestContent;

        SendContentTask(String suggestContent) {
            this.suggestContent = suggestContent;

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
//            String result = MyUtils.postGetJson("http://192.168.1.158:8080/jeesite/app/login","POST",map);
//            String result = MyUtils.postGetJson("http://192.168.199.146/jeesite/app/findDocumentList","GET",null);

            Map<String, String> temp = new HashMap<String, String>();
            temp.put("aid", Constant.userid);
            temp.put("suggestionContent", suggestContent);
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "saveSuggestion", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("") || msg.equals("error")) {
                editText.requestFocus();
                Toast.makeText(SuggestActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    if (dataJson.getString("status").equals("ok")) {
                        Toast.makeText(SuggestActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
                        SuggestActivity.this.finish();
                    } else {
                        Toast.makeText(SuggestActivity.this, "保存失败！", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SuggestActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }
}
