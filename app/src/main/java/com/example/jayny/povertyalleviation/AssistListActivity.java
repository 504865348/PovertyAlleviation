package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class AssistListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist_list);
        View policy_document = findViewById(R.id.policy_document);
        policy_document.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, ItemListActivity.class);
                        startActivity(intent);
                    }
                }
        );
        View p_detail = findViewById(R.id.p_detail);
        p_detail.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Context context = view.getContext();
                       Intent intent = new Intent(context, PoorInfoActivity.class);
                       startActivity(intent);
                   }
              }
        );
        View p_suggest = findViewById(R.id.p_suggest);
        p_suggest.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Context context = view.getContext();
                       Intent intent = new Intent(context, SuggestActivity.class);
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
                                             startActivity(intent);
                                         }
                                     }
        );
        View p_fund = findViewById(R.id.p_fund);
        p_fund.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Context context = view.getContext();
                                             Intent intent = new Intent(context, fundListActivity.class);
                                             startActivity(intent);
                                         }
                                     }
        );
        if(Constant.fundstatus.equals("0")){
            ((LinearLayout) findViewById(R.id.p_fund_l)).setVisibility(View.GONE);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
