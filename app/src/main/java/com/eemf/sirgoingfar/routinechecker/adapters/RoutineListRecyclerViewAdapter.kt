package com.eemf.sirgoingfar.rout

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.database.Routine
import com.eemf.sirgoingfar.routinechecker.R
import java.util.*

class RoutineListRecyclerViewAdapter(private val mContext: Context, private val mListener: OnRoutineClickListener) :
        RecyclerView.Adapter<RoutineListRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mDataList: ArrayList<Routine>

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_routine, container, false))
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, viewType: Int) {
        val currentItem: Routine = viewHolder.currentItem()

        viewHolder.tvRoutineName?.text = currentItem.name
        viewHolder.tvRoutineDesc?.text = currentItem.desc
        viewHolder.tvRoutineTime?.text = Helper.getTimeStringFromDate(mContext, currentItem.date)
        viewHolder.tvRoutineFreq?.text = Helper.getFreqById(currentItem.freqId)?.label ?: ""
        viewHolder.cvContainer?.setOnClickListener { mListener.onRoutineClick(viewHolder.adapterPosition, currentItem) }
    }

    fun setData(data: ArrayList<Routine>?) {
        if (data == null)
            return

        mDataList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_routine_name)
        @JvmField
        var tvRoutineName: TextView? = null

        @BindView(R.id.tv_routine_desc)
        @JvmField
        var tvRoutineDesc: TextView? = null

        @BindView(R.id.tv_routine_time)
        @JvmField
        var tvRoutineTime: TextView? = null

        @BindView(R.id.tv_routine_freq)
        @JvmField
        var tvRoutineFreq: TextView? = null

        @BindView(R.id.container)
        @JvmField
        var cvContainer: CardView? = null


        init {
            ButterKnife.bind(this, itemView)
            tvRoutineName = itemView.findViewById(R.id.tv_routine_name)
            tvRoutineDesc = itemView.findViewById(R.id.tv_routine_desc)
            tvRoutineTime = itemView.findViewById(R.id.tv_routine_time)
            tvRoutineFreq = itemView.findViewById(R.id.tv_routine_freq)
            cvContainer = itemView.findViewById(R.id.container)
        }

        fun currentItem(): Routine {
            return mDataList[adapterPosition]
        }
    }

    interface OnRoutineClickListener {
        fun onRoutineClick(position: Int, clickedRoutine: Routine)
    }
}
