package com.jinan.haosuanjia.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.utils.ConstantString;
import com.jinan.haosuanjia.utils.HMApplication;
import com.jinan.haosuanjia.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * 
 * @author gc
 * create by 2018/09/03
 * 
 */
public class GuidanceActivity extends Activity {
	private static final String TAG = "GuidanceActivity";
	private ViewPager view_pager;
	private List<View> viewList;
	private Button bt_begin;
	private ImageView iv_splish;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_guidance);
		context = getApplicationContext();
		view_pager = (ViewPager) findViewById(R.id.view_pager);

		viewList = new ArrayList<>();
		View v1 = View.inflate(context, R.layout.view_guidance_1, null);
		View v2 = View.inflate(context, R.layout.view_guidance_1, null);
		View v3 = View.inflate(context, R.layout.view_guidance_1, null);
		View v4 = View.inflate(context, R.layout.view_guidance_1, null);
		View v5 = View.inflate(context, R.layout.view_guidance_1, null);

		v1.findViewById(R.id.iv_ydy).setBackgroundResource(R.drawable.ydy_1);
		v2.findViewById(R.id.iv_ydy).setBackgroundResource(R.drawable.ydy_2);
		v3.findViewById(R.id.iv_ydy).setBackgroundResource(R.drawable.ydy_3);
		v4.findViewById(R.id.iv_ydy).setBackgroundResource(R.drawable.ydy_4);
		v5.findViewById(R.id.iv_ydy).setBackgroundResource(R.drawable.ydy_5);

		bt_begin = (Button) v5.findViewById(R.id.bt_begin);
		bt_begin.setVisibility(View.VISIBLE);
		iv_splish = (ImageView) v5.findViewById(R.id.iv_splish);
		iv_splish.setVisibility(View.VISIBLE);
		bt_begin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				SPUtil.setBoolean(context, "isFirst", true);
				SPUtil.setInt(context, ConstantString.VERSIONCODE, HMApplication.version_code);
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

		viewList.add(v1);
		viewList.add(v2);
		viewList.add(v3);
		viewList.add(v4);
		 viewList.add(v5);

		view_pager.setAdapter(new MyAdapterimg(viewList));
        view_pager.setOffscreenPageLimit(3);
	}

	/**
	 * 适配器，负责装配 、销毁 数据 和 组件 。
	 */
	@SuppressLint("NewApi")
	private class MyAdapterimg extends PagerAdapter {

		private List<View> mList;


		public MyAdapterimg(List<View> list) {
			mList = list;
		}

		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		/**
		 * Remove a page for the given position. 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
		 * instantiateItem(View container, int position) This method was
		 * deprecated in API level . Use instantiateItem(ViewGroup, int)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(mList.get(position));

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container,
				final int position) {
			container.addView(mList.get(position));
			return mList.get(position);
		}
	}
}
