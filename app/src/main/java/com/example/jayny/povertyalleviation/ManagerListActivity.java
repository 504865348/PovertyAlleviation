package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_manager_list);
        View policy_document1 = findViewById(R.id.manager_poor1);
        if(Constant.usertype.equals("2")){

        }else if(Constant.usertype.equals("3")){
            policy_document1.setBackgroundColor(Color.WHITE);
        }else if(Constant.usertype.equals("4")){

        }
        policy_document1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Context context = view.getContext();
                                                    Intent intent;
                                                    intent = new Intent(context, PoorerListActivity.class);
                                                    intent.putExtra("status3", "1");
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }
                                            }
        );
        if (Constant.usertype.equals("2")) {
            TextView tv = (TextView) findViewById(R.id.titleId);
            ImageView iv = (ImageView) findViewById(R.id.titleSrc);
            tv.setText("乡村机构");
            iv.setImageResource(R.mipmap.xtgl_icon);
        } else if (Constant.usertype.equals("3")) {
            TextView tv = (TextView) findViewById(R.id.titleId1);
            tv.setText("未挂钩帮扶对象");
            ((LinearLayout) findViewById(R.id.manager_poor1)).setVisibility(View.VISIBLE);
        }
        View policy_document = findViewById(R.id.manager_poor);
        policy_document.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Context context = view.getContext();
                                                   Intent intent;
                                                   if (Constant.usertype.equals("2")) {
                                                       intent = new Intent(context, VillageListActivity.class);
                                                       startActivity(intent);
                                                   } else {
                                                       intent = new Intent(context, PoorerListActivity.class);
                                                       intent.putExtra("status3", "0");
                                                       startActivity(intent);
                                                   }
                                               }
                                           }
        );
        View p_detail = findViewById(R.id.manager_suggset);
        if(Constant.usertype.equals("2")){

        }else if(Constant.usertype.equals("3")){
            p_detail.setBackgroundColor(Color.WHITE);
        }else if(Constant.usertype.equals("4")){
            p_detail.setBackgroundColor(Color.WHITE);
        }
        p_detail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Context context = view.getContext();
                                            Intent intent = new Intent(context, suggestRetrunListActivity.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        View p_assist = findViewById(R.id.manager_assist);
        if(Constant.usertype.equals("2")){
            p_assist.setBackgroundColor(Color.WHITE);
        }else if(Constant.usertype.equals("3")){
        }else if(Constant.usertype.equals("4")){
        }
        p_assist.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Context context = view.getContext();
                                            Intent intent = new Intent(context, UnitListActivity.class);
                                            startActivity(intent);
                                        }
                                    }
        );

        View ttt = findViewById(R.id.ttt);
        ttt.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Context context = view.getContext();
                                       Intent intent = new Intent(context, fundListActivity.class);
                                       startActivity(intent);
                                   }
                               }
        );
        if (Constant.usertype.equals("4")) {
            policy_document.setVisibility(View.GONE);
        } else {
            ttt.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
    }
    private long mPressedTime = 0;

    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            System.exit(0);
        }
    }
}
