package com.obaied.dingerquotes

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.obaied.dingerquotes.data.DataManager
import com.obaied.dingerquotes.util.d
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ab on 08.08.17.
 */

@Singleton
class MyJobCreator
@Inject constructor(var dataManager: DataManager) : JobCreator {
    override fun create(tag: String?): Job? {
        d { "create(): $tag" }

        when (tag) {
            SyncJob.TAG -> return SyncJob(dataManager)
            else -> return null
        }
    }
}