package com.limapps.base.utils

import android.transition.Transition

abstract class TransitionListenerAdapter: Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition?) = Unit

    override fun onTransitionResume(transition: Transition?) = Unit

    override fun onTransitionPause(transition: Transition?) = Unit

    override fun onTransitionCancel(transition: Transition?) = Unit

    override fun onTransitionStart(transition: Transition?) = Unit
}