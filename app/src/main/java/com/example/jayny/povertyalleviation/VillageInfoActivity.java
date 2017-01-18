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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VillageInfoActivity extends AppCompatActivity {

    private ListTask listTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        listTask = new ListTask(getIntent().getStringExtra("areaid"));
        listTask.executeOnExecutor(Executor.exec);
        }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ListTask extends AsyncTask<Void, Void, String> {
        private String areaid;
        ListTask(String areaid) {
            this.areaid = areaid;
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
            temp.put("areaid",areaid);
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findSysVillageDetailByAreaId", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);
            if (msg.equals("error")) {
                Toast.makeText(VillageInfoActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    if (!msg.equals("")) {
                        JSONObject dataJson = new JSONObject(msg);
                        TextView temp = (TextView) findViewById(R.id.registeredPopulation);
                        temp.append(dataJson.getString("registeredPopulation"));
                        temp = (TextView) findViewById(R.id.residentPopulation);
                        temp.append(dataJson.getString("residentPopulation"));
                        temp = (TextView) findViewById(R.id.participatoryPopulation);
                        temp.append(dataJson.getString("participatoryPopulation"));
                        temp = (TextView) findViewById(R.id.totalHouseholds);
                        temp.append(dataJson.getString("totalHouseholds"));
                        temp = (TextView) findViewById(R.id.filingHouseholds);
                        temp.append(dataJson.getString("filingHouseholds"));
                        temp = (TextView) findViewById(R.id.groupsNumber);
                        temp.append(dataJson.getString("groupsNumber"));
                        temp = (TextView) findViewById(R.id.naturalVillages);
                        temp.append(dataJson.getString("naturalVillages"));
                        temp = (TextView) findViewById(R.id.developmentVillages);
                        temp.append(dataJson.getString("developmentVillages"));
                        temp = (TextView) findViewById(R.id.generalVillages);
                        temp.append(dataJson.getString("generalVillages"));
                        temp = (TextView) findViewById(R.id.labourForce);
                        temp.append(dataJson.getString("labourForce"));
                        temp = (TextView) findViewById(R.id.migrantWorkers);
                        temp.append(dataJson.getString("migrantWorkers"));
                        temp = (TextView) findViewById(R.id.disposableIncome);
                        temp.append(dataJson.getString("disposableIncome"));
                        temp = (TextView) findViewById(R.id.villageCadres);
                        temp.append(dataJson.getString("villageCadres"));
                        temp = (TextView) findViewById(R.id.partyMembers);
                        temp.append(dataJson.getString("partyMembers"));
                        temp = (TextView) findViewById(R.id.partyBranches);
                        temp.append(dataJson.getString("partyBranches"));
                        temp = (TextView) findViewById(R.id.villageOfficial);
                        temp.append(dataJson.getString("villageOfficial"));
                        temp = (TextView) findViewById(R.id.activityRoom);
                        temp.append(dataJson.getString("activityRoom"));
                        temp = (TextView) findViewById(R.id.roadPavement);
                        temp.append(dataJson.getString("roadPavement"));
                        temp = (TextView) findViewById(R.id.isPassthbus);
                        temp.append(dataJson.getString("isPassthbus"));
                        temp = (TextView) findViewById(R.id.cooperativeMedical);
                        temp.append(dataJson.getString("cooperativeMedical"));
                        temp = (TextView) findViewById(R.id.endowmentInsurance);
                        temp.append(dataJson.getString("endowmentInsurance"));
                        temp = (TextView) findViewById(R.id.lowestGuarantee);
                        temp.append(dataJson.getString("lowestGuarantee"));
                        temp = (TextView) findViewById(R.id.fiveGuarantees);
                        temp.append(dataJson.getString("fiveGuarantees"));
                        temp = (TextView) findViewById(R.id.administrativeCenter);
                        temp.append(dataJson.getString("administrativeCenter"));
                        temp = (TextView) findViewById(R.id.practicingDoctor);
                        temp.append(dataJson.getString("practicingDoctor"));
                        temp = (TextView) findViewById(R.id.industrialEnterprise);
                        temp.append(dataJson.getString("industrialEnterprise"));
                        temp = (TextView) findViewById(R.id.serviceEnterprises);
                        temp.append(dataJson.getString("serviceEnterprises"));
                        temp = (TextView) findViewById(R.id.onlineStore);
                        temp.append(dataJson.getString("onlineStore"));
                        temp = (TextView) findViewById(R.id.commercialService);
                        temp.append(dataJson.getString("commercialService"));
                        temp = (TextView) findViewById(R.id.agriculturalAcreage);
                        temp.append(dataJson.getString("agriculturalAcreage"));
                        temp = (TextView) findViewById(R.id.landTransfer);
                        temp.append(dataJson.getString("landTransfer"));
                        temp = (TextView) findViewById(R.id.familyFarm);
                        temp.append(dataJson.getString("familyFarm"));
                        temp = (TextView) findViewById(R.id.farmersCooperative);
                        temp.append(dataJson.getString("farmersCooperative"));
                        temp = (TextView) findViewById(R.id.efficiencyAgriculture);
                        temp.append(dataJson.getString("efficiencyAgriculture"));
                        temp = (TextView) findViewById(R.id.collectiveIncome);
                        temp.append(dataJson.getString("collectiveIncome"));
                        temp = (TextView) findViewById(R.id.mainSource);
                        temp.append(dataJson.getString("mainSource"));
                        temp = (TextView) findViewById(R.id.generalAssets);
                        temp.append(dataJson.getString("generalAssets"));
                        temp = (TextView) findViewById(R.id.mainComposition);
                        temp.append(dataJson.getString("mainComposition"));
                        temp = (TextView) findViewById(R.id.liabilitiesAmount);
                        temp.append(dataJson.getString("liabilitiesAmount"));
                        temp = (TextView) findViewById(R.id.partyConstruction);
                        temp.append(dataJson.getString("partyConstruction"));
                        temp = (TextView) findViewById(R.id.economicDevelopment);
                        temp.append(dataJson.getString("economicDevelopment"));
                        temp = (TextView) findViewById(R.id.publicService);
                        temp.append(dataJson.getString("publicService"));
                        temp = (TextView) findViewById(R.id.employment);
                        temp.append(dataJson.getString("employment"));
                        temp = (TextView) findViewById(R.id.others);
                        temp.append(dataJson.getString("others"));
                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(VillageInfoActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
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
