package com.limapps.baseui.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Handler
import android.os.Looper
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.limapps.common.disappear
import com.limapps.common.hide
import com.limapps.common.show
import com.limapps.baseui.utils.LanesUtil
import com.limapps.common.setBackgroundView
import java.util.ArrayList

const val XMICRO_DURATION = 70
const val MICRO_DURATION = 100
const val XSHORT_DURATION = 120
const val SHORT_DURATION = 150
const val SHORT_MEDIUM_DURATION = 200
const val MEDIUM_DURATION = 300
const val XMEDIUM_DURATION = 450
const val XXMEDIUM_DURATION = 500
const val LONG_DURATION = 600
const val LARGE_DURATION = 1000
const val XLARGE_DURATION = 2000
const val XXLARGE_DURATION = 3000
const val HUGE_DURATION = 5000
const val SIZE_STANDARD = 500
const val ALPHA_MAX_VALUE = 1
const val ALPHA_MIN_VALUE = 0
const val ALPHA_MAX_RGB_VALUE = 255
const val DEFAULT_OVERSHOOT = 0.5f
const val SCROLL_MULTIPLIER = 1.5f

fun View.fromBottom(duration: Int, listener: AnimatorListenerAdapter? = null) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, LanesUtil.get().screenHeight.toFloat(), 0f).apply {
        setDuration(duration.toLong())
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                show()
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(null)
            }
        })
    }.start()
}

fun View.animateFadeOut(duration: Int, delay: Int = 0, animationListener: AnimatorListenerAdapter? = null) {
    Handler(Looper.getMainLooper()).post {
        ObjectAnimator.ofFloat(this, View.ALPHA, 0f).apply {
            setDuration(duration.toLong())
            startDelay = delay.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator) {
                    show()
                }

                override fun onAnimationEnd(animation: Animator) {
                    hide()
                    animationListener?.onAnimationEnd(animation)
                }
            })
        }.start()
    }
}

fun View.animateFadeIn(alpha: Float = 1f, duration: Int, delay: Int = 0, animationListener: AnimatorListenerAdapter? = null) {
    ViewCompat.setAlpha(this, 0f)
    ObjectAnimator.ofFloat(this, View.ALPHA, alpha).apply {
        setDuration(duration.toLong())
        startDelay = delay.toLong()
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                show()
                animationListener?.onAnimationStart(null)
            }

            override fun onAnimationEnd(animation: Animator) {
                animationListener?.onAnimationEnd(null)
            }
        })
        interpolator = DecelerateInterpolator()
    }.start()
}

@JvmOverloads
fun View.colorTransitionAnim(@ColorInt colorFrom: Int, @ColorInt colorTo: Int, duration: Int = XXMEDIUM_DURATION) {
    val trans = TransitionDrawable(arrayOf(ColorDrawable(colorFrom), ColorDrawable(colorTo)))
    setBackgroundView(trans)
    trans.startTransition(duration)
}

fun View.appearFromTop(duration: Int, delay: Int, bounce: Boolean = false, animatorListenerAdapter: AnimatorListenerAdapter? = null) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            ObjectAnimator.ofFloat(this@appearFromTop, View.TRANSLATION_Y,
                    -LanesUtil.get().screenHeight / 2f, 0f).apply {
                setDuration(duration.toLong())
                startDelay = delay.toLong()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        this@appearFromTop.show()
                        animatorListenerAdapter?.onAnimationStart(animation)
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        animatorListenerAdapter?.onAnimationEnd(animation)
                    }
                })
                interpolator = BounceInterpolator().takeIf { bounce } ?: FastOutSlowInInterpolator()
            }.start()
            return false
        }
    })
}

fun View.appearFromRight(duration: Int, delay: Int = 0, animatorListenerAdapter: AnimatorListenerAdapter? = null) {

    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            ObjectAnimator.ofFloat<View>(this@appearFromRight, View.TRANSLATION_X,
                    this@appearFromRight.measuredWidth * 2f, 0f).apply {
                setDuration(duration.toLong())
                startDelay = delay.toLong()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        this@appearFromRight.show()
                        animatorListenerAdapter?.onAnimationStart(animation)
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        animatorListenerAdapter?.onAnimationEnd(animation)
                    }
                })
                interpolator = AccelerateDecelerateInterpolator()
            }.start()
            return false
        }
    })
}

fun View.getAppearFromBottomWindow(duration: Int, delay: Int, animatorListenerAdapter: AnimatorListenerAdapter): ObjectAnimator {
    return getAppearFromBottom(duration, delay, LanesUtil.get().screenHeight * 1.5f, animatorListenerAdapter)
}

fun View.getAppearFromRightWindow(duration: Int, delay: Int, animatorListenerAdapter: AnimatorListenerAdapter): ObjectAnimator {
    return getAppearFromRight(duration, delay, LanesUtil.get().screenWidth * 1.5f, animatorListenerAdapter)
}

fun View.getAppearFromBottom(duration: Int, delay: Int, animatorListenerAdapter: AnimatorListenerAdapter): ObjectAnimator {
    return getAppearFromBottom(duration, delay, measuredHeight.toFloat(), animatorListenerAdapter)
}

fun View.getAppearFromBottom(duration: Int, delay: Int, initY: Float, animatorListenerAdapter:
AnimatorListenerAdapter? = null): ObjectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, initY, 0f).apply {
    setDuration(duration.toLong())
    startDelay = delay.toLong()
    addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            show()
            animatorListenerAdapter?.onAnimationStart(animation)
        }

        override fun onAnimationEnd(animation: Animator) {
            animatorListenerAdapter?.onAnimationEnd(animation)
        }
    })
    interpolator = AccelerateDecelerateInterpolator()
}

fun View.getAppearFromRight(duration: Int, delay: Int, initX: Float,
                            animatorListenerAdapter: AnimatorListenerAdapter? = null): ObjectAnimator =
        ObjectAnimator.ofFloat(this, View.TRANSLATION_X, initX, 0f).apply {
            setDuration(duration.toLong())
            startDelay = delay.toLong()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    show()
                    animatorListenerAdapter?.onAnimationStart(animation)
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    animatorListenerAdapter?.onAnimationEnd(animation)
                }
            })
            interpolator = AccelerateDecelerateInterpolator()
        }

fun View.appearFromBottomWindow(duration: Int, delay: Int, animatorListenerAdapter: AnimatorListenerAdapter) {
    appearFromBottom(duration, delay, LanesUtil.get().screenHeight * 1.5f, animatorListenerAdapter)
}

fun View.appearFromBottom(duration: Int, delay: Int, initY: Float = measuredHeight.toFloat(),
                          animatorListenerAdapter: AnimatorListenerAdapter? = null) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            getAppearFromBottom(duration, delay, initY, animatorListenerAdapter).start()
            return false
        }
    })
}

fun View.disappearToBottom(duration: Int, delay: Int, animatorListenerAdapter: AnimatorListenerAdapter? = null) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, measuredHeight * 2f).apply {
        setDuration(duration.toLong())
        startDelay = delay.toLong()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                show()
                animatorListenerAdapter?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                disappear()
                animatorListenerAdapter?.onAnimationEnd(animation)
            }
        })
        interpolator = AccelerateDecelerateInterpolator()
    }.start()
}

fun View.disappearToTopWindow(duration: Int, delay: Int, animatorListenerAdapter: AnimatorListenerAdapter) {
    disappearToTop(duration, delay, -LanesUtil.get().screenHeight.toFloat(), animatorListenerAdapter)
}

fun View.disappearToTop(duration: Int, delay: Int, initY: Float = -measuredHeight * 2f,
                        animatorListenerAdapter: AnimatorListenerAdapter?) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, initY).apply {
        setDuration(duration.toLong())
        startDelay = delay.toLong()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                show()
                animatorListenerAdapter?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                disappear()
                animatorListenerAdapter?.onAnimationEnd(animation)
            }
        })
        interpolator = AccelerateDecelerateInterpolator()
    }.start()
}

fun View.disappearToRight(duration: Int, delay: Int, animatorListenerAdapter: AnimatorListenerAdapter?) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            ObjectAnimator.ofFloat<View>(this@disappearToRight, View.TRANSLATION_X, 0f,
                    this@disappearToRight.measuredWidth * 2f).apply {
                setDuration(duration.toLong())
                startDelay = delay.toLong()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        show()
                        animatorListenerAdapter?.onAnimationStart(animation)
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        disappear()
                        animatorListenerAdapter?.onAnimationEnd(animation)
                    }
                })
                interpolator = AccelerateDecelerateInterpolator()
            }.start()
            return false
        }
    })
}

fun View.appearFromBottomAndFadeIn(duration: Int, initY: Float = measuredHeight * 2f, listener: AnimatorListenerAdapter? = null) {
    AnimatorSet().apply {
        playTogether(ObjectAnimator.ofFloat(this@appearFromBottomAndFadeIn, View.TRANSLATION_Y, initY, 0f).apply {
            setDuration(duration.toLong())
        }, ObjectAnimator.ofFloat(this@appearFromBottomAndFadeIn, View.ALPHA, 0f, 1f).apply {
            setDuration(duration.toLong())
        })
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                show()
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(animation)
            }
        })
    }.start()
}

fun View.appearFromTopAndFadeIn(duration: Int, listener: AnimatorListenerAdapter? = null, initY: Int = 1) {
    AnimatorSet().apply {
        playTogether(ObjectAnimator.ofFloat(this@appearFromTopAndFadeIn, View.TRANSLATION_Y,
                0f, initY * measuredHeight * 2f).apply {
            setDuration(duration.toLong())
        })
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                show()
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(animation)
            }
        })
    }.start()
}

fun View.increaseAndDecrease(duration: Int, listener: AnimatorListenerAdapter? = null) {
    AnimatorSet().apply {
        playSequentially(AnimatorSet().apply {
            playTogether(ObjectAnimator.ofFloat(this@increaseAndDecrease, View.SCALE_X, 1f, 1.3f).apply {
                setDuration(duration.toLong())
            }, ObjectAnimator.ofFloat(this@increaseAndDecrease, View.SCALE_Y, 1f, 1.3f).apply {
                setDuration(duration.toLong())
            })
        }, AnimatorSet().apply {
            playTogether(ObjectAnimator.ofFloat(this@increaseAndDecrease, View.SCALE_X, 1.3f, 1f).apply {
                setDuration(duration.toLong())
            }, ObjectAnimator.ofFloat(this@increaseAndDecrease, View.SCALE_Y, 1.3f, 1f).apply {
                setDuration(duration.toLong())
            })
        })
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(animation)
            }
        })
    }.start()
}

fun View.scale(duration: Int, delay: Int = 0, startX: Float, endX: Float, startY: Float = startX,
               endY: Float = startY, listener: AnimatorListenerAdapter? = null) {
    AnimatorSet().apply {
        playTogether(ObjectAnimator.ofFloat(this@scale, View.SCALE_X, startX, endX).apply {
            setDuration(duration.toLong())
        }, ObjectAnimator.ofFloat(this@scale, View.SCALE_Y, startY, endY).apply {
            setDuration(duration.toLong())
        })
        startDelay = delay.toLong()
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                show()
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(animation)
            }
        })
    }.start()
}

fun List<View>.scaleMultipleViews(duration: Int, delay: Int, start: Float, end: Float,
                                  listener: AnimatorListenerAdapter? = null) {

    val objectAnimators = this.toTypedArray().flatMap { view ->
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, start, end)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, start, end)
        listOf(scaleX, scaleY)
    }

    AnimatorSet().apply {
        playTogether(*objectAnimators.toTypedArray<Animator>())
        setDuration(duration.toLong())
        startDelay = delay.toLong()
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                listener?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(animation)
            }
        })
    }.start()
}

fun List<View>.fadeOutViews(duration: Int) {
    forEach {
        it.animateFadeOut(duration)
    }
}

fun List<View>.fadeInViews(duration: Int) {
    forEach {
        it.animateFadeIn(duration = duration)
    }
}

fun View.translate(duration: Int, delay: Int = 0, beginPosition: Float,
                   endPosition: Float, isVertical: Boolean, listener: AnimatorListenerAdapter? = null) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y.takeIf { isVertical }
            ?: View.TRANSLATION_X, beginPosition, endPosition).apply {
        setDuration(duration.toLong())
        startDelay = delay.toLong()
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                show()
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(null)
            }
        })
    }
}

fun View.translateDiagonalAndScale(duration: Int, beginXPosition: Float,
                                   endXPosition: Float, beginYPosition: Float, endYPosition: Float,
                                   initialScale: Float = 1f, finalScale: Float = 1f,
                                   listener: AnimatorListenerAdapter? = null) {
    val horizontalTranslation = ObjectAnimator.ofFloat(this, View.X, beginXPosition, endXPosition)
    val verticalTranslation = ObjectAnimator.ofFloat(this, View.Y, beginYPosition, endYPosition)
    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, initialScale, finalScale)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, initialScale, finalScale)
    AnimatorSet().apply {
        playTogether(horizontalTranslation, verticalTranslation, scaleX, scaleY)
        playTogether(verticalTranslation)
        interpolator = AccelerateDecelerateInterpolator()
        setDuration(duration.toLong())
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                show()
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(animation)
            }
        })
    }.start()
}

fun View.animateScaleTransitionAnimation(initialWidth: Int, initialHeight: Int,
                                         finalPositionX: Int, finalPositionY: Int, duration: Int, listener: AnimatorListenerAdapter) {

    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
    val valueAnimatorWidth = ValueAnimator.ofInt(initialWidth, LanesUtil.get().screenWidth)
    valueAnimatorWidth.addUpdateListener { valueAnimator1 -> layoutParams.width = valueAnimator1.animatedValue as Int }

    val valueAnimatorHeight = ValueAnimator.ofInt(initialHeight, LanesUtil.get().screenHeight)
    valueAnimatorHeight.addUpdateListener { valueAnimator1 -> layoutParams.height = valueAnimator1.animatedValue as Int }

    val valueAnimatorLeftMargin = ValueAnimator.ofInt(layoutParams.leftMargin, finalPositionX)
    valueAnimatorLeftMargin.addUpdateListener { valueAnimator1 -> layoutParams.leftMargin = valueAnimator1.animatedValue as Int }

    val valueAnimatorTopMargin = ValueAnimator.ofInt(layoutParams.topMargin, finalPositionY)
    valueAnimatorTopMargin.addUpdateListener { valueAnimator1 ->
        layoutParams.topMargin = valueAnimator1.animatedValue as Int
        requestLayout()
    }

    AnimatorSet().apply {
        setDuration(duration.toLong())
        playTogether(valueAnimatorWidth, valueAnimatorHeight, valueAnimatorLeftMargin, valueAnimatorTopMargin)
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                listener.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                listener.onAnimationStart(animation)
            }
        })
    }.start()
}

fun List<View>.appearViewsFromBottomAndFadeIn(callback: Animator.AnimatorListener? = null, duration: Int = SHORT_DURATION) {
    val listAnimator = ArrayList<Animator>()
    forEach {
        listAnimator.add(ObjectAnimator.ofFloat(it, View.TRANSLATION_Y, it.measuredHeight * 2f, 0f).apply {
            setDuration(SHORT_DURATION.toLong())
        })
        listAnimator.add(ObjectAnimator.ofFloat(it, View.ALPHA, 0f, 1f).apply {
            setDuration(duration.toLong())
        })
    }
    AnimatorSet().apply {
        playTogether(listAnimator)
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                callback?.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                callback?.onAnimationStart(animation)
            }
        })
    }.start()
}

fun View.animateScaleWithOvershoot(scaleFrom: Float, scaleTo: Float, duration: Int, delay: Int, overshoot: Float = DEFAULT_OVERSHOOT) {
    ViewCompat.setScaleX(this, 0f)
    ViewCompat.setScaleY(this, 0f)

    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, scaleFrom, scaleTo)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleFrom, scaleTo)
    AnimatorSet().apply {
        playTogether(scaleX, scaleY)
        setDuration(duration.toLong())
        startDelay = delay.toLong()
        interpolator = OvershootInterpolator(overshoot)
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                show()
            }
        })
    }.start()
}

fun LottieAnimationView.setLottieAnimationFromAssets(assetFileName: String) {
    LottieComposition.Factory.fromAssetFileName(context, assetFileName, { it?.let { setComposition(it) } })
}

fun LottieAnimationView.setLottieAnimationFromAssets(assetFileName: String, imagesFolder: String) {
    imageAssetsFolder = imagesFolder
    LottieComposition.Factory.fromAssetFileName(context, assetFileName) { lottieComposition ->
        if (lottieComposition != null && lottieComposition.hasImages()) {
            val imageAssetFolder = imageAssetsFolder
            if (imageAssetFolder != null && imageAssetFolder.isNotEmpty()) {
                setComposition(lottieComposition)
            }
        }
    }
}

fun LottieAnimationView.playForwardAnimation() {
    speed = 1f
    playAnimation()
}

fun LottieAnimationView.playReverseAnimation() {
    speed = -1f
    playAnimation()
}

fun LottieDrawable.setLottieAnimationFromAssets(context: Context, assetFileName: String) {
    LottieComposition.Factory.fromAssetFileName(context, assetFileName, { composition = it })
}

fun View.shakeView() {
    val overTimeStart = -2f
    val overTimeEnd = 2f
    val objectAnimator = ObjectAnimator.ofFloat(this, View.ROTATION, overTimeStart, overTimeEnd).apply {
        repeatCount = ObjectAnimator.INFINITE
        repeatMode = ObjectAnimator.REVERSE
    }
    val animatorSet = AnimatorSet()
    animatorSet.play(objectAnimator)
    animatorSet.duration = MICRO_DURATION.toLong()
    animatorSet.interpolator = LinearInterpolator()
    animatorSet.start()

    (tag as? AnimatorSet)?.cancel()
    tag = animatorSet
}

fun View.beatAnimation(animatorListenerAdapter: AnimatorListenerAdapter? = null, scale: Float = 0.2f) {
    val overShoot = OvershootInterpolator()
    val animatorSet = AnimatorSet()
    val bounceAnimX = ObjectAnimator.ofFloat(this, View.SCALE_X, scale, 1f)
    bounceAnimX.duration = MEDIUM_DURATION.toLong()
    bounceAnimX.interpolator = overShoot

    val bounceAnimY = ObjectAnimator.ofFloat(this, View.SCALE_Y, scale, 1f)
    bounceAnimY.duration = MEDIUM_DURATION.toLong()
    bounceAnimY.interpolator = overShoot
    animatorListenerAdapter?.let { bounceAnimY.addListener(it) }

    animatorSet.playTogether(bounceAnimX, bounceAnimY)
    animatorSet.start()
}