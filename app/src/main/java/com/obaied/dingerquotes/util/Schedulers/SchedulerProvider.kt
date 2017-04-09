package com.obaied.dingerquotes.util.Schedulers

import io.reactivex.Scheduler

/**
 * Created by ab on 09/04/2017.
 * Used for *Scheduler injection* between test classes and app code
 * Reference: https://medium.com/@peter.tackage/an-alternative-to-rxandroidplugins-and-rxjavaplugins-scheduler-injection-9831bbc3dfaf
 *
 * Any code that targets RxJava should use this. Its a layer of abstraction in order to be able to
 * override the multi-threaded approach the app would take and convert it to a single-threaded approach
 * when testing classes. See [AppSchedulerProvider] for app implementation and [TestSchedulerProvider]
 * inside test package for test class implementation
 */

interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun trampoline(): Scheduler
    fun newThread(): Scheduler
    fun io(): Scheduler
}