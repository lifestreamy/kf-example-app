package com.example.app.utils

import android.content.Context
import android.widget.Toast
import timber.log.Timber

internal class Toaster private constructor(val context: Context) {

    companion object {
        var globalInstance: Toaster? = null

        fun initInstance(context: Context) {
            if (globalInstance == null) {
                globalInstance = Toaster(context)
            }
        }

        fun I(): Toaster? {
            if (globalInstance == null) {
                Timber.e("Toaster is not initialized!!!")
            }
            return globalInstance
        }

        fun dispose() {
            globalInstance = null
        }
    }

    internal fun show(message: String) {
        launchSafe("toaster, show_message") {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

}