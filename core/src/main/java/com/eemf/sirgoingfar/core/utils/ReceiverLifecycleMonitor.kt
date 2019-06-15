package com.eemf.sirgoingfar.core.utils

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager

class ReceiverLifecycleMonitor(context: Context,
                               private val mReceiver: BroadcastReceiver,
                               private val mIntentFilter: IntentFilter) : LifecycleObserver {
    private val mLocalBroadcastManager: LocalBroadcastManager

    init {
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(context)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun unRegisterReceiver() {
        mLocalBroadcastManager.unregisterReceiver(mReceiver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun registerReceiver() {
        mLocalBroadcastManager.registerReceiver(mReceiver, mIntentFilter)
    }
}
