package com.example.app.ui.common

import android.app.AlertDialog
import android.content.Context
import android.view.WindowManager
import com.example.app.utils.launchSafe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class ExampleDialogView(context: Context, private var onCancel: () -> Unit) {
    private var dialog: AlertDialog

    init {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Выполнение операции")
        builder.setNegativeButton("Отмена") { _, _ ->
            onCancel()
        }

        dialog = builder.create()
        dialog.window!!.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL )
        dialog.setCancelable(false)
    }

    private fun onCancel() {
        launchSafe(tag = "dialog_view, on_cancel") {
            show("Отмена операции")
            onCancel.invoke()
            delay(500)
            hide()
        }
    }

    private var lastMessage = ""

    fun changeTitle(title: String) {
        if (dialog.isShowing) {
            dialog.setTitle(title)
        }
    }

    suspend fun show(message: String, persist: Boolean = true, delay: Long = 300, onCancel: (() -> Unit)? = null) {
        if (onCancel != null) {
            this.onCancel = onCancel
        }
        withContext(Dispatchers.Main) {
            dialog.setTitle(message)
            dialog.show()
            lastMessage = message
            delay(delay)
            if (!persist && lastMessage == message) {
                hide()
            }
        }
    }

    suspend fun hide() {
        withContext(Dispatchers.Main) {
            if (dialog.isShowing) {
                dialog.cancel()
            }
        }
    }
}