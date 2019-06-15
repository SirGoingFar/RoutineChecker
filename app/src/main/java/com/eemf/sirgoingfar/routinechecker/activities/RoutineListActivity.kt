package com.eemf.sirgoingfar.routinechecker.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import com.eemf.sirgoingfar.core.custom.AbsViewHolder
import com.eemf.sirgoingfar.database.Routine
import com.eemf.sirgoingfar.rout.RoutineListRecyclerViewAdapter
import com.eemf.sirgoingfar.routinechecker.R
import com.eemf.sirgoingfar.routinechecker.dialog_fragments.AddActivityDialogFragment
import kotlinx.android.synthetic.main.activity_routine_list.*
import java.util.*
import kotlin.collections.ArrayList

class RoutineListActivity : BaseActivity(), RoutineListRecyclerViewAdapter.OnRoutineClickListener {

    inner class ViewHolder(private val mContainer: View) : AbsViewHolder(mContainer) {

        private lateinit var adapter: RoutineListRecyclerViewAdapter

        init {
            ButterKnife.bind(this, mContainer)
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

            val dataList: ArrayList<Routine> = ArrayList()
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))
            dataList.add(Routine(1, "Cooking", "Coooooking", 2, Date()))

            adapter.setData(dataList)
        }
    }

    private lateinit var views: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_list)
        views = ViewHolder(window.decorView.findViewById(android.R.id.content))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        intent.putExtra(RoutineDetailActivity.EXTRA_ROUTINE_OBJECT, clickedRoutine)
        startActivity(intent)
    }

    fun openAddRoutineDialog() {
        AddActivityDialogFragment.newInstance().show(this.fragmentManager,
                AddActivityDialogFragment::class.java.name)
    }

    private fun openNextUpActivity() {
    }
}
