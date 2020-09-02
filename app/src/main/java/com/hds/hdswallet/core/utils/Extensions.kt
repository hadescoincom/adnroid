package com.hds.hdswallet.core.utils

import com.hds.hdswallet.core.helpers.EmptyDisposable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

fun <T : Any?> Observable<T>.subscribeIf(condition: Boolean, onNext: (T) -> Unit): Disposable {
    return if (condition) {
        subscribe(onNext)
    } else {
        EmptyDisposable()
    }
}
