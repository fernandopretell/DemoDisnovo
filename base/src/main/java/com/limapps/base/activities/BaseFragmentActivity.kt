package com.limapps.base.activities

import android.Manifest
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.transition.addListener
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.limapps.base.AndroidProvider
import com.limapps.base.R
import com.limapps.base.interfaces.FragmentListener
import com.limapps.base.interfaces.FragmentView
import com.limapps.base.models.ServerErrorResponse
import com.limapps.base.others.Callback
import com.limapps.base.others.ExtrasKeys
import com.limapps.base.others.ExtrasKeys.CHANGE_LOCATION
import com.limapps.base.others.ExtrasKeys.RESULT_CHANGE_SUCCESS
import com.limapps.base.others.ExtrasKeys.RESULT_FATHER_RESET
import com.limapps.base.others.ExtrasKeys.RESULT_UNAVAILABLE_PRODUCTS
import com.limapps.base.others.ExtrasKeys.RESULT_UNAVAILABLE_STORE
import com.limapps.base.others.PermissionCallback
import com.limapps.base.others.PermissionConstants
import com.limapps.base.others.PermissionResult
import com.limapps.base.others.PermissionState
import com.limapps.base.others.getAddressesIntent
import com.limapps.base.others.getChangeLocationIntent
import com.limapps.base.rest.interceptors.AUTHENTICATION_NEEDED
import com.limapps.base.utils.AddressActions
import com.limapps.base.utils.AddressNotifier
import com.limapps.base.utils.ToastUtil
import com.limapps.base.utils.Utils
import com.limapps.baseui.views.LoadingView
import com.limapps.baseui.views.dialogs.BaseBottomSheet
import com.limapps.common.applySchedulers
import com.limapps.common.className
import com.limapps.common.disposeWithoutFear
import com.limapps.common.hideKeyboard
import com.limapps.common.isMarshmallow
import com.limapps.common.removeFromParent
import com.limapps.common.tryOrPrintException
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

open class BaseFragmentActivity : AppCompatActivity(), FragmentListener, AndroidProvider {

    private var addressCompositeDisposable: Disposable? = null

    private val permissionSubject by lazy { PublishSubject.create<Pair<Int, List<PermissionResult>>>() }

    private val loadingView by lazy { LoadingView(this) }

    private var permissionCallback: PermissionCallback? = null

    private var permissionOnResultCallback: Callback<Unit>? = null

    private var dummyTimerDisposable: Disposable? = null

    var isFloatingButtonEnabled = false

    private var isAccountVerificationBeenShown = false

    //lateinit var firestore:FirebaseFirestore
    //lateinit var firebaseAuth: FirebaseAuth
    protected lateinit var uid : String

    private val rappiBroadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            showVerificationDialog(intent?.extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransitionEnter()
        //firestore = FirebaseFirestore.getInstance()
        //firebaseAuth = FirebaseAuth.getInstance()
        //uid = firebaseAuth.currentUser?.uid!!
        intent?.extras?.apply {
            val isUnavailable = getBoolean(IS_UNAVAILABLE_STORE, false)
            if (isUnavailable) {
                showUnavailableAlertDialog()
            }
        }
    }

    fun firestoreGet(coll: String){

    }

    fun setupAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val animation = buildEnterAnimation()
            window.enterTransition = animation
            window.returnTransition = animation
        } else {
            overridePendingTransition(R.anim.in_from_bottom, 0)
            showViews(AnimatorSet())
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    open fun buildEnterAnimation(): Slide {
        val transition = Slide()
        transition.slideEdge = Gravity.BOTTOM
        transition.duration = 500
        transition.interpolator = FastOutSlowInInterpolator()
        transition.excludeTarget(android.R.id.navigationBarBackground, true)
        transition.excludeTarget(android.R.id.statusBarBackground, true)
        transition.addListener(onStart = {
            val animator = AnimatorSet()
            animator.startDelay = 200
            showViews(animator)
        })
        return transition
    }

    override fun showProgressDialog(message: String?) {
        loadingView.setMessage(message!!)
        if (loadingView.parent == null) {
            addContentView(loadingView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) showProgressDialog() else dismissProgressDialog()
    }

    override fun showProgressDialog(@StringRes idMessage: Int) = showProgressDialog(getString(idMessage))

    override fun dismissProgressDialog() = loadingView.removeFromParent()

    override fun showMessage(message: String?) {
        dismissProgressDialog()
        ToastUtil.showSuccessfulAlert(this, message!!)
    }

    fun showBlackAlert(message: String) {
        ToastUtil.showBlackAlert(this, message)
    }

    override fun showErrorFragmentListener(message: String) {
        dismissProgressDialog()
        ToastUtil.showErrorAlert(this, message)
    }

    /*override fun showError(message: String?) {
        dismissProgressDialog()
        ToastUtil.showErrorAlert(this@BaseFragmentActivity, message)
    }*/

    override fun showError(messageRes: Int) {
        dismissProgressDialog()
        ToastUtil.showErrorAlert(this, getString(messageRes))
    }

    private fun checkUpdateApp() {
        if (forceUpdateApp) {
            val intent = Intent(this, UpdateAppActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun showMessage(@StringRes messageRes: Int) = showMessage(getString(messageRes))

    override fun showProgressDialog() = showProgressDialog(R.string.copy_loading)

    private fun showUnavailableAlertDialog() {
        AlertDialog.Builder(this, R.style.AlertDialogLight)
                .setMessage(R.string.secondary_store_unavailable)
                .setNeutralButton(R.string.secondary_store_continue) { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .show()
    }

    override fun startActivityForResult(cls: Class<*>?, requestCode: Int) = startActivityForResult(Intent(this, cls), requestCode)

    override fun startActivityForResult(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle!!)
        startActivityForResult(intent, requestCode)
    }

    override fun backToolbar() = onBackPressed()

    override fun onPause() {
        //unregisterBroadcastListener()
        super.onPause()
        hideKeyboard(this)
        //addressCompositeDisposable?.disposeWithoutFear()
    }

    override fun onResume() {
        super.onResume()
        //registerBroadcastListener()
        checkUpdateApp()
        //subscribeToAddressNotifier()
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected fun overridePendingTransitionEnter() {
        overridePendingTransition(R.animator.slide_from_left, R.animator.slide_to_right)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected fun overridePendingTransitionExit() {
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left)
    }

    private fun subscribeToAddressNotifier() {
        addressCompositeDisposable = AddressNotifier
                .changeLocationActions()
                .applySchedulers()
                .subscribe { addressChangeLocationActions ->
                    when (addressChangeLocationActions) {
                        is AddressActions.ShowAddress -> {
                            val intent = getAddressesIntent(addressChangeLocationActions.useCurrentLocationShortcut)
                            startActivityForResult(intent, ExtrasKeys.CHANGE_LOCATION)
                        }
                        is AddressActions.ChangeLocation -> {
                            val intent = getChangeLocationIntent(addressChangeLocationActions.storeType, addressChangeLocationActions.source)
                            startActivityForResult(intent, ExtrasKeys.CHANGE_LOCATION)
                        }
                    }
                }
    }

    open fun showViews(animator: AnimatorSet) {}

    fun replaceFragmentView(
            fragment: FragmentView,
            @IdRes containerId: Int,
            addToStack: Boolean = false,
            @AnimRes enter: Int = 0,
            @AnimRes exit: Int = 0
    ) {
        fragment.setListener(this)
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(enter, exit, enter, exit)
            replace(containerId, fragment, fragment.getName())
            if (addToStack) {
                addToBackStack(fragment.getName())
            }
        }.commit()
    }

    fun addFragment(
            fragmentView: FragmentView,
            addToStack: Boolean,
            @AnimRes enter: Int = 0,
            @AnimRes exit: Int = 0
    ) {
        addFragment(fragmentView, R.id.container, addToStack, enter, exit)
    }

    override fun addFragment(
            fragmentView: FragmentView,
            @IdRes containerId: Int,
            addToStack: Boolean,
            @AnimRes enter: Int,
            @AnimRes exit: Int
    ) {
        tryOrPrintException {
            if (!isFragmentInStack(fragmentView)) {

                fragmentView.setListener(this)

                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(enter, exit, enter, exit)
                    add(containerId, fragmentView, fragmentView.getName())
                    if (addToStack) {
                        addToBackStack(fragmentView.getName())
                    }
                }.commit()
            }
        }
    }

    fun removeFragment(fragmentView: Fragment, @AnimRes animExit: Int = 0, allowStateLoss: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(0, animExit)
        ft.remove(fragmentView)
        if (allowStateLoss) {
            ft.commitAllowingStateLoss()
        } else {
            ft.commit()
        }
        supportFragmentManager.popBackStack(fragmentView.className(), androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onClose(view: FragmentView) = super.onBackPressed()

    protected fun isFragmentInStack(fragmentView: FragmentView?): Boolean =
            fragmentView != null && supportFragmentManager.findFragmentByTag(fragmentView.getName()) != null

    fun checkAccessLocationPermission(callback: Callback<PermissionConstants>, skipPrepermission: Boolean = false) {

        val permission = Manifest.permission.ACCESS_FINE_LOCATION

        if (Utils.isPermissionGranted(this, permission)) {
            callback.onResolve(PermissionConstants.ALREADY_ACCEPTED)
        } else {
            requestSinglePermission(permission,
                    getString(R.string.copy_title_location_permission),
                    getString(R.string.copy_message_location_permission),
                    LOCATION_PERMISSIONS_REQUEST,
                    object : PermissionCallback {
                        override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                            val granted = (requestCode == LOCATION_PERMISSIONS_REQUEST
                                    && grantResults.isNotEmpty()
                                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)

                            callback.onResolve(if (granted) PermissionConstants.GRANTED else PermissionConstants.REVOKED)
                        }
                    }, skipPrepermission = skipPrepermission)
        }
    }

    fun validateReadContactsPermission(callback: Callback<Void>) {
        val permission = Manifest.permission.READ_CONTACTS

        if (Utils.isPermissionGranted(this, permission)) {
            callback.onFinish()
        } else {
            requestSinglePermission(permission,
                    getString(R.string.copy_title_contacts_permission),
                    getString(R.string.copy_message_contacts_permission),
                    CONTACTS_PERMISSION_REQUEST,
                    object : PermissionCallback {
                        override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                            if (requestCode == CONTACTS_PERMISSION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                callback.onFinish()
                            } else {
                                callback.onReject(Throwable())
                            }
                        }
                    },
                    false)
        }
    }

    fun validateCallPermission(callback: Callback<Void>) {
        val permission = Manifest.permission.CALL_PHONE

        if (Utils.isPermissionGranted(this, permission)) {
            callback.onFinish()
        } else {
            requestSinglePermission(permission,
                    getString(R.string.copy_title_call_permission),
                    getString(R.string.copy_message_call_permission),
                    CALL_PERMISSION_REQUEST,
                    object : PermissionCallback {
                        override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                            if (requestCode == CALL_PERMISSION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                callback.onFinish()
                            } else {
                                showError(R.string.copy_message_call_permission)
                            }
                        }
                    },
                    false)
        }
    }

    fun validateCameraPermission(callback: Callback<Unit>,
                                 title: String = getString(R.string.copy_title_external_storage),
                                 messageRationale: String = getString(R.string.copy_message_external_storage),
                                 cameraPermissions: Array<String> = arrayOf(Manifest.permission.CAMERA)) {
        if (isMarshmallow()) {
            if (!Utils.isPermissionGranted(this, cameraPermissions)) {
                requestPermissions(cameraPermissions,
                        title,
                        messageRationale,
                        CAMERA_PERMISSION_REQUEST,
                        object : PermissionCallback {
                            override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                                if (requestCode == CAMERA_PERMISSION_REQUEST) {
                                    if (areResultsGranted(grantResults)) {
                                        callback.onResolve(Unit)
                                    } else {
                                        callback.onReject(Exception())
                                    }
                                }
                            }
                        })
            } else {
                callback.onResolve(Unit)
            }
        } else {
            callback.onResolve(Unit)
        }
    }

    fun validateAudioPermission(callback: Callback<Boolean>) {
        val audioPermissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (isMarshmallow()) {
            if (!Utils.isPermissionGranted(this, audioPermissions)) {
                requestPermissions(audioPermissions,
                        getString(R.string.copy_title_record_audio),
                        getString(R.string.copy_message_record_audio),
                        AUDIO_PERMISSION_REQUEST,
                        object : PermissionCallback {
                            override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                                if (requestCode == AUDIO_PERMISSION_REQUEST) {
                                    callback.onResolve(areResultsGranted(grantResults))
                                }
                            }
                        })
            } else {
                callback.onResolve(true)
            }
        } else {
            callback.onResolve(true)
        }
    }

    fun validateAudioPermission(callback: Callback<Unit>, audioPermissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        if (isMarshmallow()) {
            if (!Utils.isPermissionGranted(this, audioPermissions)) {
                requestPermissions(audioPermissions,
                        getString(R.string.copy_title_record_audio),
                        getString(R.string.copy_message_record_audio),
                        AUDIO_PERMISSION_REQUEST,
                        object : PermissionCallback {
                            override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                                if (requestCode == AUDIO_PERMISSION_REQUEST) {
                                    if (areResultsGranted(grantResults)) {
                                        callback.onResolve(Unit)
                                    } else {
                                        callback.onReject(Exception())
                                    }
                                }
                            }
                        })
            } else {
                callback.onResolve(Unit)
            }
        } else {
            callback.onResolve(Unit)
        }
    }

    fun validateStoragePermission(callback: Callback<Unit>) {
        val storagePermissions = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (isMarshmallow()) {
            if (!Utils.isPermissionGranted(this, storagePermissions)) {
                requestSinglePermission(storagePermissions,
                        getString(R.string.copy_title_external_storage),
                        getString(R.string.copy_message_external_storage),
                        STORAGE_PERMISSION_REQUEST,
                        object : PermissionCallback {
                            override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                                if (requestCode == STORAGE_PERMISSION_REQUEST) {
                                    if (areResultsGranted(grantResults)) {
                                        callback.onResolve(Unit)
                                    } else {
                                        callback.onReject(Throwable())
                                    }
                                }
                            }
                        },
                        false)
            } else {
                callback.onResolve(Unit)
            }
        } else {
            callback.onResolve(Unit)
        }
    }

    @SuppressLint("NewApi")
    fun validateOverlayPermission(callback: Callback<Unit>) {
        if (isMarshmallow() && Settings.canDrawOverlays(this).not()) {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogLight)
            builder.setMessage(R.string.copy_message_overlay)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:$packageName"))
                        permissionOnResultCallback = callback
                        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST)
                    }
                    .setNegativeButton(R.string.no) { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        callback.onReject(Throwable())
                    }
            builder.setCancelable(false)
            builder.show()
        } else {
            callback.onResolve(Unit)
        }
    }

    private fun requestSinglePermission(permission: String, title: String, messageRationale: String, requestCode: Int, permissionCallback: PermissionCallback, skipPrepermission: Boolean = false) {
        requestPermissions(arrayOf(permission), title, messageRationale, requestCode, permissionCallback, skipPrepermission)
    }

    private fun requestPermissions(permissions: Array<String>, title: String, messageRationale: String, requestCode: Int, permissionCallback: PermissionCallback, skipPrepermission: Boolean = false) {

        this.permissionCallback = permissionCallback

        if (skipPrepermission.not()) {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogLight)
            builder.setTitle(title)
            builder.setMessage(messageRationale)
            builder.setCancelable(false)
            builder.setNegativeButton(getString(R.string.copy_omit)) { dialogInterface, _ -> permissionDenied(dialogInterface, permissionCallback, requestCode, permissions) }
            builder.setPositiveButton(getString(R.string.copy_accept)) { _, _ -> ActivityCompat.requestPermissions(this@BaseFragmentActivity, permissions, requestCode) }

            builder.show().withCenteredButtons()
        } else {
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        }
    }

    private fun AlertDialog.withCenteredButtons() {
        val negative = getButton(AlertDialog.BUTTON_NEGATIVE)
        val positive = getButton(AlertDialog.BUTTON_POSITIVE)
        val layoutParamsNegative= LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val layoutParamsPositive = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParamsNegative.marginEnd = Utils.convertPixelsToDp(20,applicationContext)
        layoutParamsPositive.marginStart = Utils.convertPixelsToDp(20,applicationContext)
        negative.layoutParams = layoutParamsNegative
        positive.layoutParams = layoutParamsPositive
    }

    fun requestPermissions(permissions: Array<String>,
                           @StringRes title: Int,
                           @StringRes messageRationale: Int,
                           requestCode: Int): Single<List<PermissionResult>> {

        val consumer = Consumer<Disposable> {
            if (shouldShowRequestPermissionRationale(this, permissions)) {
                val builder = AlertDialog.Builder(this, R.style.AlertDialogLight)
                builder.setTitle(title)
                builder.setMessage(messageRationale)
                builder.setCancelable(false)
                builder.setNegativeButton(R.string.base_permission_rationale_skip) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    val result = permissions.map { permission -> PermissionResult(permission, PermissionState.DENIED) }
                    permissionSubject.onNext(Pair(requestCode, result))
                }
                builder.setPositiveButton(R.string.base_permission_rationale_accept) { _, _ ->
                    ActivityCompat.requestPermissions(this@BaseFragmentActivity, permissions, requestCode)
                }

                builder.show()
            } else {
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }

        return permissionSubject
                .filter { it.first == requestCode }
                .map { it.second }
                .firstOrError()
                .doOnSubscribe(consumer)
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun permissionDenied(dialogInterface: DialogInterface, permissionCallback: PermissionCallback, requestCode: Int, permissions: Array<String>) {
        dialogInterface.dismiss()
        permissionCallback.onRequestPermissionResult(requestCode, permissions, intArrayOf(PackageManager.PERMISSION_DENIED))
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        handlePermissionResult(requestCode, permissions, grantResults)

        permissionCallback?.onRequestPermissionResult(requestCode, permissions, grantResults)

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun handlePermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val result = permissions.mapIndexed { index, permission ->
            val result = when (grantResults[index]) {
                PackageManager.PERMISSION_GRANTED -> PermissionState.GRANTED

                else -> {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this@BaseFragmentActivity, permission)) {
                        PermissionState.DENIED
                    } else {
                        PermissionState.COMPLETELY_DENIED
                    }
                }
            }
            PermissionResult(permission, result)
        }

        permissionSubject.onNext(Pair(requestCode, result))
    }

    fun isFragmentDialogVisible(dialogTag: String): Boolean =
            supportFragmentManager.findFragmentByTag(dialogTag) != null

    override fun tryToCheckout() {
    }

    override fun onServerError(error: ServerErrorResponse?, showError: Boolean?) {}

    companion object {

        const val LOCATION_PERMISSIONS_REQUEST = 1
        const val CALL_PERMISSION_REQUEST = 2
        const val CAMERA_PERMISSION_REQUEST = 3
        const val STORAGE_PERMISSION_REQUEST = 4
        const val AUDIO_PERMISSION_REQUEST = 5
        const val CONTACTS_PERMISSION_REQUEST = 6
        const val OVERLAY_PERMISSION_REQUEST = 8
        const val FLOAT_BUTTON_REQUEST = 9
        const val IS_UNAVAILABLE_STORE = "isUnavailable"

        var forceUpdateApp = false

        var isLongDistanceAllowed = false

        lateinit var accountVerificationFragmentClass: Class<out BaseBottomSheet<() -> Unit>>

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        fun areResultsGranted(grantResults: IntArray): Boolean {
            return grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        }

        fun shouldShowRequestPermissionRationale(activity: Activity, permissions: Array<String>): Boolean {
            return permissions.any {
                ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
            }
        }
    }

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CHANGE_LOCATION -> {
                when (resultCode) {
                    RESULT_UNAVAILABLE_STORE -> killActivityResetingFather()
                    RESULT_CHANGE_SUCCESS, RESULT_UNAVAILABLE_PRODUCTS -> resetActivity()
                }

            }
            OVERLAY_PERMISSION_REQUEST -> {
                if (isMarshmallow() && Settings.canDrawOverlays(this)) {
                    permissionOnResultCallback?.onResolve(Unit)
                } else {
                    dummyTimerDisposable = Observable.interval(1, TimeUnit.SECONDS)
                            .take(6)
                            .doFinally {
                                if (isMarshmallow() && Settings.canDrawOverlays(this)) {
                                    permissionOnResultCallback?.onResolve(Unit)
                                } else {
                                    permissionOnResultCallback?.onReject(Throwable())
                                }
                            }
                            .subscribe {
                                if (isMarshmallow() && Settings.canDrawOverlays(this)) {
                                    dummyTimerDisposable?.disposeWithoutFear()
                                    permissionOnResultCallback?.onResolve(Unit)
                                }
                            }
                }
            }
            FLOAT_BUTTON_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    isFloatingButtonEnabled = true
                }
            }
            else -> {
                when (resultCode) {
                    RESULT_FATHER_RESET -> resetActivity()
                    CHANGE_LOCATION -> killActivityResetingFather()
                    else -> super.onActivityResult(requestCode, resultCode, data)
                }
            }
        }
    }

    open fun resetActivity() {
        val intent = this.intent
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    open fun killActivityResetingFather() {
        setResult(RESULT_FATHER_RESET)
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        //dummyTimerDisposable?.disposeWithoutFear()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    open fun buildEnterTransition(): Transition {
        val enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.in_animation)
        enterTransition.excludeTarget(android.R.id.navigationBarBackground, true)
        enterTransition.excludeTarget(android.R.id.statusBarBackground, true)
        return enterTransition
    }

    fun checkMyLocationPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        return Utils.isPermissionGranted(this, permission)
    }

    fun showVerificationDialog(bundle: Bundle?) {
        if (!isAccountVerificationBeenShown) {
            isAccountVerificationBeenShown = true
            val accountVerificationFragment = accountVerificationFragmentClass.getConstructor().newInstance()
            accountVerificationFragment.setCallback {
                isAccountVerificationBeenShown = false
            }
            accountVerificationFragment.arguments = bundle
            accountVerificationFragment.show(supportFragmentManager, accountVerificationFragment.className())
        }
    }

    private fun registerBroadcastListener() {
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(rappiBroadCastReceiver,
                IntentFilter(AUTHENTICATION_NEEDED))
    }

    private fun unregisterBroadcastListener() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(rappiBroadCastReceiver)
    }


    fun showAlertLikeRappi(callback: Callback<Unit>?) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogLight)
        builder.setMessage(R.string.home_do_you_like_rappi)
                .setPositiveButton(R.string.yes) { _, _ ->
                    callback?.onResolve(Unit)
                }
                .setNegativeButton(R.string.no) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    callback?.onFinish()
                }
        builder.show()
    }

    fun showAlertRateOnPlayStore(callback: Callback<Unit>?) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogLight)
        builder.setMessage(R.string.home_rate_on_play_store)
                .setPositiveButton(R.string.yes) { _, _ ->
                    Utils.openPlayStoreOnApp(this@BaseFragmentActivity)
                    callback?.onResolve(Unit)
                }
                .setNegativeButton(R.string.no) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    callback?.onFinish()
                }
        builder.show()
    }
}