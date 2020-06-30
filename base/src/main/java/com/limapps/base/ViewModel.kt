package com.limapps.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.databinding.BaseObservable
import com.limapps.common.disposeWithoutFear
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class ViewModel : BaseObservable(), LifecycleObserver {

    protected open val disposable = CompositeDisposable()

    val showProgress: PublishSubject<Boolean> = PublishSubject.create<Boolean>()
    val showMessage: PublishSubject<Int> = PublishSubject.create<Int>()
    val showMessageText: PublishSubject<String> = PublishSubject.create<String>()
    val showError: PublishSubject<Int> = PublishSubject.create<Int>()
    val showErrorMessage: PublishSubject<String> = PublishSubject.create<String>()
    val close: PublishSubject<Unit> = PublishSubject.create<Unit>()

    open fun onClickIconHeader() {
        close.onNext(Unit)
    }

    protected open fun subscribeToObservables() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        subscribeToObservables()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        disposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        disposable.disposeWithoutFear()
    }

    fun observableShowProgress(): Observable<Boolean> {
        return showProgress.observeOn(AndroidSchedulers.mainThread())
    }

    fun observableShowMessage(): Observable<Int> {
        return showMessage.observeOn(AndroidSchedulers.mainThread())
    }

    fun observableShowMessageText(): Observable<String> {
        return showMessageText.observeOn(AndroidSchedulers.mainThread())
    }

    fun observableShowError(): Observable<Int> {
        return showError.observeOn(AndroidSchedulers.mainThread())
    }

    fun observableShowErrorMessage(): Observable<String> {
        return showErrorMessage.observeOn(AndroidSchedulers.mainThread())
    }

    fun observableClose(): Observable<Unit> {
        return close.observeOn(AndroidSchedulers.mainThread())
    }
}