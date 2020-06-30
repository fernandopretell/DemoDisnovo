package com.limapps.base.binding

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.limapps.base.R
import com.limapps.base.managers.FileManager
import com.limapps.base.others.ImageOptionsAdapter
import com.limapps.base.utils.ImageLoaderUtil
import com.squareup.picasso.Picasso


@BindingAdapter(value = ["img_url_product", "placeholder"], requireAll = false)
fun ImageView.setImageProductUrl(url: String, placeholder: Int) {

    val fileManager = FileManager
    ImageLoaderUtil.loadImage(object : ImageOptionsAdapter() {
        override val url: String?
            get() = fileManager.getRemotePathProduct(url)

        override var resourcePlaceHolder: Int
            get() = if (placeholder != 0) placeholder else R.drawable.ic_product_placeholder
            set(value) {
                super.resourcePlaceHolder = value
            }
    }, this)
}

@BindingAdapter(value = ["img_url_product", "placeholder", "quality"], requireAll = false)
fun ImageView.setImageProductUrl(url: String?, placeholder: Int, quality: String?) {

    val fileManager = FileManager
    ImageLoaderUtil.loadImage(object : ImageOptionsAdapter() {

        override val url: String?
            get() = fileManager.getRemotePathProduct(url, quality)

        override var resourcePlaceHolder: Int
            get() = if (placeholder != 0) placeholder else R.drawable.ic_product_placeholder
            set(value) {
                super.resourcePlaceHolder = value
            }
    }, this)
}

@BindingAdapter("android:src")
fun ImageView.setImage(resource: Int) {
    setImageResource(resource)
}

@BindingAdapter("img_url_tag")
fun ImageView.setImageTagUrl(imageUrl: String) {
    ImageLoaderUtil.loadBasicImageWithPlaceHolder(imageUrl, R.drawable.circle_tag_placeholder, this)
}


@BindingAdapter(value = ["img_url", "place_holder"], requireAll = false)
fun ImageView.loadBasicImageWithPlaceHolder(url: String, holder: Int) {
    ImageLoaderUtil.loadBasicImageWithPlaceHolder(url, holder, this)
}

fun ImageView.loadBasicImageWithPlaceHolder(url: String?, holder: Int, fadeIn: Boolean) {
    if (url != null) {
        ImageLoaderUtil.loadBasicImageWithPlaceHolder(url, holder, this)
    }
}


@BindingAdapter(value = ["image_url", "place_holder", "error_place_holder"])
fun ImageView.loadImageWithPlaceHolderAndError(url: String, holder: Int, errorPlaceHolder: Int) {
    if (!TextUtils.isEmpty(url)) {
        Picasso.get().load(url).placeholder(holder).error(errorPlaceHolder).into(this)
    } else {
        setImageResource(errorPlaceHolder)
    }
}

@BindingAdapter(value = ["image_url", "place_holder"], requireAll = false)
fun ImageView.loadImageWithPlaceHolder(url: String?, holder: Drawable?) {
    if (!TextUtils.isEmpty(url)) {
        Picasso.get().load(url).placeholder(holder!!).into(this)
    } else {
        setImageDrawable(holder)
    }
}

@BindingAdapter(value = ["imageRadius", "imageUrlRadius"], requireAll = false)
fun ImageView.setImageWithCornerRadius(radiusRsc: Float, image: String) {
    setImageWithCornerRadius(radiusRsc, image, 0)
}

@BindingAdapter(value = ["imageRadius", "imageUrlRadius", "place_holder"], requireAll = false)
fun ImageView.setImageWithCornerRadius(radiusRsc: Float, image: String, holder: Int) {
    val radiusImage = if (radiusRsc == 0f) this.resources.getDimensionPixelSize(R.dimen.spacing_small) else radiusRsc.toInt()
    ImageLoaderUtil.loadImage(object : ImageOptionsAdapter() {
        override val url: String?
            get() = image

        override var resourcePlaceHolder: Int
            get() = if (holder != 0) R.color.transparent else holder
            set(value) {
                super.resourcePlaceHolder = value
            }

        override var transformation: Transformation<Bitmap>?
            get() = RoundedCorners(radiusImage)
            set(value) {
                super.transformation = value
            }
    }, this)
}

@BindingAdapter(value = ["urlImage"])
fun ImageView.loadRemoteImage(urlImage: String?) {
    ImageLoaderUtil.loadImage(object : ImageOptionsAdapter() {
        override val url: String?
            get() = urlImage

        override var resourcePlaceHolder: Int
            get() = R.color.transparent
            set(value) {
                super.resourcePlaceHolder = value
            }
    }, this)
}

@BindingAdapter(value = ["transform_image_url", "place_holder"], requireAll = false)
fun ImageView.setImageWithCircleTransformation(image: String?, holder: Int) {
    ImageLoaderUtil.loadImage(object : ImageOptionsAdapter() {
        override val url: String?
            get() = image

        override var resourcePlaceHolder: Int
            get() = if (holder != 0) holder else R.drawable.new_home_placeholder
            set(value) {
                super.resourcePlaceHolder = value
            }

        override var transformation: Transformation<Bitmap>?
            get() = CircleCrop()
            set(value) {
                super.transformation = value
            }
    }, this)
}

@BindingAdapter(value = ["transform_image_url", "place_holder"], requireAll = false)
fun ImageView.setImageWithCircleTransformation(image: String?, holder: Drawable?) {
    ImageLoaderUtil.loadImage(object : ImageOptionsAdapter() {
        override val url: String?
            get() = image

        override var drawablePlaceHolder: Drawable?
            get() = holder
            set(value) {
                super.drawablePlaceHolder = value
            }

        override var transformation: Transformation<Bitmap>?
            get() = CircleCrop()
            set(value) {
                super.transformation = value
            }
    }, this)
}

@BindingAdapter(value = ["colorFilter"])
fun ImageView.setColorFilter(@ColorRes color: Int) {
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}

@BindingAdapter(value = ["vectorDrawable"], requireAll = false)
fun ImageView.setVectorImage(@DrawableRes drawableRes: Int) {
    setImageResource(drawableRes)
}

@BindingAdapter(value = ["background_drawable"], requireAll = false)
fun View.setVectorView(drawableRes: Drawable) {
    ViewCompat.setBackground(this, drawableRes)
}

fun ImageView.setBackgroundColorToDrawable(@ColorRes color: Int) {
    when (val background = background) {
        is ShapeDrawable -> background.paint.color = ContextCompat.getColor(context, color)
        is GradientDrawable -> background.setColor(ContextCompat.getColor(context, color))
        is ColorDrawable -> background.color = ContextCompat.getColor(context, color)
    }
}

@BindingAdapter("bind:userImageUrl")
fun ImageView.loadUserPicture(url: String) {
    ImageLoaderUtil.loadUserPictureUrlCircleTransformation(url, this)
}