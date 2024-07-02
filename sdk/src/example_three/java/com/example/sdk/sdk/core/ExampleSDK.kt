package com.example.sdk.sdk.core

import android.app.Activity
import android.content.Context
import com.example.sdk.BuildConfig
import com.example.sdk.data.listeners.SDKExampleListener
import timber.log.Timber
import java.math.BigDecimal

class ExampleSDK(context: Context) : ISDK {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        private var globalInstance: ExampleSDK? = null

        @JvmStatic
        fun getInstance(context: Context): ExampleSDK {
            if (globalInstance == null) {
                globalInstance = ExampleSDK(context)
            }
            return globalInstance!!
        }
    }

    override suspend fun example(
        e1: BigDecimal,
        e2: String,
        sdkExampleListener: SDKExampleListener,
        e3: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun isAuthorized(): Boolean {
        return true
    }

    override fun onDestroy() {
        globalInstance = null
    }

    override fun onResume(activity: Activity) {}

    override fun onPause(activity: Activity) {}
}