package com.limapps.base.helpers

import androidx.databinding.BindingAdapter
import android.graphics.PorterDuff
import android.widget.ImageView

@BindingAdapter("img_color_filter")
fun ImageView.setColorTint(color: Int) = setColorFilter(color, PorterDuff.Mode.SRC_IN)