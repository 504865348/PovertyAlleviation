package com.example.jayny.povertyalleviation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a single suggestRetrun detail screen.
 * This fragment is either contained in a {@link suggestRetrunListActivity}
 * in two-pane mode (on tablets) or a {@link suggestRetrunDetailActivity}
 * on handsets.
 */
public class suggestRetrunDetailFragment extends Fragment {
    SendContentTask sendContentTask;
    private EditText editText;
    private TextView editText1;
    Button buttonForSolution;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public suggestRetrunDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getArguments().getString("suggestionContent"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.suggestretrun_detail, container, false);

        // Show the dummy content as text in a TextView.
        editText1 = ((TextView) rootView.findViewById(R.id.require_detail));
        editText1.setText(getArguments().getString("suggestionContent"));
        editText = (EditText) rootView.findViewById(R.id.solution);
        buttonForSolution = (Button) rootView.findViewById(R.id.buttonForSolution);
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
        editText.setError(null);
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
            sendContentTask = new SendContentTask(editText.getText().toString());
            sendContentTask.execute();
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SendContentTask extends AsyncTask<Void, Void, String> {
        private String solution;

        SendContentTask(String solution) {
            this.solution = solution;
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
            temp.put("aid", getArguments().getString("aid"));
            temp.put("solution", solution);
            temp.put("suggestionContent", getArguments().getString("suggestionContent"));
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "saveSuggestion", "POST", temp);
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
                        getActivity().navigateUpTo(new Intent(getActivity(), suggestRetrunListActivity.class));
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
