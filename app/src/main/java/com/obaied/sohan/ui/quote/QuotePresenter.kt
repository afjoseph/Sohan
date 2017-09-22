package com.obaied.sohan.ui.quote

import com.obaied.sohan.data.DataManager
import com.obaied.sohan.ui.base.BasePresenter
import com.obaied.sohan.util.Schedulers.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class QuotePresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable,
                    schedulerProvider: SchedulerProvider)
    : BasePresenter<QuoteMvpView>(dataManager, compositeDisposable, schedulerProvider) {
}