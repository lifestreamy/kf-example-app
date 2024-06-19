package com.example.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenStateReceiver(private val callback: (state: Boolean) -> Unit = {}) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_SCREEN_ON -> callback(true)
            Intent.ACTION_SCREEN_OFF -> callback(false)
        }
    }
}