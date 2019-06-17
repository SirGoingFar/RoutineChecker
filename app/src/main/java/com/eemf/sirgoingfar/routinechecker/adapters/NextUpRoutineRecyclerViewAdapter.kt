package com.eemf.sirgoingfar.routinechecker.adapters

import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.eemf.sirgoingfar.core.models.NextUpRoutine
import com.eemf.sirgoingfar.routinechecker.R

/**
 * @property routineList is a list of NextUpRoutine objects
 * @constructor creates an instance of the NextUpRecyclerViewAdapter
 * */
class NextUpRoutineRecyclerViewAdapter(private val routineList: ArrayList<Parcelable>) : RecyclerView.Adapter<NextUpRoutineRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(container.context).inflate(R.layout.item_next_up_routine, container, false))
    }

    override fun getItemCount(): Int {
        return routineList.size
    }

    override fun onBindViewHolder(viewholder: ViewHolder, itemType: Int) {
        val currentItem = viewholder.currentItem()

        viewholder.tvRoutineName?.text = currentItem.name
        viewholder.tvRoutineEstimate?.text = currentItem.estimate
    }

    /**
     * @property itemView is a container of a NextUpRoutine card
     * @constructor creates a RecyclerView ViewHolder
     * */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_routine_name)
        @JvmField
        var tvRoutineName: TextView? = null

        @BindView(R.id.tv_routine_estimate)
        @JvmField
        var tvRoutineEstimate: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            tvRoutineName = itemView.findViewById(R.id.tv_routine_name)
            tvRoutineEstimate = itemView.findViewById(R.id.tv_routine_estimate)
        }

        fun currentItem(): NextUpRoutine {
            return routineList[adapterPosition] as NextUpRoutine
        }
    }

}
