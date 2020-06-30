package com.limapps.base.home.actions

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestAddressBus @Inject constructor() {

    private val subject: PublishSubject<RequestAddressActions> by lazy {
        PublishSubject.create<RequestAddressActions>()
    }

    fun publishEvent(event: RequestAddressActions) {
        subject.onNext(event)
    }

    fun getActions(): Observable<RequestAddressActions> {
        return subject.hide()
    }
}