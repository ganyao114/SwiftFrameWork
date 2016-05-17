package net.gy.SwiftFrameWork.UI.customwidget.gridpage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by gy on 2016/4/16.
 */
public class GridPageAdapter extends PagerAdapter{
    private View[] contents;

    public GridPageAdapter(View[] contents) {
        this.contents = contents;
    }

    @Override
    public int getCount() {
        return contents.length;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(contents[0]);
        return contents[arg1];
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
