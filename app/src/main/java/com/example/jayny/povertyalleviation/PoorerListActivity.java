package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Poorer. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PoorerListActivity extends AppCompatActivity {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    Map<String, String> map = null;
    private ListTask listTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poorer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(Constant.usertype.equals("2")){
            toolbar.setTitle("精准扶贫-"+getIntent().getStringExtra("areaname")+"贫困成员");
        }else if(Constant.usertype.equals("5")){
            toolbar.setTitle("未挂钩帮扶对象列表");
        }else{
            toolbar.setTitle("帮扶对象列表");
        }
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        View recyclerView = findViewById(R.id.poorer_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        listTask = new ListTask(recyclerView);
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
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
                    .inflate(R.layout.poorer_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(String.valueOf(position));
            holder.mContentView.setText(mValues.get(position).get("name"));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PoorerListDetailActivity.class);
                    intent.putExtra("name",holder.mContentView.getText());
                    intent.putExtra("status3",null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                    intent.putExtra("status2", holder.mItem.get("status2"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Constant.aid = holder.mItem.get("aid");
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final LinearLayout mPoorList;
            public Map<String, String> mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mPoorList= (LinearLayout) view.findViewById(R.id.ll_poor_list);
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
            String result = "";
            Map<String, String> map = new HashMap<String, String>();
            if (Constant.usertype.equals("2")) {
                map.put("areaid", getIntent().getStringExtra("areaid"));
                result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorerByAreaId", "POST", map);
            } else if (Constant.usertype.equals("3")) {
                map.put("manageid", Constant.userid);
                if(null!=getIntent().getStringExtra("status3")&&getIntent().getStringExtra("status3").equals("1")){
                    result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorerByStatus3", "POST", map);
                }else{
                    result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorerByManageId", "POST", map);
                }
            }else{
                map.put("manageid", Constant.userid);
                result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorByElseId", "POST", map);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(PoorerListActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray dataJson = new JSONArray(msg);
                    for (int i = 0; i < dataJson.length(); i++) {
                        map = new HashMap<String, String>();
                        JSONObject item = dataJson.getJSONObject(i);
                        try{
                            map.put("aid", item.getString("aid"));
                            map.put("status2", "1");
                        }catch (Exception es){
                            map.put("aid", item.getString("id"));
                            map.put("status2", "0");
                        }
                        String name = item.getString("name");
                        map.put("name", name);
                        list.add(map);
                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(PoorerListActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
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
