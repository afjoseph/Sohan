package com.obaied.dingerquotes.ui.quote

import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.data.model.RandomImage
import com.obaied.dingerquotes.ui.base.BasePresenter
import com.obaied.dingerquotes.util.Schedulers.SchedulerProvider
import com.obaied.dingerquotes.util.d
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.net.SocketTimeoutException
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