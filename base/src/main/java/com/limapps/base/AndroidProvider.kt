package com.limapps.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import com.limapps.base.models.ServerErrorResponse

interface AndroidProvider {
    //fun showError(message: String?)
    fun showError(@StringRes messageRes: Int)
    fun showMessage(message: String?)
    fun showMessage(@StringRes messageRes: Int)
    fun showLoading(show: Boolean)
    fun showProgressDialog()
    fun showProgressDialog(message: String?)
    fun showProgressDialog(@StringRes messageRes: Int)
    fun dismissProgressDialog()
    fun startActivity(intent: Intent?)
    fun startActivityForResult(cls: Class<*>?, requestCode: Int)
    fun startActivityForResult(cls: Class<*>?, bundle: Bundle?, requestCode: Int)
    fun startActivityForResult(intent: Intent?, requestCode: Int)
    fun backToolbar()
    fun tryToCheckout()
    fun onServerError(error: ServerErrorResponse?, showError: Boolean?)
}