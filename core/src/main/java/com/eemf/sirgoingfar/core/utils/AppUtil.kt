package com.eemf.sirgoingfar.core.utils

import android.os.Build
import android.view.View

import java.util.concurrent.atomic.AtomicInteger

object AppUtil {

    private val sNextGeneratedId = AtomicInteger(1)

    fun generateViewId(): Int {
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId()
        }

        /*
         Using Android View internal implementation, to be compatible with version < 17.
         Ref: https://gist.github.com/omegasoft7/fdf7225a5b2955a1aba8
         */
        while (true) {
            val result = sNextGeneratedId.get()
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    }

}
