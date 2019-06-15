package com.eemf.sirgoingfar.routinechecker.dialog_fragments

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.eemf.sirgoingfar.core.custom.ProgressBarDialog
import com.eemf.sirgoingfar.core.utils.Prefs
import com.eemf.sirgoingfar.routinechecker.activities.BaseActivity

open class BaseDialogFragment : DialogFragment() {

    protected lateinit var appCompatActivity: AppCompatActivity
    protected lateinit var mBaseActivity: BaseActivity
    protected lateinit var prefs: Prefs
    private var progressDialog: ProgressBarDialog? = null

    fun dismissAllDialogs(manager: FragmentManager) {
        val fragments = manager.fragments

        for (fragment in fragments) {
            if (fragment is DialogFragment) {
                fragment.dismissAllowingStateLoss()
            }

            val childFragmentManager = fragment.childFragmentManager
            dismissAllDialogs(childFragmentManager)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is AppCompatActivity)
            appCompatActivity = context

        if (context is BaseActivity)
            mBaseActivity = context

        prefs = Prefs.getsInstance()
    }

    fun toast(msg: Any?) {
        if (msg == null) {
            return
        }

        Toast.makeText(appCompatActivity.applicationContext, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg: Any?) {
        if (msg == null) {
            return
        }
        Toast.makeText(appCompatActivity.applicationContext, msg.toString(), Toast.LENGTH_LONG).show()
    }

    fun makeProgressDialog(isCancelable: Boolean) {
        progressDialog = ProgressBarDialog(appCompatActivity)
        progressDialog!!.setCancelable(isCancelable)
        progressDialog!!.setCanceledOnTouchOutside(isCancelable)
    }

    fun showProgressDialog() {
        if (progressDialog != null && !progressDialog!!.isShowing)
            progressDialog!!.show()
    }

    fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }

    interface OnDismissListener {
        fun onDismiss()
    }
}
