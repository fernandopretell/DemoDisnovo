package com.limapps.base.interfaces

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import com.limapps.base.AndroidProvider
import com.limapps.base.activities.BaseFragmentActivity
import com.limapps.base.models.ServerErrorResponse
import com.limapps.base.others.Callback
import com.limapps.base.others.PermissionConstants
import com.limapps.common.className

abstract class FragmentView : ViewLifecycleFragment(), AndroidProvider {

    private var fragmentListener: FragmentListener? = null

    val hasLocationPermissionGranted: Boolean
        get() {
            return (activity as BaseFragmentActivity).checkMyLocationPermission()
        }

    open fun getName(): String = className()

    fun getBaseActivity(): BaseFragmentActivity {
        return activity as BaseFragmentActivity
    }

    fun setListener(fragmentListener: FragmentListener) {
        this.fragmentListener = fragmentListener
    }

    open fun close() {
        fragmentListener?.onClose(this)
    }

    /*override fun showError(message: String?) {
        getBaseActivity().showError(message.orEmpty())
    }*/

    override fun showError(@StringRes messageRes: Int) {
        getBaseActivity().showError(messageRes)
    }

    override fun showMessage(message: String?) {
        getBaseActivity().showMessage(message)
    }

    override fun showMessage(@StringRes messageRes: Int) {
        getBaseActivity().showMessage(messageRes)
    }

    override fun showLoading(show: Boolean) {
        getBaseActivity().showLoading(show)
    }

    override fun showProgressDialog(message: String?) {
        getBaseActivity().showProgressDialog(message)
    }

    override fun showProgressDialog(@StringRes messageRes: Int) {
        getBaseActivity().showProgressDialog(messageRes)
    }

    override fun dismissProgressDialog() {
        getBaseActivity().dismissProgressDialog()
    }

    override fun startActivityForResult(cls: Class<*>?, requestCode: Int) {
        getBaseActivity().startActivityForResult(cls, requestCode)
    }

    override fun backToolbar() {
        getBaseActivity().backToolbar()
    }

    override fun startActivity(intent: Intent?) {
        getBaseActivity().startActivity(intent)
    }

    override fun startActivityForResult(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {}

    override fun showProgressDialog() {
        getBaseActivity().showProgressDialog()
    }

    override fun tryToCheckout() {

    }

    open fun onBackPressed() {}

    override fun onServerError(error: ServerErrorResponse?, showError: Boolean?) {
        getBaseActivity().onServerError(error, showError)
    }

    fun checkMyLocationPermission(): Boolean {
        return (activity as BaseFragmentActivity).checkMyLocationPermission()
    }

    fun validateCameraPermission(callback: Callback<Unit>) {
        (activity as? BaseFragmentActivity)?.validateCameraPermission(callback)
    }

    fun validateCameraPermission(callback: Callback<Unit>, title: String, messageRationale: String) {
        (activity as? BaseFragmentActivity)?.validateCameraPermission(callback, title, messageRationale)
    }

    fun validateCameraPermission(callback: Callback<Unit>, title: String, messageRationale: String,
                                 cameraPermissions: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        (activity as? BaseFragmentActivity)?.validateCameraPermission(callback, title, messageRationale,
                cameraPermissions = cameraPermissions)
    }

    fun checkAccessLocationPermission(callback: Callback<PermissionConstants>) {
        (activity as? BaseFragmentActivity)?.checkAccessLocationPermission(callback)
    }

    fun validateAudioPermission(callback: Callback<Unit>, audioPermissions: Array<String> = arrayOf(Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.RECORD_AUDIO)) {
        (activity as? BaseFragmentActivity)?.validateAudioPermission(callback, audioPermissions)
    }

    fun showAlertLikeRappi(callback: Callback<Unit>?) {
        (activity as? BaseFragmentActivity)?.showAlertLikeRappi(callback)
    }

    fun showAlertRateOnPlayStore(callback: Callback<Unit>?) {
        (activity as? BaseFragmentActivity)?.showAlertRateOnPlayStore(callback)
    }
}