package suanhang.jinan.com.suannihen.view;

import android.util.SparseArray;
import android.view.View;

/**
 * adapter的所有item必须实现此接口.
 * 通过返回{@link #getLayoutResId()}来自动初始化view，之后在{@link #onBindViews(View)}中就可以初始化item的内部视图了。<br>
 */
public abstract class AdapterItem<T> {
	private SparseArray<View> mViews;
	public int mPosition;
	public View mConvertView;
    private T itemModel;

	public AdapterItem() {
        this.mViews = new SparseArray<>();
    }
	
    /**
     * @return item布局文件的layoutId
     */
	public abstract int getLayoutResId();

    /**
     * 初始化views
     */
	public void onBindViews(final View root){
        mConvertView = root;
    }

    /**
     * 设置view的参数
     */
	public abstract void onSetViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param model    数据list内部的model
     * @param position 当前adapter调用item的位置
     */
	public abstract void onUpdateViews(T model, int position);

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public T getModel(){
        return itemModel;
    }

    public void setModel(T model){
        itemModel = model;
    }

    public void setItemModelPosition(T model, int position){
        itemModel = model;
        mPosition = position;
    }
}  