package com.eemf.sirgoingfar.routinechecker.dialog_fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker

import com.eemf.sirgoingfar.core.custom.CustomSpinnerArrayAdapter
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.routinechecker.R

import java.io.Serializable
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale

import butterknife.BindView
import butterknife.ButterKnife

import com.eemf.sirgoingfar.core.utils.Constants.MINIMUM_TITLE_TEXT
import java.lang.String.format

class AddActivityDialogFragment : BaseDialogFragment(), TimePickerDialog.OnTimeSetListener {

   /* @BindView(R.id.tv_header_text)
    internal var headerText: TextView? = null

    @BindView(R.id.et_activity_desc)
    internal var activityDesc: EditText? = null

    @BindView(R.id.tv_activity_time_from)
    internal var timeFromView: TextView? = null

    @BindView(R.id.tv_activity_time_to)
    internal var timeToView: TextView? = null

    @BindView(R.id.spinner_priority)
    internal var prioritySpinner: Spinner? = null

    @BindView(R.id.btn_add_activity)
    internal var btnAddActivity: Button? = null

    @BindView(R.id.btn_add_another_activity)
    internal var btnAddAnother: Button? = null

    private var mCurrentActivity: TodoActivity? = null
    private var mActivityId: Int = 0
    private var selectedTime_from: Calendar? = null
    private var selectedTime_to: Calendar? = null
    private var selectedPriority: Int = 0
    private var isTimeFromClicked: Boolean = false
    private var is24Hour: Boolean = false
    private var isEdit: Boolean = false
    private var isTimeFromSet: Boolean = false
    private var isTimeToSet: Boolean = false
    private var title: String? = null
    private var timePicker: TimePickerDialog? = null
    private var mListener: OnSaveActivity? = null
    private var mCurrentActivities: List<TodoActivity>? = null
    private var mTodo: Todo? = null

    private//From and to cannot be equal
    //Start/End time must not be within a specific activity's time frame/range
    //within range
    // from is within range
    //to is within range
    //ensure the second is zero
    val activityObject: TodoActivity?
        get() {
            val description: String
            val startTime: Date
            val endTime: Date

            description = activityDesc!!.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(description)) {
                activityDesc!!.error = appCompatActivity.getString(R.string.text_title_cannot_be_empty)
                return null
            }

            if (description.length < MINIMUM_TITLE_TEXT) {
                activityDesc!!.error = appCompatActivity.getString(R.string.text_title_cannot_be_less_than) + " " + MINIMUM_TITLE_TEXT.toString() + " " + appCompatActivity.getString(R.string.text_characters)
                return null
            }
            if (selectedTime_from == null) {
                timeFromView!!.error = appCompatActivity.getString(R.string.text_set_start_time)
                toast(appCompatActivity.getString(R.string.text_set_end_time))
                return null
            }

            if (selectedTime_to == null) {
                timeToView!!.error = appCompatActivity.getString(R.string.text_end_time_must_be_set)
                toast(appCompatActivity.getString(R.string.text_end_time_must_be_set))
                return null
            }

            val from = selectedTime_from!!.timeInMillis
            val to = selectedTime_to!!.timeInMillis
            if (from >= to) {
                timeFromView!!.error = appCompatActivity.getString(R.string.text_start_time_must_be_lesser_than_start_time)
                timeToView!!.error = appCompatActivity.getString(R.string.text_end_time_must_be_more_than_start_time)
                toast(appCompatActivity.getString(R.string.text_set_appropriate_time))
                return null
            }
            var currentActivity_from: Long
            var currentActivity_to: Long
            for (activity in mCurrentActivities!!) {

                if (activity === mCurrentActivity)
                    continue

                currentActivity_from = activity.getStartTime().getTime()
                currentActivity_to = activity.getEndTime().getTime()

                if (from >= currentActivity_from && to <= currentActivity_to
                        || from >= currentActivity_from && from <= currentActivity_to
                        || to >= currentActivity_from && to <= currentActivity_to) {
                    toast(appCompatActivity.getString(R.string.text_invalid_activity_time_range))
                    return null
                }
            }
            selectedTime_from!!.set(Calendar.SECOND, 0)
            selectedTime_to!!.set(Calendar.SECOND, 0)

            startTime = selectedTime_from!!.time
            endTime = selectedTime_to!!.time

            if (selectedPriority < 0) {
                toast("Select a priority level")
                return null
            }

            if (isEdit) {
                mCurrentActivity!!.setDescription(description)
                mCurrentActivity!!.setStartTime(startTime)
                mCurrentActivity!!.setEndTime(endTime)
                mCurrentActivity!!.setPriority(selectedPriority)

            } else {
                mCurrentActivity = TodoActivity(description, startTime, endTime, Date(), mTodo!!.getId(),
                        mTodo!!.getDate(), selectedPriority, Constants.Status.PENDING.id, null, mTodo!!.getFirebaseDbId(),
                        prefs.nextAlarmId, AlarmStatus.PENDING.getStatusId())
            }

            return mCurrentActivity
        }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_add_routine, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        /*if (savedInstanceState != null) {
            selectedTime_from = Calendar.getInstance()
            selectedTime_to = Calendar.getInstance()
            selectedTime_from!!.timeInMillis = savedInstanceState.getLong(Constants.ARG_TIME_FROM)
            selectedTime_to!!.timeInMillis = savedInstanceState.getLong(Constants.ARG_TIME_TO)
            title = savedInstanceState.getString(Constants.ARG_ACTIVITY_TITLE)
            selectedPriority = savedInstanceState.getInt(Constants.ARG_ACTIVITY_PRIORITY)
            mListener = savedInstanceState.getSerializable(Constants.ARG_ACTIVITY_LISTENER) as OnSaveActivity
            mCurrentActivities = savedInstanceState.getParcelableArrayList<TodoActivity>(Constants.ARG_CURRENT_ACTIVITIES)
            mTodo = savedInstanceState.getParcelable<Parcelable>(Constants.ARG_CURRENT_TODO)
        }

        val dialog = super.onCreateDialog(savedInstanceState)

        //get device width and height
        val metrics = resources.displayMetrics
        val deviceWidth = metrics.widthPixels
        val deviceHeight = metrics.heightPixels

        var width = (deviceWidth * 0.9).toInt()
        val height: Int

        val window = dialog.window
        if (window != null) {

            //remove window title
            window.requestFeature(Window.FEATURE_NO_TITLE)

            //set window layout
            dialog.setContentView(R.layout.dialog_add_activity)
        }*/

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*//check if there is a Listener
        if (mListener == null)
            throw IllegalArgumentException(appCompatActivity.getString(R.string.text_listener_cannot_be_null))

        //check if the available activities are passed
        if (mCurrentActivities == null)
            throw IllegalArgumentException(appCompatActivity.getString(R.string.text_list_cannot_be_null))

        //check if the Todo date is available
        if (mTodo == null)
            throw IllegalArgumentException(appCompatActivity.getString(R.string.text_todo_cannot_be_null))

        is24Hour = false

        initializeViews()*/
    }

    /*fun setCurrentActivities(mCurrentActivities: List<TodoActivity>) {
        this.mCurrentActivities = mCurrentActivities
    }

    private fun initializeViews() {

        if (isEdit && mCurrentActivity != null) {
            val cal = Calendar.getInstance()
            cal.time = mCurrentActivity!!.getStartTime()
            var from_hr = if (is24Hour) cal.get(Calendar.HOUR_OF_DAY) else cal.get(Calendar.HOUR)
            if (!is24Hour && cal.get(Calendar.AM_PM) == Calendar.PM)
                from_hr += 12
            val from_min = cal.get(Calendar.MINUTE)

            cal.time = mCurrentActivity!!.getEndTime()
            var to_hr = if (is24Hour) cal.get(Calendar.HOUR_OF_DAY) else cal.get(Calendar.HOUR)
            if (!is24Hour && cal.get(Calendar.AM_PM) == Calendar.PM)
                to_hr += 12
            val to_min = cal.get(Calendar.MINUTE)

            selectedTime_from = Calendar.getInstance()
            selectedTime_to = Calendar.getInstance()

            selectedTime_from!!.time = mCurrentActivity!!.getStartTime()
            selectedTime_to!!.time = mCurrentActivity!!.getEndTime()

            activityDesc!!.setText(mCurrentActivity!!.getDescription())
            timeFromView!!.text = getTimeText(is24Hour, from_hr, from_min)
            timeToView!!.text = getTimeText(is24Hour, to_hr, to_min)
            selectedPriority = mCurrentActivity!!.getPriority() + 1
            headerText!!.text = appCompatActivity.getString(R.string.text_edit_activity)
            btnAddAnother!!.visibility = View.GONE
            btnAddActivity!!.text = appCompatActivity.getString(R.string.text_done)
        }

        val priority = ArrayList<String>()
        priority.add("Select a priority level")
        priority.add(Constants.Priority.NONE.title)
        priority.add(Constants.Priority.LOW.title)
        priority.add(Constants.Priority.MEDIUM.title)
        priority.add(Constants.Priority.HIGH.title)

        val spinnerArrayAdapter = CustomSpinnerArrayAdapter(appCompatActivity, R.layout.spinner_selected_item_look, priority)
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinnerArrayAdapter.setSelectedItemColor(R.color.colorBlack)
        spinnerArrayAdapter.setUnselectableItemColor(R.color.colorBlack_transluscent)
        spinnerArrayAdapter.setUnselectablePosition(0)
        spinnerArrayAdapter.setSelectedItemTextSize(16f)
        prioritySpinner!!.adapter = spinnerArrayAdapter
        prioritySpinner!!.prompt = appCompatActivity.getString(R.string.text_select_an_option)
        prioritySpinner!!.setSelection(selectedPriority)
        prioritySpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            internal var prioritySelected: Boolean = false

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                selectedPriority = position - 1

                if (selectedPriority >= 0) {
                    trackEvent(if (prioritySelected)
                        GeneralAnalyticsConstants.USER_UPDATED_ACTIVITY_PRIORITY
                    else
                        GeneralAnalyticsConstants.USER_SELECTED_ACTIVITY_PRIORITY)
                    prioritySelected = true
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        if (!TextUtils.isEmpty(title))
            activityDesc!!.setText(title)

        activityDesc!!.addTextChangedListener(object : TextWatcher {
            internal var hasTrackedFirstTextEvent: Boolean = false
            internal var hasEditEventTracked: Boolean = false

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (TextUtils.isEmpty(s) && !hasTrackedFirstTextEvent) {
                    trackEvent(GeneralAnalyticsConstants.USER_ENTERED_VALUE_IN_THE_TODO_ACTIVITY_TITLE_FIELD)
                    hasTrackedFirstTextEvent = true
                } else if (!TextUtils.isEmpty(s) && !hasEditEventTracked) {
                    trackEvent(GeneralAnalyticsConstants.USER_UPDATED_VALUE_IN_THE_TODO_ACTIVITY_TITLE_FIELD)
                    hasEditEventTracked = true
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {

            }
        })

        timeFromView!!.setOnClickListener { v ->

            isTimeFromClicked = true

            if (selectedTime_from == null)
                selectedTime_from = Calendar.getInstance()

            val hour = if (is24Hour) selectedTime_from!!.get(Calendar.HOUR_OF_DAY) else selectedTime_from!!.get(Calendar.HOUR)

            timePicker = TimePickerDialog(appCompatActivity, this, hour,
                    selectedTime_from!!.get(Calendar.MINUTE), is24Hour)

            timePicker!!.show()
        }

        if (selectedTime_from != null) {
            timeFromView!!.text = getTimeText(is24Hour, selectedTime_from!!.get(Calendar.HOUR_OF_DAY), selectedTime_from!!.get(Calendar.MINUTE))
        }

        timeToView!!.setOnClickListener { v ->

            isTimeFromClicked = false

            if (selectedTime_to == null)
                selectedTime_to = Calendar.getInstance()

            val hour = if (is24Hour) selectedTime_to!!.get(Calendar.HOUR_OF_DAY) else selectedTime_to!!.get(Calendar.HOUR)

            timePicker = TimePickerDialog(appCompatActivity, this, hour,
                    selectedTime_to!!.get(Calendar.MINUTE), is24Hour)

            timePicker!!.show()

        }

        if (selectedTime_to != null) {
            timeToView!!.text = getTimeText(is24Hour, selectedTime_to!!.get(Calendar.HOUR_OF_DAY), selectedTime_to!!.get(Calendar.MINUTE))
        }

        btnAddActivity!!.setOnClickListener { v -> saveActivity(true) }

        btnAddAnother!!.setOnClickListener { v -> saveActivity(false) }
    }

    private fun saveActivity(dismissDialog: Boolean) {

        trackEvent(if (dismissDialog)
            if (isEdit) GeneralAnalyticsConstants.USER_CLICKED_DONE_BUTTON_ON_ADD_ACTIVITY_DIALOG else GeneralAnalyticsConstants.USER_CLICKED_ADD_BUTTON_ON_ADD_ACTIVITY_DIALOG
        else
            GeneralAnalyticsConstants.USER_CLICKED_ADD_ANOTHER_BUTTON_ON_ADD_ACTIVITY_DIALOG)

        val activity = activityObject ?: return

        mListener!!.onSaveActivity(activity, isEdit)

        if (dismissDialog) {
            if (isAdded)
                dismiss()
        } else {
            resetViews()
        }
    }

    private fun resetViews() {
        activityDesc!!.setText("")
        timeFromView!!.text = ""
        timeToView!!.text = ""
        resetCalendarRes()
        prioritySpinner!!.setSelection(0)
    }

    private fun resetCalendarRes() {
        selectedTime_from!!.time = mTodo!!.getDate()
        selectedTime_to!!.time = mTodo!!.getDate()
    }
*/
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        /*if (isTimeFromClicked) {
            if (is24Hour) {
                selectedTime_from!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime_from!!.set(Calendar.MINUTE, minute)
            } else {
                selectedTime_from!!.set(Calendar.HOUR, hourOfDay % 12)
                selectedTime_from!!.set(Calendar.MINUTE, minute)
                selectedTime_from!!.set(Calendar.AM_PM, if (hourOfDay >= 12) Calendar.PM else Calendar.AM)
            }
            timeFromView!!.text = getTimeText(is24Hour, hourOfDay, minute)

            isTimeFromClicked = true
            if (isTimeFromSet) {
                trackEvent(GeneralAnalyticsConstants.USER_UPDATED_ACTIVITY_START_TIME)
                isTimeFromSet = true
            } else {
                trackEvent(GeneralAnalyticsConstants.USER_SELECTED_ACTIVITY_START_TIME)
            }
        } else {
            if (is24Hour) {
                selectedTime_to!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime_to!!.set(Calendar.MINUTE, minute)
            } else {
                selectedTime_to!!.set(Calendar.HOUR, hourOfDay % 12)
                selectedTime_to!!.set(Calendar.MINUTE, minute)
                selectedTime_to!!.set(Calendar.AM_PM, if (hourOfDay >= 12) Calendar.PM else Calendar.AM)
            }
            timeToView!!.text = getTimeText(is24Hour, hourOfDay, minute)

            if (isTimeToSet) {
                trackEvent(GeneralAnalyticsConstants.USER_UPDATED_ACTIVITY_END_TIME)
                isTimeToSet = true
            } else {
                trackEvent(GeneralAnalyticsConstants.USER_SELECTED_ACTIVITY_END_TIME)
            }
        }*/

    }

    private fun getTimeText(is24Hour: Boolean, hourOfDay: Int, minute: Int): String {
        return if (is24Hour)
            String.format(Locale.getDefault(), "%02d", hourOfDay) + ":" + minute.toString()
        else
            (if (hourOfDay % 12 == 0) 12 else hourOfDay % 12).toString() + ":" + format(Locale.getDefault(), "%02d", minute) + if (hourOfDay >= 12) " PM" else " AM"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
       /* outState.putLong(Constants.ARG_TIME_FROM, selectedTime_from!!.timeInMillis)
        outState.putLong(Constants.ARG_TIME_TO, selectedTime_to!!.timeInMillis)

        val title = activityDesc!!.text.toString().trim { it <= ' ' }

        if (!TextUtils.isEmpty(title))
            outState.putString(Constants.ARG_ACTIVITY_TITLE, title)

        if (selectedPriority > 0)
            outState.putInt(Constants.ARG_ACTIVITY_PRIORITY, selectedPriority)

        if (mListener != null)
            outState.putSerializable(Constants.ARG_ACTIVITY_LISTENER, mListener)

        if (mCurrentActivities != null)
            outState.putParcelableArrayList(Constants.ARG_CURRENT_ACTIVITIES, mCurrentActivities as ArrayList<out Parcelable>?)

        if (mTodo != null)
            outState.putParcelable(Constants.ARG_CURRENT_TODO, mTodo)*/
    }

    interface OnSaveActivity : Serializable {
//        fun onSaveActivity(activity: TodoActivity, isEdit: Boolean)
    }

    companion object {

        fun newInstance() : AddActivityDialogFragment{
            val args = Bundle()
            val fragment = AddActivityDialogFragment()
            fragment.arguments = args
            return fragment
        }

        /*fun newInstance(todo: Todo, activity: TodoActivity, listener: OnSaveActivity, isEdit: Boolean): AddActivityDialogFragment {
            val args = Bundle()
            val fragment = AddActivityDialogFragment()
            fragment.mTodo = todo
            fragment.mCurrentActivity = activity
            fragment.mListener = listener
            fragment.isEdit = isEdit
            fragment.arguments = args
            return fragment
        }

        fun newInstance(todo: Todo, activityId: Int, listener: OnSaveActivity, isEdit: Boolean): AddActivityDialogFragment {
            val args = Bundle()
            val fragment = AddActivityDialogFragment()
            fragment.mTodo = todo
            fragment.mActivityId = activityId
            fragment.mListener = listener
            fragment.isEdit = isEdit
            fragment.arguments = args
            return fragment
        }

        fun newInstance(todo: Todo, listener: OnSaveActivity): AddActivityDialogFragment {
            val args = Bundle()
            val fragment = AddActivityDialogFragment()
            fragment.mTodo = todo
            fragment.mListener = listener
            fragment.arguments = args
            return fragment
        }*/
    }
}

