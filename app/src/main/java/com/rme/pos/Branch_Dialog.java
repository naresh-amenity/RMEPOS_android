package com.rme.pos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.rme.utils.ConvertDate;
import com.rme.utils.MySession;

import java.util.Date;


public class Branch_Dialog extends Dialog {

    public Activity mActivity;
    TextInputEditText edBranch;
    CardView crdCancel,crdSaveNow;

    public interface onSelectListener {
        public void onCancel();

        public void onDone();

    }

    private onSelectListener onSelectListener;

    public Branch_Dialog(Activity a, onSelectListener onSelectListener) {
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
        setContentView(R.layout.branch_dialog);

        edBranch = findViewById(R.id.edBranch);
        crdCancel = findViewById(R.id.crdCancel);
        crdSaveNow = findViewById(R.id.crdSaveNow);

        crdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onCancel();
            }
        });
        crdSaveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sBranch = edBranch.getText().toString();

                if (sBranch.isEmpty()){
                    edBranch.setError("Please enter branch id");
                    edBranch.requestFocus();
                    return;
                }
                MySession.set(mActivity,sBranch,MySession.Share_BranchID);
                MySession.set(mActivity, ConvertDate.getCurrendDateMMM(),MySession.Share_SetupDate);
                dismiss();
                onSelectListener.onDone();

            }
        });

    }


}