package suanhang.jinan.com.suannihen.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import suanhang.jinan.com.suannihen.R;
import suanhang.jinan.com.suannihen.bean.BussinessFragmentBean;
import suanhang.jinan.com.suannihen.view.adapter.AdapterItem;
import suanhang.jinan.com.suannihen.view.adapter.CommonAdapter;
import suanhang.jinan.com.suannihen.view.listviewload.XListView;

/**
 * 客服
 * create by gc on 2018/05/29
 */
public class CustomerServiceActivity extends StatisticsActivity implements  View.OnClickListener {
    private ImageView iv_back_head;
    private ImageView iv_right_head;
    private TextView tv_left_head;
    private TextView tv_title_head;
    private TextView tv_right_head;
    private RelativeLayout layout_sjzx;
    private RelativeLayout layout_dscsj;
    private RelativeLayout layout_cgdsxq;
    private RelativeLayout layout_kfrx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        inits();
    }

    private void inits() {
        tv_left_head = (TextView) findViewById(R.id.tv_left_head);
        tv_title_head = (TextView) findViewById(R.id.tv_title_head);
        tv_right_head = (TextView) findViewById(R.id.tv_right_head);
        iv_back_head = (ImageView) findViewById(R.id.iv_back_head);
        iv_right_head = (ImageView) findViewById(R.id.iv_right_head);
        layout_sjzx = (RelativeLayout) findViewById(R.id.layout_sjzx);
        layout_dscsj = (RelativeLayout) findViewById(R.id.layout_dscsj);
        layout_cgdsxq = (RelativeLayout) findViewById(R.id.layout_cgdsxq);
        layout_kfrx = (RelativeLayout) findViewById(R.id.layout_kfrx);
        iv_back_head.setOnClickListener(this);
        tv_right_head.setOnClickListener(this);
        layout_sjzx.setOnClickListener(this);
        layout_dscsj.setOnClickListener(this);
        layout_cgdsxq.setOnClickListener(this);
        layout_kfrx.setOnClickListener(this);
        tv_title_head.setText("客服服务");
        iv_back_head.setVisibility(View.VISIBLE);
        tv_left_head.setVisibility(View.GONE);
        tv_right_head.setVisibility(View.GONE);
//        tv_right_head.setText("发贴");
        iv_right_head.setVisibility(View.GONE);
//

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_head:
                finish();
                break;
            case R.id.tv_right_head:
                break;
            case R.id.layout_sjzx:
            case R.id.layout_dscsj:
            case R.id.layout_cgdsxq:
            case R.id.layout_kfrx:
                String callPhoneNum=getResources().getString(R.string.call_phone_num);
                Intent intent = new Intent();
//                                    intent.setAction("android.intent.action.CALL");
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:" + callPhoneNum));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
