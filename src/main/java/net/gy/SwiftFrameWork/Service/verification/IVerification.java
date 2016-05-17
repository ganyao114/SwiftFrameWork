package net.gy.SwiftFrameWork.Service.verification;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by gy on 2016/4/21.
 */
public interface IVerification<T> {
    public void setValidate(TextView textView);
    public void setValidate(EditText editText);
    public void setValidate(ProgressBar progressBar);

    public void setValiResult(IValiResult result);
}
