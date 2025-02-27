package com.rme.pos;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Confirm_Dialog extends Dialog {

    public Activity mActivity;
    TextView txtDialogTitle, txtDialogDetails, txtNo, txtYes;
    LinearLayout layMain, layNo, layYes;
    View vBG;
    String sTitle = "", sDetails = "", sNo = "", sYes = "";

    public interface onSelectListener {
        public void onYes();

        public void onNo();

    }

    private onSelectListener onSelectListener;

    public Confirm_Dialog(Activity a, String sTitle, String sDetails, String sNo, String sYes, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOGNoAnim);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.sTitle = sTitle;
        this.sDetails = sDetails;
        this.sNo = sNo;
        this.sYes = sYes;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.confirm_dialog);

        layMain = findViewById(R.id.layMain);
        vBG = findViewById(R.id.vBG);
        txtDialogTitle = findViewById(R.id.txtDialogTitle);
        txtDialogDetails = findViewById(R.id.txtDialogDetails);
        txtNo = findViewById(R.id.txtNo);
        txtYes = findViewById(R.id.txtYes);
        layNo = findViewById(R.id.layNo);
        layYes = findViewById(R.id.layYes);
        vBG.setVisibility(View.INVISIBLE);
        layMain.setVisibility(View.INVISIBLE);

        if (!sTitle.equals("")) {
            txtDialogTitle.setText(Html.fromHtml(sTitle));
            txtDialogTitle.setVisibility(View.VISIBLE);
        } else {
            txtDialogTitle.setVisibility(View.GONE);
        }
        if (!sDetails.equals("")) {
            txtDialogDetails.setText(Html.fromHtml(sDetails));
            txtDialogDetails.setVisibility(View.VISIBLE);
        } else {
            txtDialogDetails.setVisibility(View.GONE);
        }
        txtNo.setText(sNo);
        txtYes.setText(sYes);
        if (sNo.equals("")) {
            layNo.setVisibility(View.GONE);
        } else {
            layNo.setVisibility(View.VISIBLE);
        }
        if (sYes.equals("")) {
            layYes.setVisibility(View.GONE);
        } else {
            layYes.setVisibility(View.VISIBLE);
        }


        layNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onNo();
            }
        });
        layYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onYes();
            }
        });

        layMain.requestLayout();
        YoYo.with(Techniques.BounceInUp).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                layMain.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        vBG.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).duration(400).playOn(vBG);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(1000).playOn(layMain);

    }

}