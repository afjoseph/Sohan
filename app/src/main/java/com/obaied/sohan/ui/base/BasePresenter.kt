package com.joseph.sohan.ui.base

import com.joseph.sohan.data.DataManager
import com.joseph.sohan.util.Schedulers.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by ab on 18/03/2017.
 */

open class BasePresenter<T : MvpView>
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable,
                    schedulerProvider: SchedulerProvider) {

    protected var mDataManager: DataManager = dataManager
        private set

    protected var mCompositeDisposable = compositeDisposable
        private set

    protected var mSchedulerProvider = schedulerProvider
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