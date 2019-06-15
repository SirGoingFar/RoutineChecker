package com.eemf.sirgoingfar.routinechecker.activities

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.Toast
import com.eemf.sirgoingfar.core.utils.Prefs


abstract class BaseActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    private val currentFragment: Fragment? = null
    protected var fragmentManager: FragmentManager? = supportFragmentManager
    protected lateinit var prefs: Prefs
    private var root: FrameLayout? = null

    val isActivityStarted: Boolean
        get() = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs.getsInstance()
        root = findViewById(android.R.id.content)
    }

    fun setActionBarTitle(title: String) {

        if (actionBar == null)
            actionBar = supportActionBar

        actionBar!!.title = title
    }

    fun toast(msg: Any?) {
        if (msg == null) {
            return
        }
        Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg: Any?) {
        if (msg == null) return
        Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_LONG).show()
    }
}