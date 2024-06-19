package com.example.sdk.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

internal fun launchSafe(
    tag: String,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ((Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit
) = KSafeScoupe(tag, onException = onException).launch(start = start, block = block)


private fun KSafeScoupe(tag: String? = null, onException: ((Throwable) -> Unit)? = null): CoroutineScope =
    CoroutineScope(GCoroutineContext(tag, onException = onException).mainContext)


internal class GCoroutineContext(val tag: String? = null, private val onException: ((Throwable) -> Unit)? = null) {
    private val supervisor = SupervisorJob()
    private val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Job cancelled ($tag)")
        Timber.e("::: $throwable")
        onException?.invoke(throwable)
    }

    val mainContext: CoroutineContext
        get() = Dispatchers.Main + exceptionHandler + supervisor
}
