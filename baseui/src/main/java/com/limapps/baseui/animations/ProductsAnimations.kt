package com.limapps.baseui.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.limapps.common.show

object ProductsAnimations {

    private const val VELOCITY_DROP = 300
    private const val VELOCITY_WEIGHT_BALANCE_DROP = 400

    fun showCheck(check: View, animationListener: AnimatorListenerAdapter? = null) {
        val animatorSet = AnimatorSet()

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(check, View.SCALE_X, 1.2f),
                ObjectAnimator.ofFloat(check, View.SCALE_Y, 1.2f),
                ObjectAnimator.ofFloat(check, View.ALPHA, 1f))

        animatorSet.duration = VELOCITY_WEIGHT_BALANCE_DROP.toLong()

        val animatorSet2 = AnimatorSet()
        animatorSet2.playTogether(
                ObjectAnimator.ofFloat(check, View.SCALE_X, 1f),
                ObjectAnimator.ofFloat(check, View.SCALE_Y, 1f))
        animatorSet2.duration = VELOCITY_DROP.toLong()

        val animatorSet3 = AnimatorSet()
        animatorSet3.playTogether(
                ObjectAnimator.ofFloat(check, View.SCALE_X, 0f),
                ObjectAnimator.ofFloat(check, View.SCALE_Y, 0f),
                ObjectAnimator.ofFloat(check, View.ALPHA, 0f))
        animatorSet3.duration = VELOCITY_WEIGHT_BALANCE_DROP.toLong()

        val animatorSet1 = AnimatorSet()
        animatorSet1.playSequentially(animatorSet, animatorSet2, animatorSet3)
        animatorSet1.interpolator = AccelerateDecelerateInterpolator()
        animatorSet1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animationListener?.onAnimationEnd(null)
            }

            override fun onAnimationStart(animation: Animator) = check.show()
        })

        animatorSet1.start()
    }
}
