package com.example.sdk.sdk.core

import android.app.Activity
import com.example.sdk.data.listeners.SDKExampleListener
import java.math.BigDecimal

@FunctionalInterface
interface ISDK {
    suspend fun example(
        e1: BigDecimal,
        e2: String,
        sdkExampleListener: SDKExampleListener,
        e3: Boolean = false
    )
    fun isAuthorized(): Boolean
    fun onDestroy()
    fun onResume(activity: Activity)
    fun onPause(activity: Activity)
}