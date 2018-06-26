package com.jinan.haosuanjia.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinan.haosuanjia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/11/29
 */
public abstract class CommonPagerAdapter<T> extends BasePagerAdapter<View> {

    private List<T> mDataList;

    private LayoutInflater mInflater;

    private boolean mIsLazy = false;

    private int currentPos;
    
    public CommonPagerAdapter(@Nullable List<T> data) {
        this(data, false);
    }

    public CommonPagerAdapter(@Nullable List<T> data, boolean isLazy) {
        if (data == null) {
            data = new ArrayList<>();
        }

        mDataList = data;
        mIsLazy = isLazy;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @NonNull
    @Override
    protected View getViewFromItem(View item, int pos) {
        return item;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = super.instantiateItem(container, position);
        if (!mIsLazy) {
            initItem(position, view);
        }
        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, @NonNull Object object) {
        if (mIsLazy && object != currentItem) {
            initItem(position, ((View) object));
        }
        super.setPrimaryItem(container, position, object);
    }

    private void initItem(int position, View view) {
        final AdapterItem item = (AdapterItem) view.getTag(R.id.tag_item);

        if(getData().size() <= position) return;
        Object o = getConvertedData(mDataList.get(position), getItemType(position));
        item.setItemModelPosition(o, position);
        item.onUpdateViews(o, position);
    }

    @Override
    protected View createItem(ViewGroup viewPager, int position) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewPager.getContext());
        }
        AdapterItem item = createItem(getItemType(position));
        View view = mInflater.inflate(item.getLayoutResId(), null);
        view.setTag(R.id.tag_item, item);
        item.onBindViews(view);
        item.onSetViews();
        return view;
    }

    public void setIsLazy(boolean isLazy) {
        mIsLazy = isLazy;
    }

    @NonNull
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    /**
     * instead by {@link #getItemType(Object)}
     */
    @Deprecated
    protected Object getItemType(int position) {
        currentPos = position;
        if (position < mDataList.size()) {
            return getItemType(mDataList.get(position));
        } else {
            return null;
        }
    }

    /**
     * 强烈建议返回string,int,bool类似的基础对象做type
     */
    public Object getItemType(T t) {
        return -1; // default
    }

    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    public List<T> getData() {
        return mDataList;
    }

    public int getCurrentPosition() {
        return currentPos;
    }
    /**
     * 当缓存中无法得到所需item时才会调用
     *
     * @param type 通过{@link #getItemType(Object)}得到的type
     * @return 任意类型的 AdapterItem
     */
    protected abstract AdapterItem createItem(Object type);
}
