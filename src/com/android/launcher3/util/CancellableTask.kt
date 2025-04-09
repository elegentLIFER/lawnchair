

package com.android.launcher3.util

import java.util.concurrent.Executor
import java.util.function.Consumer
import java.util.function.Supplier

/** A [Runnable] that can be posted to a [Executor] that can be cancelled. */
class CancellableTask<T>
@JvmOverloads
constructor(
    private val task: Supplier<T>,
    // Executor where consumer needs to be executed on. Typically UI executor.
    private val callbackExecutor: Executor,
    // Consumer that needs to be accepted upon completion of the task. Typically work that needs to
    // be done in UI thread after task completes.
    private val callback: Consumer<T>,
    // Callback to be executed on callbackExecutor at the end irrespective of the task being
    // completed or cancelled
    private val endRunnable: Runnable = Runnable {}
) : Runnable {

    // flag to cancel the callback
    var canceled = false
        private set

    private var ended = false

    override fun run() {
        if (canceled) return
        val value = task.get()
        callbackExecutor.execute {
            if (!canceled) {
                callback.accept(value)
            }
            onEnd()
        }
    }

    /**
     * Cancel the [CancellableTask] if not scheduled. If [CancellableTask] has started execution at
     * this time, we will try to cancel the callback if not executed yet.
     */
    fun cancel() {
        canceled = true
        callbackExecutor.execute(this::onEnd)
    }

    private fun onEnd() {
        if (!ended) {
            ended = true
            endRunnable.run()
        }
    }
}
