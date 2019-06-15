package com.eemf.sirgoingfar.routinechecker.activities

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import com.eemf.sirgoingfar.core.custom.AbsViewHolder
import com.eemf.sirgoingfar.database.Routine
import com.eemf.sirgoingfar.routinechecker.R
import com.eemf.sirgoingfar.routinechecker.adapters.RoutineListRecyclerViewAdapter
import com.eemf.sirgoingfar.routinechecker.dialog_fragments.AddActivityDialogFragment
import com.eemf.sirgoingfar.routinechecker.viewmodels.BaseViewModel
import com.eemf.sirgoingfar.routinechecker.viewmodels.RoutineListActivityViewModel
import com.eemf.sirgoingfar.routinechecker.viewmodels.ViewModelModule
import kotlinx.android.synthetic.main.activity_routine_list.*

class RoutineListActivity : BaseActivity(), RoutineListRecyclerViewAdapter.OnRoutineClickListener {

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
        }

        fun refreshPage(routineList: List<Routine>) {

            addOrRemoveMenuOption(routineList.isNotEmpty(), R.id.action_next_up)

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

            val option = mMenu.findItem(menuId) ?: return

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


    inner class Model {

        private var mActivity: RoutineListActivity = this@RoutineListActivity
        private var mViewModel: RoutineListActivityViewModel = ViewModelModule().getRoutineListActivityViewModel(
                mActivity.application, mActivity) as RoutineListActivityViewModel

        init {

            //observe for Routine List data
            mViewModel.getRoutineListObserver().observe(mActivity, android.arch.lifecycle.Observer {
                if (it == null)
                    return@Observer

                refreshPage(it)
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

    }

    private lateinit var views: ViewHolder
    private lateinit var model: Model
    private lateinit var mMenu: Menu

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

            R.id.action_add_todo -> {
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

    fun openAddRoutineDialog() {
        AddActivityDialogFragment.newInstance().show(this.fragmentManager,
                AddActivityDialogFragment::class.java.name)
    }

    private fun openNextUpActivity() {
    }

    private fun switchScreenState(state: Int) {
        views.switchScreenState(state)
    }

    private fun refreshPage(routineList: List<Routine>) {
        views.refreshPage(routineList)
    }
}
