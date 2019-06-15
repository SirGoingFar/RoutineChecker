package com.eemf.sirgoingfar.core.custom

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.eemf.sirgoingfar.core.R


class CustomSpinnerArrayAdapter(private val mContext: Context, private val resourceId: Int, data: ArrayList<String>) : ArrayAdapter<String>(mContext, resourceId, data) {
    private var data = ArrayList<String>()
    private var unselectablePosition: Int = 0
    private var unselectableItemColor: Int = 0
    private var selectedItemColor: Int = 0
    private var selectedItemTextSize: Float = 0.toFloat()

    init {
        this.data = data

        //set default values
        setUnselectablePosition(R.color.colorBlack_transluscent)
        setSelectedItemColor(R.color.colorBlack)
        setSelectedItemTextSize(12f)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        try {

            val view = super.getView(position, convertView, parent) as TextView

            view.textSize = selectedItemTextSize
            if (position == unselectablePosition)
                view.setTextColor(ContextCompat.getColor(mContext, unselectableItemColor))
            else
                view.setTextColor(ContextCompat.getColor(mContext, selectedItemColor))

            return view

        } catch (ex: Exception) {
            ex.printStackTrace()
            return super.getView(position, convertView, parent)
        }

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        try {

            val textView = super.getDropDownView(position, convertView, parent) as TextView

            if (position == unselectablePosition) {
                // Set the hint text color gray
                textView.setTextColor(ContextCompat.getColor(mContext, unselectableItemColor))
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext, selectedItemColor))
            }

            return textView

        } catch (ex: Exception) {
            ex.printStackTrace()
            return super.getDropDownView(position, convertView, parent)
        }

    }

    override fun isEnabled(position: Int): Boolean {
        return position != unselectablePosition && super.isEnabled(position)
    }

    fun setUnselectablePosition(unselectablePosition: Int) {
        this.unselectablePosition = unselectablePosition
    }

    fun setUnselectableItemColor(unselectableItemColor: Int) {
        this.unselectableItemColor = unselectableItemColor
    }

    fun setSelectedItemColor(selectedItemColor: Int) {
        this.selectedItemColor = selectedItemColor
    }

    fun setSelectedItemTextSize(selectedItemTextSize: Float) {
        this.selectedItemTextSize = selectedItemTextSize
    }
}
