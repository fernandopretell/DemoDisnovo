package com.limapps.base.utils

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.limapps.baseui.others.OnClickViewListener
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject

object ActionBinding {

    @JvmStatic
    @BindingConversion
    fun toOnClickListener(listener: Action?): View.OnClickListener = object : OnClickViewListener() {
        override fun onClickView(view: View) {
            listener?.run()
        }
    }

    @JvmStatic
    @BindingConversion
    fun toOnAfterTextChangeWatcher(subject: PublishSubject<String>): TextViewBindingAdapter.AfterTextChanged {
        return TextViewBindingAdapter.AfterTextChanged { subject.onNext(it.toString()) }
    }

    @JvmStatic
    @BindingConversion
    fun toOnAfterTextChangeWatcher(listener: Action?): TextViewBindingAdapter.AfterTextChanged {
        return TextViewBindingAdapter.AfterTextChanged { listener?.run() }
    }

    @JvmStatic
    @BindingConversion
    fun toOnClickMenuListener(listener: Action?): Toolbar.OnMenuItemClickListener = object : OnClickMenuListener {
        override var duration: Int = OnClickViewListener.SHORT_DURATION

        override fun onClickItem(id: Int) {
            listener?.run()
        }
    }
    @JvmStatic
    @BindingConversion
    fun booleanToVisibility(visibility: Boolean): Int {
        return if (visibility) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("action_menu")
    fun setMenuOnToolbar(toolbar: Toolbar, listener: Action?) {
        toolbar.setOnMenuItemClickListener(toOnClickMenuListener(listener))
    }

    @JvmStatic
    @BindingAdapter("action_navigation")
    fun setNavigationOnToolbar(toolbar: Toolbar, listener: Action?) {
        toolbar.setNavigationOnClickListener(toOnClickListener(listener))
    }

    @JvmStatic
    @BindingConversion
    fun subjectToListener(subject: PublishSubject<Unit>): View.OnClickListener {
        return View.OnClickListener { subject.onNext(Unit) }
    }

    @JvmStatic
    @BindingConversion
    fun subjectToIdClickListener(subject: PublishSubject<Int>): View.OnClickListener {
        return View.OnClickListener { subject.onNext(it.id) }
    }
}