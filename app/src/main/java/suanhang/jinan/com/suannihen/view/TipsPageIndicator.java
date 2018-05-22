/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package suanhang.jinan.com.suannihen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


import java.util.Timer;
import java.util.TimerTask;

import suanhang.jinan.com.suannihen.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 * 轮播图
 */
public class TipsPageIndicator extends HorizontalScrollView implements PageIndicator {

    private Runnable mTabSelector;

    private final LinearLayout mTabLayout;

    private ViewPager mViewPager;
    private OnPageChangeListener mListener;

    private int mSelectedTabIndex;

    public TipsPageIndicator(Context context) {
        this(context, null);
    }

    public TipsPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);

        mTabLayout = new LinearLayout(context);
        addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                scrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        if(mTimerWait != null) {
            mTimerWait.cancel();
            mTimerWait = null;
        }
        mHandlerwait.removeMessages(1);
    }

    private void addTab(int index, int width, int height) {
        View iv = new View(getContext());
        if (index == 0) {
            iv.setBackgroundResource(R.mipmap.icon_home_select_yes);
        } else {
            iv.setBackgroundResource(R.mipmap.icon_home_select_no);
        }
        iv.measure(width, height);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                iv.getMeasuredWidth(), iv.getMeasuredHeight());
        ll.leftMargin = 10;
        ll.rightMargin = 10;
        iv.setLayoutParams(ll);
        ll.gravity = Gravity.CENTER_VERTICAL;

        mTabLayout.addView(iv);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentItem(arg0);
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        final int count = adapter.getCount();
        int width = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int height = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < count; i++) {
            addTab(i, width, height);
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
//            throw new IllegalStateException("ViewPager has not been bound.");
            return ;
        }
        tabCount = mTabLayout.getChildCount();
        if (item >= tabCount) {
            item = 0;
        }
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item);

        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            if (i == item) {
                animateToTab(item);
                child.setBackgroundResource(
                        R.mipmap.icon_home_select_yes);
            } else {
                child.setBackgroundResource(
                        R.mipmap.icon_home_select_no);
            }
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    int tabCount;
    public Timer mTimerWait;// 定时器
    @SuppressLint("HandlerLeak")
    public Handler mHandlerwait = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSelectedTabIndex++;
            setCurrentItem(mSelectedTabIndex);
            super.handleMessage(msg);
        }
    };

    private void timerTaskWait(int index) {
        if (mTimerWait != null) return;

        mSelectedTabIndex = index;
        mTimerWait = new Timer();
        mTimerWait.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandlerwait.sendEmptyMessage(1);// 向Handler发送消息
            }
        }, 3000, 3000);// 定时任务
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        if(!timerCan) return;

        if(visibility == VISIBLE){
            timerTaskWait(mSelectedTabIndex);
        } else {
            if(mTimerWait!=null){
                mTimerWait.cancel();
                mTimerWait = null;
            }
        }
    }



    boolean timerCan;
    public void setTimerCan(boolean timerCan){
        this.timerCan = timerCan;
    }
}
