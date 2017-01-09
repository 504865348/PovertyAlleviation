package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class PoorerListDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poorerlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        View p_detail = findViewById(R.id.p_detail);
        p_detail.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Context context = view.getContext();
                       Intent intent = new Intent(context, PoorInfoActivity.class);
                       intent.putExtra("name",getIntent().getStringExtra("name"));
                       intent.putExtra("status3",null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                       intent.putExtra("status2",null==getIntent().getStringExtra("status2")?"0":getIntent().getStringExtra("status2"));
                       intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }
              }
        );
        View assist_set = findViewById(R.id.assist_set);
        assist_set.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Context context = view.getContext();
                                             Intent intent = new Intent(context, AssistSetActivity.class);
                                             intent.putExtra("status3",null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                                             intent.putExtra("status2",null==getIntent().getStringExtra("status2")?"0":getIntent().getStringExtra("status2"));
                                             intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             startActivity(intent);
                                         }
                                     }
        );

        View p_need = findViewById(R.id.p_need);
        p_need.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Context context = view.getContext();
                                             Intent intent = new Intent(context, RequireListActivity.class);
                                             intent.putExtra("name",getIntent().getStringExtra("name"));
                                             intent.putExtra("status3",null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3"));
                                             intent.putExtra("status2",null==getIntent().getStringExtra("status2")?"0":getIntent().getStringExtra("status2"));
                                             intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             startActivity(intent);
                                         }
                                     }
        );
    }
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
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
