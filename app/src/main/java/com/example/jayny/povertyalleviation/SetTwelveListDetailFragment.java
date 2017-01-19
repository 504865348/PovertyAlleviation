package com.example.jayny.povertyalleviation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A fragment representing a single SetTwelveList detail screen.
 * This fragment is either contained in a {@link SetTwelveListListActivity}
 * in two-pane mode (on tablets) or a {@link SetTwelveListDetailActivity}
 * on handsets.
 */
public class SetTwelveListDetailFragment extends Fragment {
    private GridView gridView1;              //网格显示缩略图
    private final int IMAGE_OPEN = 1;        //打开图片标记
    private String pathImage;                //选择图片路径
    private Bitmap bmp;                      //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器
    private Handler pic_hdl;
    private String tmpUrl;
    private String pi;
    private Intent intent;
    private EditText editText1;
    private EditText editText2;
    Button buttonFor12;
    private SendSetTask sendSetTask;
    private ProgressDialog pd;
    public SetTwelveListDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            if (null != getArguments().getString("oid") && !getArguments().getString("oid").equals("")) {
                appBarLayout.setTitle(getArguments().getString("title"));
            } else {
                appBarLayout.setTitle("新建");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settwelvelist_detail, container, false);
        ((TextView) rootView.findViewById(R.id.settwelvelist_detail)).setText(getArguments().getString("words"));
        //获取控件对象
        gridView1 = (GridView) rootView.findViewById(R.id.gridView1);
        /*
         * 载入默认图片添加图片加号
         * 通过适配器实现
         * SimpleAdapter参数imageItem为数据源 R.layout.griditem_addpic为布局
         */
        imageItem = new ArrayList<HashMap<String, Object>>();
        if (Constant.usertype.equals("1")) {
            //获取资源图片加号
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", bmp);
            imageItem.add(map);
        }else if(Constant.usertype.equals("3")&&getArguments().getString("status3").equals("1")){
            //获取资源图片加号
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", bmp);
            imageItem.add(map);
        }
        tmpUrl = getArguments().getString("pic");
        pi = getArguments().getString("remarks");
        if (null != tmpUrl && !tmpUrl.equals("")) {
            pic_hdl = new PicHandler();
            Thread t = new LoadPicThread();
            t.start();
        } else {
            simpleAdapter = new SimpleAdapter(getActivity(),
                    imageItem, R.layout.griditem_addpic,
                    new String[]{"itemImage"}, new int[]{R.id.imageView1});
        /*
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
         * map.put("itemImage", R.drawable.img);
         * 解决方法:
         *              1.自定义继承BaseAdapter实现
         *              2.ViewBinder()接口实现
         *  参考 http://blog.csdn.net/admin_/article/details/7257901
         */
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
        }
        if (Constant.usertype.equals("1")) {
            //设置事件监听(长按)
            gridView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    if (position != 0) {
                        dialog(position);
                        return true;
                    } else {
                        return false;
                    }
                }
            });

        }else if(Constant.usertype.equals("3")&&getArguments().getString("status3").equals("1")){
            //设置事件监听(长按)
            gridView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    if (position != 0) {
                        dialog(position);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        //设置事件监听(短按)
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if (Constant.usertype.equals("1")) {
                    if (position != 0) {
                        intent = new Intent(getActivity(), PhotoZoomActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bitmap tmp_ = (Bitmap) imageItem.get(1).get("itemImage");
                        BitmapCoast.setBitmap(tmp_);
                        startActivity(intent);
                    } else {
                        if (imageItem.size() == 2) {
                            Toast.makeText(getActivity(), "图片数已满", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, IMAGE_OPEN);
                            //通过onResume()刷新数据
                        }
                    }
                }else if(Constant.usertype.equals("3")&&getArguments().getString("status3").equals("1")){
                    if (position != 0) {
                        intent = new Intent(getActivity(), PhotoZoomActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bitmap tmp_ = (Bitmap) imageItem.get(1).get("itemImage");
                        BitmapCoast.setBitmap(tmp_);
                        startActivity(intent);
                    } else {
                        if (imageItem.size() == 2) {
                            Toast.makeText(getActivity(), "图片数已满", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, IMAGE_OPEN);
                            //通过onResume()刷新数据
                        }
                    }
                } else {
                    intent = new Intent(getActivity(), PhotoZoomActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bitmap tmp_ = (Bitmap) imageItem.get(0).get("itemImage");
                    BitmapCoast.setBitmap(tmp_);
                    startActivity(intent);
                }
            }
        });
        editText1 = (EditText) rootView.findViewById(R.id.settwelvelist_tilte);
        editText1.setText(getArguments().getString("title"));
        editText2 = (EditText) rootView.findViewById(R.id.settwelvelist_detail);
        editText2.setText(getArguments().getString("words"));
        buttonFor12 = (Button) rootView.findViewById(R.id.buttonFor12);
        if (Constant.usertype.equals("1")) {
            buttonFor12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSubmit();
                }
            });
        }else if(Constant.usertype.equals("3")&&getArguments().getString("status3").equals("1")){
            buttonFor12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSubmit();
                }
            });
        } else {
            editText1.setFocusable(false);
            editText1.setEnabled(false);
            editText2.setFocusable(false);
            editText2.setEnabled(false);
            buttonFor12.setVisibility(View.GONE);
        }
        return rootView;
    }

    private void setSubmit() {
        editText1.setError(null);
        editText2.setError(null);
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(editText2.getText().toString())) {
            editText2.setError(getString(R.string.error_p_set));
            focusView = editText2;
            cancel = true;
        }
        if (TextUtils.isEmpty(editText1.getText().toString())) {
            editText1.setError(getString(R.string.error_p_set));
            focusView = editText1;
            cancel = true;
        }
        if (imageItem.size() < 2) {
            editText1.setError("请上传图片");
            focusView = editText1;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            sendSetTask = new SendSetTask(imageItem.get(1).get("pathImage").toString());
            sendSetTask.execute();
        }
    }

    //刷新图片
    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            map.put("pathImage", pathImage);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(getActivity(),
                    imageItem, R.layout.griditem_addpic,
                    new String[]{"itemImage"}, new int[]{R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }

    /*
 * Dialog对话框提示用户删除操作
 * position为删除图片位置
 */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    class LoadPicThread extends Thread {
        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            Bitmap img = getUrlImage(getResources().getString(R.string.host_port_server_pic) + tmpUrl);
            Message msg = pic_hdl.obtainMessage();
            msg.what = 0;
            msg.obj = img;
            pic_hdl.sendMessage(msg);
        }
    }

    class PicHandler extends Handler {

        PicHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Bitmap myimg = (Bitmap) msg.obj;
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", myimg);
            map.put("pathImage", pi);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(getActivity(),
                    imageItem, R.layout.griditem_addpic,
                    new String[]{"itemImage"}, new int[]{R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }

    }

    //获取图片路径 响应startActivityForResult
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if (resultCode == getActivity().RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getActivity().getContentResolver().query(
                        uri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }  //end if 打开图片
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SendSetTask extends AsyncTask<Void, Void, String> {

        String pathImage;

        SendSetTask(String pathImage) {
            this.pathImage = pathImage;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMessage("正在上传，请稍后...");
            pd.setCancelable(false);
            pd.setMax(100);
            pd.incrementProgressBy(20);
            pd.show();
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
            return uploadFile(new File(pathImage));
        }

        @Override
        protected void onPostExecute(String msg) {
            pd.dismiss();
            if (msg.equals("") || msg.equals("error")) {
                Toast.makeText(getActivity(), getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    if (dataJson.getString("status").equals("ok")) {
                        Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_LONG).show();
                        getActivity().navigateUpTo(new Intent(getActivity(), SetTwelveListListActivity.class).putExtra("as",getArguments().getString("as")).putExtra("as_type",getArguments().getString("as_type")).putExtra("status3",null==getArguments().getString("status3")?"0":getArguments().getString("status3")));
                    } else {
                        Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), getString(R.string.error_local), Toast.LENGTH_LONG).show();
                }
            }
        }

/*        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }*/
    }

    /**
     * 上传文件到服务器
     *
     * @param file 需要上传的文件
     * @return 返回响应的内容
     */
    public String uploadFile(File file) {
        String RequestURL = getResources().getString(R.string.host_port_server) + "saveOrUpdateAssistSet";
        int res = 0;
        String BOUNDARY = "*****"; // 边界标识 随机生成
        String PREFIX = "--";
        String LINE_END = "\r\n";
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空时执行上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                StringBuffer sb1 = new StringBuffer();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINE_END);
                if (Constant.usertype.equals("5")) {
                    sb1.append("Content-Disposition: form-data; name=\"pid\";" + LINE_END);
                } else if(Constant.usertype.equals("2")){
                    if(getArguments().getString("status2").equals("0")){
                        sb1.append("Content-Disposition: form-data; name=\"pid\";" + LINE_END);
                    }else{
                        sb1.append("Content-Disposition: form-data; name=\"aid\";" + LINE_END);
                    }
                }else if(Constant.usertype.equals("3")){
                    sb1.append("Content-Disposition: form-data; name=\"pid\";" + LINE_END);
                }else{
                    sb1.append("Content-Disposition: form-data; name=\"aid\";" + LINE_END);
                }
                sb1.append(LINE_END);
                if (Constant.usertype.equals("1")) {
                    sb1.append(Constant.userid);
                } else if(Constant.usertype.equals("3")){
                    sb1.append(Constant.aid);
                }else if(Constant.usertype.equals("5")){
                    sb1.append( Constant.userid);
                }  else {
                    sb1.append(Constant.aid);
                }
                sb1.append(LINE_END);
                dos.write(sb1.toString().getBytes());

                StringBuffer sb2 = new StringBuffer();
                sb2.append(PREFIX);
                sb2.append(BOUNDARY);
                sb2.append(LINE_END);
                sb2.append("Content-Disposition: form-data; name=\"type\";" + LINE_END);
                sb2.append(LINE_END);
                sb2.append(getArguments().getString("as_type"));
                sb2.append(LINE_END);
                dos.write(sb2.toString().getBytes());

                StringBuffer sb3 = new StringBuffer();
                sb3.append(PREFIX);
                sb3.append(BOUNDARY);
                sb3.append(LINE_END);
                sb3.append("Content-Disposition: form-data; name=\"setContent\";" + LINE_END);
                sb3.append(LINE_END);
                sb3.append(editText2.getText().toString());
                sb3.append(LINE_END);
                dos.write(sb3.toString().getBytes());

                StringBuffer sb7 = new StringBuffer();
                sb7.append(PREFIX);
                sb7.append(BOUNDARY);
                sb7.append(LINE_END);
                sb7.append("Content-Disposition: form-data; name=\"setTitle\";" + LINE_END);
                sb7.append(LINE_END);
                sb7.append(editText1.getText().toString());
                sb7.append(LINE_END);
                dos.write(sb7.toString().getBytes());

                StringBuffer sb6 = new StringBuffer();
                sb6.append(PREFIX);
                sb6.append(BOUNDARY);
                sb6.append(LINE_END);
                sb6.append("Content-Disposition: form-data; name=\"flagPath\";" + LINE_END);
                sb6.append(LINE_END);
                sb6.append(file.getPath());
                sb6.append(LINE_END);
                dos.write(sb6.toString().getBytes());

                StringBuffer sb8 = new StringBuffer();
                sb8.append(PREFIX);
                sb8.append(BOUNDARY);
                sb8.append(LINE_END);
                sb8.append("Content-Disposition: form-data; name=\"oid\";" + LINE_END);
                sb8.append(LINE_END);
                sb8.append(getArguments().getString("oid"));
                sb8.append(LINE_END);
                dos.write(sb8.toString().getBytes());

                StringBuffer sb4 = new StringBuffer();
                sb4.append(PREFIX);
                sb4.append(BOUNDARY);
                sb4.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */
                sb4.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb4.append("Content-Type: application/octet-stream; charset=utf-8"
                        + LINE_END);
                sb4.append(LINE_END);
                dos.write(sb4.toString().getBytes());

                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                pd.incrementProgressBy(20);

                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                pd.incrementProgressBy(20);

                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                res = conn.getResponseCode();
                pd.incrementProgressBy(20);

                Log.d("respondCode", "response code:" + res);
                if (res == 200) {
                    Log.d("success", "request success");
                    InputStream input = conn.getInputStream();
                    StringBuffer sb5 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb5.append((char) ss);
                    }
                    Log.d("success", "result : " + sb5.toString());
                    return sb5.toString();
                } else {
                    return "error";
                }
            } else {
                return "error";
            }
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}
