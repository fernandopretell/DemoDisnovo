package com.limapps.baseui.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BeatAnimation {


    public static void beatView(View v) {


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(100);
        animatorSet.playTogether(ObjectAnimator.ofFloat(v, "translationY", 0, -15), ObjectAnimator.ofFloat(v, "scaleX", 1, 1.2f), ObjectAnimator.ofFloat(v, "scaleY", 1, 1.1f));

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet2.setDuration(100);
        animatorSet2.playTogether(ObjectAnimator.ofFloat(v, "translationY", -15, 0), ObjectAnimator.ofFloat(v, "scaleX", 1.2f, 1), ObjectAnimator.ofFloat(v, "scaleY", 1.1f, 1));

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playSequentially(animatorSet, animatorSet2);
        animatorSet1.start();

    }

}