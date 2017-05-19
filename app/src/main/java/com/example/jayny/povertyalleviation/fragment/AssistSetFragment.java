package com.example.jayny.povertyalleviation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayny.povertyalleviation.AssistSetActivity;
import com.example.jayny.povertyalleviation.AssistSetPhotoActivity;
import com.example.jayny.povertyalleviation.Constant;
import com.example.jayny.povertyalleviation.Executor;
import com.example.jayny.povertyalleviation.ItemDetailActivity;
import com.example.jayny.povertyalleviation.ItemDetailFragment;
import com.example.jayny.povertyalleviation.MyUtils;
import com.example.jayny.povertyalleviation.R;
import com.example.jayny.povertyalleviation.SetTwelveListListActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AssistSetFragment extends Fragment {
    private ListStatusTask listStatusTask;
    private View mView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.new_activity_assistset_list, container, false);
        View as_1 = mView.findViewById(R.id.as_1);
        as_1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "水");
                                        intent.putExtra("as_type", "1");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_2 = mView.findViewById(R.id.as_2);
        as_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "电");
                                        intent.putExtra("as_type", "2");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_3 = mView.findViewById(R.id.as_3);
        as_3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "气");
                                        intent.putExtra("as_type", "3");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_4 = mView.findViewById(R.id.as_4);
        as_4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "有线电视");
                                        intent.putExtra("as_type", "4");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_5 = mView.findViewById(R.id.as_5);
        as_5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "基本生活保障");
                                        intent.putExtra("as_type", "5");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_6 = mView.findViewById(R.id.as_6);
        as_6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "基本生活家具");
                                        intent.putExtra("as_type", "6");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_7 = mView.findViewById(R.id.as_7);
        as_7.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "基本生活电器");
                                        intent.putExtra("as_type", "7");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_8 = mView.findViewById(R.id.as_8);
        as_8.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "基本厨卫设施");
                                        intent.putExtra("as_type", "8");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_9 = mView.findViewById(R.id.as_9);
        as_9.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                        intent.putExtra("as", "结对认亲");
                                        intent.putExtra("as_type", "9");
                                        intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                        intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
        );
        View as_10 = mView.findViewById(R.id.as_10);
        as_10.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Context context = view.getContext();
                                         Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                         intent.putExtra("as", "结对帮亲");
                                         intent.putExtra("as_type", "10");
                                         intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                         intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                         intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         startActivity(intent);
                                     }
                                 }
        );
        View as_11 = mView.findViewById(R.id.as_11);
        as_11.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Context context = view.getContext();
                                         Intent intent = new Intent(context, AssistSetPhotoActivity.class);
                                         intent.putExtra("as", "结对暖亲");
                                         intent.putExtra("as_type", "11");
                                         intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                         intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                         intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         startActivity(intent);
                                     }
                                 }
        );
        View as_12 = mView.findViewById(R.id.as_12);
        as_12.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Context context = view.getContext();
                                         Intent intent = new Intent(context, SetTwelveListListActivity.class);
                                         intent.putExtra("as", "结对访亲");
                                         intent.putExtra("as_type", "12");
                                         intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                         intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                         intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         startActivity(intent);
                                     }
                                 }
        );

        View as_13 = mView.findViewById(R.id.as_13);
        as_13.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Context context = view.getContext();
                                         Intent intent = new Intent(context, SetTwelveListListActivity.class);
                                         intent.putExtra("as", "自定义事件");
                                         intent.putExtra("as_type", "13");
                                         intent.putExtra("status3", null==getActivity().getIntent().getStringExtra("status3")?"0":getActivity().getIntent().getStringExtra("status3"));
                                         intent.putExtra("status2", null==getActivity().getIntent().getStringExtra("status2")?"0":getActivity().getIntent().getStringExtra("status2"));
                                         intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         startActivity(intent);
                                     }
                                 }
        );
        listStatusTask = new ListStatusTask();
        listStatusTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ListStatusTask extends AsyncTask<Void, Void, String> {

        ListStatusTask() {
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
                if (null!=getActivity().getIntent().getStringExtra("status2")&&getActivity().getIntent().getStringExtra("status2").equals("0")) {
                    temp.put("pid", Constant.aid);
                } else {
                    temp.put("aid", Constant.aid);
                }
            } else if (Constant.usertype.equals("5")) {
                temp.put("pid", Constant.userid);
            } else if (Constant.usertype.equals("3")) {
                temp.put("pid", Constant.aid);
            } else {
                temp.put("aid", Constant.aid);
            }
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "getAssistSetStatus", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {
            //showProgress(false);

            if (msg.equals("error")) {
                Toast.makeText(getActivity(), getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    if (!msg.equals("")) {
                        int color = getResources().getColor(R.color.font_green);
                        JSONObject dataJson = new JSONObject(msg);
                        ImageView temp = (ImageView) mView.findViewById(R.id.as_1_status);
                        if (dataJson.optString("1").equals("1")) {
                            temp.setImageResource(R.mipmap.tongdian);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_2_status);
                        if (dataJson.optString("2").equals("1")) {
                            temp.setImageResource(R.mipmap.tongshui);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_3_status);
                        if (dataJson.optString("3").equals("1")) {
                            temp.setImageResource(R.mipmap.tongqi);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_4_status);
                        if (dataJson.optString("4").equals("1")) {
                            temp.setImageResource(R.mipmap.youxian);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_5_status);
                        if (dataJson.optString("5").equals("1")) {
                            temp.setImageResource(R.mipmap.shenghuo);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_6_status);
                        if (dataJson.optString("6").equals("1")) {
                            temp.setImageResource(R.mipmap.jiaju);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_7_status);
                        if (dataJson.optString("7").equals("1")) {
                            temp.setImageResource(R.mipmap.chuwei);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_8_status);
                        if (dataJson.optString("8").equals("1")) {
                            temp.setImageResource(R.mipmap.jiadian);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_9_status);
                        if (dataJson.optString("9").equals("1")) {
                            temp.setImageResource(R.mipmap.renqin);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_10_status);
                        if (dataJson.optString("10").equals("1")) {
                            temp.setImageResource(R.mipmap.nuanqin);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_11_status);
                        if (dataJson.optString("11").equals("1")) {
                            temp.setImageResource(R.mipmap.fangqin);
                        }
                        temp = (ImageView) mView.findViewById(R.id.as_12_status);
                        if (dataJson.optString("12").equals("1")) {
                            temp.setImageResource(R.mipmap.bangqin);
                        }
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
