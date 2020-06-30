package com.limapps.base.others

import io.reactivex.subjects.PublishSubject

object BaseActions {
    val actionSubject: PublishSubject<BaseActionModel> by lazy {
        PublishSubject.create<BaseActionModel>()
    }
}
