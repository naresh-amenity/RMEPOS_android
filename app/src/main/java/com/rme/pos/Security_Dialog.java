package com.rme.pos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.rme.utils.ConvertDate;
import com.rme.utils.MySession;


public class Security_Dialog extends Dialog {

    public Activity mActivity;
    CheckBox chkAppLock, chkDownloadLock, chkNetworkLock;
    LinearLayout layRestPassword;
    CardView crdCancel;

    public interface onSelectListener {
        public void onCancel();

        public void onReset();

    }

    private onSelectListener onSelectListener;

    public Security_Dialog(Activity a, onSelectListener onSelectListener) {
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
        setContentView(R.layout.security_dialog);

        chkAppLock = findViewById(R.id.chkAppLock);
        chkDownloadLock = findViewById(R.id.chkDownloadLock);
        chkNetworkLock = findViewById(R.id.chkNetworkLock);
        crdCancel = findViewById(R.id.crdCancel);
        layRestPassword = findViewById(R.id.layRestPassword);

        crdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onCancel();
            }
        });
        layRestPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySession.setPin(mActivity, "");
                dismiss();
                onSelectListener.onReset();

            }
        });
        if (MySession.get(mActivity, MySession.Share_AppLock).equals("1")) {
            chkAppLock.setChecked(true);
        } else {
            chkAppLock.setChecked(false);
        }
        if (MySession.get(mActivity, MySession.Share_DownloadLock).equals("1")) {
            chkDownloadLock.setChecked(true);
        } else {
            chkDownloadLock.setChecked(false);
        }
        if (MySession.get(mActivity, MySession.Share_ServerLock).equals("1")) {
            chkNetworkLock.setChecked(true);
        } else {
            chkNetworkLock.setChecked(false);
        }
        chkAppLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MySession.set(mActivity,"1",MySession.Share_AppLock);
                }else{
                    MySession.set(mActivity,"0",MySession.Share_AppLock);
                }
            }
        });
        chkDownloadLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MySession.set(mActivity,"1",MySession.Share_DownloadLock);
                }else{
                    MySession.set(mActivity,"0",MySession.Share_DownloadLock);
                }
            }
        });
        chkNetworkLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MySession.set(mActivity,"1",MySession.Share_ServerLock);
                }else{
                    MySession.set(mActivity,"0",MySession.Share_ServerLock);
                }
            }
        });
    }


}