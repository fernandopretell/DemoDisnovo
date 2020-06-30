package com.limapps.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun androidx.fragment.app.Fragment.requireArguments(): Bundle {
    return arguments ?: throw IllegalStateException("Fragment " + this + " has null arguments.")
}

fun android.app.Fragment.requireArguments(): Bundle {
    return arguments ?: throw IllegalStateException("Fragment " + this + " has null arguments.")
}

fun androidx.fragment.app.FragmentManager.isFragmentDialogVisible(tag: String): Boolean {
    return findFragmentByTag(tag) != null
}