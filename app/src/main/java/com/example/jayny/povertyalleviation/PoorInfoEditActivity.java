package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PoorInfoEditActivity extends AppCompatActivity {

    private ListTask listTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poor_info_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("精准扶贫-编辑" + getIntent().getStringExtra("name") + "详细信息");
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        listTask = new ListTask();
        listTask.executeOnExecutor(Executor.exec);
        Button b = (Button) findViewById(R.id.editButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, PoorInfoEditActivity.class);
                if (Constant.usertype.equals("2")) {
                    if ((getIntent().getStringExtra("status2") == null ? "0" : getIntent().getStringExtra("status2")).equals("0")) {
                        intent.putExtra("pid", Constant.aid);
                    } else {
                        intent.putExtra("aid", Constant.aid);
                    }
                } else if (Constant.usertype.equals("3")) {
                    intent.putExtra("pid", Constant.aid);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        if (Constant.usertype.equals("2") || Constant.usertype.equals("3")) {
            b.setVisibility(View.VISIBLE);
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
            if (Constant.usertype.equals("2")) {
                if ((getIntent().getStringExtra("status2") == null ? "0" : getIntent().getStringExtra("status2")).equals("0")) {
                    temp.put("pid", Constant.aid);
                } else {
                    temp.put("aid", Constant.aid);
                }
            } else if (Constant.usertype.equals("3")) {
                temp.put("pid", Constant.aid);
            }
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorInfo", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);
            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(PoorInfoEditActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    EditText temp = (EditText) findViewById(R.id.name);
                    temp.append(dataJson.optString("name"));
                    temp = (EditText) findViewById(R.id.identityCard);
                    temp.append(dataJson.optString("identityCard"));
                    temp = (EditText) findViewById(R.id.sex);
                    temp.append(dataJson.optString("sex"));
                    temp = (EditText) findViewById(R.id.homeAddress);
                    temp.append(dataJson.optString("homeAddress"));
                    temp = (EditText) findViewById(R.id.permanentAddress);
                    temp.append(dataJson.optString("permanentAddress"));
                    temp = (EditText) findViewById(R.id.educationLevels);
                    temp.append(dataJson.optString("educationLevels"));
                    temp = (EditText) findViewById(R.id.familyPopulation);
                    temp.append(dataJson.optString("familyPopulation"));
                    temp = (EditText) findViewById(R.id.labourForce);
                    temp.append(dataJson.optString("labourForce"));
                    temp = (EditText) findViewById(R.id.agriculturalNum);
                    temp.append(dataJson.optString("agriculturalNum"));
                    temp = (EditText) findViewById(R.id.familyIncome);
                    temp.append(dataJson.optString("familyIncome"));
                    temp = (EditText) findViewById(R.id.lowIncome);
                    temp.append(dataJson.optString("lowIncome"));
                    temp = (EditText) findViewById(R.id.enjoyAllowance);
                    temp.append(dataJson.optString("enjoyAllowance"));
                    temp = (EditText) findViewById(R.id.enjoyResidual);
                    temp.append(dataJson.optString("enjoyResidual"));
                    temp = (EditText) findViewById(R.id.medicalExpenses);
                    temp.append(dataJson.optString("medicalExpenses"));
                    temp = (EditText) findViewById(R.id.eRelationship1);
                    temp.append(dataJson.optString("eRelationship1"));
                    temp = (EditText) findViewById(R.id.eName1);
                    temp.append(dataJson.optString("eName1"));
                    temp = (EditText) findViewById(R.id.eSex1);
                    temp.append(dataJson.optString("eSex1"));
                    temp = (EditText) findViewById(R.id.eIdentityCard1);
                    temp.append(dataJson.optString("eIdentityCard1"));
                    temp = (EditText) findViewById(R.id.eEducationLevels1);
                    temp.append(dataJson.optString("eEducationLevels1"));
                    temp = (EditText) findViewById(R.id.eIncome1);
                    temp.append(dataJson.optString("eIncome1"));
                    temp = (EditText) findViewById(R.id.eExpenses1);
                    temp.append(dataJson.optString("eExpenses1"));
                    temp = (EditText) findViewById(R.id.eRelationship2);
                    temp.append(dataJson.optString("eRelationship2"));
                    temp = (EditText) findViewById(R.id.eName2);
                    temp.append(dataJson.optString("eName2"));
                    temp = (EditText) findViewById(R.id.eSex2);
                    temp.append(dataJson.optString("eSex2"));
                    temp = (EditText) findViewById(R.id.eIdentityCard2);
                    temp.append(dataJson.optString("eIdentityCard2"));
                    temp = (EditText) findViewById(R.id.eEducationLevels2);
                    temp.append(dataJson.optString("eEducationLevels2"));
                    temp = (EditText) findViewById(R.id.eIncome2);
                    temp.append(dataJson.optString("eIncome2"));
                    temp = (EditText) findViewById(R.id.eExpenses2);
                    temp.append(dataJson.optString("eExpenses2"));
                    temp = (EditText) findViewById(R.id.eRelationship3);
                    temp.append(dataJson.optString("eRelationship3"));
                    temp = (EditText) findViewById(R.id.eName3);
                    temp.append(dataJson.optString("eName3"));
                    temp = (EditText) findViewById(R.id.eSex3);
                    temp.append(dataJson.optString("eSex3"));
                    temp = (EditText) findViewById(R.id.eIdentityCard3);
                    temp.append(dataJson.optString("eIdentityCard3"));
                    temp = (EditText) findViewById(R.id.eEducationLevels3);
                    temp.append(dataJson.optString("eEducationLevels3"));
                    temp = (EditText) findViewById(R.id.eIncome3);
                    temp.append(dataJson.optString("eIncome3"));
                    temp = (EditText) findViewById(R.id.eExpenses3);
                    temp.append(dataJson.optString("eExpenses3"));
                    temp = (EditText) findViewById(R.id.eRelationship4);
                    temp.append(dataJson.optString("eRelationship4"));
                    temp = (EditText) findViewById(R.id.eName4);
                    temp.append(dataJson.optString("eName4"));
                    temp = (EditText) findViewById(R.id.eSex4);
                    temp.append(dataJson.optString("eSex4"));
                    temp = (EditText) findViewById(R.id.eIdentityCard4);
                    temp.append(dataJson.optString("eIdentityCard4"));
                    temp = (EditText) findViewById(R.id.eEducationLevels4);
                    temp.append(dataJson.optString("eEducationLevels4"));
                    temp = (EditText) findViewById(R.id.eIncome4);
                    temp.append(dataJson.optString("eIncome4"));
                    temp = (EditText) findViewById(R.id.eExpenses4);
                    temp.append(dataJson.optString("eExpenses4"));
                    temp = (EditText) findViewById(R.id.eRelationship5);
                    temp.append(dataJson.optString("eRelationship5"));
                    temp = (EditText) findViewById(R.id.eName5);
                    temp.append(dataJson.optString("eName5"));
                    temp = (EditText) findViewById(R.id.eSex5);
                    temp.append(dataJson.optString("eSex5"));
                    temp = (EditText) findViewById(R.id.eIdentityCard5);
                    temp.append(dataJson.optString("eIdentityCard5"));
                    temp = (EditText) findViewById(R.id.eEducationLevels5);
                    temp.append(dataJson.optString("eEducationLevels5"));
                    temp = (EditText) findViewById(R.id.eIncome5);
                    temp.append(dataJson.optString("eIncome5"));
                    temp = (EditText) findViewById(R.id.eExpenses5);
                    temp.append(dataJson.optString("eExpenses5"));
                    temp = (EditText) findViewById(R.id.eRelationship6);
                    temp.append(dataJson.optString("eRelationship6"));
                    temp = (EditText) findViewById(R.id.eName6);
                    temp.append(dataJson.optString("eName6"));
                    temp = (EditText) findViewById(R.id.eSex6);
                    temp.append(dataJson.optString("eSex6"));
                    temp = (EditText) findViewById(R.id.eIdentityCard6);
                    temp.append(dataJson.optString("eIdentityCard6"));
                    temp = (EditText) findViewById(R.id.eEducationLevels6);
                    temp.append(dataJson.optString("eEducationLevels6"));
                    temp = (EditText) findViewById(R.id.eIncome6);
                    temp.append(dataJson.optString("eIncome6"));
                    temp = (EditText) findViewById(R.id.eExpenses6);
                    temp.append(dataJson.optString("eExpenses6"));
                    temp = (EditText) findViewById(R.id.eRelationship7);
                    temp.append(dataJson.optString("eRelationship7"));
                    temp = (EditText) findViewById(R.id.eName7);
                    temp.append(dataJson.optString("eName7"));
                    temp = (EditText) findViewById(R.id.eSex7);
                    temp.append(dataJson.optString("eSex7"));
                    temp = (EditText) findViewById(R.id.eIdentityCard7);
                    temp.append(dataJson.optString("eIdentityCard7"));
                    temp = (EditText) findViewById(R.id.eEducationLevels7);
                    temp.append(dataJson.optString("eEducationLevels7"));
                    temp = (EditText) findViewById(R.id.eIncome7);
                    temp.append(dataJson.optString("eIncome7"));
                    temp = (EditText) findViewById(R.id.eExpenses7);
                    temp.append(dataJson.optString("eExpenses7"));
                    temp = (EditText) findViewById(R.id.eRelationship8);
                    temp.append(dataJson.optString("eRelationship8"));
                    temp = (EditText) findViewById(R.id.eName8);
                    temp.append(dataJson.optString("eName8"));
                    temp = (EditText) findViewById(R.id.eSex8);
                    temp.append(dataJson.optString("eSex8"));
                    temp = (EditText) findViewById(R.id.eIdentityCard8);
                    temp.append(dataJson.optString("eIdentityCard8"));
                    temp = (EditText) findViewById(R.id.eEducationLevels8);
                    temp.append(dataJson.optString("eEducationLevels8"));
                    temp = (EditText) findViewById(R.id.eIncome8);
                    temp.append(dataJson.optString("eIncome8"));
                    temp = (EditText) findViewById(R.id.eExpenses8);
                    temp.append(dataJson.optString("eExpenses8"));
                    temp = (EditText) findViewById(R.id.eRelationship9);
                    temp.append(dataJson.optString("eRelationship9"));
                    temp = (EditText) findViewById(R.id.eName9);
                    temp.append(dataJson.optString("eName9"));
                    temp = (EditText) findViewById(R.id.eSex9);
                    temp.append(dataJson.optString("eSex9"));
                    temp = (EditText) findViewById(R.id.eIdentityCard9);
                    temp.append(dataJson.optString("eIdentityCard9"));
                    temp = (EditText) findViewById(R.id.eEducationLevels9);
                    temp.append(dataJson.optString("eEducationLevels9"));
                    temp = (EditText) findViewById(R.id.eIncome9);
                    temp.append(dataJson.optString("eIncome9"));
                    temp = (EditText) findViewById(R.id.eExpenses9);
                    temp.append(dataJson.optString("eExpenses9"));
                    temp = (EditText) findViewById(R.id.eRelationship10);
                    temp.append(dataJson.optString("eRelationship10"));
                    temp = (EditText) findViewById(R.id.eName10);
                    temp.append(dataJson.optString("eName10"));
                    temp = (EditText) findViewById(R.id.eSex10);
                    temp.append(dataJson.optString("eSex10"));
                    temp = (EditText) findViewById(R.id.eIdentityCard10);
                    temp.append(dataJson.optString("eIdentityCard10"));
                    temp = (EditText) findViewById(R.id.eEducationLevels10);
                    temp.append(dataJson.optString("eEducationLevels10"));
                    temp = (EditText) findViewById(R.id.eIncome10);
                    temp.append(dataJson.optString("eIncome10"));
                    temp = (EditText) findViewById(R.id.eExpenses10);
                    temp.append(dataJson.optString("eExpenses10"));
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(PoorInfoEditActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
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
}
