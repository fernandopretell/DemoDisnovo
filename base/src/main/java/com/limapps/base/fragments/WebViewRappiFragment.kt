package com.limapps.base.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import com.limapps.base.R
import com.limapps.base.deepLink.DIAL_SCHEME
import com.limapps.base.deepLink.RAPPI_SCHEME
import com.limapps.base.deepLink.URL
import com.limapps.base.deepLink.WHATSAPP_URL
import com.limapps.base.interfaces.FragmentView
import com.limapps.base.managers.FileManager
import com.limapps.base.managers.MediaManager
import com.limapps.base.others.CallbackAdapter
import com.limapps.base.others.ExtrasKeys.FOUND_URL
import com.limapps.base.others.ExtrasKeys.POST_DATA
import com.limapps.base.others.ExtrasKeys.SHOW_CUSTOM_LOADERS
import com.limapps.base.others.ExtrasKeys.URL_TAG
import com.limapps.base.others.RappiData
import com.limapps.base.rest.constants.HttpConstants
import com.limapps.base.utils.ImageLoaderUtil
import com.limapps.base.utils.Utils
import com.limapps.baseui.interfaces.ConfirmationBottomCallback
import com.limapps.baseui.views.dialogs.BottomConfirmFragment
import com.limapps.common.applySchedulersOnIo
import com.limapps.common.className
import com.limapps.common.disposeWithoutFear
import com.limapps.common.isNotNullOrBlank
import com.limapps.common.subscribeOnComputation
import com.limapps.common.tryOrPrintException
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection

class WebViewRappiFragment : FragmentView() {

    private val callPermissions = arrayOf(Manifest.permission.INTERNET,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO)
    private val cameraPermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA)
    private val url: String? by lazy { arguments?.getString(URL_TAG) }
    private val foundUrl: String? by lazy { arguments?.getString(FOUND_URL) }
    private val postData: String? by lazy { arguments?.getString(POST_DATA) }
    private val showCustomLoaders: Boolean? by lazy { arguments?.getBoolean(SHOW_CUSTOM_LOADERS) }
    private var webView: WebView? = null
    private var dialog: BottomConfirmFragment? = null
    var listener: RappiWebViewListener? = null
    private val disposable = CompositeDisposable()
    private var redirect = false
    private var firstWebLoaded = false
    private var pageStartedTime = 0L
    private var pageFinishedTime = 0L

    companion object {
        fun newInstance(url: String,
                        foundUrl: String = "",
                        postData: String? = "",
                        showCustomLoaders: Boolean = false): WebViewRappiFragment {
            val fragment = WebViewRappiFragment()
            fragment.arguments = Bundle().apply {
                putString(URL_TAG, url)
                putString(FOUND_URL, foundUrl)
                putString(POST_DATA, postData)
                putBoolean(SHOW_CUSTOM_LOADERS, showCustomLoaders)
            }
            return fragment
        }
    }

    private fun getBytes(data: String?, charset: String?): ByteArray {
        if (data == null) {
            throw IllegalArgumentException("data may not be null")
        }
        if (charset == null || charset.isEmpty()) {
            throw IllegalArgumentException("charset may not be null or empty")
        }
        try {
            return data.toByteArray(charset(charset))
        } catch (e: UnsupportedEncodingException) {
            return data.toByteArray()
        }
    }

    private val cameraPermissionCallback = object : CallbackAdapter<Unit>() {
        override fun onResolve(resolved: Unit) = showDialogToAddAttachment()
    }

    private val callPermisionCallback = object : CallbackAdapter<Unit>() {
        override fun onResolve(resolved: Unit) = initWebCall(webView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_web_view_rappi, container, false)

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }

    override fun onDestroy() {
        disposable.disposeWithoutFear()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = initFragment(view)

    @SuppressLint("SetJavaScriptEnabled")
    private fun initFragment(view: View) {
        webView = view.findViewById(R.id.web_view)

        val customLoaderRappibox = view.findViewById<View>(R.id.custom_loader_rappibox)
        val progressBarLoading = view.findViewById<View>(R.id.progressBar_loading)
        webView?.let {
            it.settings.javaScriptEnabled = true
            it.settings.allowFileAccess = true
            it.settings.allowContentAccess = true
            it.settings.allowFileAccessFromFileURLs = true
            it.settings.domStorageEnabled = true
            it.settings.mediaPlaybackRequiresUserGesture = false

            showProgress(customLoaderRappibox, progressBarLoading)

            it.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    pageStartedTime = System.currentTimeMillis()
                    listener?.onPageStarted()
                    showProgress(customLoaderRappibox, progressBarLoading)
                    if (foundUrl.isNotNullOrBlank() && url?.contains(foundUrl.orEmpty()) == true) {
                        listener?.foundPage()
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!redirect) {
                        pageFinishedTime = System.currentTimeMillis()
                        if (url.isNotNullOrBlank()) {
                            getConnectionInfo(url ?: "")
                        }
                        hideProgress(customLoaderRappibox, progressBarLoading)
                    } else {
                        redirect = false
                    }
                }

                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    super.onReceivedError(view, request, error)
                    hideProgress(customLoaderRappibox, progressBarLoading)
                    listener?.onWebError(firstWebLoaded, true)
                    firstWebLoaded = true
                }

                override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    hideProgress(customLoaderRappibox, progressBarLoading)
                    listener?.onWebError(firstWebLoaded, false)
                    firstWebLoaded = true
                }

                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                    super.onReceivedSslError(view, handler, error)
                    hideProgress(customLoaderRappibox, progressBarLoading)
                    listener?.onWebError(firstWebLoaded, true)
                    firstWebLoaded = true
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val uri: Uri? = request?.url
                    val url: String? = request?.url.toString()
                    redirect = true
                    return if (url != null && (url.contains(URL) || url.contains(RAPPI_SCHEME)) || isWhatsappUrl(url)) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        tryOrPrintException { activity?.startActivity(intent) }
                        true
                    } else if (DIAL_SCHEME == uri?.scheme) {
                        dialPhoneNumber(uri)
                        true
                    } else {
                        url?.let { webUrl ->
                            view?.loadUrl(webUrl)
                        }
                        return true
                    }
                }
            }

            it.webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest) {
                    activity?.runOnUiThread {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            request.grant(request.resources)
                        }
                    }
                }

                override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                    showDialogToAddAttachment()
                    return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                }
            }

            val jsInterface = JavaScriptInterface()
            it.addJavascriptInterface(jsInterface, "app")
            url?.let { webUrl ->
                if (!postData.isNullOrEmpty()) {
                    val extraData = StringBuilder(postData!!)
                            .append("&").append(HttpConstants.APP_VERSION).append("=").append(RappiData.appVersion)
                            .append("&").append(HttpConstants.APP_VERSION_NAME).append("=").append(RappiData.appVersionName)
                    it.postUrl(webUrl, getBytes(extraData.toString(), "UTF-8"))
                } else {
                    it.loadUrl(webUrl)
                }
            }

        }
    }

    private fun getConnectionInfo(url: String) {
        disposable.add(
                Observable.fromCallable {
                    val netUrl = java.net.URL(url)
                    val urlConnection: HttpURLConnection = netUrl.openConnection() as HttpURLConnection
                    urlConnection.connect()
                    val fileSize = urlConnection.contentLength / 1024
                    val responseCode = urlConnection.responseCode
                    urlConnection.disconnect()
                    listener?.onPageFinished(fileSize.toString(), responseCode.toString(), (pageFinishedTime - pageStartedTime).toString())
                }
                        .applySchedulersOnIo()
                        .subscribe({}, { throwable ->
                            throwable.printStackTrace()
                        })
        )
    }

    private fun hideProgress(customLoaderRappibox: View, progressBarLoading: View) {
        if (showCustomLoaders != null && (showCustomLoaders as Boolean)) {
            customLoaderRappibox.visibility = View.GONE
        } else {
            progressBarLoading.visibility = View.GONE
        }
    }

    private fun showProgress(customLoaderRappibox: View, progressBarLoading: View) {
        if (showCustomLoaders != null && (showCustomLoaders as Boolean)) {
            customLoaderRappibox.visibility = View.VISIBLE
        } else {
            progressBarLoading.visibility = View.VISIBLE
        }
    }

    private fun showDialogToAddAttachment() {
        dialog?.let {
            if (it.showsDialog && it.fragmentManager != null) {
                it.dismiss()
            }
        }
        val bundle = Bundle().apply {
            putString(BottomConfirmFragment.TEXT_TITLE, getString(R.string.upload_photo))
            putString(BottomConfirmFragment.TEXT_BUTTON_ONE, getString(R.string.take_photo))
            putString(BottomConfirmFragment.TEXT_BUTTON_TWO, getString(R.string.choose_photo_from_gallery))
            putBoolean(BottomConfirmFragment.CANCEL, true)
        }

        val callback = object : ConfirmationBottomCallback {
            override fun onYesOption() {
                MediaManager.loadCameraIntent(this@WebViewRappiFragment, FileManager.CHAT)
            }

            override fun onNoOption() {
                MediaManager.loadGalleryIntent(this@WebViewRappiFragment)
            }
        }

        dialog = BottomConfirmFragment.newInstance(bundle, callback)

        if (checkPermision(cameraPermissions)) {
            activity?.let {
                dialog?.show(it.supportFragmentManager, className())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        dialog.let {
            it?.dismiss()
        }
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                MediaManager.REQUEST_IMAGE_CAPTURE -> onResultCamera()
                MediaManager.REQUEST_LOAD_IMAGE -> onResultGallery(data)
            }
        }
    }

    private fun onResultGallery(data: Intent?) {
        data?.let {
            val attachmentPath = this.context?.let { it1 -> ImageLoaderUtil.getGalleryPath(it1, data) }.toString()
            handleImagePath(attachmentPath)
        }
    }

    private fun onResultCamera() {
        (FileManager.currentPathImages)?.let {
            handleImagePath(it)
        }
    }

    private fun handleImagePath(path: String) {
        context?.let {
            handleImagePath(ImageLoaderUtil.resizeChatImage(
                    it, path,
                    ImageLoaderUtil.DEFAULT_IMAGE_WIDTH,
                    ImageLoaderUtil.DEFAULT_IMAGE_HEIGHT
            ))
        }
    }

    private fun handleImagePath(fileObservable: Observable<String>) {
        disposable.add(
                fileObservable
                        .subscribeOnComputation()
                        .subscribe({ uploadImage(it, webView) }, { it.printStackTrace() })
        )
    }

    override fun getName(): String = WebViewRappiFragment::class.java.name

    interface RappiWebViewListener {
        fun foundPage()
        fun onBackPressed(finish: Boolean)
        fun onOrderSuccessful(orderId: String, placeAt: String)
        fun onWebError(firstWebLoaded: Boolean, isCriticalError: Boolean)
        fun onWebSuccess()
        fun startCheckout(rappiBoxOrder: String)
        fun startNativeCheckout(json: String)
        fun onPageStarted()
        fun onPageFinished(fileSize: String, responseCode: String, loadingTime: String)
        fun setStatusBarColor(color: String, colorLine: String)
    }

    fun dialPhoneNumber(phoneNumberUri: Uri) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = phoneNumberUri
        activity?.let { activity ->
            intent.resolveActivity(activity.packageManager)?.let { activity.startActivity(intent) }
        }
    }

    private fun initWebCall(webView: WebView?) {
        webView?.let {
            it.post {
                it.loadUrl("javascript:voximplant.initAndCall()")
            }
        }
    }

    fun canGoBack() {
        webView?.let { webView ->
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                webView.post {
                    webView.loadUrl("javascript:voximplant.hangupCall()")
                    listener?.onBackPressed(true)
                }
            }
        }
    }

    private fun uploadImage(imagePath: String, webView: WebView?) {
        val base64data = Utils.encodeImageToBase64(imagePath)
        val url = "javascript:webUploader.retrieveImageFromDevice(\"$base64data\")"
        webView?.let {
            it.post {
                it.loadUrl(url)
            }
        }
    }

    private fun checkPermision(permissions: Array<String>): Boolean {
        var permissionOK = false
        permissions.forEach { permission ->
            activity?.let {
                permissionOK = ActivityCompat.checkSelfPermission(it, permission) == PERMISSION_GRANTED
            }
        }
        return permissionOK
    }

    inner class JavaScriptInterface {

        @JavascriptInterface
        fun requestCallPermissions() = validateAudioPermission(callPermisionCallback, callPermissions)

        @JavascriptInterface
        fun requestCameraPermissions() {
            val title: String = getString(R.string.copy_title_external_storage)
            val messageRationale: String = getString(R.string.copy_message_external_storage)
            validateCameraPermission(cameraPermissionCallback, title, messageRationale, cameraPermissions)
        }

        @JavascriptInterface
        fun onWebviewReady() {
            firstWebLoaded = true
            listener?.onWebSuccess()
        }

        @JavascriptInterface
        fun onOrderSuccessful(orderId: String, placeAt: String) {
            listener?.onOrderSuccessful(orderId, placeAt)
        }

        @JavascriptInterface
        fun endCall() = activity?.finish()

        @JavascriptInterface
        fun startCheckout(rappiboxOrder: String) = listener?.startCheckout(rappiboxOrder)

        @JavascriptInterface
        fun startNativeCheckout(json: String) = listener?.startNativeCheckout(json)

        @JavascriptInterface
        fun setStatusBarColor(color: String, colorLine: String) = listener?.setStatusBarColor(color, colorLine)
    }

    private fun isWhatsappUrl(url: String?): Boolean {
        try {
            val parsedUrl = Uri.parse(url)
            return parsedUrl.host == WHATSAPP_URL
        } catch (e: Exception) {
            return false
        }
    }

}