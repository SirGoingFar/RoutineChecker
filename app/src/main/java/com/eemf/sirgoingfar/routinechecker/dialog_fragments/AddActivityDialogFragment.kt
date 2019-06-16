package com.eemf.sirgoingfar.routinechecker.dialog_fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.eemf.sirgoingfar.core.custom.CustomSpinnerArrayAdapter
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.Frequency
import com.eemf.sirgoingfar.database.Routine
import com.eemf.sirgoingfar.routinechecker.R
import java.io.Serializable
import java.lang.String.format
import java.util.*

class AddActivityDialogFragment : BaseDialogFragment(), TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.tv_header_text)
    @JvmField
    var headerText: TextView? = null

    @BindView(R.id.et_routine_title)
    @JvmField
    var title: EditText? = null

    @BindView(R.id.et_routine_desc)
    @JvmField
    var desc: EditText? = null

    @BindView(R.id.tv_routine_start_time)
    @JvmField
    var time: TextView? = null

    @BindView(R.id.spinner_routine_freq)
    @JvmField
    var freqSpinner: Spinner? = null

    @BindView(R.id.btn_add_routine)
    @JvmField
    var btnAddActivity: Button? = null

    @BindView(R.id.btn_add_another_routine)
    @JvmField
    var btnAddAnother: Button? = null

    private var selectedFrequencyIndex: Int = 0
    private var selectedRoutineTime: Calendar = Calendar.getInstance()
    private var titleText: String? = null
    private var descText: String? = null
    private var isEdit: Boolean = false
    private var is24Hour: Boolean = false
    private var isTimeSelected: Boolean = false
    private lateinit var mListener: OnSaveOccurrence
    private var mRoutine: Routine? = null
    private lateinit var mTimePicker: TimePickerDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_add_routine, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        if (savedInstanceState != null) {
            selectedRoutineTime.timeInMillis = savedInstanceState.getLong(Constants.ARG_TIME)
            titleText = savedInstanceState.getString(Constants.ARG_ROUTINE_TITLE)
            descText = savedInstanceState.getString(Constants.ARG_ROUTINE_DESC)
            selectedRoutineTime.time = Date(savedInstanceState.getLong(Constants.ARG_TIME))
            isTimeSelected = savedInstanceState.getBoolean(Constants.ARG_IS_TIME_SELECTED, false)
            selectedFrequencyIndex = savedInstanceState.getInt(Constants.ARG_ROUTINE_PRIORITY)
            mListener = savedInstanceState.getSerializable(Constants.ARG_LISTENER) as OnSaveOccurrence

            if (isEdit)
                mRoutine = savedInstanceState.getParcelable<Parcelable>(Constants.ARG_CURRENT_ROUTINE) as Routine
        }

        val dialog = super.onCreateDialog(savedInstanceState)

        //get device width and height
        val metrics = resources.displayMetrics
        val deviceWidth = metrics.widthPixels
        val deviceHeight = metrics.heightPixels

        val width = (deviceWidth * 0.9).toInt()
        val height: Int = (deviceHeight * 0.6).toInt()

        val window = dialog.window
        if (window != null) {

            //remove window title
            window.requestFeature(Window.FEATURE_NO_TITLE)

            //set Width and Height
            dialog.setContentView(R.layout.dialog_add_routine)
            window.setLayout(width, height)

            //set window layout
            dialog.setContentView(R.layout.dialog_add_routine)
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
    }

    private fun initializeViews(container: View) {
        headerText = container.findViewById(R.id.tv_header_text)
        title = container.findViewById(R.id.et_routine_title)
        desc = container.findViewById(R.id.et_routine_desc)
        time = container.findViewById(R.id.tv_routine_start_time)
        freqSpinner = container.findViewById(R.id.spinner_routine_freq)
        btnAddActivity = container.findViewById(R.id.btn_add_routine)
        btnAddAnother = container.findViewById(R.id.btn_add_another_routine)

        if (isEdit) {
            val cal = Calendar.getInstance()
            cal.time = mRoutine?.date
            var hr = if (is24Hour) cal.get(Calendar.HOUR_OF_DAY) else cal.get(Calendar.HOUR)
            if (!is24Hour && cal.get(Calendar.AM_PM) == Calendar.PM)
                hr += 12
            val min = cal.get(Calendar.MINUTE)

            selectedRoutineTime = Calendar.getInstance()
            selectedRoutineTime.time = mRoutine?.date

            title?.setText(mRoutine?.name)
            desc?.setText(mRoutine?.desc)
            time?.text = getTimeText(is24Hour, hr, min)
            selectedFrequencyIndex = mRoutine?.freqId?.plus(1) ?: 0
            headerText!!.text = appCompatActivity.getString(R.string.text_edit_routine)
            btnAddAnother?.visibility = View.GONE
            btnAddActivity?.text = appCompatActivity.getString(R.string.text_done)
            isTimeSelected = true
        }

        val freqList = ArrayList<String>()
        freqList.add(appCompatActivity.getString(R.string.text_select_a_frequency))
        freqList.add(Frequency.HOURLY.label)
        freqList.add(Frequency.DAILY.label)
        freqList.add(Frequency.WEEKLY.label)
        freqList.add(Frequency.MONTHLY.label)
        freqList.add(Frequency.YEARLY.label)

        val spinnerArrayAdapter = CustomSpinnerArrayAdapter(appCompatActivity, R.layout.spinner_selected_item_look, freqList)
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinnerArrayAdapter.setSelectedItemColor(R.color.colorBlack)
        spinnerArrayAdapter.setUnselectableItemColor(R.color.colorBlack_transluscent)
        spinnerArrayAdapter.setUnselectablePosition(0)
        spinnerArrayAdapter.setSelectedItemTextSize(16f)
        freqSpinner?.adapter = spinnerArrayAdapter
        freqSpinner?.prompt = appCompatActivity.getString(R.string.text_select_a_frequency)
        freqSpinner?.setSelection(selectedFrequencyIndex)
        freqSpinner?.onItemSelectedListener = this@AddActivityDialogFragment

        if (!TextUtils.isEmpty(titleText))
            title?.setText(titleText)

        if (!TextUtils.isEmpty(descText))
            desc?.setText(descText)

        time?.setOnClickListener {
            val hour = if (is24Hour) selectedRoutineTime.get(Calendar.HOUR_OF_DAY) else selectedRoutineTime.get(Calendar.HOUR)

            mTimePicker = TimePickerDialog(appCompatActivity, this, hour,
                    selectedRoutineTime.get(Calendar.MINUTE), is24Hour)

            mTimePicker.show()
        }

        if (isTimeSelected)
            time?.text = getTimeText(is24Hour, selectedRoutineTime.get(Calendar.HOUR_OF_DAY), selectedRoutineTime.get(Calendar.MINUTE))

        btnAddActivity?.setOnClickListener { saveRoutine(true) }

        btnAddAnother?.setOnClickListener { saveRoutine(false) }
    }

    private fun saveRoutine(dismissDialog: Boolean) {

        val routine = getRoutineObject() ?: return

        mListener.onSaveRoutine(routine, isEdit)

        if (dismissDialog) {
            if (isAdded)
                dismiss()
        } else {
            resetViews()
        }
    }

    private fun getRoutineObject(): Routine? {

        titleText = title?.text.toString()

        if (TextUtils.isEmpty(titleText)) {
            title?.error = "Title cannot be empty"
            return null
        }

        descText = desc?.text.toString()
        if (TextUtils.isEmpty(descText)) {
            desc?.error = "Description cannot be empty"
            return null
        }

        if (!isTimeSelected) {
            toast("Select time")
            return null
        }

        if (selectedFrequencyIndex < 0) {
            toast("Select a frequency")
            return null
        }

        if (isEdit) {
            mRoutine?.name = titleText
            mRoutine?.desc = descText
            mRoutine?.freqId = selectedFrequencyIndex
            mRoutine?.date = selectedRoutineTime.time
            return mRoutine
        } else {
            return Routine(titleText, descText, selectedFrequencyIndex, selectedRoutineTime.time)
        }
    }

    private fun resetViews() {
        title?.setText("")
        desc?.setText("")
        time?.text = ""
        freqSpinner?.setSelection(0)

        if (isEdit)
            selectedRoutineTime.time = mRoutine?.date
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        isTimeSelected = true
        if (is24Hour) {
            selectedRoutineTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedRoutineTime.set(Calendar.MINUTE, minute)
        } else {
            selectedRoutineTime.set(Calendar.HOUR, hourOfDay % 12)
            selectedRoutineTime.set(Calendar.MINUTE, minute)
            selectedRoutineTime.set(Calendar.AM_PM, if (hourOfDay >= 12) Calendar.PM else Calendar.AM)
        }
        time?.text = getTimeText(is24Hour, hourOfDay, minute)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedFrequencyIndex = position - 1
    }

    private fun getTimeText(is24Hour: Boolean, hourOfDay: Int, minute: Int): String {
        return if (is24Hour)
            String.format(Locale.getDefault(), "%02d", hourOfDay) + ":" + minute.toString()
        else
            (if (hourOfDay % 12 == 0) 12 else hourOfDay % 12).toString() + ":" + format(Locale.getDefault(), "%02d", minute) + if (hourOfDay >= 12) " PM" else " AM"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(Constants.ARG_LISTENER, mListener)
        outState.putInt(Constants.ARG_ROUTINE_PRIORITY, selectedFrequencyIndex)

        titleText = title?.text.toString()
        descText = desc?.text.toString()

        if (!TextUtils.isEmpty(titleText))
            outState.putString(Constants.ARG_ROUTINE_TITLE, titleText)

        if (!TextUtils.isEmpty(descText))
            outState.putString(Constants.ARG_ROUTINE_DESC, descText)

        if (mRoutine != null)
            outState.putParcelable(Constants.ARG_CURRENT_ROUTINE, mRoutine)

        if (isTimeSelected) {
            outState.putLong(Constants.ARG_TIME, selectedRoutineTime.timeInMillis)
            outState.putBoolean(Constants.ARG_IS_TIME_SELECTED, true)
        }
    }

    interface OnSaveOccurrence : Serializable {
        fun onSaveRoutine(routine: Routine, isEdit: Boolean)
    }

    companion object {
        fun newInstance(routine: Routine, listener: OnSaveOccurrence, isEdit: Boolean): AddActivityDialogFragment {
            val args = Bundle()
            val fragment = AddActivityDialogFragment()
            fragment.mRoutine = routine
            fragment.mListener = listener
            fragment.isEdit = isEdit
            fragment.arguments = args
            return fragment
        }

        fun newInstance(listener: OnSaveOccurrence, isEdit: Boolean): AddActivityDialogFragment {
            val args = Bundle()
            val fragment = AddActivityDialogFragment()
            fragment.mListener = listener
            fragment.isEdit = isEdit
            fragment.arguments = args
            return fragment
        }
    }
}

