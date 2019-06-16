package com.eemf.sirgoingfar.routinechecker.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.routinechecker.R
import java.util.*

class RoutineOccurrenceRecyclerViewAdapter(private val mContext: Context, private val mListener: OnStatusButtonClickListener) :
        RecyclerView.Adapter<RoutineOccurrenceRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mDataList: ArrayList<RoutineOccurrence>

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_routine_occurrence, container, false))
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, viewType: Int) {
        val currentItem: RoutineOccurrence = viewHolder.currentItem()

        viewHolder.tvOccurrenceDate?.text = Helper.getDateString(currentItem.occurrenceDate)
                ?: mContext.getString(R.string.text_unavailable)
        viewHolder.tvOccurrenceStatus?.text = Constants.Status.getStatusById(currentItem.status)?.label

        if (currentItem.status == Constants.Status.DONE.id)
            viewHolder.tvOccurrenceStatus?.setTextColor(ContextCompat.getColor(mContext, R.color.colorDone))
        else
            viewHolder.tvOccurrenceStatus?.setTextColor(ContextCompat.getColor(mContext, R.color.colorMissed))

        if (currentItem.status == Constants.Status.PROGRESS.id) {
            viewHolder.btnDone?.visibility = View.VISIBLE
            viewHolder.btnMissed?.visibility = View.VISIBLE
            viewHolder.divider?.visibility = View.VISIBLE
            viewHolder.btnDone?.setOnClickListener { mListener.onDoneBtnClick(viewHolder.adapterPosition, currentItem) }
            viewHolder.btnMissed?.setOnClickListener { mListener.onMissedBtnClick(viewHolder.adapterPosition, currentItem) }
        } else {
            viewHolder.btnDone?.visibility = View.GONE
            viewHolder.btnMissed?.visibility = View.GONE
            viewHolder.divider?.visibility = View.GONE
        }
    }

    fun setData(data: ArrayList<RoutineOccurrence>?) {
        if (data == null)
            return

        mDataList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_occurrence_date)
        @JvmField
        var tvOccurrenceDate: TextView? = null

        @BindView(R.id.tv_occurrence_status)
        @JvmField
        var tvOccurrenceStatus: TextView? = null

        @BindView(R.id.btn_done)
        @JvmField
        var btnDone: TextView? = null

        @BindView(R.id.btn_missed)
        @JvmField
        var btnMissed: TextView? = null

        @BindView(R.id.horizontal_rule)
        @JvmField
        var divider: View? = null


        init {
            ButterKnife.bind(this, itemView)
            tvOccurrenceDate = itemView.findViewById(R.id.tv_occurrence_date)
            tvOccurrenceStatus = itemView.findViewById(R.id.tv_occurrence_status)
            btnDone = itemView.findViewById(R.id.btn_done)
            btnMissed = itemView.findViewById(R.id.btn_missed)
            divider = itemView.findViewById(R.id.horizontal_rule)
        }

        fun currentItem(): RoutineOccurrence {
            return mDataList[adapterPosition]
        }
    }

    interface OnStatusButtonClickListener {

        fun onDoneBtnClick(position: Int, clickedRoutine: RoutineOccurrence)

        fun onMissedBtnClick(position: Int, clickedRoutine: RoutineOccurrence)

    }
}