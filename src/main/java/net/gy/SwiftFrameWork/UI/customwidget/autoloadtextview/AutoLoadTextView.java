package net.gy.SwiftFrameWork.UI.customwidget.autoloadtextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import net.gy.SwiftFrameWork.R;

/**
 * Created by gy on 2015/12/5.
 */
public class AutoLoadTextView extends TextView{

    private Context mContext;
    private TypedArray mTypedArray;

    public AutoLoadTextView(Context context) {
        super(context);
    }

    public AutoLoadTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.constomTextView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoLoadTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setText(String url){

    }

}
