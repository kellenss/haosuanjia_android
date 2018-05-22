package suanhang.jinan.com.suannihen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import suanhang.jinan.com.suannihen.R;


/**
 * 我的
 */
public class MineActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        init();
//        Toast.makeText(context,"我的",Toast.LENGTH_SHORT).show();
    }

    private void init() {
        iv_setting=(ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_setting:
                Intent intent =new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
            default:
                    break;
        }

    }
}
