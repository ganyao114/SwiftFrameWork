package net.gy.SwiftFrameWork.UI.customwidget.siderbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.gy.SwiftFrameWork.R;

public class SiderBar extends View {

    private Paint paint = new Paint();
    public static String[] SideIndex = {"热门", "A", "B", "C", "D", "E", "F", "G", "H", "IAuPlayerService", "G", "K", "L", "M", "N", "O"
            , "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private SiderBarListener listener;

    public SiderBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public SiderBar(Context context, AttributeSet attrs) {
        // TODO Auto-generated constructor stub
        super(context, attrs);
    }

    public void setOnSlideListener(SiderBarListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(35);
        int height = getHeight();
        int width = getWidth();
        int eachheight = height / SideIndex.length;
        for (int i = 0; i < SideIndex.length; i++) {
            float x = width / 2 - paint.measureText(SideIndex[i]) / 2;
            float y = (i + 1) * eachheight;
            canvas.drawText(SideIndex[i], x, y, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        final int action = event.getAction();
        final float y = event.getY();
        final int c = (int) (y / getHeight() * SideIndex.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundResource(0);
                invalidate();
                break;
            default:
                setBackgroundResource(R.drawable.siderbar_background);
                if (c > 0 && c < SideIndex.length) {
                    listener.OnSlide(SideIndex[c]);
                }
                invalidate();
                break;
        }
        return true;
    }

}
