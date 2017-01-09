package com.example.jayny.povertyalleviation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

public class PhotoZoomActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    ImageView imageview;
    Bitmap bp;

    int h;
    boolean num=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_zoom);
        DisplayMetrics dm=new DisplayMetrics();//创建矩阵
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if(null!=BitmapCoast.getBitmap()){
            bp = BitmapCoast.getBitmap();
            imageview=(ImageView)findViewById(R.id.imageview);
            int w=dm.widthPixels; //得到屏幕的宽度
            int h=dm.heightPixels; //得到屏幕的高度
            Bitmap newBitmap=Bitmap.createScaledBitmap(bp, w, h, true);
            imageview.setImageBitmap(newBitmap);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:  //当屏幕检测到第一个触点按下之后就会触发到这个事件。
                finish();
                break;
        }


        return super.onTouchEvent(event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
