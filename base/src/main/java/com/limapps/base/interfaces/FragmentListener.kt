package com.limapps.base.interfaces

import androidx.annotation.AnimRes
import androidx.annotation.IdRes

interface FragmentListener {
    fun onClose(view: FragmentView)

    fun addFragment(fragmentView: FragmentView, @IdRes containerId: Int, addToStack: Boolean = false,  @AnimRes enter: Int = 0, @AnimRes exit: Int = 0)

    fun showErrorFragmentListener(message: String)
}
