package com.example.jayny.povertyalleviation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A fragment representing a single Require detail screen.
 * This fragment is either contained in a {@link RequireListActivity}
 * in two-pane mode (on tablets) or a {@link RequireDetailActivity}
 * on handsets.
 */
public class RequireDetailFragment extends Fragment {
    SendContentTask sendContentTask;
    private EditText editText;
    private EditText editText1;
    Button buttonForSolution;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RequireDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getArguments().getString("poorContent") == null ? "新建" : getArguments().getString("poorContent"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.require_detail, container, false);
        LinearLayout temp = (LinearLayout) rootView.findViewById(R.id.activess);
        temp.setVisibility(View.INVISIBLE);
        // Show the dummy content as text in a TextView.
        editText1 = ((EditText) rootView.findViewById(R.id.require_detail));
        editText1.setText(getArguments().getString("poorContent"));
        editText = (EditText) rootView.findViewById(R.id.solution);
        editText.setText(getArguments().getString("solution"));
        buttonForSolution = (Button) rootView.findViewById(R.id.buttonForSolution);
        if(Constant.usertype.equals("1")){
            editText1.setFocusable(false);
            editText1.setEnabled(false);
        }
        else if (Constant.usertype.equals("3")) {
            editText1.setFocusable(false);
            editText1.setEnabled(false);
            if(null!=getArguments().getString("status3")&&getArguments().getString("status3").equals("0")){
                editText.setFocusable(false);
                editText.setEnabled(false);
                buttonForSolution.setVisibility(View.GONE);
            }
        } else if (Constant.usertype.equals("0")||Constant.usertype.equals("5")) {
            if(null==getArguments().getString("oid")||getArguments().getString("oid").equals("")){
                LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.require_detail_id);
                ll.setVisibility(View.GONE);
            }else{
                editText.setFocusable(false);
                editText.setEnabled(false);
            }
        } else {
            editText.setFocusable(false);
            editText.setEnabled(false);
            editText1.setFocusable(false);
            editText1.setEnabled(false);
            buttonForSolution.setVisibility(View.GONE);
        }
        buttonForSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestSubmit();
            }
        });
        return rootView;
    }

    private void suggestSubmit() {
        boolean cancel = false;
        View focusView = null;
        if (Constant.usertype.equals("1")||Constant.usertype.equals("3")) {
            editText.setError(null);
            if (TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError(getString(R.string.error_p_set));
                focusView = editText;
                cancel = true;
            }
        }else{
            editText1.setError(null);
            if (TextUtils.isEmpty(editText1.getText().toString())) {
                editText1.setError(getString(R.string.error_p_set));
                focusView = editText1;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            sendContentTask = new SendContentTask(editText.getText().toString(),editText1.getText().toString());
            sendContentTask.execute();
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SendContentTask extends AsyncTask<Void, Void, String> {
        private String solution;
        private String poorContent;

        SendContentTask(String solution,String poorContent) {
            this.solution = solution;
            this.poorContent = poorContent;

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
            temp.put("oid", getArguments().getString("oid"));
            temp.put("isChange", getArguments().getString("isChange"));
            temp.put("solution", solution);
            temp.put("poorContent", poorContent);
            if(Constant.usertype.equals("0")||Constant.usertype.equals("5")){
                temp.put("pid", Constant.userid);
            }
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "savePoorRequire", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("") || msg.equals("error")) {
                editText.requestFocus();
                Toast.makeText(getActivity(), getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    if (dataJson.optString("status").equals("ok")) {
                        Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_LONG).show();
                        getActivity().navigateUpTo(new Intent(getActivity(), RequireListActivity.class).putExtra("name",getArguments().getString("name")));
                    } else {
                        Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_LONG).show();
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
