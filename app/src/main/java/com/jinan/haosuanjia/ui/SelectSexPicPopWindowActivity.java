package com.jinan.haosuanjia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jinan.haosuanjia.R;

/**
 * Created by hu on 2018/08/22.
 */
public class SelectSexPicPopWindowActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_head);

        findViewById(R.id.pop_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        TextView tv_pop_title = (TextView) findViewById(R.id.tv_pop_title);
        Button btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        Button btn_pick_photo = (Button) findViewById(R.id.btn_pick_photo);
        btn_take_photo.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

        int type = getIntent().getIntExtra("type",0);
//        if (type == 0) {
//            tv_pop_title.setText(R.string.chang_sex);
//            btn_take_photo.setText("男");
//            btn_pick_photo.setText("女");
//        } else
 if (type == 1) {
            tv_pop_title.setText(R.string.chang_photo);
            btn_take_photo.setText("拍照");
            btn_pick_photo.setText("相册");
//        } else if(type == 2){
//            tv_pop_title.setText(R.string.chang_bg);
//            btn_take_photo.setText("从相册中选择");
//            btn_pick_photo.setText("相机拍摄");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_take_photo:
                intent.putExtra("selectItem", 1);
                break;
            case R.id.btn_pick_photo:
                intent.putExtra("selectItem", 2);
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }
}
