package com.limapps.common

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import io.reactivex.Observable

fun <T> ObservableField<T>.toObservable(): Observable<T> {
    return Observable.create { e ->
        val initialValue = get()
        initialValue?.let {
            e.onNext(initialValue)
        }
        val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: androidx.databinding.Observable, i: Int) {
                get()?.let { e.onNext(it) }
            }
        }
        addOnPropertyChangedCallback(callback)
        e.setCancellable { removeOnPropertyChangedCallback(callback) }
    }
}

fun ObservableBoolean.toObservable(): Observable<Boolean> {
    return Observable.create { e ->
        e.onNext(get())
        val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: androidx.databinding.Observable, i: Int) {
                e.onNext(get())
            }
        }
        addOnPropertyChangedCallback(callback)
        e.setCancellable { removeOnPropertyChangedCallback(callback) }
    }
}

fun ObservableDouble.toObservable(): Observable<Double> {
    return Observable.create { e ->
        e.onNext(get())
        val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: androidx.databinding.Observable, i: Int) {
                e.onNext(get())
            }
        }
        addOnPropertyChangedCallback(callback)
        e.setCancellable { removeOnPropertyChangedCallback(callback) }
    }
}

fun ObservableInt.toObservable(): Observable<Int> {
    return Observable.create { emitter ->
        if (!emitter.isDisposed) {
            emitter.onNext(get())
        }

        val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: androidx.databinding.Observable, propertyId: Int) {
                if (sender == this@toObservable) {
                    emitter.onNext(get())
                }
            }
        }
        addOnPropertyChangedCallback(callback)
        emitter.setCancellable { removeOnPropertyChangedCallback(callback) }
    }
}