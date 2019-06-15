package com.eemf.sirgoingfar.core.custom

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle

import com.eemf.sirgoingfar.core.R


class ProgressBarDialog(context: Context) : ProgressDialog(context) {

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        setContentView(R.layout.dialog_progress_bar)
    }
}