package com.example.app.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.utils.Globals
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {
    var inFocus: Boolean = false

    fun setFragment(fragment: Fragment, saveStepToMemory: Boolean = true) {
        try {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, fragment, fragment.tag)
            if (saveStepToMemory)
                ft.addToBackStack(fragment.tag)
            ft.commitAllowingStateLoss()
            Globals.currentFragment = fragment
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }

    override fun onResume() {
        super.onResume()
        inFocus = true
    }

    override fun onPause() {
        super.onPause()
        inFocus = false
    }
}