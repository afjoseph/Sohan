package com.obaied.dingerquotes.ui.base

import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by ab on 18/03/2017.
 */

open class BasePresenter<T : MvpView>
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable) {

    protected var mDataManager: DataManager = dataManager
        private set

    protected var mCompositeDisposable = compositeDisposable
        private set

    var mvpView: T? = null
        private set

    open fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    open fun detachView() {
        mCompositeDisposable.dispose()
        mvpView = null
    }

    val isViewAttached: Boolean
        get() = mvpView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException
        : RuntimeException("Please call Presenter.attachView(MvpView) before"
            + " requesting data to the Presenter")
}