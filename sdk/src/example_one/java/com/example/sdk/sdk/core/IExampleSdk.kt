package com.example.sdk.sdk.core


@FunctionalInterface
interface IExampleSdk {
    val sdk: ISDK
    val exampleListener: ExampleListener?
    fun setListener(listener: ExampleListener)
}
