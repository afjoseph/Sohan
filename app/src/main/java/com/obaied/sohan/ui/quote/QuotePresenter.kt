package com.joseph.sohan.ui.quote

import com.joseph.sohan.data.DataManager
import com.joseph.sohan.ui.base.BasePresenter
import com.joseph.sohan.util.Schedulers.SchedulerProvider
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