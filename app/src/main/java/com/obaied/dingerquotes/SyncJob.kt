package com.obaied.dingerquotes

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.ui.start.StartActivity
import com.obaied.dingerquotes.util.d
import com.obaied.dingerquotes.util.e
import com.obaied.dingerquotes.util.i
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by ab on 08.08.17.
 */
class SyncJob(val dataManager: DataManager)
    : Job() {
    private val compositeDisposable = CompositeDisposable()

    companion object {
        val TAG = "job_sync_tag"

        fun scheduleJob() {
            JobRequest.Builder(SyncJob.TAG)
                    .setExecutionWindow(2_000L, 5_000L)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .build()
                    .schedule()
        }
    }

    override fun onRunJob(params: Params?): Result {
        d { "onRunJob()" }

        compositeDisposable.add(dataManager.fetchQuotesFromApi(StartActivity.QUOTE_LIMIT_PER_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onNext = { },
                        onError = { e { "Error occurred: ${it.printStackTrace()}" } },
                        onComplete = { i { "done server fetch" } }
                ))

        return Result.SUCCESS
    }

}