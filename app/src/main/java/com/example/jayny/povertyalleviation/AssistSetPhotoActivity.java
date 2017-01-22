package com.example.jayny.povertyalleviation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssistSetPhotoActivity extends AppCompatActivity {
    private GridView gridView1;              //网格显示缩略图
    private Button buttonPublish;            //发布按钮
    private final int IMAGE_OPEN = 1;        //打开图片标记
    private String pathImage;                //选择图片路径
    private Bitmap bmp;                      //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器
    private EditText editText;
    private SendSetTask sendSetTask;
    private GetSetTask getSetTask;
    private Handler pic_hdl;
    private String tmpUrl;
    private String pi;
    private Intent intent;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /*
         * 防止键盘挡住输入框
         * 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan"
         * 希望动态调整高度 android:windowSoftInputMode="adjustResize"
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_assist_set_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("as"));
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        //获取控件对象
        gridView1 = (GridView) findViewById(R.id.gridView1);

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
        }else if(Constant.usertype.equals("3")&&getIntent().getStringExtra("status3").equals("1")){
                //获取资源图片加号
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", bmp);
            imageItem.add(map);
        }
        simpleAdapter = new SimpleAdapter(this,
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
        }else if(Constant.usertype.equals("3")&&getIntent().getStringExtra("status3").equals("1")){
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
                        intent = new Intent(AssistSetPhotoActivity.this, PhotoZoomActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bitmap tmp_ = (Bitmap) imageItem.get(1).get("itemImage");
                        BitmapCoast.setBitmap(tmp_);
                        startActivity(intent);
                    } else {
                        if (imageItem.size() == 2) {
                            Toast.makeText(AssistSetPhotoActivity.this, "图片数已满", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, IMAGE_OPEN);
                            //通过onResume()刷新数据
                        }
                    }
                }else if(Constant.usertype.equals("3")&&getIntent().getStringExtra("status3").equals("1")){
                    if (position != 0) {
                        intent = new Intent(AssistSetPhotoActivity.this, PhotoZoomActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bitmap tmp_ = (Bitmap) imageItem.get(1).get("itemImage");
                        BitmapCoast.setBitmap(tmp_);
                        startActivity(intent);
                    } else {
                        if (imageItem.size() == 2) {
                            Toast.makeText(AssistSetPhotoActivity.this, "图片数已满", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, IMAGE_OPEN);
                            //通过onResume()刷新数据
                        }
                    }
                } else {
                    intent = new Intent(AssistSetPhotoActivity.this, PhotoZoomActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bitmap tmp_ = (Bitmap) imageItem.get(0).get("itemImage");
                    BitmapCoast.setBitmap(tmp_);
                    startActivity(intent);
                }
            }
        });

        editText = (EditText) findViewById(R.id.editText1);
        buttonPublish = (Button) findViewById(R.id.buttonForSet);
        if (Constant.usertype.equals("1")) {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.buttonForSet || id == EditorInfo.IME_ACTION_DONE) {
                        setSubmit();
                        return true;
                    }
                    return false;
                }
            });
            buttonPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSubmit();
                }
            });
        }else if(Constant.usertype.equals("3")&&getIntent().getStringExtra("status3").equals("1")){
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.buttonForSet || id == EditorInfo.IME_ACTION_DONE) {
                        setSubmit();
                        return true;
                    }
                    return false;
                }
            });
            buttonPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSubmit();
                }
            });
        } else {
            editText.setFocusable(false);
            editText.setEnabled(false);
            buttonPublish.setVisibility(View.GONE);
        }
        getSetTask = new GetSetTask();
        getSetTask.executeOnExecutor(com.example.jayny.povertyalleviation.Executor.exec);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getContentResolver().query(
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

    private void setSubmit() {
        editText.setError(null);
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(getString(R.string.error_p_set));
            focusView = editText;
            cancel = true;
        }
        if (imageItem.size() < 2) {
            editText.setError("请上传图片");
            focusView = editText;
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
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            map.put("pathImage", pathImage);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AssistSetPhotoActivity.this);
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

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
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
            pd = new ProgressDialog(AssistSetPhotoActivity.this);
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
                Toast.makeText(AssistSetPhotoActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject dataJson = new JSONObject(msg);
                    if (dataJson.optString("status").equals("ok")) {
                        Toast.makeText(AssistSetPhotoActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
                        navigateUpTo(new Intent(AssistSetPhotoActivity.this, AssistSetActivity.class).putExtra("status3",null==getIntent().getStringExtra("status3")?"0":getIntent().getStringExtra("status3")));
                    } else {
                        Toast.makeText(AssistSetPhotoActivity.this, "保存失败！", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("exception", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(AssistSetPhotoActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
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
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetSetTask extends AsyncTask<Void, Void, String> {

        GetSetTask() {
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
            } else if(Constant.usertype.equals("2")){
                if(getIntent().getStringExtra("status2").equals("0")){
                    temp.put("pid", Constant.aid);
                }else{
                    temp.put("aid", Constant.aid);
                }
            }else if(Constant.usertype.equals("5")){
                temp.put("pid", Constant.userid);
            }else if(Constant.usertype.equals("3")){
                temp.put("pid", Constant.aid);
            }  else {
                temp.put("aid", Constant.aid);
            }
            temp.put("type", getIntent().getStringExtra("as_type"));
            String result = MyUtils.postGetJson(getResources().getString(R.string.host_port_server) + "getAssistSetDetail", "POST", temp);
            return result;
        }

        @Override
        protected void onPostExecute(String msg) {

            if (msg.equals("error")) {
                Toast.makeText(AssistSetPhotoActivity.this, getString(R.string.error_remote), Toast.LENGTH_LONG).show();
            } else {
                try {
                    if (!msg.equals("")) {
                        JSONObject dataJson = new JSONObject(msg);
                        editText.setText(dataJson.optString("words"));
                        if (!TextUtils.isEmpty(dataJson.optString("pic"))) {
                            tmpUrl = dataJson.optString("pic");
                            pi = dataJson.optString("remarks");
                            pic_hdl = new PicHandler();
                            Thread t = new LoadPicThread();
                            t.start();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(AssistSetPhotoActivity.this, getString(R.string.error_local), Toast.LENGTH_LONG).show();
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
                } else {
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
                sb2.append(getIntent().getStringExtra("as_type"));
                sb2.append(LINE_END);
                dos.write(sb2.toString().getBytes());

                StringBuffer sb3 = new StringBuffer();
                sb3.append(PREFIX);
                sb3.append(BOUNDARY);
                sb3.append(LINE_END);
                sb3.append("Content-Disposition: form-data; name=\"setContent\";" + LINE_END);
                sb3.append(LINE_END);
                sb3.append(editText.getText().toString());
                sb3.append(LINE_END);
                dos.write(sb3.toString().getBytes());

                StringBuffer sb6 = new StringBuffer();
                sb6.append(PREFIX);
                sb6.append(BOUNDARY);
                sb6.append(LINE_END);
                sb6.append("Content-Disposition: form-data; name=\"flagPath\";" + LINE_END);
                sb6.append(LINE_END);
                sb6.append(file.getPath());
                sb6.append(LINE_END);
                dos.write(sb6.toString().getBytes());

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
                    Log.d("error", "request error");
                    return "error";
                }
            } else {
                return "error";
            }
        } catch (ConnectTimeoutException cte) {
            Log.d("exception", cte.getMessage());
            return "outTime";
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
            e.printStackTrace();
            return "error";
        }
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
            simpleAdapter = new SimpleAdapter(AssistSetPhotoActivity.this,
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