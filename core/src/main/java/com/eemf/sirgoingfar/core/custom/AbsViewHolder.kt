package com.eemf.sirgoingfar.core.custom

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View

abstract class AbsViewHolder {

    protected lateinit var fragment: Fragment
    protected var container: View? = null
    protected lateinit var activity: Activity

    constructor(container: View) {
        this.container = container
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
    }

    constructor(activity: Activity) {
        this.activity = activity
    }

    protected abstract fun init(container: View)

    fun gone(v: View) {
        v.visibility = View.GONE
    }

    fun hide(v: View) {
        v.visibility = View.INVISIBLE
    }

    fun show(v: View) {
        v.visibility = View.VISIBLE
    }

    fun show(@IdRes viewId: Int) {

        val view = getViewById(viewId)

        if (view != null)
            show(view)
    }

    fun hide(@IdRes viewId: Int) {

        val view = getViewById(viewId)

        if (view != null)
            hide(view)
    }

    fun gone(@IdRes viewId: Int) {

        val view = getViewById(viewId)

        if (view != null)
            gone(view)
    }

    fun getViewById(@IdRes viewId: Int): View? {
        var view: View? = null

        if (container != null) {
            view = container!!.findViewById(viewId)
        }

        return view
    }

    interface AbsViewState {
        fun setBlank()

        fun setNoData()

        fun setHasData()

        fun setLoading(tag: String)

        fun setLoaded(tag: String)

        fun setLoading()

        fun setLoaded()
    }
}
