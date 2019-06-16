package com.eemf.sirgoingfar.routinechecker.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.eemf.sirgoingfar.routinechecker.R
import com.eemf.sirgoingfar.routinechecker.adapters.NextUpRoutineRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_next_up.*

class NextUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_up)

        if (intent.hasExtra(EXTRA_NEXT_UP_ROUTINE_LIST)) {
            val adapter = NextUpRoutineRecyclerViewAdapter(intent.getParcelableArrayListExtra(EXTRA_NEXT_UP_ROUTINE_LIST))
            rv_next_up_routine?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rv_next_up_routine?.setHasFixedSize(true)
            rv_next_up_routine?.isFocusable = true
            rv_next_up_routine?.clipToPadding = false
            rv_next_up_routine?.adapter = adapter
        } else {
            finish()
        }
    }

    companion object {
        const val EXTRA_NEXT_UP_ROUTINE_LIST = "extra_next_up_routine_list"
    }
}
