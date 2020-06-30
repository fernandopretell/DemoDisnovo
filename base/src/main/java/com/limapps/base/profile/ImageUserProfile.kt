package com.limapps.base.profile

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.jakewharton.picasso.OkHttp3Downloader
import com.limapps.base.R
import com.limapps.base.others.CircleTransform
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.rest.constants.HttpConstants
import com.limapps.common.disposeWithoutFear
import com.squareup.picasso.MemoryPolicy.NO_CACHE
import com.squareup.picasso.MemoryPolicy.NO_STORE
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient

class ImageUserProfile @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val PROFILE_IMAGE_REQUEST_URL = "http://graph.facebook.com/%1\$s/picture?type="
        private const val PROFILE_IMAGE_LARGE_SIZE = "large"
        private const val imageSize = 500
    }

    private lateinit var profileDisposable: Disposable
    private val oneWeekCache = 60 * 60 * 24 * 7
    private var picasso: Picasso? = null

    init {
        scaleType = ImageView.ScaleType.CENTER_CROP
        loadPicture()
    }

    private fun loadPicture() {
        PreferencesManager.userProfile?.let {
            val pictureUrl = when {
                it.urlPicture.isNotBlank() -> it.urlPicture
                //it.socialNetworkId?.isNotBlank() == true -> getFacebookAvatarURLWithSize(it.socialNetworkId)
                else -> ""
            }

            if (pictureUrl.isNotBlank()) {
                getPicassoClient(PreferencesManager.accessToken().get())
                        .load(pictureUrl)
                        .resize(imageSize, imageSize)
                        .centerCrop()
                        .transform(CircleTransform())
                        .memoryPolicy(NO_CACHE, NO_STORE)
                        .error(R.drawable.ic_avatar_new)
                        .into(this)
            }
        }
    }

    private fun logout() {
        PreferencesManager.userProfile?.let {
            if (it.urlPicture?.isNotBlank() == true) {
                Picasso.get().invalidate(it.urlPicture)
            }
        }
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_avatar_new))
    }

    private fun getPicassoClient(connectionProperties: String): Picasso {
        return picasso?.let { it } ?: kotlin.run {
            val picassoClient = OkHttpClient.Builder()
                    .cache(OkHttp3Downloader.createDefaultCache(context))
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                                .addHeader(HttpConstants.AUTHORIZATION, "${HttpConstants.BEARER} $connectionProperties")
                                .addHeader(HttpConstants.CACHE_CONTROL, "${HttpConstants.MAX_AGE}$oneWeekCache")
                                .build()
                        chain.proceed(newRequest)
                    }
                    .build()
            Picasso.Builder(context).downloader(OkHttp3Downloader(picassoClient)).build()
        }.also { picasso = it }
    }

    private fun getFacebookAvatarURLWithSize(facebookID: String): String =
            String.format(PROFILE_IMAGE_REQUEST_URL, facebookID) + PROFILE_IMAGE_LARGE_SIZE

    private fun subscribeToProfile() {
        profileDisposable = ProfileActionsEvents.getActions().subscribe { state ->
            when (state) {
                is ProfileActions.UpdateProfilePhoto -> loadPicture()
                is ProfileActions.Logout -> logout()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        subscribeToProfile()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        profileDisposable.disposeWithoutFear()
    }
}
