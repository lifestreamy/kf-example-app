package com.example.app.data.listeners

import com.example.app.ui.common.ExampleDialogView
import com.example.sdk.data.listeners.SDKExampleListener


class ExampleListener(
    private val dialogView: ExampleDialogView?,
): SDKExampleListener {
    override suspend fun onExample() {
        dialogView?.show("Example...")
    }
}