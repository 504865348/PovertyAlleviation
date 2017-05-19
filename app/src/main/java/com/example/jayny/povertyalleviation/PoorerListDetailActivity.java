package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PoorerListDetailActivity extends AppCompatActivity {
    private ListTask listTask;
    private String aUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poorerlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("精准扶贫-" + getIntent().getStringExtra("name") + "详细信息");
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
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
        if(null!=getIntent().getStringExtra("statusForDocs")&&getIntent().getStringExtra("statusForDocs").equals("1")){
            policy_document.setVisibility(View.GONE);
            new_info.setVisibility(View.GONE);
        }
        View p_detail = findViewById(R.id.p_detail);
        p_detail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Context context = view.getContext();
                                            Intent intent = new Intent(context, PoorInfoActivity.class);
                                            intent.putExtra("name", getIntent().getStringExtra("name"));
                                            intent.putExtra("status3", null == getIntent().getStringExtra("status3") ? "0" : getIntent().getStringExtra("status3"));
                                            intent.putExtra("status2", null == getIntent().getStringExtra("status2") ? "0" : getIntent().getStringExtra("status2"));
                                            intent.putExtra("aUnit",aUnit);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }
        );
        View assist_set = findViewById(R.id.assist_set);
        assist_set.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Context context = view.getContext();
                                              Intent intent = new Intent(context, AssistSetActivity.class);
                                              intent.putExtra("status3", null == getIntent().getStringExtra("status3") ? "0" : getIntent().getStringExtra("status3"));
                                              intent.putExtra("status2", null == getIntent().getStringExtra("status2") ? "0" : getIntent().getStringExtra("status2"));
                                              intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                                          intent.putExtra("name", getIntent().getStringExtra("name"));
                                          intent.putExtra("status3", null == getIntent().getStringExtra("status3") ? "0" : getIntent().getStringExtra("status3"));
                                          intent.putExtra("status2", null == getIntent().getStringExtra("status2") ? "0" : getIntent().getStringExtra("status2"));
                                          intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                          startActivity(intent);
                                      }
                                  }
        );
        listTask = new ListTask();
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            return MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findFrontPoorInfo", "POST", temp);
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);
            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(PoorerListDetailActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    TextView temp = (TextView) findViewById(R.id.name);
                    temp.append(dataJson.optString("name"));
                    temp = (TextView) findViewById(R.id.birthday);
                    if(dataJson.optString("identityCard").length()>14) {
                        temp.append(dataJson.optString("identityCard").substring(6, 14));
                    }
                    temp = (TextView) findViewById(R.id.homeAddress);
                    temp.append(dataJson.optString("homeAddress"));
                    temp = (TextView) findViewById(R.id.aUnit);
                    temp.append(dataJson.optString("aUnit"));
                    aUnit = dataJson.optString("aUnit");

                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(PoorerListDetailActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }
}
