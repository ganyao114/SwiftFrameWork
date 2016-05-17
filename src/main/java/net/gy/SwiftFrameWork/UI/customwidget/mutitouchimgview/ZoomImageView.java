package net.gy.SwiftFrameWork.UI.customwidget.mutitouchimgview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

/**
 * 
 * @author Administrator
 * 
 */
public class ZoomImageView extends ImageView implements OnGlobalLayoutListener,
		OnScaleGestureListener, OnTouchListener {
	/**
	 * ֵ����һ�ε�ͼƬ���Ŵ���
	 */
	private boolean mOnce;

	/**
	 * ��ʼ��ʱ���ŵ�ֵ
	 */
	private float mInitScale;
	/**
	 * ˫���Ŵ�ﵽ��ֵ
	 */
	private float mMidScale; // ���ŵ�ֵ��mInitScale��mMaxScale֮��
	/**
	 * �Ŵ�ļ���
	 */
	private float mMaxScale;

	/**
	 * ��������λ�Ƶľ���
	 */
	private Matrix mScaleMatrix;

	// --------------------------------

	/**
	 * �����û���ָ����ʱ���ŵı���
	 */
	private ScaleGestureDetector mScaleGestureDetector;

	// -------------------->�����ƶ�------------------

	/**
	 * ��¼��һ�ζ�㴥�ص�����
	 */
	private int mLastPointCount;

	// ��󴥿ص����ĵ�λ��
	private float mLastX;
	private float mLastY;
	/**
	 * һ��ֵ�������ж��Ƿ����ƶ���
	 */
	private int mTouchSlop;
	/**
	 * �Ƿ�����ƶ�
	 */
	private boolean isCanDrag;

	private boolean isCheckLeftAndRight;
	private boolean isCheckTopAndBottom;

	// ----------------------˫���Ŵ���С--------

	private GestureDetector mGestureDetector;

	/**
	 * �����жϵ�ǰ�Ƿ�������״̬����ֹ�û��ظ�˫��
	 */
	private boolean isAutoScale;

	public ZoomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// init
		mScaleMatrix = new Matrix();
		setScaleType(ScaleType.MATRIX);

		mScaleGestureDetector = new ScaleGestureDetector(context, this);
		// ���ü����¼�
		setOnTouchListener(this);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mGestureDetector = new GestureDetector(context,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {

						if (isAutoScale) {
							return true;
						}

						// ���������
						float x = e.getX();
						float y = e.getY();

						if (getScale() < mMidScale) {
							// mScaleMatrix.postScale(mMidScale / getScale(),
							// mMidScale / getScale(), x, y);// �Ŵ�ָ����С
							// setImageMatrix(mScaleMatrix);
							postDelayed(new AutoScaleRunnable(mMidScale, x, y),
									16);
//							isAutoScale = true;
						} else {
							// ��С
							// mScaleMatrix.postScale(mInitScale / getScale(),
							// mInitScale / getScale(), x, y);// �Ŵ�ָ����С
							// setImageMatrix(mScaleMatrix);
							postDelayed(
									new AutoScaleRunnable(mInitScale, x, y), 16);
//							isAutoScale = true;
						}

						return true;
					}
				});
	}

	/**
	 * �Զ��Ŵ�����С
	 * 
	 * @author Zhang
	 * 
	 */
	private class AutoScaleRunnable implements Runnable {

		/**
		 * ���ŵ�Ŀ��ֵ
		 */
		private float mTargetScale;
		// ���ŵ����ĵ�
		private float x;
		private float y;
		/**
		 * ���ŵ��ݶȣ�ÿ��1.07f �Ŵ�0.93����С
		 */
		private final float BIGGER = 1.07f;
		private final float SMALL = 0.93f;

		private float temScale;// ��ʱ

		public AutoScaleRunnable(float mTargetScale, float x, float y) {
			this.mTargetScale = mTargetScale;
			this.x = x;
			this.y = y;

			if (getScale() < mTargetScale) {
				temScale = BIGGER;// ����Ҫ�Ŵ�
			}
			if (getScale() >mTargetScale) {
				temScale = SMALL;
			}
		}

		@Override
		public void run() {
			// ��������
			mScaleMatrix.postScale(temScale, temScale, x, y);
			checkBorderAndCenterWhenScale();// ���
			setImageMatrix(mScaleMatrix);

			float currentScale = getScale();
			// ��temScale����1.�ҵ�ǰ��scale��С��Ŀ��ֵ�����ԷŴ�,��֮������С
			if ((temScale > 1.0f && currentScale < mTargetScale)
					|| (temScale < 1.0f && currentScale > mTargetScale)) {
				postDelayed(this, 16);// ÿ16��ִ��������������ۿ�������˳�������Ŷ���
			} else {
				// �ﵽĿ��ֵ,����Ŀ��ֵ
				float scale = mTargetScale / currentScale;
				mScaleMatrix.postScale(scale, scale, x, y);
				checkBorderAndCenterWhenScale();
				setImageMatrix(mScaleMatrix);

				// ����
				isAutoScale = false;
			}
		}

	}

	public ZoomImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ZoomImageView(Context context) {
		this(context, null);
	}

	/**
	 * ��onAttachedToWindow
	 */
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	/**
	 * ��onDetachedFromWindow
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	/**
	 * ȫ�ֵĲ��ּ�����ɺ󣬵��ô˷��� �� ��ȡImageView������ɵ�ͼƬ,ʹͼƬ��������
	 */
	@Override
	public void onGlobalLayout() {
		if (!mOnce) {
			// �õ�ͼƬ�Ŀ��
			int width = getWidth();
			int height = getHeight();
			// �õ�ͼƬ���Լ���͸�
			Drawable d = getDrawable();
			if (d == null)
				return;
			// �����Ŀ��.����������ͼƬ�Ŀ��
			int dw = d.getIntrinsicWidth();
			int dh = d.getIntrinsicHeight();

			Log.d("Debug", "width:" + width + ",height:" + height + ",dw:" + dw
					+ ",dh:" + dh);

			float scale = 1.0f;// Ĭ�����ŵ�ֵ

			// ��ͼƬ�Ŀ�ߺͿؼ��Ŀ�����Աȣ����ͼƬ�Ƚ�С����ͼƬ�Ŵ󣬷�֮��Ȼ��
			// ���ͼƬ�Ŀ�ȴ��ڿؼ��Ŀ��,����ͼƬ�߶�С�ڿؼ��߶�
			if (dw > width && dh < height) {
				scale = width * 1.0f / dw;// ͼƬ̫���������
				Log.d("Debug", "ͼƬ��󣬸�С");
			}

			if (dh > height && dw < width) {
				scale = height * 1.0f / dh;// ͼƬ̫�ߣ��߶�����
				Log.d("Debug", "ͼƬ�ߴ󣬿�С");
			}
			// // �����߶����ڿؼ����??
			// if (dw > width && dh > height) {
			// scale = Math.min(width * 1.0f / dw, height * 1.0f / dw);
			// }
			// // ͼƬ��߶�С�ڿؼ��Ŀ�� ��������һ��
			// if (dw < width && dh < height) {
			// scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
			// }

			if (dw < width && dh < height || dw > width && dh > height) {
				scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
				Log.d("Debug", "ͼƬ��߶��󣬻�С");
			}

			// �õ���ʼ�����ŵı���
			mInitScale = scale;// ԭ��С
			mMaxScale = mInitScale * 4;// 4��
			mMidScale = mInitScale * 2;// 2����˫���Ŵ�ﵽ��ֵ

			// ��ͼƬ�ƶ����ؼ�������
			int dx = getWidth() / 2 - dw / 2;// ��x���ƶ�dx����
			int dy = getHeight() / 2 - dh / 2;// ��y���ƶ�dx����

			/**
			 * matrix: xScale xSkew xTrans ��Ҫ9�� ySkew yScale yTrans 0 0 0
			 */
			mScaleMatrix.postTranslate(dx, dy);// ƽ��
			mScaleMatrix.postScale(mInitScale, mInitScale, width / 2,
					height / 2);// ����,������ʾwidth/2,height/2����
			setImageMatrix(mScaleMatrix);

			mOnce = true;
		}
	}

	/**
	 * �õ���ǰ��ͼƬ��ֵ
	 * 
	 * @return
	 */
	public float getScale() {
		float[] values = new float[9];
		mScaleMatrix.getValues(values);
		return values[Matrix.MSCALE_X];
	}

	// ����----------------------------------
	// ���ŵ����䣬initScale ��maxScale֮��
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		float scale = getScale();
		// �õ����ŵ�ֵ
		// getScaleFactor()�����������Ҫ�����ĺ����Ǹ�������������ų̶�Ԥ�ڵõ���ͼƬ��С�͵�ǰͼƬ��С��һ����ֵ��
		// ���ﵽ������Сֵʱ�����ŵ���Ϊ1���У�����ʦ�����ļ��㣬�����ŵ����ֵ����Сֵ���п��ܳ��ֲ��������ŵ����
		float scaleFactor = detector.getScaleFactor();// ���ش�ǰһ�������¼�����ǰ�����¼�����������,
		if (getDrawable() == null) {
			return true;
		}
		// Log.d("Debug", "scaleFactor--> " + scaleFactor + ".");
		// ���ŷ�Χ�Ŀ���
		// ��ǰ���������ż����ڣ�����һ���Ӵ���1��˵����Ŵ�С��1˵������С
		if ((scale < mMaxScale && scaleFactor > 1.0f)
				|| (scale > mInitScale && scaleFactor < 1.0f)) {
			// ����С����Сֵ
			if (scale * scaleFactor < mInitScale) {
				// С�ڵĻ�����
				scaleFactor = mInitScale / scale;// scale=mInitScale/scaleFactory
			}

			if (scale * scaleFactor > mMaxScale) {
				// ����Ҳ��ֵ
				scaleFactor = mMaxScale / scale;// scale��ǰ����
			}
			// ���ţ���Ļ�̫С�ĸ�ԭ
			/*
			 * scale ��ʾ���ǵ�ǰͼƬ����ԭͼ�Ŵ�ı��� mScaleMatix.postScale(scaleFactor,
			 * scaleFactor, getWidth() / 2,getHeight() /
			 * 2);�е�scaleFactor������ʾ���ڵ�ǰ�ѷŴ��ͼƬ�ٷŴ�scaleFactor��
			 * ������ͼƬ��ʵ�ʷŴ�Ĵ�С��ԭͼ��scaleFactor
			 * *scale����Ҳ����˵����ǰ����postScale����Ĳ�����scaleFactor
			 * ����ͼƬʵ�ʻ���ԭͼ�Ŵ�ı�����scaleFactor*scale ��ʽscaleFactor=mInitScale/scale
			 * �����Ƶ���---�� scaleFactor*scale = mInitScale -----�� ��ʱ��scaleFactor
			 * ��ΪpostScale�Ĳ�����ʵ��ͼƬ�����Ŵ�С����mInitScale
			 */
			mScaleMatrix.postScale(scaleFactor, scaleFactor,
					detector.getFocusX(), detector.getFocusY());// detector.getFocusX(),
																// detector.getFocusY()��ô��������ĵ㣬�����Ŵ��ǿ��԰��մ��������ķŴ���С�ͻ������⣬����ͼƬ��ʾƫ�ơ�
			// ���ϵļ�⣬
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
		}

		return true;
	}

	/**
	 * Ҫ�õ�ͼƬ�Ŵ���С�Ժ���Ľǵ����꣬�Լ����
	 * 
	 * @return
	 */
	private RectF getMatrixRectF() {
		Matrix matrix = mScaleMatrix;
		RectF rectF = new RectF();

		Drawable d = getDrawable();
		// �õ�ͼƬ�Ŵ����С�Ժ�Ŀ�͸�
		if (d != null) {
			rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			matrix.mapRect(rectF);
		}

		return rectF;
	}

	/**
	 * �����ŵ�ʱ����б߽���ƣ��Լ����ǵ�λ�õĿ���
	 */
	private void checkBorderAndCenterWhenScale() {
		RectF rect = getMatrixRectF();

		// Ҫ�ƶ���ƫ�ƾ��롣
		float deltaX = 0;
		float deltaY = 0;

		int width = getWidth();
		int height = getHeight();

		// ����ʱ�����б߽��⣬��ֹ���ְױߡ�
		if (rect.width() >= width) {// rect.width()�Ŵ����С��Ŀ��
			// �Ŵ���С���ͼƬ����Ļ��ߵĿ�϶��
			if (rect.left > 0) {// ����ͼƬû����ȫ����Ļ�У��������ұ��ƶ���һС�ξ��룬rect.left�������Ǹ�����
				// Log.d("Debug", "rect.left��ƫ�ƾ����ǣ�" + rect.left);
				deltaX = -(rect.left);
			}
			// �ұ�����Ļ�п�϶
			if (rect.right < width) {
				deltaX = width - rect.right;// ��Ҫ���ұ��ƶ�
				// Log.d("Debug", " X��rect.right < width");
			}
		}

		if (rect.height() >= height) {
			if (rect.top > 0) {
				deltaY = -rect.top;
				// Log.d("Debug", " Y��rect.height() >= height");
			}

			if (rect.bottom < height) {
				deltaY = height - rect.bottom;
				// Log.d("Debug", " Y��rect.bottom < height");
			}
		}

		// �����Ȼ�߶�С�ڿؼ��Ŀ�Ȼ�߶ȣ����������
		if (rect.width() < width) {
			deltaX = width / 2f - rect.right + rect.width() / 2f;// x���ƶ��ľ��롣�Ƶ����ĵ�
			// Log.d("Debug", " ���С�ڿؼ��Ŀ��rect.right < width");
		}
		if (rect.height() < height) {
			deltaY = height / 2f - rect.bottom + rect.height() / 2f;
			// Log.d("Debug", " �߶�С�ڿؼ��ĸ߶�rect.height() < height");
		}

		mScaleMatrix.postTranslate(deltaX, deltaY);
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;// ��return true��
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event)) {
			return true;
		}
		mScaleGestureDetector.onTouchEvent(event);// ��mScaleGestureDetector����

		// ��㴥�ص����ĵ�
		float x = 0;
		float y = 0;
		// �õ���㴥�ص�����
		int pointerCount = event.getPointerCount();
		for (int i = 0; i < pointerCount; i++) {
			// �õ����е��x��y���ֵ������pointerCount���õ�����
			x += event.getX(i);
			y += event.getY(i);
		}

		x /= pointerCount;
		y /= pointerCount;

		// �����󴥿ص�����,,һ��ʼ�ǲ���ȵ�
		if (mLastPointCount != pointerCount) {
			isCanDrag = false;
			mLastX = x;
			mLastY = y;
		}

		mLastPointCount = pointerCount;

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// ��¼ƫ����
			float dx = x - mLastX;
			float dy = y - mLastY;

			if (!isCanDrag) {
				isCanDrag = isMoveAction(dx, dy);
			}

			// �ж��Ƿ�����ƶ�
			if (isCanDrag) {
				RectF rectF = getMatrixRectF();// �õ����ź��ͼƬ�Ŀ��
				if (getDrawable() != null) {
					// Ĭ�Ͽ���ȫ�����
					isCheckLeftAndRight = isCheckTopAndBottom = true;

					// ���ͼƬС�ڿؼ��Ŀ��
					if (rectF.width() < getWidth()) {
						isCheckLeftAndRight = false;
						dx = 0;// ����������ƶ�
					}
					// ���ͼƬ�ĸ߶�С�ڿؼ��ĸ߶�
					if (rectF.height() < getHeight()) {
						isCheckTopAndBottom = false;// ���ü�⣬���ƶ���ʱ��
						dy = 0;// �����������ƶ�
					}

					mScaleMatrix.postTranslate(dx, dy);
					checkBorderWhenTranslate();// �ж��ƶ�ʱ���Ƿ񵽱߽�
					setImageMatrix(mScaleMatrix);// �ƶ�
				}
			}
			// ��¼
			mLastX = x;
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mLastPointCount = 0;
			break;
		}

		return true;
	}

	/**
	 * ���ƶ�ʱ�����б߽���
	 */
	private void checkBorderWhenTranslate() {
		RectF rectF = getMatrixRectF();

		float deltaX = 0;
		float deltaY = 0;

		int width = getWidth();
		int height = getHeight();

		if (rectF.top > 0 && isCheckTopAndBottom) {
			Log.d("Debug", " top����0��rectF.top=" + rectF.top);
			deltaY = -rectF.top;
		}

		if (rectF.bottom < height && isCheckTopAndBottom) {
			deltaY = height - rectF.bottom;
			Log.d("Debug", " bottomС�ڸ߶ȣ�rectF.bottom=" + rectF.bottom);
		}
		if (rectF.left > 0 && isCheckLeftAndRight) {
			deltaX = -rectF.left;
			Log.d("Debug", " left����0��rectF.left=" + rectF.left);
		}
		if (rectF.right < width && isCheckLeftAndRight) {
			deltaX = width - rectF.right;
			Log.d("Debug", " �ұ�С�ڿ�ȣ�rectF.right=" + rectF.right);
		}
		mScaleMatrix.postTranslate(deltaX, deltaY);
	}

	/**
	 * �ж��Ƿ����Ӵ����ƶ�
	 * 
	 * @param dx
	 * @param dy
	 * @return
	 */
	private boolean isMoveAction(float dx, float dy) {

		return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;// �Խ��ߵĳ���
	}

}
