package suanhang.jinan.com.suannihen.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by hu on 2017/07/10.
 *
 */

public class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private ArrayList<T> list;
    private String[] titles;

    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<T> list) {
        super(fm);
        this.list = list;
    }

    public void setList(ArrayList<T> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<T> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(titles == null || titles.length == 0) return super.getPageTitle(position);
        return titles[position];
    }
}
