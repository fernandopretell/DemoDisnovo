package com.limapps.base.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Looper
import android.provider.MediaStore
import android.util.Patterns
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.limapps.base.managers.FileManager
import com.limapps.base.others.CallbackAdapter
import com.limapps.base.others.ImageOptions
import io.reactivex.Observable
import java.io.File
import java.io.FileOutputStream
import java.util.regex.Pattern
import kotlin.properties.Delegates

object ImageLoaderUtil {

    private val TAG = ImageLoaderUtil::class.java.name

    const val DEFAULT_IMAGE_WIDTH = 600
    const val DEFAULT_IMAGE_HEIGHT = 600
    private const val GIF_URL_PATTERN = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:gif)"
    private const val GLIDE_ORIGINAL_SIZE = Target.SIZE_ORIGINAL

    private var glide: RequestManager by Delegates.notNull()

    fun init(context: Context) {
        glide = Glide.with(context)
    }

    @JvmOverloads
    fun loadImage(
            imageOptions: ImageOptions,
            imageView: ImageView,
            bitmapNeeded: Boolean = false,
            onBitmapLoaded: ((bitmap: Bitmap?) -> Unit)? = null,
            onLoadFail: (() -> Unit)? = null) {
        val url = imageOptions.url
        if (url.isNullOrEmpty().not() && Patterns.WEB_URL.matcher(url!!).matches()) {
            loadImageMemory(imageView, imageOptions, bitmapNeeded, onBitmapLoaded, onLoadFail)
        } else {
            onLoadFail?.invoke()
            loadPlaceholder(imageOptions, imageView)
        }
    }

    fun loadImageWithCustomPlaceholder(url: String,
                                       imageView: ImageView,
                                       placeholder: Int,
                                       bitmapNeeded: Boolean = false,
                                       onBitmapLoaded: ((bitmap: Bitmap?) -> Unit)? = null,
                                       onLoadFail: (() -> Unit)? = null) {

        val imageOptions = object : ImageOptions() {
            override var url: String? = url
            override var resourcePlaceHolder: Int = placeholder
        }

        loadImage(imageOptions, imageView, bitmapNeeded, onBitmapLoaded, onLoadFail)
    }


    @JvmOverloads
    fun loadBasicImageWithPlaceHolder(
            imageUrl: String?,
            placeholder: Int = 0,
            imageView: ImageView,
            transformation: Transformation<Bitmap>? = null,
            bitmapNeeded: Boolean = false,
            onLoadedBitmap: ((bitmap: Bitmap?) -> Unit)? = null,
            onLoadFail: (() -> Unit)? = null) {

        val imageOptions = object : ImageOptions() {
            override var url: String? = imageUrl
            override var transformation: Transformation<Bitmap>? = transformation
            override var resourcePlaceHolder: Int = placeholder
        }

        glide.clear(imageView)

        loadImage(imageOptions, imageView, bitmapNeeded, onLoadedBitmap, onLoadFail)
    }

    private fun loadPlaceholder(imageOptions: ImageOptions, imageView: ImageView) {
        if (imageOptions.resourcePlaceHolder != -1) {
            val bitmap = decodeSampledBitmapFromResource(imageView.context.resources,
                    imageOptions.resourcePlaceHolder, imageOptions.width, imageOptions.height)
            if (imageOptions.width > 0 && imageOptions.height > 0 && bitmap != null) {
                imageView.setImageBitmap(bitmap)
            } else if (imageOptions.drawablePlaceHolder != null) {
                imageView.setImageDrawable(imageOptions.drawablePlaceHolder)
            } else {
                imageView.setImageResource(imageOptions.resourcePlaceHolder)
            }
        }
    }

    private fun loadImageMemory(
            imageView: ImageView,
            imageOptions: ImageOptions,
            bitmapNeeded: Boolean,
            onBitmapLoaded: ((bitmap: Bitmap?) -> Unit)? = null,
            onLoadFail: (() -> Unit)? = null) {

        val url = imageOptions.url.orEmpty()
        val stringBuilder = StringBuilder(url)

        if (isGifUrl(url).not()) {
           appendWebpFormat(stringBuilder)
            if (imageOptions.width > 0 && imageOptions.height > 0) {
                appendSizeParameter(stringBuilder, imageOptions.width, imageOptions.height)
            }
        }

        val urlToLoad = stringBuilder.toString()
        LogUtil.e(TAG, "Loading image $urlToLoad")

        var requestCreator: RequestBuilder<Drawable> = glide.load(urlToLoad).placeholder(imageOptions.resourcePlaceHolder)
        if (imageOptions.width > 0 && imageOptions.height > 0) {
            requestCreator = requestCreator.override(imageOptions.width, imageOptions.height)
            if (imageOptions.useCenterInside) {
                requestCreator = requestCreator.fitCenter()
            }
        }

        imageOptions.transformation?.let { transformation ->
            requestCreator = requestCreator.transform(transformation)
        }


        val requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                LogUtil.e(TAG, "ERROR Loading $urlToLoad", e)
                onLoadFail?.invoke()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                LogUtil.e(TAG, "LOADED  $urlToLoad")
                if (bitmapNeeded) {
                    resource?.toBitmap()?.let { bitmap ->
                        onBitmapLoaded?.invoke(bitmap)
                    }
                } else {
                    onBitmapLoaded?.invoke(null)
                }
                return false
            }
        }


        requestCreator.listener(requestListener).into(imageView)
    }

    private fun appendSizeParameter(url: StringBuilder, width: Int, height: Int): StringBuilder {

        val parameter = "d=${width}x$height"

        if (url.contains("?")) {
            url.append("&")
        } else {
            url.append("?")
        }

        url.append(parameter)

        return url
    }

    private fun appendWebpFormat(url: StringBuilder): StringBuilder {
        val parameterFormat = "e=webp"
        if (url.contains("?")) {
            url.append("&")
        } else {
            url.append("?")
        }

        url.append(parameterFormat)
        return url
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (inSampleSize != 0 && halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap? {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    fun decodeSampledBitmapFromPath(pathName: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(pathName, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(pathName, options)
    }

    fun getGalleryPath(context: Context, data: Intent?): String {
        var picturePath = ""
        data?.data?.let {
            var cursor: Cursor? = null
            try {
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(it, filePathColumn, null, null, null)
                cursor?.let {
                    it.moveToFirst()
                    val columnIndex = it.getColumnIndex(filePathColumn.first())
                    picturePath = it.getString(columnIndex)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
        }
        return picturePath
    }

    fun resizeChatImage(context: Context, filePath: String, newWidth: Int, newHeight: Int): Observable<String> {
        val defaultQuality = 75

        return Observable.fromCallable {
            val scaledBitmap: Bitmap
            var finalFile: File? = null
            val unscaledBitmap = ScalingUtilities.decodeFile(filePath, newWidth, newHeight, ScalingUtilities.ScalingLogic.FIT)

            if ((unscaledBitmap.width <= newWidth && unscaledBitmap.height <= newHeight).not()) {
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, newWidth, newHeight, ScalingUtilities.ScalingLogic.FIT)
                finalFile = FileManager.createImageFile(context, FileManager.CHAT)

                val fileOutputStream = FileOutputStream(finalFile)
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, defaultQuality, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()

                scaledBitmap.recycle()
            } else {
                unscaledBitmap.recycle()
            }
            return@fromCallable finalFile?.absolutePath ?: filePath
        }
    }

    private fun createCustomTarget(callbackAdapter: CallbackAdapter<Bitmap>,
                                   width: Int? = null,
                                   height: Int? = null): CustomTarget<Bitmap> {
        val bitmapWidth = if (width != null && width > 0) width else GLIDE_ORIGINAL_SIZE
        val bitmapHeight = if (height != null && height > 0) height else GLIDE_ORIGINAL_SIZE


        return object : CustomTarget<Bitmap>(bitmapWidth, bitmapHeight) {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                callbackAdapter.onResolve(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                callbackAdapter.onReject(null)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        }
    }

    fun getBitmapFromUrl(url: String,
                         callbackAdapter: CallbackAdapter<Bitmap>,
                         width: Int? = null,
                         height: Int? = null) {
        val mainThread = Looper.myLooper() == Looper.getMainLooper()

        val target = createCustomTarget(callbackAdapter, width, height)

        if (mainThread) {
            glide.asBitmap().load(url).into(target)
        } else {
            try {
                val bitmapWidth = width ?: GLIDE_ORIGINAL_SIZE
                val bitmapHeight = height ?: GLIDE_ORIGINAL_SIZE
                val bitmap = glide.asBitmap().load(url).fitCenter()
                        .into(bitmapWidth, bitmapHeight)
                        .get()
                target.onResourceReady(bitmap, null)
            } catch (e: Exception) {
                target.onLoadFailed(null)
            }
        }
    }

    fun loadImageUrlCircleTransformation(url: String, imageView: ImageView) {
        loadBasicImageWithPlaceHolder(url, imageView = imageView, transformation = CircleCrop())
    }

    fun loadUserPictureUrlCircleTransformation(url: String, imageView: ImageView) {
        loadBasicImageWithPlaceHolder(url, imageView = imageView, transformation = CircleCrop())
    }

    private fun isGifUrl(url: String): Boolean {
        return Pattern.compile(GIF_URL_PATTERN).matcher(url).matches()
    }
}
