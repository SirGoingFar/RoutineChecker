package com.eemf.sirgoingfar.core.utils

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 *
 * This class provides the Threading tool in the App
 *
 * @property diskIO handles the disk I/O
 * @property networkIO handles the network I/O
 * @property onMainThread handles the executions on the Worker Thread
 * @property offMainThread handles the executions off the Worker Thread
 * @constructor creates an instance of the AppExecutors class
 *
 * */
class AppExecutors private constructor(private val diskIO: Executor, private val networkIO: Executor, private val onMainThread: Executor, private val offMainThread: Executor) {

    fun diskIO(): Executor {
        return diskIO
    }

    fun onMainThread(): Executor {
        return onMainThread
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun offMainThread(): Executor {
        return offMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {

        // For Singleton instantiation
        private val LOCK = Any()
        private var sInstance: AppExecutors? = null

        val instance: AppExecutors?
            get() {
                if (sInstance == null) {
                    synchronized(LOCK) {
                        sInstance = AppExecutors(Executors.newSingleThreadExecutor(),
                                Executors.newFixedThreadPool(3),
                                MainThreadExecutor(),
                                Executors.newSingleThreadExecutor())
                    }
                }
                return sInstance
            }
    }
}
