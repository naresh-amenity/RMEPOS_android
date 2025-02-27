package com.rme.utils;



import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rme.pos.R;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;

/**
 * Created by Rajneesh on 25-Aug-17.
 */

public class ProgressDialog extends Dialog {
    public Context c;
    RippleBackground rippleBackground;
    ImageView imgLogoCenter;

    public ProgressDialog(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progressh_dialog);
        getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        rippleBackground = findViewById(R.id.rippleBackground);
        imgLogoCenter = findViewById(R.id.imgLogoCenter);
        imgLogoCenter.setVisibility(View.INVISIBLE);
        Picasso.get().load(R.mipmap.ic_launcher).transform(new RoundedCornersTransform()).centerCrop().fit().into(imgLogoCenter);

    }

    public void showNow() {
        try {
            if (isShowing()){

            }else{
                show();
                if (rippleBackground != null) {
                    if (!rippleBackground.isRippleAnimationRunning()) {
                        rippleBackground.startRippleAnimation();
                        YoYo.with(Techniques.FadeIn).withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                imgLogoCenter.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                YoYo.with(Techniques.Pulse).withListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

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
                                }).duration(600).repeat(Animation.INFINITE).playOn(imgLogoCenter);

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).duration(500).playOn(imgLogoCenter);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dismissNow() {
        try {
            if (isShowing()){
                dismiss();
            }
            if (rippleBackground != null) {
                if (rippleBackground.isRippleAnimationRunning()) {
                    rippleBackground.stopRippleAnimation();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
