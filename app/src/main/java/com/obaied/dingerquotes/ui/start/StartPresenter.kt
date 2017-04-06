package com.obaied.dingerquotes.ui.start

import com.obaied.dingerquotes.data.DataManager
//import com.obaied.dingerquotes.injection.ConfigPersistent
import com.obaied.dingerquotes.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by ab on 19/03/2017.
 */

class StartPresenter
@Inject constructor(dataManager: DataManager,
                    compositeDisposable: CompositeDisposable)
    : BasePresenter<StartMvpView>(dataManager, compositeDisposable) {
}
