package com.limapps.base.binding

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.google.android.material.textfield.TextInputLayout
import com.limapps.base.R
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.utils.priceToString
import com.limapps.base.views.ButtonTip
import com.limapps.common.applySpans
import com.limapps.common.tryOrDefault
import com.limapps.common.upperCaseFirstLetter
import io.reactivex.functions.Action
import java.util.Arrays

//TODO review this class methods it was converted from java to kotlin with the IDE tool
object TextViewBinding {

    @JvmStatic
    @BindingAdapter("text_in_tv")
    fun setTextInTextView(textView: TextView, text: String?) {
        if (!TextUtils.isEmpty(text)) {
            textView.visibility = View.VISIBLE
            textView.text = text
        } else {
            textView.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("text_upper_first")
    fun upperCaseFirstLetterInTextView(textView: TextView, text: String) {
        setTextInTextView(textView, text.upperCaseFirstLetter())
    }

    @JvmStatic
    @BindingAdapter("text_drawable_color")
    fun setTextViewDrawableColor(textView: TextView, color: Int) {
        val lineHeight = textView.lineHeight
        textView.compoundDrawablePadding = textView.resources.getDimensionPixelSize(R.dimen.spacing_small)
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {
                if (color != 0) {
                    drawable.colorFilter = PorterDuffColorFilter(
                            ContextCompat.getColor(textView.context, color),
                            PorterDuff.Mode.SRC_IN
                    )
                }
                drawable.setBounds(0, 0, lineHeight, lineHeight)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("text_underline")
    fun underlineText(textView: TextView, text: ObservableField<String>) {
        text.get()?.let {
            val spannable = SpannableString(it)
            spannable.setSpan(UnderlineSpan(), 0, it.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.text = spannable
        }
    }

    @JvmStatic
    @BindingAdapter("text_first", "text_underline", "text_underline_color")
    fun underlineTextView(textView: TextView, firstText: String, secondText: String, textColor: Int) {
        val text = firstText + secondText
        val color = ContextCompat.getColor(textView.context, textColor)
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(color), firstText.length, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(UnderlineSpan(), firstText.length, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable
    }

    @JvmStatic
    @BindingAdapter("text_first", "text_underline", "text_third", "text_underline_color")
    fun underlineTextView(textView: TextView, firstText: String, secondText: String, thirdText: String, textColor: Int) {
        val text = firstText + secondText + thirdText
        val color = ContextCompat.getColor(textView.context, textColor)
        val spannable = SpannableString(text)
        val endPosition = text.length - thirdText.length
        spannable.setSpan(ForegroundColorSpan(color), firstText.length, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(UnderlineSpan(), firstText.length, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable
    }

    @JvmStatic
    @BindingAdapter("text_spannable_first", "text_spannable_second", "text_spannable_color")
    fun spannableTextView(textView: TextView, firstText: String, secondText: String, textColor: Int) {
        val text = firstText + secondText
        val color = ContextCompat.getColor(textView.context.applicationContext, textColor)
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(color), firstText.length, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable
    }

    @JvmStatic
    @BindingAdapter("text_spannable_first", "text_spannable_second", "text_spannable_color", "text_spannable_size")
    fun spannableSizeAndColorTextView(textView: TextView, firstText: String, secondText: String, spannableTextColor: Int, spannableTextSize: Int) {
        val text = "$firstText $secondText"
        val applicationContext = textView.context.applicationContext
        val textColor = ContextCompat.getColor(applicationContext, spannableTextColor)
        val textSize = applicationContext.resources.getDimensionPixelSize(spannableTextSize)
        val spannable = SpannableString(text)
        spannable.applySpans(Arrays.asList(firstText.length, text.length),
                ForegroundColorSpan(textColor), null, null, null, AbsoluteSizeSpan(textSize))
        textView.text = spannable
    }

    @JvmStatic
    @BindingAdapter("text_color_bold_middle_first", "text_color_bold_middle_second", "text_color_bold_middle_third", "text_color_bold_middle_color")
    fun spannableMiddleColorBold(textView: TextView, firstText: String, secondText: String, thirdText: String, textColor: Int) {
        val text = firstText + secondText + thirdText
        val color = ContextCompat.getColor(textView.context, textColor)
        val spannable = SpannableString(text)

        val firstTextLength = firstText.length
        val finishSecondText = firstTextLength + secondText.length
        spannable.applySpans(Arrays.asList(firstTextLength, finishSecondText),
                ForegroundColorSpan(color), StyleSpan(Typeface.BOLD), null)
        textView.text = spannable
    }

    @JvmStatic
    @BindingAdapter("text_bold_color_init_first", "text_bold_color_init_second", "text_bold_color_init_color")
    fun spannableInitBoldColor(textView: TextView, firstText: String, secondText: String, textColor: Int) {
        val spannableString = SpannableString(firstText + secondText)
        val color = ContextCompat.getColor(textView.context, textColor)
        val firstTextLength = firstText.length
        spannableString.applySpans(Arrays.asList(0, firstTextLength),
                ForegroundColorSpan(color), StyleSpan(Typeface.BOLD), null)
        textView.text = spannableString
    }

    @JvmStatic
    @BindingAdapter("text_bold_init_first", "text_bold_init_second")
    fun spannableInitBold(textView: TextView, firstText: String, secondText: String) {
        val spannableString = SpannableString(firstText + secondText)
        spannableString.applySpans(Arrays.asList(0, firstText.length), null, StyleSpan(Typeface.BOLD), null)
        textView.text = spannableString
    }

    @JvmStatic
    @BindingAdapter("text_bold_next_first", "text_bold_next_second")
    fun spannableNextBold(textView: TextView, firstText: String, secondText: String) {
        val spannableString = SpannableString(firstText + secondText)
        val firstTextLength = firstText.length
        spannableString.applySpans(Arrays.asList(firstTextLength, firstTextLength + secondText.length), null, StyleSpan(Typeface.BOLD), null)
        textView.text = spannableString
    }

    @JvmStatic
    @BindingAdapter("text_bold_color_init_first", "text_bold_color_init_second", "text_bold_color_init_color")
    fun spannableNextBoldColor(textView: TextView, firstText: String, secondText: String, textColor: Int) {
        val spannableString = SpannableString("$firstText $secondText")
        val color = ContextCompat.getColor(textView.context, textColor)
        val firstTextLength = firstText.length
        spannableString.applySpans(Arrays.asList(firstTextLength, firstTextLength + secondText.length),
                ForegroundColorSpan(color), StyleSpan(Typeface.BOLD), null)
        textView.text = spannableString
    }

    @JvmStatic
    @BindingAdapter("text_bold_middle_first", "text_bold_middle_second", "text_bold_middle_third")
    fun spannableMiddleBold(textView: TextView, firstText: String, secondText: String, thirdText: String) {
        val spannableString = SpannableString(firstText + secondText + thirdText)
        val firstTextLength = firstText.length
        spannableString.applySpans(Arrays.asList(firstTextLength, firstTextLength + secondText.length), null, StyleSpan(Typeface.BOLD), null)
        textView.text = spannableString
    }

    @JvmStatic
    @BindingAdapter("text_in_money", "percentage", "deduction")
    fun setMoney(textView: TextView, price: Double?, percentage: Double?, hasDeduction: Boolean) {
        if (price != null) {
            var newPrice = price
            if (percentage != null && percentage != 0.0) {
                val pricePercentage = percentage / 100
                newPrice = if (hasDeduction) price * (1 - pricePercentage) else price * (1 + pricePercentage)
            }
            setTextInTextView(textView, newPrice.priceToString())
        }
    }

    @JvmStatic
    @BindingAdapter("text_in_money")
    fun setMoney(textView: TextView, price: Double?) {
        price?.let {
            textView.text = it.priceToString()
        }
    }

    @JvmStatic
    @BindingAdapter("text_res_id", "text_in_money")
    fun setTextWithMoney(textView: TextView, resId: Int, price: Double?) {
        if (price != null && resId != 0) {
            textView.text = ResourcesProvider.getString(resId, price.priceToString())
        }
    }

    @JvmStatic
    @BindingAdapter("text_in_percentage")
    fun setPercentage(textView: TextView, percentage: Double?) {
        if (percentage != null && percentage != 0.0) {
            setTextInTextView(textView, ResourcesProvider
                    .getString(R.string.copy_schedule_percentage, percentage.toFloat().toString()))
        } else {
            textView.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["rotate_drawable", "degrees", "color_tint"], requireAll = false)
    fun setRotateDrawable(textView: TextView, @DrawableRes drawableResource: Int, degrees: Int, color: Int) {
        val drawable: Drawable
        val resourcesProvider = ResourcesProvider
        drawable = if (degrees != 0) {
            val bmpOriginal = resourcesProvider.decodeResource(drawableResource)
            val bmResult = Bitmap.createBitmap(bmpOriginal.width, bmpOriginal.height, Bitmap.Config.ARGB_8888)
            val tempCanvas = Canvas(bmResult)
            tempCanvas.rotate(degrees.toFloat(), (bmpOriginal.width / 2).toFloat(), (bmpOriginal.height / 2).toFloat())
            tempCanvas.drawBitmap(bmpOriginal, 0f, 0f, null)
            resourcesProvider.getBitmapDrawableFromBitmap(bmResult)
        } else {
            resourcesProvider.getDrawable(drawableResource)
        }
        if (color != 0) {
            drawable.mutate()
            drawable.setColorFilter(resourcesProvider.getColor(color), PorterDuff.Mode.MULTIPLY)
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    @JvmStatic
    @BindingAdapter("focus_change")
    fun setFocusListener(editText: EditText, listener: View.OnFocusChangeListener) {
        editText.onFocusChangeListener = listener
    }

    @JvmStatic
    @BindingAdapter("font")
    fun setFont(textView: TextView, @FontRes fontResource: Int) {
        textView.typeface = ResourcesProvider.getFontTypeFace(fontResource)
    }

    @JvmStatic
    @BindingAdapter("errorText")
    fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
        view.error = errorMessage
    }

    @JvmStatic
    @BindingAdapter("errorText")
    fun setErrorMessage(view: TextInputLayout, @StringRes errorId: Int) {
        view.error = view.context.getString(errorId)
    }

    @JvmStatic
    @BindingAdapter("ime_action")
    fun setIMEAction(editTexView: EditText?, action: Action) {
        editTexView?.setOnEditorActionListener { _, actionId, _ ->
            val result = actionId and EditorInfo.IME_MASK_ACTION
            if (result == EditorInfo.IME_ACTION_DONE) {
                action.run()
            }
            false
        }
    }

    @JvmStatic
    @BindingAdapter("tipFormatted")
    fun setFormattedTip(view: ButtonTip, tipValue: String?) {
        view.tipValue.set(tryOrDefault({ tipValue?.toDouble()?.priceToString() }, tipValue))
    }

    @JvmStatic
    @BindingAdapter("tipSelected")
    fun setSelectedTip(view: ButtonTip, selected: Boolean?) {
        view.setSelectedType(selected == true)
    }
}
