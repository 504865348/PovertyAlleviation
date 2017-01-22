package com.example.jayny.povertyalleviation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A fragment representing a single fund detail screen.
 * This fragment is either contained in a {@link fundListActivity}
 * in two-pane mode (on tablets) or a {@link fundDetailActivity}
 * on handsets.
 */
public class fundDetailFragment extends Fragment {
    SendContentTask sendContentTask;
    getNameAndIdTask getNameAndIdTask;
    private SearchView mSearchView;
    private Button button;
    private ListView mListView;
    private TextView mTextView;
    private EditText editText;
    private EditText editText1;
    private String[] strs1;
    private String[] strs2;
    private ArrayAdapter arrayAdapter;
    private String tempid="";

    public fundDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null && null != getArguments().getString("oid") && !getArguments().getString("oid").equals("")) {
            appBarLayout.setTitle(getArguments().getString("title"));
        } else {
            appBarLayout.setTitle("新建");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fund_detail, container, false);
        LinearLayout lll = (LinearLayout) rootView.findViewById(R.id.activess);
        lll.setVisibility(View.INVISIBLE);
        editText1 = ((EditText) rootView.findViewById(R.id.require_detail));
        editText1.setText(getArguments().getString("title"));
        editText = (EditText) rootView.findViewById(R.id.solution);
        editText.setText(getArguments().getString("content"));
        button = (Button) rootView.findViewById(R.id.buttonForSolution);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestSubmit();
            }
        });
        mTextView = (TextView) rootView.findViewById(R.id.tv);

        mListView = (ListView) rootView.findViewById(R.id.lv);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tempid = strs1[position];
                int sid = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
                EditText  textView = (EditText ) mSearchView.findViewById(sid);
                textView.setText(strs2[position]);
                mListView.setVisibility(View.GONE);
            }
        });
        mSearchView = (SearchView) rootView.findViewById(R.id.sv);
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    mListView.setVisibility(View.VISIBLE);
                    String reg = "[a-zA-Z]+";
                    if(!newText.matches(reg)){
                        getNameAndIdTask = new getNameAndIdTask(newText);
                        getNameAndIdTask.execute();
                    }
                } else {
                    mListView.setVisibility(View.GONE);
                    mListView.clearTextFilter();
                    tempid="";
                }
                return false;
            }
        });
        if (!Constant.usertype.equals("4")) {
            button.setVisibility(View.GONE);
            mTextView.append(getArguments().getString("name"));
            mTextView.setVisibility(View.VISIBLE);
            editText1.setFocusable(false);
            editText1.setEnabled(false);
            editText.setFocusable(false);
            editText.setEnabled(false);
            mSearchView.setVisibility(View.GONE);
        } else {
            if (null != getArguments().getString("oid") && !getArguments().getString("oid").equals("")) {
                button.setVisibility(View.GONE);
                mSearchView.setVisibility(View.GONE);
                mTextView.append(getArguments().getString("name"));
                mTextView.setVisibility(View.VISIBLE);
                editText1.setFocusable(false);
                editText1.setEnabled(false);
                editText.setFocusable(false);
                editText.setEnabled(false);
            }
        }

        return rootView;
    }

    private void suggestSubmit() {
        boolean cancel = false;
        View focusView = null;
        editText1.setError(null);
        if (TextUtils.isEmpty(editText1.getText().toString())) {
            editText1.setError(getString(R.string.error_p_set));
            focusView = editText1;
            cancel = true;
        }
        editText.setError(null);
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(getString(R.string.error_p_set));
            focusView = editText;
            cancel = true;
        }
        if (TextUtils.isEmpty(tempid)) {
            Toast.makeText(getActivity(), getString(R.string.error_p_set), Toast.LENGTH_LONG).show();
            focusView = mSearchView;
            cancel = true;
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
        private String title;
        private String content;

        SendContentTask(String title, String content) {
            this.title = title;
            this.content = content;

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
            temp.put("title", title);
            temp.put("content", content);
            temp.put("helpingId", Constant.userid);
            temp.put("pid", tempid);
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "savejijin", "POST", temp);
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
                        getActivity().navigateUpTo(new Intent(getActivity(), fundListActivity.class));
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class getNameAndIdTask extends AsyncTask<Void, Void, String> {
        private String str;

        getNameAndIdTask(String str) {
            this.str = str;

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
            temp.put("str", str);
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "getNameAndId", "POST", temp);
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
                    JSONArray dataJson = new JSONArray(msg);
                    strs1 = new String[dataJson.length()];
                    strs2 = new String[dataJson.length()];
                    for (int i = 0; i < dataJson.length(); i++) {
                        JSONObject item = dataJson.getJSONObject(i);
                        strs1[i]=item.optString("id");
                        strs2[i]=item.optString("name");
                    }
                    arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs2);
                    mListView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                    // 如果没有设置数据适配器，则ListView没有子项，返回。
                    ListAdapter listAdapter = mListView.getAdapter();
                    int totalHeight = 0;
                    if (listAdapter == null) {
                        return;
                    }
                    for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
                        View listViewItem = listAdapter.getView(index, null, mListView);
                        // 计算子项View 的宽高
                        listViewItem.measure(0, 0);
                        // 计算所有子项的高度和
                        totalHeight += listViewItem.getMeasuredHeight();
                    }

                    ViewGroup.LayoutParams params = mListView.getLayoutParams();
                    // listView.getDividerHeight()获取子项间分隔符的高度
                    // params.height设置ListView完全显示需要的高度
                    params.height = totalHeight + (mListView.getDividerHeight() * (listAdapter.getCount() - 1));
                    mListView.setLayoutParams(params);
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
