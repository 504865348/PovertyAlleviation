package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of SetTwelveList. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SetTwelveListDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class SetTwelveListListActivity extends AppCompatActivity {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    Map<String, String> map = null;
    private ListTask listTask;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settwelvelist_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(getIntent().getStringExtra("as_type").equals("13")){
            toolbar.setTitle(getIntent().getStringExtra("as"));
        }else{
            toolbar.setTitle("结对访亲");
        }
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (Constant.usertype.equals("1")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString("as_type", getIntent().getStringExtra("as_type"));
                        arguments.putString("as", getIntent().getStringExtra("as"));
                        SetTwelveListDetailFragment fragment = new SetTwelveListDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.settwelvelist_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(SetTwelveListListActivity.this, SetTwelveListDetailActivity.class);
                        intent.putExtra("as_type", getIntent().getStringExtra("as_type"));
                        intent.putExtra("as", getIntent().getStringExtra("as"));
                        SetTwelveListListActivity.this.startActivity(intent);
                    }
                }
            });
        } else if(Constant.usertype.equals("3")&&getIntent().getStringExtra("status3").equals("1")){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString("as_type", getIntent().getStringExtra("as_type"));
                        arguments.putString("as", getIntent().getStringExtra("as"));
                        arguments.putString("status3", null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                        arguments.putString("status2", null==getIntent().getStringExtra("status2")?"0":getIntent().getStringExtra("status2"));
                        SetTwelveListDetailFragment fragment = new SetTwelveListDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.settwelvelist_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(SetTwelveListListActivity.this, SetTwelveListDetailActivity.class);
                        intent.putExtra("as_type", getIntent().getStringExtra("as_type"));
                        intent.putExtra("as", getIntent().getStringExtra("as"));
                        intent.putExtra("status3", null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                        intent.putExtra("status2", null==getIntent().getStringExtra("status2")?"0":getIntent().getStringExtra("status2"));
                        SetTwelveListListActivity.this.startActivity(intent);
                    }
                }
            });
        }else {
            fab.setVisibility(View.GONE);
        }

        View recyclerView = findViewById(R.id.settwelvelist_list);
        assert recyclerView != null;
        listTask = new ListTask(recyclerView);
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
        if (findViewById(R.id.settwelvelist_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(list));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Map<String, String>> mValues;

        public SimpleItemRecyclerViewAdapter(List<Map<String, String>> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settwelvelist_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mContentView.setText(mValues.get(position).get("title"));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString("oid", holder.mItem.get("oid"));
                        arguments.putString("pic", holder.mItem.get("pic"));
                        arguments.putString("words", holder.mItem.get("words"));
                        arguments.putString("title", holder.mItem.get("title"));
                        arguments.putString("remarks", holder.mItem.get("remarks"));
                        arguments.putString("as_type", getIntent().getStringExtra("as_type"));
                        arguments.putString("as", getIntent().getStringExtra("as"));
                        arguments.putString("status3", null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                        arguments.putString("status2", null==getIntent().getStringExtra("status2")?"0":getIntent().getStringExtra("status2"));
                        SetTwelveListDetailFragment fragment = new SetTwelveListDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.settwelvelist_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, SetTwelveListDetailActivity.class);
                        intent.putExtra("oid", holder.mItem.get("oid"));
                        intent.putExtra("pic", holder.mItem.get("pic"));
                        intent.putExtra("words", holder.mItem.get("words"));
                        intent.putExtra("title", holder.mItem.get("title"));
                        intent.putExtra("remarks", holder.mItem.get("remarks"));
                        intent.putExtra("as_type", getIntent().getStringExtra("as_type"));
                        intent.putExtra("as", getIntent().getStringExtra("as"));
                        intent.putExtra("status3", null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                        intent.putExtra("status2", null==getIntent().getStringExtra("status2")?"0":getIntent().getStringExtra("status2"));

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public Map<String, String> mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ListTask extends AsyncTask<Void, Void, String> {

        private final View view;

        ListTask(View view) {
            this.view = view;
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
            Map<String, String> map = new HashMap<String, String>();
            if (Constant.usertype.equals("1")) {
                map.put("aid", Constant.userid);
            } else if(Constant.usertype.equals("2")){
                if(getIntent().getStringExtra("status2").equals("0")){
                    map.put("pid", Constant.aid);
                }else{
                    map.put("aid", Constant.aid);
                    map.put("aid", Constant.aid);
                }
            } else if(Constant.usertype.equals("3")){
                map.put("pid", Constant.aid);
            } else if(Constant.usertype.equals("5")){
                map.put("pid", Constant.userid);
            } else {
                map.put("aid", Constant.aid);
            }
            map.put("as_type",getIntent().getStringExtra("as_type"));
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "getAssistSetTwelveList", "POST", map);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(SetTwelveListListActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray dataJson = new JSONArray(msg);
                    for (int i = 0; i < dataJson.length(); i++) {
                        JSONObject item = dataJson.getJSONObject(i);
                        String oid = item.optString("id");
                        String pic = item.optString("pic");
                        String words = item.optString("words");
                        String title = item.optString("title");
                        String remarks = item.optString("remarks");
                        map = new HashMap<String, String>();
                        map.put("oid", oid);
                        map.put("pic", pic);
                        map.put("words", words);
                        map.put("title", title);
                        map.put("remarks", remarks);
                        list.add(map);
                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(SetTwelveListListActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
                setupRecyclerView((RecyclerView) view);
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
