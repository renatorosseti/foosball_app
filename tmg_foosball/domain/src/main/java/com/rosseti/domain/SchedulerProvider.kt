package com.rosseti.domain

import io.reactivex.Scheduler

interface SchedulerProvider {
    val subscribeOn: Scheduler
    val observeOn: Scheduler
    val newThread: Scheduler
}