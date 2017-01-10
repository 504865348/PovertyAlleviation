package com.example.jayny.povertyalleviation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PoorInfoActivity extends AppCompatActivity {

    private ListTask listTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poor_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(Constant.usertype.equals("1")){
            toolbar.setTitle(Constant.pname+"的信息");
        }else{
            toolbar.setTitle("精准扶贫-"+getIntent().getStringExtra("name")+"详细信息");
        }
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        listTask = new ListTask();
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
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
            if(Constant.usertype.equals("1")){
                temp.put("aid", Constant.userid);
            }else if(Constant.usertype.equals("2")){
                if(getIntent().getStringExtra("status2").equals("0")){
                    temp.put("pid", Constant.aid);
                }else{
                    temp.put("aid", Constant.aid);
                }
            }else if(Constant.usertype.equals("3")){
                temp.put("pid", Constant.aid);
            }else{
                temp.put("aid", Constant.aid);
            }
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorInfo", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);
            if ( msg.equals("error")) {
                Toast.makeText(PoorInfoActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    if (!msg.equals("")) {
                        JSONObject dataJson = new JSONObject(msg);
                        TextView temp = (TextView) findViewById(R.id.name);
                        temp.append(dataJson.getString("name"));
                        temp = (TextView) findViewById(R.id.identityCard);
                        temp.append(dataJson.getString("identityCard"));
                        temp = (TextView) findViewById(R.id.sex);
                        temp.append(dataJson.getString("sex"));
                        temp = (TextView) findViewById(R.id.homeAddress);
                        temp.append(dataJson.getString("homeAddress"));
                        temp = (TextView) findViewById(R.id.permanentAddress);
                        temp.append(dataJson.getString("permanentAddress"));
                        temp = (TextView) findViewById(R.id.educationLevels);
                        temp.append(dataJson.getString("educationLevels"));
                        temp = (TextView) findViewById(R.id.familyPopulation);
                        temp.append(dataJson.getString("familyPopulation"));
                        temp = (TextView) findViewById(R.id.labourForce);
                        temp.append(dataJson.getString("labourForce"));
                        temp = (TextView) findViewById(R.id.agriculturalNum);
                        temp.append(dataJson.getString("agriculturalNum"));
                        temp = (TextView) findViewById(R.id.familyIncome);
                        temp.append(dataJson.getString("familyIncome"));
                        temp = (TextView) findViewById(R.id.lowIncome);
                        temp.append(dataJson.getString("lowIncome"));
                        temp = (TextView) findViewById(R.id.enjoyAllowance);
                        temp.append(dataJson.getString("enjoyAllowance"));
                        temp = (TextView) findViewById(R.id.enjoyResidual);
                        temp.append(dataJson.getString("enjoyResidual"));
                        temp = (TextView) findViewById(R.id.medicalExpenses);
                        temp.append(dataJson.getString("medicalExpenses"));
                        LinearLayout tempLineLayOut;

                        if(dataJson.getString("eName1").equals("")&&dataJson.getString("eName2").equals("")&&dataJson.getString("eName3").equals("")&&dataJson.getString("eName4").equals("")&&dataJson.getString("eName5").equals("")&&dataJson.getString("eName6").equals("")&&dataJson.getString("eName7").equals("")&&dataJson.getString("eName8").equals("")&&dataJson.getString("eName9").equals("")&&dataJson.getString("eName10").equals("")){
                            View vvv = (View) findViewById(R.id.temp_textview);
                            vvv.setVisibility(View.GONE);
                        }

                        if(dataJson.getString("eName1").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout1);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship1);
                            temp.append(dataJson.getString("eRelationship1"));
                            temp = (TextView) findViewById(R.id.eName1);
                            temp.append(dataJson.getString("eName1"));
                            temp = (TextView) findViewById(R.id.eSex1);
                            temp.append(dataJson.getString("eSex1"));
                            temp = (TextView) findViewById(R.id.eIdentityCard1);
                            temp.append(dataJson.getString("eIdentityCard1"));
                            temp = (TextView) findViewById(R.id.eEducationLevels1);
                            temp.append(dataJson.getString("eEducationLevels1"));
                            temp = (TextView) findViewById(R.id.eIncome1);
                            temp.append(dataJson.getString("eIncome1"));
                            temp = (TextView) findViewById(R.id.eExpenses1);
                            temp.append(dataJson.getString("eExpenses1"));
                        }

                        if(dataJson.getString("eName2").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout2);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship2);
                            temp.append(dataJson.getString("eRelationship2"));
                            temp = (TextView) findViewById(R.id.eName2);
                            temp.append(dataJson.getString("eName2"));
                            temp = (TextView) findViewById(R.id.eSex2);
                            temp.append(dataJson.getString("eSex2"));
                            temp = (TextView) findViewById(R.id.eIdentityCard2);
                            temp.append(dataJson.getString("eIdentityCard2"));
                            temp = (TextView) findViewById(R.id.eEducationLevels2);
                            temp.append(dataJson.getString("eEducationLevels2"));
                            temp = (TextView) findViewById(R.id.eIncome2);
                            temp.append(dataJson.getString("eIncome2"));
                            temp = (TextView) findViewById(R.id.eExpenses2);
                            temp.append(dataJson.getString("eExpenses2"));
                        }

                        if(dataJson.getString("eName3").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout3);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship3);
                            temp.append(dataJson.getString("eRelationship3"));
                            temp = (TextView) findViewById(R.id.eName3);
                            temp.append(dataJson.getString("eName3"));
                            temp = (TextView) findViewById(R.id.eSex3);
                            temp.append(dataJson.getString("eSex3"));
                            temp = (TextView) findViewById(R.id.eIdentityCard3);
                            temp.append(dataJson.getString("eIdentityCard3"));
                            temp = (TextView) findViewById(R.id.eEducationLevels3);
                            temp.append(dataJson.getString("eEducationLevels3"));
                            temp = (TextView) findViewById(R.id.eIncome3);
                            temp.append(dataJson.getString("eIncome3"));
                            temp = (TextView) findViewById(R.id.eExpenses3);
                            temp.append(dataJson.getString("eExpenses3"));
                        }

                        if(dataJson.getString("eName4").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout4);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship4);
                            temp.append(dataJson.getString("eRelationship4"));
                            temp = (TextView) findViewById(R.id.eName4);
                            temp.append(dataJson.getString("eName4"));
                            temp = (TextView) findViewById(R.id.eSex4);
                            temp.append(dataJson.getString("eSex4"));
                            temp = (TextView) findViewById(R.id.eIdentityCard4);
                            temp.append(dataJson.getString("eIdentityCard4"));
                            temp = (TextView) findViewById(R.id.eEducationLevels4);
                            temp.append(dataJson.getString("eEducationLevels4"));
                            temp = (TextView) findViewById(R.id.eIncome4);
                            temp.append(dataJson.getString("eIncome4"));
                            temp = (TextView) findViewById(R.id.eExpenses4);
                            temp.append(dataJson.getString("eExpenses4"));
                        }

                        if(dataJson.getString("eName5").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout5);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship5);
                            temp.append(dataJson.getString("eRelationship5"));
                            temp = (TextView) findViewById(R.id.eName5);
                            temp.append(dataJson.getString("eName5"));
                            temp = (TextView) findViewById(R.id.eSex5);
                            temp.append(dataJson.getString("eSex5"));
                            temp = (TextView) findViewById(R.id.eIdentityCard5);
                            temp.append(dataJson.getString("eIdentityCard5"));
                            temp = (TextView) findViewById(R.id.eEducationLevels5);
                            temp.append(dataJson.getString("eEducationLevels5"));
                            temp = (TextView) findViewById(R.id.eIncome5);
                            temp.append(dataJson.getString("eIncome5"));
                            temp = (TextView) findViewById(R.id.eExpenses5);
                            temp.append(dataJson.getString("eExpenses5"));
                        }

                        if(dataJson.getString("eName6").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout6);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship6);
                            temp.append(dataJson.getString("eRelationship6"));
                            temp = (TextView) findViewById(R.id.eName6);
                            temp.append(dataJson.getString("eName6"));
                            temp = (TextView) findViewById(R.id.eSex6);
                            temp.append(dataJson.getString("eSex6"));
                            temp = (TextView) findViewById(R.id.eIdentityCard6);
                            temp.append(dataJson.getString("eIdentityCard6"));
                            temp = (TextView) findViewById(R.id.eEducationLevels6);
                            temp.append(dataJson.getString("eEducationLevels6"));
                            temp = (TextView) findViewById(R.id.eIncome6);
                            temp.append(dataJson.getString("eIncome6"));
                            temp = (TextView) findViewById(R.id.eExpenses6);
                            temp.append(dataJson.getString("eExpenses6"));
                        }

                        if(dataJson.getString("eName7").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout7);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship7);
                            temp.append(dataJson.getString("eRelationship7"));
                            temp = (TextView) findViewById(R.id.eName7);
                            temp.append(dataJson.getString("eName7"));
                            temp = (TextView) findViewById(R.id.eSex7);
                            temp.append(dataJson.getString("eSex7"));
                            temp = (TextView) findViewById(R.id.eIdentityCard7);
                            temp.append(dataJson.getString("eIdentityCard7"));
                            temp = (TextView) findViewById(R.id.eEducationLevels7);
                            temp.append(dataJson.getString("eEducationLevels7"));
                            temp = (TextView) findViewById(R.id.eIncome7);
                            temp.append(dataJson.getString("eIncome7"));
                            temp = (TextView) findViewById(R.id.eExpenses7);
                            temp.append(dataJson.getString("eExpenses7"));
                        }

                        if(dataJson.getString("eName8").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout8);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship8);
                            temp.append(dataJson.getString("eRelationship8"));
                            temp = (TextView) findViewById(R.id.eName8);
                            temp.append(dataJson.getString("eName8"));
                            temp = (TextView) findViewById(R.id.eSex8);
                            temp.append(dataJson.getString("eSex8"));
                            temp = (TextView) findViewById(R.id.eIdentityCard8);
                            temp.append(dataJson.getString("eIdentityCard8"));
                            temp = (TextView) findViewById(R.id.eEducationLevels8);
                            temp.append(dataJson.getString("eEducationLevels8"));
                            temp = (TextView) findViewById(R.id.eIncome8);
                            temp.append(dataJson.getString("eIncome8"));
                            temp = (TextView) findViewById(R.id.eExpenses8);
                            temp.append(dataJson.getString("eExpenses8"));
                        }

                        if(dataJson.getString("eName9").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout9);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship9);
                            temp.append(dataJson.getString("eRelationship9"));
                            temp = (TextView) findViewById(R.id.eName9);
                            temp.append(dataJson.getString("eName9"));
                            temp = (TextView) findViewById(R.id.eSex9);
                            temp.append(dataJson.getString("eSex9"));
                            temp = (TextView) findViewById(R.id.eIdentityCard9);
                            temp.append(dataJson.getString("eIdentityCard9"));
                            temp = (TextView) findViewById(R.id.eEducationLevels9);
                            temp.append(dataJson.getString("eEducationLevels9"));
                            temp = (TextView) findViewById(R.id.eIncome9);
                            temp.append(dataJson.getString("eIncome9"));
                            temp = (TextView) findViewById(R.id.eExpenses9);
                            temp.append(dataJson.getString("eExpenses9"));
                        }

                        if(dataJson.getString("eName10").equals("")){
                            tempLineLayOut = (LinearLayout) findViewById(R.id.temp_linelayout10);
                            tempLineLayOut.setVisibility(View.GONE);
                        }else{
                            temp = (TextView) findViewById(R.id.eRelationship10);
                            temp.append(dataJson.getString("eRelationship10"));
                            temp = (TextView) findViewById(R.id.eName10);
                            temp.append(dataJson.getString("eName10"));
                            temp = (TextView) findViewById(R.id.eSex10);
                            temp.append(dataJson.getString("eSex10"));
                            temp = (TextView) findViewById(R.id.eIdentityCard10);
                            temp.append(dataJson.getString("eIdentityCard10"));
                            temp = (TextView) findViewById(R.id.eEducationLevels10);
                            temp.append(dataJson.getString("eEducationLevels10"));
                            temp = (TextView) findViewById(R.id.eIncome10);
                            temp.append(dataJson.getString("eIncome10"));
                            temp = (TextView) findViewById(R.id.eExpenses10);
                            temp.append(dataJson.getString("eExpenses10"));
                        }

                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(PoorInfoActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }
}
