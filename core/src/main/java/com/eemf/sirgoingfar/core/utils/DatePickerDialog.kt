package com.eemf.sirgoingfar.core.utils

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.DatePicker

import java.util.Calendar

class DatePickerDialog : DialogFragment(), android.app.DatePickerDialog.OnDateSetListener {

    private var startDate: Long = 0
    private var isMaxDate: Boolean = false
    private var previouslySelectedDate: Calendar? = null
    private var mAppCompatActivity: AppCompatActivity? = null

    private var datePickedListener: OnDatePickedListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (savedInstanceState != null) {
            startDate = savedInstanceState.getLong(Constants.ARG_START_DATE, System.currentTimeMillis())
        } else {
            startDate = System.currentTimeMillis()
        }

        val cal = Calendar.getInstance()
        cal.timeInMillis = startDate
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DATE)

        val dialog = android.app.DatePickerDialog(mAppCompatActivity!!,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, this, year, month, day)

        if (isMaxDate)
            dialog.datePicker.maxDate = cal.time.time
        else
            dialog.datePicker.minDate = cal.time.time

        if (previouslySelectedDate != null)
            dialog.datePicker.init(previouslySelectedDate!!.get(Calendar.YEAR), previouslySelectedDate!!.get(Calendar.MONTH),
                    previouslySelectedDate!!.get(Calendar.DAY_OF_MONTH)) { view, sYear, monthOfYear, dayOfMonth -> onDateSet(view, sYear, monthOfYear, dayOfMonth) }

        val window = dialog.window

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(Constants.ARG_START_DATE, startDate)
        super.onSaveInstanceState(outState)
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                           dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        if (datePickedListener != null)
            datePickedListener!!.onDatePicked(cal.timeInMillis)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        if (datePickedListener != null)
            datePickedListener!!.onCanceled()
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface?) {
        if (datePickedListener != null)
            datePickedListener!!.onCanceled()
        super.onCancel(dialog)
    }

    interface OnDatePickedListener {

        fun onDatePicked(timeInMillis: Long)

        fun onCanceled()
    }

    companion object {

        fun newInstance(appCompatActivity: AppCompatActivity, args: Bundle?, datePickedListener: OnDatePickedListener): DatePickerDialog {
            val frag = DatePickerDialog()
            if (args != null) {
                frag.arguments = args
            }
            frag.datePickedListener = datePickedListener
            frag.mAppCompatActivity = appCompatActivity
            return frag
        }

        fun newInstance(appCompatActivity: AppCompatActivity, startDate: Long, datePickedListener: OnDatePickedListener): DialogFragment {
            val frag = DatePickerDialog()
            frag.datePickedListener = datePickedListener
            frag.startDate = startDate
            frag.mAppCompatActivity = appCompatActivity
            return frag
        }

        fun newInstance(appCompatActivity: AppCompatActivity, startDate: Long, isMaxDate: Boolean, datePickedListener: OnDatePickedListener): DialogFragment {
            val frag = DatePickerDialog()
            frag.datePickedListener = datePickedListener
            frag.startDate = startDate
            frag.isMaxDate = isMaxDate
            frag.previouslySelectedDate = Calendar.getInstance()
            frag.mAppCompatActivity = appCompatActivity
            return frag
        }

        fun newInstance(appCompatActivity: AppCompatActivity, startDate: Long, previouslySelectedDate: Calendar, isMaxDate: Boolean, datePickedListener: OnDatePickedListener): DialogFragment {
            val frag = DatePickerDialog()
            frag.datePickedListener = datePickedListener
            frag.startDate = startDate
            frag.isMaxDate = isMaxDate
            frag.previouslySelectedDate = previouslySelectedDate
            frag.mAppCompatActivity = appCompatActivity
            return frag
        }
    }
}
