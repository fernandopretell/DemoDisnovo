package com.limapps.baseui.views.tooltip

import android.content.Context
import android.graphics.Point
import androidx.core.content.ContextCompat
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.limapps.baseui.R
import com.limapps.baseui.animations.XMEDIUM_DURATION
import com.limapps.baseui.biLet
import com.limapps.baseui.databinding.ViewTooltipBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class TooltipView : LinearLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val binding: ViewTooltipBinding by lazy {
        ViewTooltipBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var timerSubscription: Disposable? = null

    private var tooltip: ToolTip.Companion.Builder by Delegates.notNull()
    private var targetView: View? = null
    private var containerView: ViewGroup? = null

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
    }

    fun bind(tooltip: ToolTip.Companion.Builder) {

        this.tooltip = tooltip
        binding.textViewMessage.text = tooltip.message
        binding.slice.setColor(ContextCompat.getColor(context, R.color.red))

        if (tooltip.backgroundResource != 0) {
            binding.layoutContainer.setBackgroundResource(tooltip.backgroundResource)
        }

        if (tooltip.backgroundColor != 0) {
            binding.layoutContainer.setBackgroundColor(tooltip.backgroundColor)
            binding.slice.setColor(tooltip.backgroundColor)
        }

        when (tooltip.gravity) {
            Gravity.START -> {
                val layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

                removeAllViews()
                orientation = HORIZONTAL
                gravity = Gravity.END
                addView(binding.layoutContainer)
                addView(binding.slice)
                layoutParams.leftMargin = resources.getDimensionPixelSize(R.dimen.tutorial_tooltip_arrow_size)
                binding.layoutContainer.layoutParams = layoutParams
                binding.slice.rotation = -90f
            }
            Gravity.END -> {
                removeAllViews()
                orientation = HORIZONTAL
                addView(binding.slice)
                addView(binding.layoutContainer)
                binding.slice.rotation = 90f
            }
            Gravity.BOTTOM -> {
                removeAllViews()
                orientation = VERTICAL
                addView(binding.slice)
                addView(binding.layoutContainer)
                binding.slice.rotation = 180f
            }
        }

        animateTooltip()

        if (tooltip.onClickDismiss) {
            binding.layoutContainer.isClickable = true
            binding.layoutContainer.setOnClickListener { dismiss() }
        }
    }

    fun setMessage(message: SpannableStringBuilder) {
        binding.textViewMessage.text = message
        placeTooltip()
    }

    fun setMessage(message: String) {
        binding.textViewMessage.text = message
        placeTooltip()
    }

    fun setTargetViews(targetView: View, containerView: ViewGroup) = apply {
        this.targetView = targetView
        this.containerView = containerView
    }

    fun placeTooltip() {
        (targetView to containerView).biLet { targetView, containerView ->
            targetView.post {

                val adjustmentPadding: Float = context.resources.getDimension(R.dimen.spacing_small)
                val resultPoint = Point()
                val pos = IntArray(2)
                val adjustment = (adjustmentPadding * 2).toInt()

                targetView.getLocationOnScreen(pos)
                val targetViewX = pos[0]
                val targetViewY = pos[1]

                containerView.getLocationOnScreen(pos)
                val containerViewX = pos[0]
                val containerViewY = pos[1]

                measure(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)

                resultPoint.x = getXForTooTip(tooltip.gravity, measuredWidth, targetViewX, targetView.width, adjustmentPadding)
                resultPoint.y = getYForTooTip(tooltip.gravity, measuredHeight, targetViewY, targetView.height, containerViewY.toFloat(), tooltip.verticalAdjustment.toFloat())

                parent ?: addViewToContainer()

                x = resultPoint.x.toFloat()
                y = resultPoint.y.toFloat()

                if (tooltip.gravity == Gravity.TOP || tooltip.gravity == Gravity.BOTTOM) {
                    if (x + (measuredWidth) + adjustment > containerView.width) {
                        x = containerView.width.toFloat() - measuredWidth - adjustment
                    }

                    if (x - adjustment < 0 || measuredWidth > containerView.width) {
                        x = adjustment.toFloat()
                    }
                }

                if (tooltip.gravity == Gravity.END && measuredWidth > (containerView.width - targetViewX - targetView.width)) {
                    layoutParams.width = (containerView.width - targetViewX - targetView.width - adjustment)
                    x = targetViewX.toFloat() + targetView.width + adjustmentPadding.toInt()
                }

                if (tooltip.gravity == Gravity.START && measuredWidth > (targetViewX)) {
                    layoutParams.width = (targetViewX - adjustment)
                    x = adjustmentPadding
                }

                post {
                    val toolTipHeight = height
                    y = getYForTooTip(tooltip.gravity, toolTipHeight, targetViewY, targetView.height, containerViewY.toFloat(), tooltip.verticalAdjustment.toFloat()).toFloat()
                    if (tooltip.gravity == Gravity.TOP || tooltip.gravity == Gravity.BOTTOM) {
                        binding.slice.x = targetViewX - x + (targetView.width / 2) - (binding.slice.width / 2)
                    }

                    handleTimer()
                }
            }
        }
    }

    fun addViewToContainer() {
        containerView?.addView(this, layoutParams)
    }

    private fun handleTimer() {
        timerSubscription?.let {
            if (it.isDisposed.not()) {
                it.dispose()
            }
        }

        if (tooltip.dismissTime != -1) {
            timerSubscription = Observable.timer(tooltip.dismissTime.toLong(), TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({ dismiss() })
        }
    }

    private fun animateTooltip() {
        val enterAnimation: Animation = TranslateAnimation(0f, 0f, 0f, (-resources.getDimension(R.dimen.spacing_medium)))
        enterAnimation.duration = XMEDIUM_DURATION.toLong()
        enterAnimation.fillAfter = true
        enterAnimation.repeatMode = Animation.REVERSE
        enterAnimation.repeatCount = -1

        this.startAnimation(enterAnimation)
    }

    private fun getXForTooTip(gravity: Int, toolTipMeasuredWidth: Int, targetViewX: Int, targetViewWidth: Int, adjustment: Float): Int =
            when (gravity) {
                Gravity.START -> targetViewX - toolTipMeasuredWidth - adjustment.toInt()
                Gravity.END -> targetViewX + targetViewWidth + adjustment.toInt()
                else -> targetViewX + (targetViewWidth / 2) - (toolTipMeasuredWidth / 2)
            }

    private fun getYForTooTip(gravity: Int, toolTipMeasuredHeight: Int, targetViewY: Int, targetViewHeight: Int, containerViewY: Float, adjustment: Float): Int =
            when (gravity) {
                Gravity.TOP -> targetViewY - toolTipMeasuredHeight - adjustment.toInt() - containerViewY.toInt()
                Gravity.START, Gravity.END -> targetViewY + (targetViewHeight / 2) - (toolTipMeasuredHeight / 2) - containerViewY.toInt()
                else -> targetViewY + targetViewHeight + adjustment.toInt() - containerViewY.toInt()
            }

    fun dismiss() {
        clearAnimation()
        (parent as? ViewGroup)?.removeView(this)
    }
}
