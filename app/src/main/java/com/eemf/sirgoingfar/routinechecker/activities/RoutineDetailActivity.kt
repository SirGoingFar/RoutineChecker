package com.eemf.sirgoingfar.routinechecker.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.eemf.sirgoingfar.core.custom.AbsViewHolder
import com.eemf.sirgoingfar.core.utils.ColorUtil
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.database.Routine
import com.eemf.sirgoingfar.routinechecker.R
import kotlinx.android.synthetic.main.activity_routine_detail.*

class RoutineDetailActivity : BaseActivity() {

    inner class ViewHolder(mContainer: View) : AbsViewHolder(mContainer) {

        private var mContext: RoutineDetailActivity = this@RoutineDetailActivity
        private var mState: State

        init {
            mState = State()
            init(mContainer)
        }

        override fun init(container: View) {
            //set state: Loading
            mState.setLoading()

            //set up other views
            tv_routine_title.text = mRoutine.name
            tv_routine_desc.text = mRoutine.desc
            tv_routine_freq_detail.text = Helper.getRoutineTimeDetail(mContext, mRoutine.date, mRoutine.freqId)
                    ?: mContext.getString(R.string.text_unavailable)
            pb_routine_progress.setProgressColor((ContextCompat.getColor(mContext, ColorUtil.getColor(mRoutine.id))))
            mContext.setSupportActionBar(toolbar)
            mContext.toolbar.title = Helper.getNextRoutineOccurrenceText(mContext, mRoutine.freqId, mRoutine.date)
                    ?: mContext.getString(R.string.text_unavailable)
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

    private lateinit var mRoutine: Routine
    private lateinit var views: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_detail)

        val intent = intent

        if (intent.hasExtra(EXTRA_ROUTINE_OBJECT)) {
            mRoutine = intent.getParcelableExtra(EXTRA_ROUTINE_OBJECT)
            views = ViewHolder(window.decorView.rootView)
        } else {
            //no Routine object, close this activity
            finish()
        }
    }

    companion object {
        const val EXTRA_ROUTINE_OBJECT: String = "extra_routine_object"
    }
}
