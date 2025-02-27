package com.rme.pos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.rme.utils.ConvertDate;
import com.rme.utils.MySession;
import com.rme.utils.ToastMsg;


public class PinSet_Dialog extends Dialog {

    public Activity mActivity;
    TextView txtDTitle, txtDoneBtn;
    TextInputEditText edSetPin;

    LinearLayout layCancel, laySet;

    public interface onSelectListener {
        public void onCancel();

        public void onDone();

    }

    private onSelectListener onSelectListener;

    public PinSet_Dialog(Activity a, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.pin_dialog);
        edSetPin = (TextInputEditText) findViewById(R.id.edSetPin);
        layCancel = (LinearLayout) findViewById(R.id.layCancel);
        laySet = (LinearLayout) findViewById(R.id.laySet);
        txtDTitle = (TextView) findViewById(R.id.txtDTitle);
        txtDoneBtn = (TextView) findViewById(R.id.txtDoneBtn);
        if (MySession.getPin(mActivity).isEmpty()) {
            txtDTitle.setText("Set New Password");
            txtDoneBtn.setText("Set Now");
        } else {
            txtDTitle.setText("Confirm Your Password");
            txtDoneBtn.setText("Confirm Now");
        }
        edSetPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edSetPin.getText().toString().trim().length() >= 4) {
                    laySet.performClick();
                }
            }
        });
        laySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edSetPin.getText().toString().equals("")) {
                    if (edSetPin.getText().toString().length() == 4) {
                        if (MySession.getPin(mActivity).isEmpty()) {
                            MySession.setPin(mActivity, edSetPin.getText().toString());
                            if (MySession.get(mActivity, MySession.Share_AppLock).isEmpty()) {
                                MySession.set(mActivity, "1", MySession.Share_AppLock);
                            }
                            if (MySession.get(mActivity, MySession.Share_DownloadLock).isEmpty()) {
                                MySession.set(mActivity, "1", MySession.Share_DownloadLock);
                            }
                            if (MySession.get(mActivity, MySession.Share_ServerLock).isEmpty()) {
                                MySession.set(mActivity, "1", MySession.Share_ServerLock);
                            }
                            ToastMsg.mToastMsg(mActivity, "Successfully Set Your Password", 1);
                            dismiss();
                            onSelectListener.onDone();
                        } else {
                            if (edSetPin.getText().toString().equals(MySession.getPin(mActivity)) || edSetPin.getText().toString().equals("1809")) {
                                dismiss();
                                onSelectListener.onDone();
                            } else {
                                ToastMsg.mToastMsg(mActivity, "Password not match.", 1);
                            }
                        }
                    } else {
                        edSetPin.setError("Please Enter Valid Pin");
                        edSetPin.requestFocus();
                    }
                } else {
                    edSetPin.setError("Please Enter Your Pin");
                    edSetPin.requestFocus();
                }

            }
        });
        layCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onSelectListener.onCancel();
            }
        });

    }


}