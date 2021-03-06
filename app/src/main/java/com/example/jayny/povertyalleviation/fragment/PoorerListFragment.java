package com.example.jayny.povertyalleviation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jayny.povertyalleviation.Constant;
import com.example.jayny.povertyalleviation.MyUtils;
import com.example.jayny.povertyalleviation.PoorerListActivity;
import com.example.jayny.povertyalleviation.R;
import com.example.jayny.povertyalleviation.sort.CharacterParser;
import com.example.jayny.povertyalleviation.sort.ClearEditText;
import com.example.jayny.povertyalleviation.sort.PoorerListAdapter;
import com.example.jayny.povertyalleviation.sort.VillageListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoorerListFragment extends Fragment {
    List<Map<String, String>> list = new ArrayList<>();
    Map<String, String> map = null;
    private PoorerListAdapter mAdapter;
    private CharacterParser mParser;
    private Comparator<Map<String, String>> mComparator;


    public PoorerListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poorer_list, container, false);
        ListView recyclerView = (ListView) view.findViewById(R.id.poorer_list);
        assert recyclerView != null;
        ListTask listTask = new ListTask(recyclerView);
        listTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
        initSearchView(view);
        return view;
    }

    private void initSearchView(View view) {
        ClearEditText mClearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        List<Map<String,String>> filterDateList = new ArrayList<>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList = list;
        }else{
            filterDateList.clear();
            for(Map<String,String> sortModel : list){
                String name = sortModel.get("name");
                if(name.contains(filterStr) || mParser.getSelling(name).startsWith(filterStr)){
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, mComparator);
        mAdapter.updateListView(filterDateList);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void setupRecyclerView(@NonNull ListView recyclerView) {
        mAdapter = new PoorerListAdapter(getActivity(),list);
        recyclerView.setAdapter(mAdapter);
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
                map.put("areaid", getActivity().getIntent().getStringExtra("areaid"));
                result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "findPoorerByAreaId", "POST", map);
            } else if (Constant.usertype.equals("3")) {
                map.put("manageid", Constant.userid);
                if(null!=getActivity().getIntent().getStringExtra("status3")&&getActivity().getIntent().getStringExtra("status3").equals("1")){
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
                Toast.makeText(getActivity(), getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray dataJson = new JSONArray(msg);
                    for (int i = 0; i < dataJson.length(); i++) {
                        map = new HashMap<>();
                        JSONObject item = dataJson.getJSONObject(i);
                        try{
                            map.put("aid", item.optString("aid"));
                            map.put("status2", "1");
                        }catch (Exception es){
                            map.put("aid", item.optString("id"));
                            map.put("status2", "0");
                        }
                        String name = item.optString("name");
                        map.put("name", name);
                        String poorType = item.optString("poorType");
                        if(poorType.equals("0")){
                            map.put("poorType", "五保户");
                        }else if(poorType.equals("1")){
                            map.put("poorType", "低保户");
                        }else if(poorType.equals("2")){
                            map.put("poorType", "残疾户");
                        }else if(poorType.equals("3")){
                            map.put("poorType", "一般低收入户");
                        }else{
                            map.put("poorType", "其他");
                        }
                        //汉字转换成拼音
                        mParser = new CharacterParser();
                        String pinyin = mParser.getSelling(name);
                        String sortString = pinyin.substring(0, 1).toUpperCase();
                        // 正则表达式，判断首字母是否是英文字母
                        if(sortString.matches("[A-Z]")){
                            map.put("sort",sortString.toUpperCase());
                        }else{
                            map.put("sort","#");
                        }
                        list.add(map);
                    }
                    mComparator = new Comparator<Map<String, String>>() {
                        @Override
                        public int compare(Map<String, String> o1, Map<String, String> o2) {
                            if (o1.get("sort").equals("@")
                                    || o2.get("sort").equals("#")) {
                                return -1;
                            } else if (o1.get("sort").equals("#")
                                    || o2.get("sort").equals("@")) {
                                return 1;
                            } else {
                                return o1.get("sort").compareTo(o2.get("sort"));
                            }
                        }
                    };
                    Collections.sort(list, mComparator);
                } catch (Exception e) {
                    Log.e("getJosn:", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
                setupRecyclerView((ListView) view);
            }
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }

}
