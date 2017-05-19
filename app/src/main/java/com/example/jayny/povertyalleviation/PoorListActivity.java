package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PoorListActivity extends AppCompatActivity {
    private ListTask listTask;
    private String aUnit;
    private String name;
    private String tmpUrl;
    private ImageView lllSrc;
    private Handler pic_hdl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_poorerlistdetail);
        lllSrc = (ImageView) findViewById(R.id.lllSrc);
        View policy_document = findViewById(R.id.policy_document);
        policy_document.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, ItemListActivity.class);
                        intent.putExtra("getDocType","0");
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
        View new_info = findViewById(R.id.new_info);
        new_info.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Context context = view.getContext();
                                            Intent intent = new Intent(context, ItemListActivity.class);
                                            intent.putExtra("getDocType","1");
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
        View p_detail = findViewById(R.id.p_detail);
        p_detail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Context context = view.getContext();
                                            Intent intent = new Intent(context, PoorInfoActivity.class);
                                            intent.putExtra("aUnit",aUnit);
                                            intent.putExtra("name",name);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }
        );
        if(Constant.fundstatus.equals("1")){
            p_fund.setVisibility(View.VISIBLE);
        }
        listTask = new ListTask();
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
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
                if (getIntent().getStringExtra("status2").equals("0")) {
                    temp.put("pid", Constant.aid);
                } else {
                    temp.put("aid", Constant.aid);
                }
            } else if (Constant.usertype.equals("3")) {
                temp.put("pid", Constant.aid);
            } else {
                temp.put("aid", Constant.aid);
            }
            return MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findFrontPoorInfo", "POST", temp);
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);
            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(PoorListActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    TextView temp = (TextView) findViewById(R.id.name);
                    temp.append(dataJson.optString("name"));
                    temp = (TextView) findViewById(R.id.birthday);
                    if(dataJson.optString("identityCard").length()>14){
                        temp.append(dataJson.optString("identityCard").substring(6,14));
                    }
                    temp = (TextView) findViewById(R.id.homeAddress);
                    temp.append(dataJson.optString("homeAddress"));
                    temp = (TextView) findViewById(R.id.aUnit);
                    temp.append(dataJson.optString("aUnit"));
                    aUnit = dataJson.optString("aUnit");
                    name = dataJson.optString("name");
                    tmpUrl = dataJson.optString("photo");
                    if(tmpUrl!=null&&!tmpUrl.equals("")){
                        pic_hdl = new PicHandler();
                        Thread t = new LoadPicThread();
                        t.start();
                    }
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(PoorListActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }

    class LoadPicThread extends Thread {
        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            Bitmap img = getUrlImage(getResources().getString(R.string.host_port) + tmpUrl);
            if(img!=null) {
                Message msg = pic_hdl.obtainMessage();
                msg.what = 0;
                msg.obj = img;
                pic_hdl.sendMessage(msg);
            }
        }

        //加载图片
        public Bitmap getUrlImage(String url) {
            Bitmap img = null;
            try {
                URL picurl = new URL(url);
                // 获得连接
                HttpURLConnection conn = (HttpURLConnection) picurl.openConnection();
                conn.setDoInput(true);
                conn.setUseCaches(false);//不缓存
                conn.connect();
                InputStream is = conn.getInputStream();//获得图片的数据流
                img = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return img;
        }
    }

    class PicHandler extends Handler {

        PicHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            lllSrc.setImageBitmap((Bitmap) msg.obj);
        }

    }
}
