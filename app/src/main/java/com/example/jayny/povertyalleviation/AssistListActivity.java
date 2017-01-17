package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AssistListActivity extends AppCompatActivity {
    private ListTask listTask;
    private String aUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_poorerlistdetail);
        View policy_document = findViewById(R.id.policy_document);
        policy_document.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, ItemListActivity.class);
                        intent.putExtra("getDocType","0");
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
        View new_info = findViewById(R.id.new_info);
        new_info.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Context context = view.getContext();
                                            Intent intent = new Intent(context, ItemListActivity.class);
                                            intent.putExtra("getDocType","1");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }
        );
        View p_detail = findViewById(R.id.p_detail);
        p_detail.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Context context = view.getContext();
                       Intent intent = new Intent(context, PoorInfoActivity.class);
                       intent.putExtra("aUnit",aUnit);
                       intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }
              }
        );

        View p_suggest = findViewById(R.id.p_suggest);
        p_suggest.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Context context = view.getContext();
                       Intent intent = new Intent(context, SuggestActivity.class);
                       startActivity(intent);
                   }
              }
        );
        p_suggest.setVisibility(View.VISIBLE);
        View assist_set = findViewById(R.id.assist_set);
        assist_set.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Context context = view.getContext();
                                             Intent intent = new Intent(context, AssistSetActivity.class);
                                             startActivity(intent);
                                         }
                                     }
        );

        View p_need = findViewById(R.id.p_need);
        p_need.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Context context = view.getContext();
                                             Intent intent = new Intent(context, RequireListActivity.class);
                                             startActivity(intent);
                                         }
                                     }
        );
        View p_fund = findViewById(R.id.p_fund);
        p_fund.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Context context = view.getContext();
                                             Intent intent = new Intent(context, fundListActivity.class);
                                             startActivity(intent);
                                         }
                                     }
        );
        if(Constant.fundstatus.equals("1")){
            p_fund.setVisibility(View.VISIBLE);
            p_suggest.setBackgroundColor(Color.WHITE);
        }
        listTask = new ListTask();
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private long mPressedTime = 0;

    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            System.exit(0);
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ListTask extends AsyncTask<Void, Void, String> {

        ListTask() {
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
            } else if (Constant.usertype.equals("2")) {
                if (getIntent().getStringExtra("status2").equals("0")) {
                    temp.put("pid", Constant.aid);
                } else {
                    temp.put("aid", Constant.aid);
                }
            } else if (Constant.usertype.equals("3")) {
                temp.put("pid", Constant.aid);
            } else {
                temp.put("aid", Constant.aid);
            }
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findFrontPoorInfo", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);
            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(AssistListActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    TextView temp = (TextView) findViewById(R.id.name);
                    temp.append(dataJson.getString("name"));
                    temp = (TextView) findViewById(R.id.birthday);
                    temp.append(dataJson.getString("identityCard").substring(6,14));
                    temp = (TextView) findViewById(R.id.homeAddress);
                    temp.append(dataJson.getString("homeAddress"));
                    temp = (TextView) findViewById(R.id.aUnit);
                    temp.append(dataJson.getString("aUnit"));
                    aUnit = dataJson.getString("aUnit");
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(AssistListActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }
}
