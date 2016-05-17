package net.gy.SwiftFrameWork.UI.customwidget.gridpage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.gy.SwiftFrameWork.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy on 2016/4/16.
 */
public class GridPage extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TextView tv_title;
    private List<View> dots;
    private View[] contents;
    private String[] titles;
    private Context context;
    private int currentItem = 0;
    private int oldPosition = 0;

    public GridPage(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public GridPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public GridPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.mypageview_layout, this,true);
        tv_title = (TextView) findViewById(R.id.v_title);
        viewPager = (ViewPager) findViewById(R.id.vp);
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.v_dot0));
        dots.add(findViewById(R.id.v_dot1));
        dots.add(findViewById(R.id.v_dot2));
        dots.add(findViewById(R.id.v_dot3));
        dots.add(findViewById(R.id.v_dot4));
    }

    public void setPagerView(GridPageBean bean) {
        titles = bean.getTitle();
        contents = bean.getContent();
        tv_title.setText(titles[0]);
        viewPager.setAdapter(new GridPageAdapter(contents));
        viewPager.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        tv_title.setText(titles[position]);
        dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
        dots.get(position).setBackgroundResource(R.drawable.dot_focused);
        oldPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
