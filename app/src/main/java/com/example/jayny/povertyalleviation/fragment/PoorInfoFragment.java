package com.example.jayny.povertyalleviation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayny.povertyalleviation.Constant;
import com.example.jayny.povertyalleviation.Executor;
import com.example.jayny.povertyalleviation.ItemDetailActivity;
import com.example.jayny.povertyalleviation.ItemDetailFragment;
import com.example.jayny.povertyalleviation.MyUtils;
import com.example.jayny.povertyalleviation.PoorInfoActivity;
import com.example.jayny.povertyalleviation.PoorInfoEditActivity;
import com.example.jayny.povertyalleviation.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PoorInfoFragment extends Fragment {
    private ListTask listTask;
    private View mView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_poor_info, container, false);

        listTask = new ListTask();
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
        TextView tt3 = (TextView) mView.findViewById(R.id.aUnit);
        String sss = getActivity().getIntent().getStringExtra("aUnit");
        tt3.setText(sss);
        Button b = (Button) mView.findViewById(R.id.editButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, PoorInfoEditActivity.class);
                if (Constant.usertype.equals("2")) {
                    if (getActivity().getIntent().getStringExtra("status2").equals("0")) {
                        intent.putExtra("pid",Constant.aid);
                    } else {
                        intent.putExtra("aid",Constant.aid);
                    }
                    intent.putExtra("status2",getActivity().getIntent().getStringExtra("status2"));
                    intent.putExtra("status3",getActivity().getIntent().getStringExtra("status3"));
                } else if (Constant.usertype.equals("3")) {
                    intent.putExtra("pid",Constant.aid);
                    intent.putExtra("status2",getActivity().getIntent().getStringExtra("status2"));
                    intent.putExtra("status3",getActivity().getIntent().getStringExtra("status3"));
                }
                intent.putExtra("name",getActivity().getIntent().getStringExtra("name"));

                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//        if(Constant.usertype.equals("2")||Constant.usertype.equals("3")){
//            b.setVisibility(View.VISIBLE);
//        }
        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
                if (getActivity().getIntent().getStringExtra("status2").equals("0")) {
                    temp.put("pid", Constant.aid);
                } else {
                    temp.put("aid", Constant.aid);
                }
            } else if (Constant.usertype.equals("3")) {
                temp.put("pid", Constant.aid);
            } else {
                temp.put("aid", Constant.aid);
            }
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorInfo", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);
            if (msg.equals("error")) {
                Toast.makeText(getActivity(), getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    if (!msg.equals("")) {
                        JSONObject dataJson = new JSONObject(msg);
                        TextView temp = (TextView) mView.findViewById(R.id.name);
                        temp.append(dataJson.optString("name"));
                        temp = (TextView) mView.findViewById(R.id.birthday);
                        if(dataJson.optString("identityCard").length()>14) {
                            temp.append(dataJson.optString("identityCard").substring(6, 14));
                        }
                        temp = (TextView) mView.findViewById(R.id.identityCard);
                        temp.append(dataJson.optString("identityCard"));
                        temp = (TextView) mView.findViewById(R.id.sex);
                        temp.append(dataJson.optString("sex"));
                        temp = (TextView) mView.findViewById(R.id.homeAddress);
                        temp.append(dataJson.optString("homeAddress"));
                        temp = (TextView) mView.findViewById(R.id.permanentAddress);
                        temp.append(dataJson.optString("permanentAddress"));
                        temp = (TextView) mView.findViewById(R.id.educationLevels);
                        temp.append(dataJson.optString("educationLevels"));
                        temp = (TextView) mView.findViewById(R.id.familyPopulation);
                        temp.append(dataJson.optString("familyPopulation"));
                        temp = (TextView) mView.findViewById(R.id.labourForce);
                        temp.append(dataJson.optString("labourForce"));
                        temp = (TextView) mView.findViewById(R.id.agriculturalNum);
                        temp.append(dataJson.optString("agriculturalNum"));
                        temp = (TextView) mView.findViewById(R.id.familyIncome);
                        temp.append(dataJson.optString("familyIncome"));
                        temp = (TextView) mView.findViewById(R.id.lowIncome);
                        temp.append(dataJson.optString("lowIncome"));
                        temp = (TextView) mView.findViewById(R.id.enjoyAllowance);
                        temp.append(dataJson.optString("enjoyAllowance"));
                        temp = (TextView) mView.findViewById(R.id.enjoyResidual);
                        temp.append(dataJson.optString("enjoyResidual"));
                        temp = (TextView) mView.findViewById(R.id.medicalExpenses);
                        temp.append(dataJson.optString("medicalExpenses"));
                        temp = (TextView) mView.findViewById(R.id.agriculturalArea);
                        temp.append(dataJson.optString("agriculturalArea"));
                        temp = (TextView) mView.findViewById(R.id.aquacultureArea);
                        temp.append(dataJson.optString("aquacultureArea"));
                        temp = (TextView) mView.findViewById(R.id.landArea);
                        temp.append(dataJson.optString("landArea"));
                        temp = (TextView) mView.findViewById(R.id.landRent);
                        temp.append(dataJson.optString("landRent"));
                        temp = (TextView) mView.findViewById(R.id.housingType);
                        temp.append(dataJson.optString("housingType"));
                        temp = (TextView) mView.findViewById(R.id.housingArea);
                        temp.append(dataJson.optString("housingArea"));
                        temp = (TextView) mView.findViewById(R.id.familyYear);
                        temp.append(dataJson.optString("familyYear"));
                        temp = (TextView) mView.findViewById(R.id.wageIncome);
                        temp.append(dataJson.optString("wageIncome"));
                        temp = (TextView) mView.findViewById(R.id.operatingIncome);
                        temp.append(dataJson.optString("operatingIncome"));
                        temp = (TextView) mView.findViewById(R.id.propertyIncome);
                        temp.append(dataJson.optString("propertyIncome"));
                        temp = (TextView) mView.findViewById(R.id.transferIncome);
                        temp.append(dataJson.optString("transferIncome"));
                        temp = (TextView) mView.findViewById(R.id.poorCauses);
                        temp.append(dataJson.optString("poorCauses"));
                        temp = (TextView) mView.findViewById(R.id.poorNeed);
                        temp.append(dataJson.optString("poorNeed"));
                        temp = (TextView) mView.findViewById(R.id.poorSuggest);
                        temp.append(dataJson.optString("poorSuggest"));
                        temp = (TextView) mView.findViewById(R.id.poorType);
                        temp.append(dataJson.optString("poorType"));
                        LinearLayout tempLineLayOut;

                        if (dataJson.optString("eName1").equals("") && dataJson.optString("eName2").equals("") && dataJson.optString("eName3").equals("") && dataJson.optString("eName4").equals("") /*&& dataJson.optString("eName5").equals("") && dataJson.optString("eName6").equals("") && dataJson.optString("eName7").equals("") && dataJson.optString("eName8").equals("") && dataJson.optString("eName9").equals("") && dataJson.optString("eName10").equals("")*/) {
                            View vvv = (View) mView.findViewById(R.id.temp_textview);
                            vvv.setVisibility(View.GONE);
                        }

                        if (dataJson.optString("eName1").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout1);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship1);
                            temp.append(dataJson.optString("eRelationship1"));
                            temp = (TextView) mView.findViewById(R.id.eName1);
                            temp.append(dataJson.optString("eName1"));
                            temp = (TextView) mView.findViewById(R.id.eSex1);
                            temp.append(dataJson.optString("eSex1"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard1);
                            temp.append(dataJson.optString("eIdentityCard1"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels1);
                            temp.append(dataJson.optString("eEducationLevels1"));
                            temp = (TextView) mView.findViewById(R.id.eIncome1);
                            temp.append(dataJson.optString("eIncome1"));
                            temp = (TextView) mView.findViewById(R.id.eHealth1);
                            temp.append(dataJson.optString("eHealth1"));
                            temp = (TextView) mView.findViewById(R.id.eJob1);
                            temp.append(dataJson.optString("eJob1"));
                            temp = (TextView) mView.findViewById(R.id.eWorkspace1);
                            temp.append(dataJson.optString("eWorkspace1"));
                            temp = (TextView) mView.findViewById(R.id.eSplitting1);
                            temp.append(dataJson.optString("eSplitting1"));
                        }

                        if (dataJson.optString("eName2").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout2);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship2);
                            temp.append(dataJson.optString("eRelationship2"));
                            temp = (TextView) mView.findViewById(R.id.eName2);
                            temp.append(dataJson.optString("eName2"));
                            temp = (TextView) mView.findViewById(R.id.eSex2);
                            temp.append(dataJson.optString("eSex2"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard2);
                            temp.append(dataJson.optString("eIdentityCard2"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels2);
                            temp.append(dataJson.optString("eEducationLevels2"));
                            temp = (TextView) mView.findViewById(R.id.eIncome2);
                            temp.append(dataJson.optString("eIncome2"));
                            temp = (TextView) mView.findViewById(R.id.eHealth2);
                            temp.append(dataJson.optString("eHealth2"));
                            temp = (TextView) mView.findViewById(R.id.eJob2);
                            temp.append(dataJson.optString("eJob2"));
                            temp = (TextView) mView.findViewById(R.id.eWorkspace2);
                            temp.append(dataJson.optString("eWorkspace2"));
                            temp = (TextView) mView.findViewById(R.id.eSplitting2);
                            temp.append(dataJson.optString("eSplitting2"));
                        }

                        if (dataJson.optString("eName3").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout3);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship3);
                            temp.append(dataJson.optString("eRelationship3"));
                            temp = (TextView) mView.findViewById(R.id.eName3);
                            temp.append(dataJson.optString("eName3"));
                            temp = (TextView) mView.findViewById(R.id.eSex3);
                            temp.append(dataJson.optString("eSex3"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard3);
                            temp.append(dataJson.optString("eIdentityCard3"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels3);
                            temp.append(dataJson.optString("eEducationLevels3"));
                            temp = (TextView) mView.findViewById(R.id.eIncome3);
                            temp.append(dataJson.optString("eIncome3"));
                            temp = (TextView) mView.findViewById(R.id.eHealth3);
                            temp.append(dataJson.optString("eHealth3"));
                            temp = (TextView) mView.findViewById(R.id.eJob3);
                            temp.append(dataJson.optString("eJob3"));
                            temp = (TextView) mView.findViewById(R.id.eWorkspace3);
                            temp.append(dataJson.optString("eWorkspace3"));
                            temp = (TextView) mView.findViewById(R.id.eSplitting3);
                            temp.append(dataJson.optString("eSplitting3"));
                        }

                        if (dataJson.optString("eName4").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout4);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship4);
                            temp.append(dataJson.optString("eRelationship4"));
                            temp = (TextView) mView.findViewById(R.id.eName4);
                            temp.append(dataJson.optString("eName4"));
                            temp = (TextView) mView.findViewById(R.id.eSex4);
                            temp.append(dataJson.optString("eSex4"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard4);
                            temp.append(dataJson.optString("eIdentityCard4"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels4);
                            temp.append(dataJson.optString("eEducationLevels4"));
                            temp = (TextView) mView.findViewById(R.id.eIncome4);
                            temp.append(dataJson.optString("eIncome4"));
                            temp = (TextView) mView.findViewById(R.id.eHealth4);
                            temp.append(dataJson.optString("eHealth4"));
                            temp = (TextView) mView.findViewById(R.id.eJob4);
                            temp.append(dataJson.optString("eJob4"));
                            temp = (TextView) mView.findViewById(R.id.eWorkspace4);
                            temp.append(dataJson.optString("eWorkspace4"));
                            temp = (TextView) mView.findViewById(R.id.eSplitting4);
                            temp.append(dataJson.optString("eSplitting4"));
                        }
/*
                        if (dataJson.optString("eName5").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout5);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship5);
                            temp.append(dataJson.optString("eRelationship5"));
                            temp = (TextView) mView.findViewById(R.id.eName5);
                            temp.append(dataJson.optString("eName5"));
                            temp = (TextView) mView.findViewById(R.id.eSex5);
                            temp.append(dataJson.optString("eSex5"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard5);
                            temp.append(dataJson.optString("eIdentityCard5"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels5);
                            temp.append(dataJson.optString("eEducationLevels5"));
                            temp = (TextView) mView.findViewById(R.id.eIncome5);
                            temp.append(dataJson.optString("eIncome5"));
                            temp = (TextView) mView.findViewById(R.id.eExpenses5);
                            temp.append(dataJson.optString("eExpenses5"));
                        }

                        if (dataJson.optString("eName6").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout6);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship6);
                            temp.append(dataJson.optString("eRelationship6"));
                            temp = (TextView) mView.findViewById(R.id.eName6);
                            temp.append(dataJson.optString("eName6"));
                            temp = (TextView) mView.findViewById(R.id.eSex6);
                            temp.append(dataJson.optString("eSex6"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard6);
                            temp.append(dataJson.optString("eIdentityCard6"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels6);
                            temp.append(dataJson.optString("eEducationLevels6"));
                            temp = (TextView) mView.findViewById(R.id.eIncome6);
                            temp.append(dataJson.optString("eIncome6"));
                            temp = (TextView) mView.findViewById(R.id.eExpenses6);
                            temp.append(dataJson.optString("eExpenses6"));
                        }

                        if (dataJson.optString("eName7").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout7);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship7);
                            temp.append(dataJson.optString("eRelationship7"));
                            temp = (TextView) mView.findViewById(R.id.eName7);
                            temp.append(dataJson.optString("eName7"));
                            temp = (TextView) mView.findViewById(R.id.eSex7);
                            temp.append(dataJson.optString("eSex7"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard7);
                            temp.append(dataJson.optString("eIdentityCard7"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels7);
                            temp.append(dataJson.optString("eEducationLevels7"));
                            temp = (TextView) mView.findViewById(R.id.eIncome7);
                            temp.append(dataJson.optString("eIncome7"));
                            temp = (TextView) mView.findViewById(R.id.eExpenses7);
                            temp.append(dataJson.optString("eExpenses7"));
                        }

                        if (dataJson.optString("eName8").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout8);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship8);
                            temp.append(dataJson.optString("eRelationship8"));
                            temp = (TextView) mView.findViewById(R.id.eName8);
                            temp.append(dataJson.optString("eName8"));
                            temp = (TextView) mView.findViewById(R.id.eSex8);
                            temp.append(dataJson.optString("eSex8"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard8);
                            temp.append(dataJson.optString("eIdentityCard8"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels8);
                            temp.append(dataJson.optString("eEducationLevels8"));
                            temp = (TextView) mView.findViewById(R.id.eIncome8);
                            temp.append(dataJson.optString("eIncome8"));
                            temp = (TextView) mView.findViewById(R.id.eExpenses8);
                            temp.append(dataJson.optString("eExpenses8"));
                        }

                        if (dataJson.optString("eName9").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout9);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship9);
                            temp.append(dataJson.optString("eRelationship9"));
                            temp = (TextView) mView.findViewById(R.id.eName9);
                            temp.append(dataJson.optString("eName9"));
                            temp = (TextView) mView.findViewById(R.id.eSex9);
                            temp.append(dataJson.optString("eSex9"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard9);
                            temp.append(dataJson.optString("eIdentityCard9"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels9);
                            temp.append(dataJson.optString("eEducationLevels9"));
                            temp = (TextView) mView.findViewById(R.id.eIncome9);
                            temp.append(dataJson.optString("eIncome9"));
                            temp = (TextView) mView.findViewById(R.id.eExpenses9);
                            temp.append(dataJson.optString("eExpenses9"));
                        }

                        if (dataJson.optString("eName10").equals("")) {
                            tempLineLayOut = (LinearLayout) mView.findViewById(R.id.temp_linelayout10);
                            tempLineLayOut.setVisibility(View.GONE);
                        } else {
                            temp = (TextView) mView.findViewById(R.id.eRelationship10);
                            temp.append(dataJson.optString("eRelationship10"));
                            temp = (TextView) mView.findViewById(R.id.eName10);
                            temp.append(dataJson.optString("eName10"));
                            temp = (TextView) mView.findViewById(R.id.eSex10);
                            temp.append(dataJson.optString("eSex10"));
                            temp = (TextView) mView.findViewById(R.id.eIdentityCard10);
                            temp.append(dataJson.optString("eIdentityCard10"));
                            temp = (TextView) mView.findViewById(R.id.eEducationLevels10);
                            temp.append(dataJson.optString("eEducationLevels10"));
                            temp = (TextView) mView.findViewById(R.id.eIncome10);
                            temp.append(dataJson.optString("eIncome10"));
                            temp = (TextView) mView.findViewById(R.id.eExpenses10);
                            temp.append(dataJson.optString("eExpenses10"));
                        }*/

                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }

}
