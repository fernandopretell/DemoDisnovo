package com.limapps.base.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


fun ProgressBar.animateProgressBar(currentProgress: Int, maxProgress: Int) {
    isIndeterminate = false
    max = maxProgress
    animate(0, currentProgress, durationInMillis = 500)
}

fun ProgressBar.animateToProgress(toProgress: Int) {
    isIndeterminate = false
    animate(
            progress,
            toProgress,
            durationInMillis = 300
    )
}

fun View.setOnClickListenerWithoutRepeated(): Observable<Any> {
    return RxView
            .clicks(this)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
}

fun View.executeAfterOnClickListenerWithoutRepeated(f: () -> Unit): Disposable {
    return this.setOnClickListenerWithoutRepeated()
            .subscribe({
                f()
            }, Throwable::printStackTrace)
}

private fun ProgressBar.animate(fromProgress: Int,
                                toProgress: Int,
                                durationInMillis: Long = TimeUnit.SECONDS.toMillis(1),
                                delayInMillis: Long = 0) {
    if (progress != toProgress) {
        ValueAnimator.ofInt(fromProgress, toProgress)
                .apply {
                    startDelay = delayInMillis
                    duration = durationInMillis
                    interpolator = AccelerateDecelerateInterpolator()

                    addUpdateListener { animation ->
                        (animation.animatedValue as Int?)?.let {
                            progress = it
                        }
                    }

                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            progress = toProgress
                        }
                    })

                    start()
                }
    }
}