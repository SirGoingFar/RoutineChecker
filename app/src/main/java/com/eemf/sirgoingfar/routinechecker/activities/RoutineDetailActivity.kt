package com.eemf.sirgoingfar.routinechecker.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.eemf.sirgoingfar.core.custom.AbsViewHolder
import com.eemf.sirgoingfar.core.utils.ColorUtil
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.database.Routine
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.routinechecker.R
import com.eemf.sirgoingfar.routinechecker.adapters.RoutineOccurrenceRecyclerViewAdapter
import com.eemf.sirgoingfar.routinechecker.dialog_fragments.AddActivityDialogFragment
import com.eemf.sirgoingfar.routinechecker.viewmodels.BaseViewModel
import com.eemf.sirgoingfar.routinechecker.viewmodels.RoutineDetailOccurrenceListViewModel
import com.eemf.sirgoingfar.routinechecker.viewmodels.RoutineListActivityViewModel
import com.eemf.sirgoingfar.routinechecker.viewmodels.ViewModelModule
import kotlinx.android.synthetic.main.activity_routine_detail.*
import java.util.*

class RoutineDetailActivity : BaseActivity(), RoutineOccurrenceRecyclerViewAdapter.OnStatusButtonClickListener, AddActivityDialogFragment.OnSaveOccurrence {

    inner class ViewHolder(mContainer: View) : AbsViewHolder(mContainer) {

        private var mContext: RoutineDetailActivity = this@RoutineDetailActivity
        private var mState: State
        private lateinit var adapter: RoutineOccurrenceRecyclerViewAdapter

        init {
            mState = State()
            init(mContainer)
        }

        override fun init(container: View) {
            //set state: Loading
            mState.setLoading()

            refreshHeader()

            //set up other views
            adapter = RoutineOccurrenceRecyclerViewAdapter(mContext, mContext)
            rv_routine_occurrences_list?.layoutManager = LinearLayoutManager(mContext,
                    LinearLayoutManager.VERTICAL, false)
            rv_routine_occurrences_list?.setHasFixedSize(true)
            rv_routine_occurrences_list?.isFocusable = true
            rv_routine_occurrences_list?.clipToPadding = false
            rv_routine_occurrences_list?.adapter = adapter
        }

        fun refreshHeader() {
            tv_routine_title.text = mRoutine.name
            tv_routine_desc.text = mRoutine.desc
            tv_routine_freq_detail.text = Helper.getRoutineTimeDetail(mContext, mRoutine.date, mRoutine.freqId)
                    ?: mContext.getString(R.string.text_unavailable)
            pb_routine_progress.setProgressColor((ContextCompat.getColor(mContext, ColorUtil.getColor(mRoutine.id))))
            mContext.setSupportActionBar(toolbar)
            toolbar.title = Helper.getNextRoutineOccurrenceText(mContext, mRoutine.freqId, mRoutine.date)
                    ?: mContext.getString(R.string.text_unavailable)
        }

        fun switchScreenState(state: Int) {
            when (state) {
                BaseViewModel.STATE_LOADING -> mState.setLoading()
                BaseViewModel.STATE_LOADED -> mState.setLoaded()
            }
        }

        fun refreshPage(list: List<RoutineOccurrence>) {
            if (list.isEmpty()) {
                mState.setNoData()
            } else {
                adapter.setData(list as ArrayList<RoutineOccurrence>)
                val percentDone = getPercentageDone(list)
                pb_routine_progress.setProgressPercent(percentDone)
                if (percentDone >= Constants.MINIMUM_PASS_MARK) {
                    iv_routine_progress_emoji.setImageResource(R.drawable.ic_thumb_up)
                } else {
                    iv_routine_progress_emoji.setImageResource(R.drawable.ic_frown)
                }
                mState.setHasData()
            }
        }

        private fun getPercentageDone(list: List<RoutineOccurrence>): Int {
            var doneCount = 0

            for (routine: RoutineOccurrence in list) {
                if (routine.status == Constants.Status.DONE.id)
                    doneCount++
            }

            return (doneCount / list.size) * 100
        }

        inner class State : AbsViewState {

            override fun setBlank() {

            }

            override fun setNoData() {
                show(tv_empty_state)
                gone(pb_loading)
                gone(ns_filled_state)
            }

            override fun setHasData() {
                show(ns_filled_state)
                gone(tv_empty_state)
                gone(pb_loading)
            }

            override fun setLoading(tag: String) {

            }

            override fun setLoaded(tag: String) {

            }

            override fun setLoading() {
                show(pb_loading)
                gone(tv_empty_state)
                gone(ns_filled_state)
            }

            override fun setLoaded() {

            }
        }
    }

    inner class Model {

        private var mActivity: RoutineDetailActivity = this@RoutineDetailActivity
        private var mRoutineViewModel: RoutineListActivityViewModel = ViewModelModule().getRoutineListActivityViewModel(
                mActivity.application, mActivity) as RoutineListActivityViewModel
        private var mOccurrenceViewModel: RoutineDetailOccurrenceListViewModel = ViewModelModule().getRoutineOccurenceListViewModel(
                mActivity.application, mActivity) as RoutineDetailOccurrenceListViewModel

        init {
            //observe for Routine Change
            mRoutineViewModel.getRoutineObserver().observe(mActivity, Observer {
                if (it == null) {
                    performAction(true)
                } else {
                    mRoutine = it
                    performAction(false)
                }

            })

            //observe for data change
            mOccurrenceViewModel.getRoutineOccurrenceListObserver().observe(mActivity, Observer {
                if (it == null)
                    return@Observer

                refreshPage(it)
            })

            //Observe for UI state change
            mOccurrenceViewModel.getRequestStateObserver().observe(mActivity, Observer {
                if (it == null)
                    return@Observer

                switchScreenState(it)
            })
        }

        fun getRoutine(routineId: Int) {
            if (routineId < 1)
                performAction(true)

            mRoutineViewModel.getRoutineById(routineId)
        }

        fun getRoutineOccurrences() {
            mOccurrenceViewModel.getAllRoutineOccurrences(mRoutine.id)
        }

        fun updateRoutine(routine: Routine) {
            mRoutineViewModel.editRoutine(routine)
        }
    }

    private lateinit var mRoutine: Routine
    private lateinit var views: ViewHolder
    private lateinit var model: Model

    private var isEdit: Boolean = false //Todo: Set this to true when there's an edit to the routine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_detail)

        val intent = intent

        if (intent.hasExtra(EXTRA_ROUTINE_ID)) {
            model = Model()
            model.getRoutine(intent.getIntExtra(EXTRA_ROUTINE_ID, -1))
        } else {
            //no Routine object, close this activity
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_routine_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.action_edit_routine) {
            openEditDialog()
            true
        } else
            super.onOptionsItemSelected(item)
    }

    private fun openEditDialog() {
        AddActivityDialogFragment.newInstance(mRoutine, this, true).show(this.fragmentManager,
                AddActivityDialogFragment::class.java.name)
    }

    override fun onSaveRoutine(routine: Routine, isEdit: Boolean) {
        this.isEdit = isEdit
        model.updateRoutine(routine)
    }

    override fun onDoneBtnClick(position: Int, clickedRoutine: RoutineOccurrence) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMissedBtnClick(position: Int, clickedRoutine: RoutineOccurrence) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun performAction(closeActivity: Boolean) {
        if (closeActivity)
            finish()

        if (isEdit) {
            views.refreshHeader()
            isEdit = false
        } else {
            views = ViewHolder(window.decorView.rootView)
            model.getRoutineOccurrences()
        }
    }

    private fun refreshPage(routineOccurrenceList: List<RoutineOccurrence>) {
        views.refreshPage(routineOccurrenceList)
    }

    private fun switchScreenState(status: Int) {
        views.switchScreenState(status)
    }


    companion object {
        const val EXTRA_ROUTINE_ID: String = "extra_routine_object"
    }
}
