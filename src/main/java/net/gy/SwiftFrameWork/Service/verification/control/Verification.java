package net.gy.SwiftFrameWork.Service.verification.control;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.gy.SwiftFrameWork.Service.verification.IValiResult;
import net.gy.SwiftFrameWork.Service.verification.IValidater;
import net.gy.SwiftFrameWork.Service.verification.IVerification;
import net.gy.SwiftFrameWork.Service.verification.entity.EditTextValidater;

/**
 * Created by gy on 2016/4/21.
 */
public class Verification implements IVerification{

    private IValidater validater;
    private IValiResult result;

    public Verification(IValidater validater, IValiResult result) {
        this.validater = validater;
        this.result = result;
    }

    @Override
    public void setValidate(TextView textView) {

    }

    @Override
    public void setValidate(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                validater.beforeValidate(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditTextValidater editTextValidater = new EditTextValidater();
                validater.onValidate(editTextValidater);
            }

            @Override
            public void afterTextChanged(Editable s) {
                validater.afterValidate(null);
            }
        });
    }

    @Override
    public void setValidate(ProgressBar progressBar) {

    }

    @Override
    public void setValiResult(IValiResult result) {
        this.result = result;
    }
}
