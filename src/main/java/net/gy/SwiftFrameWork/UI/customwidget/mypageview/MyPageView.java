package net.gy.SwiftFrameWork.UI.customwidget.mypageview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.gy.SwiftFrameWork.R;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.entity.ImgLoadConfigs;
import net.gy.SwiftFrameWork.Service.loader.imgloader.strategy.mystrategy.impl.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyPageView extends RelativeLayout implements
		View.OnClickListener {

	private ViewPager viewPager; 
	private List<ImageView> imageViews; 
	private ImageLoader imageLoader;
	private String[] titles; 
	private int[] imageResId; 
	private List<View> dots; 
	private Context context;
	private TextView tv_title;
	private ImgLoadConfigs configs;
	private int currentItem = 0; 

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	private MyPageViewListerner listerner;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
		};
	};

	public MyPageView(Context context) {
		super(context);
		this.context = context;
		configs = new ImgLoadConfigs();
		intview(context);
		// TODO Auto-generated constructor stub
	}

	public MyPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		configs = new ImgLoadConfigs();
		intview(context);
	}

	public MyPageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		configs = new ImgLoadConfigs();
		intview(context);
	}

	private void intview(Context context) {
		LayoutInflater.from(context).inflate(R.layout.mypageview_layout, this,true);
		imageViews = new ArrayList<ImageView>();
		imageLoader = (ImageLoader) ImageLoader.getInstance(context,new ImgLoadConfigs());//new ImageLoader(context,configs.getLoadImgsrc());
		tv_title = (TextView) findViewById(R.id.v_title);
		viewPager = (ViewPager) findViewById(R.id.vp);
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));
	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
				currentItem = position;
				tv_title.setText(titles[position]);
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				int i = currentItem + 1;
				currentItem = (i) % titles.length;
				handler.obtainMessage().sendToTarget(); 
			}
		}

	}

	public void setPagerView(PagerViewBean bean) {
		
		for (int i = 0; i < dots.size(); i++) {
			ImageView imageView = new ImageView(context);
			imageLoader.ShowImg(bean.getImgsrc()[i], imageView);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}
		titles = bean.getTitle();
		tv_title.setText(titles[0]);
		viewPager.setAdapter(new MyPageViewAdapter(imageViews));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	public void setOnItemClickListerner(MyPageViewListerner listerner) {
		for (int i = 0; i < imageViews.size(); i++) {
			imageViews.get(i).setOnClickListener(this);
		}
		this.listerner = listerner;
	}

	public void Start() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 3,
				TimeUnit.SECONDS);

	}

	public void Stop() {
		scheduledExecutorService.shutdown();
	}

	@Override
	public void onClick(View v) {
		listerner.OnItemClicked(currentItem);
	}

}
