package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
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
 * An activity representing a list of fund. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link fundDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class fundListActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_fund_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("基金发放明细");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    fundDetailFragment fragment = new fundDetailFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fund_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(fundListActivity.this, fundDetailActivity.class);
                    fundListActivity.this.startActivity(intent);
                }
            }
        });

        if(!Constant.usertype.equals("4")){
            fab.setVisibility(View.GONE);
        }
        View recyclerView = findViewById(R.id.fund_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        listTask = new ListTask(recyclerView);
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
        if (findViewById(R.id.fund_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
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
                    .inflate(R.layout.fund_list_content, parent, false);
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
                        arguments.putString("title", holder.mItem.get("title"));
                        arguments.putString("content", holder.mItem.get("content"));
                        arguments.putString("name", holder.mItem.get("name"));
                        fundDetailFragment fragment = new fundDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fund_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, fundDetailActivity.class);
                        intent.putExtra("oid", holder.mItem.get("oid"));
                        intent.putExtra("title", holder.mItem.get("title"));
                        intent.putExtra("content", holder.mItem.get("content"));
                        intent.putExtra("name", holder.mItem.get("name"));
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
            if(Constant.usertype.equals("4")){
                map.put("HelpingId",Constant.userid);
                String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findUsedRecordByHelpingId", "POST", map);
                return result;
            }else if(Constant.usertype.equals("1")){
                map.put("aid",Constant.userid);
                String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findUsedRecordByPid", "POST", map);
                return result;
            }else if(Constant.usertype.equals("0")||Constant.usertype.equals("5")){
                map.put("pid",Constant.userid);
                String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findUsedRecordByPid", "POST", map);
                return result;
            }else{
                return "";
            }

        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(fundListActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray dataJson = new JSONArray(msg);
                    for (int i = 0; i < dataJson.length(); i++) {
                        JSONObject item = dataJson.getJSONObject(i);
                        String oid = item.getString("id");
                        String title = item.getString("title");
                        String content = item.getString("content");
                        String name = item.getString("name");
                        map = new HashMap<String, String>();
                        map.put("oid", oid);
                        map.put("title", title);
                        map.put("content", content);
                        map.put("name", name);
                        list.add(map);
                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(fundListActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
