package com.eemf.sirgoingfar.routinechecker.activities

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import com.eemf.sirgoingfar.core.custom.AbsViewHolder
import com.eemf.sirgoingfar.core.models.NextUpRoutine
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.core.utils.Prefs
import com.eemf.sirgoingfar.database.Routine
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.routinechecker.R
import com.eemf.sirgoingfar.routinechecker.adapters.RoutineListRecyclerViewAdapter
import com.eemf.sirgoingfar.routinechecker.dialog_fragments.AddActivityDialogFragment
import com.eemf.sirgoingfar.routinechecker.viewmodels.BaseViewModel
import com.eemf.sirgoingfar.routinechecker.viewmodels.RoutineListActivityViewModel
import com.eemf.sirgoingfar.routinechecker.viewmodels.ViewModelModule
import com.eemf.sirgoingfar.timely.alarm.AlarmHelper
import kotlinx.android.synthetic.main.activity_routine_list.*
import java.util.*

class RoutineListActivity : BaseActivity(), RoutineListRecyclerViewAdapter.OnRoutineClickListener,
        AddActivityDialogFragment.OnSaveOccurrence {

    inner class ViewHolder(mContainer: View) : AbsViewHolder(mContainer) {

        private lateinit var adapter: RoutineListRecyclerViewAdapter
        private var mState: State

        init {
            ButterKnife.bind(this, mContainer)
            mState = State()
            init(mContainer)
        }

        override fun init(container: View) {
            //setup FAB
            fab_add_routine!!.setOnClickListener {
                openAddRoutineDialog()
            }

            //setup RecyclerView
            adapter = RoutineListRecyclerViewAdapter(this@RoutineListActivity, this@RoutineListActivity)
            rv_rountine_list?.layoutManager = LinearLayoutManager(this@RoutineListActivity,
                    LinearLayoutManager.VERTICAL, false)
            rv_rountine_list?.setHasFixedSize(true)
            rv_rountine_list?.isFocusable = true
            rv_rountine_list?.clipToPadding = false
            rv_rountine_list?.adapter = adapter
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteRoutine(viewHolder.adapterPosition)
                }
            }).attachToRecyclerView(rv_rountine_list)
        }

        fun refreshPage(routineList: List<Routine>) {

            addOrRemoveMenuOption(model.mNextUpList.isNotEmpty(), R.id.action_next_up)

            if (routineList.isEmpty()) {
                mState.setNoData()
            } else {
                adapter.setData(routineList as java.util.ArrayList<Routine>)
                mState.setHasData()
            }
        }

        fun switchScreenState(state: Int) {
            when (state) {
                BaseViewModel.STATE_LOADING -> mState.setLoading()
                BaseViewModel.STATE_LOADED -> mState.setLoaded()
            }
        }

        private fun addOrRemoveMenuOption(isVisible: Boolean, @IdRes menuId: Int) {
            if (mMenu == null)
                return

            val option = mMenu?.findItem(menuId) ?: return

            option.isVisible = isVisible
        }

        inner class State : AbsViewState {
            override fun setBlank() {

            }

            override fun setNoData() {
                show(tv_empty_state)
                gone(rv_rountine_list)
                gone(pb_loading)
            }

            override fun setHasData() {
                show(rv_rountine_list)
                gone(tv_empty_state)
                gone(pb_loading)
            }

            override fun setLoading(tag: String) {
                show(pb_loading)
                gone(tv_empty_state)
                gone(rv_rountine_list)
            }

            override fun setLoaded(tag: String) {

            }

            override fun setLoading() {

            }

            override fun setLoaded() {

            }
        }
    }

    private fun deleteRoutine(index: Int) {
        createAlertDialog(getString(R.string.text_delete_routine))
                .setNegativeButton(getString(R.string.alert_dialog_negative_button_label)) { dialog, which -> refreshPage(model.getCurrentRoutineList()) }
                .setPositiveButton(getString(R.string.alert_dialog_positive_button_label)) { dialog, which ->
                    model.deleteRoutine(index)
                }.create().show()
    }


    inner class Model {

        private var mActivity: RoutineListActivity = this@RoutineListActivity
        private var mViewModel: RoutineListActivityViewModel = ViewModelModule().getRoutineListActivityViewModel(
                mActivity.application, mActivity) as RoutineListActivityViewModel

        private var mRoutineList: List<Routine>? = null
        lateinit var mNextUpList: ArrayList<NextUpRoutine>

        init {

            //observe for Routine List data
            mViewModel.getRoutineListObserver().observe(mActivity, android.arch.lifecycle.Observer {
                if (it == null)
                    return@Observer

                mRoutineList = it
                mNextUpList = getNextUpRoutinelist(mRoutineList!!)
                refreshPage(it)

                if (isAddition) {
                    isAddition = false
                    val addedRoutine = it[it.size - 1]
                    val occurrence = RoutineOccurrence(addedRoutine.id, Constants.Status.UNKNOWN.id, addedRoutine.date,
                            Prefs.getsInstance().nextAlarmId, addedRoutine.name, addedRoutine.desc, addedRoutine.freqId)
                    AlarmHelper().execute(occurrence, AlarmHelper.ACTION_SCHEDULE_ALARM)
                }
            })

            //Observe for UI state change
            mViewModel.getRequestStateObserver().observe(mActivity, android.arch.lifecycle.Observer {
                if (it == null)
                    return@Observer

                switchScreenState(it)
            })

            //Query for data
            mViewModel.getAllRoutines()
        }

        fun saveRoutine(routine: Routine) {
            mViewModel.addRoutine(routine)
        }

        fun getCurrentRoutineList(): List<Routine> {
            return mRoutineList!!
        }

        fun deleteRoutine(index: Int) {
            mViewModel.deleteRoutine(mRoutineList!![index])
        }
    }

    private lateinit var views: ViewHolder
    private lateinit var model: Model
    private var mMenu: Menu? = null
    private var isAddition: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_list)
        views = ViewHolder(window.decorView.findViewById(android.R.id.content))
        model = Model()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        mMenu = menu
        menuInflater.inflate(R.menu.menu_routine_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {

            R.id.action_add_routine -> {
                openAddRoutineDialog()
                true
            }

            R.id.action_next_up -> {
                openNextUpActivity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRoutineClick(position: Int, clickedRoutine: Routine) {
        val intent = Intent(this, RoutineDetailActivity::class.java)
        intent.putExtra(RoutineDetailActivity.EXTRA_ROUTINE_ID, clickedRoutine.id)
        startActivity(intent)
    }

    override fun onSaveRoutine(routine: Routine, isEdit: Boolean) {
        isAddition = !isEdit
        model.saveRoutine(routine)
    }

    fun openAddRoutineDialog() {
        AddActivityDialogFragment.newInstance(this, false).show(this.fragmentManager,
                AddActivityDialogFragment::class.java.name)
    }

    private fun openNextUpActivity() {
        val intent = Intent(this, NextUpActivity::class.java)
        intent.putParcelableArrayListExtra(NextUpActivity.EXTRA_NEXT_UP_ROUTINE_LIST, model.mNextUpList)
        startActivity(intent)
    }

    fun getNextUpRoutinelist(routineList: List<Routine>): ArrayList<NextUpRoutine> {
        val list: ArrayList<NextUpRoutine> = ArrayList()
        val cal: Calendar = Calendar.getInstance()

        for (routine: Routine in routineList) {
            if (routine.nextRoutineDate?.time!!.minus(cal.time.time) <= Constants.TWELVE_HOURS_IN_MILLIS) {
                list.add(NextUpRoutine(routine.name, Helper.getUpNext(routine.freqId, routine.nextRoutineDate)))
            }
        }

        return list
    }

    private fun switchScreenState(state: Int) {
        views.switchScreenState(state)
    }

    private fun refreshPage(routineList: List<Routine>) {
        views.refreshPage(routineList)
    }
}
