package com.example.app

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.multidex.MultiDexApplication
import com.example.app.utils.Globals
import com.jakewharton.threetenabp.AndroidThreeTen

class NewApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        Globals.appContext = this
        Globals.imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}