package com.example.sdk.sdk.core

import android.content.Context

class Sdk(private val context: Context) : IExampleSdk {
    override val sdk: ISDK = ExampleSDK.getInstance(context)
    override var exampleListener: ExampleListener? = null


    override fun setListener(listener: ExampleListener) {
        this.exampleListener = listener
    }

    companion object {
        private var globalInstance: IExampleSdk? = null

        @JvmStatic
        fun getInstance(context: Context): IExampleSdk {
            if (globalInstance == null) {
                globalInstance = Sdk(context)
            }
            return globalInstance!!
        }
    }
}