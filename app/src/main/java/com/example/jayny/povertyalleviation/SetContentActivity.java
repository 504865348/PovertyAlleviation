package com.example.jayny.povertyalleviation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SetContentActivity extends AppCompatActivity {
    ContentTask contentTask;
    SendContentTask sendContentTask;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("as"));
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        editText = (EditText) findViewById(R.id.setContentContent);
        Button buttonForSuggest = (Button) findViewById(R.id.buttonForSetContentContent);
        if (Constant.usertype.equals("1")) {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.buttonForSetContentContent || id == EditorInfo.IME_ACTION_DONE) {
                        suggestSubmit();
                        return true;
                    }
                    return false;
                }
            });
            buttonForSuggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    suggestSubmit();
                }
            });
        }else if(Constant.usertype.equals("3")&&getIntent().getStringExtra("status3").equals("1")){
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.buttonForSetContentContent || id == EditorInfo.IME_ACTION_DONE) {
                        suggestSubmit();
                        return true;
                    }
                    return false;
                }
            });
            buttonForSuggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    suggestSubmit();
                }
            });
        } else {
            editText.setFocusable(false);
            editText.setEnabled(false);
            buttonForSuggest.setVisibility(View.GONE);
        }
        contentTask = new ContentTask();
        contentTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void suggestSubmit() {
        editText.setError(null);
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(getString(R.string.error_p_set));
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
            sendContentTask = new SendContentTask();
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
            if (Constant.usertype.equals("1")) {
                temp.put("aid", Constant.userid);
            } else if(Constant.usertype.equals("2")){
                if(getIntent().getStringExtra("status2").equals("0")){
                    temp.put("pid", Constant.aid);
                }else{
                    temp.put("aid", Constant.aid);
                }
            } else if(Constant.usertype.equals("3")){
                temp.put("pid", Constant.aid);
            }else if(Constant.usertype.equals("5")){
                temp.put("pid", Constant.userid);
            } else {
                temp.put("aid", Constant.aid);
            }
            temp.put("type", getIntent().getStringExtra("as_type"));
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "getAssistSetDetail", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("error")) {
                Toast.makeText(SetContentActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    if(!msg.equals("")){
                        JSONObject dataJson = new JSONObject(msg);
                        TextView temp = (TextView) findViewById(R.id.setContentContent);
                        temp.setText(dataJson.optString("words"));
                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(SetContentActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
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

        SendContentTask() {
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

            return upload();
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("")||msg.equals("error")) {
                editText.requestFocus();
                Toast.makeText(SetContentActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    if (dataJson.optString("status").equals("ok")) {
                        Toast.makeText(SetContentActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
                        navigateUpTo(new Intent(SetContentActivity.this, AssistSetActivity.class).putExtra("status3",null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3")));
                    } else {
                        Toast.makeText(SetContentActivity.this, "保存失败！", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(SetContentActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /**
     * 上传文件到服务器
     *
     * @return 返回响应的内容
     */
    public String upload() {
        String RequestURL = getResources().getString(R.string.host_port_server) + "saveOrUpdateAssistSet";
        int res = 0;
        String BOUNDARY = "*****"; // 边界标识 随机生成
        String PREFIX = "--";
        String LINE_END = "\r\n";
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            StringBuffer sb1 = new StringBuffer();
            sb1.append(PREFIX);
            sb1.append(BOUNDARY);
            sb1.append(LINE_END);
            if (Constant.usertype.equals("5")) {
                sb1.append("Content-Disposition: form-data; name=\"pid\";" + LINE_END);
            }else if(Constant.usertype.equals("3")){
                sb1.append("Content-Disposition: form-data; name=\"pid\";" + LINE_END);
            }else{
                sb1.append("Content-Disposition: form-data; name=\"aid\";" + LINE_END);
            }
            sb1.append(LINE_END);
            if (Constant.usertype.equals("1")) {
                sb1.append(Constant.userid);
            } else if(Constant.usertype.equals("3")){
                sb1.append(Constant.aid);
            } else if(Constant.usertype.equals("5")){
                sb1.append( Constant.userid);
            }  else {
                sb1.append(Constant.aid);
            }
            sb1.append(LINE_END);
            dos.write(sb1.toString().getBytes());

            StringBuffer sb2 = new StringBuffer();
            sb2.append(PREFIX);
            sb2.append(BOUNDARY);
            sb2.append(LINE_END);
            sb2.append("Content-Disposition: form-data; name=\"type\";" + LINE_END);
            sb2.append(LINE_END);
            sb2.append(getIntent().getStringExtra("as_type"));
            sb2.append(LINE_END);
            dos.write(sb2.toString().getBytes());

            StringBuffer sb3 = new StringBuffer();
            sb3.append(PREFIX);
            sb3.append(BOUNDARY);
            sb3.append(LINE_END);
            sb3.append("Content-Disposition: form-data; name=\"setContent\";" + LINE_END);
            sb3.append(LINE_END);
            sb3.append(editText.getText().toString());
            sb3.append(LINE_END);
            dos.write(sb3.toString().getBytes());
            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                    .getBytes();
            dos.write(end_data);
            dos.flush();
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            res = conn.getResponseCode();
            Log.d("respondCode", "response code:" + res);
            if (res == 200) {
                Log.d("success", "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb5 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb5.append((char) ss);
                }
                Log.d("success", "result : " + sb5.toString());
                return sb5.toString();
            } else {
                return "error";
            }
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}
