

package com.android.app.viewcapture

import android.content.Context
import android.os.Looper
import android.os.Process
import android.tracing.Flags
import android.util.Log

/**
 * Factory to create polymorphic instances of ViewCapture according to build configurations and
 * flags.
 */
class ViewCaptureFactory {
    companion object {
        private val TAG = ViewCaptureFactory::class.java.simpleName
        private var instance: ViewCapture? = null

        @JvmStatic
        fun getInstance(context: Context): ViewCapture {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                return ViewCapture.MAIN_EXECUTOR.submit { getInstance(context) }.get()
            }

            if (instance != null) {
                return instance!!
            }

            return when {
                !android.os.Build.IS_DEBUGGABLE -> {
                    Log.i(TAG, "instantiating ${NoOpViewCapture::class.java.simpleName}")
                    NoOpViewCapture()
                }
                !Flags.perfettoViewCaptureTracing() -> {
                    Log.i(TAG, "instantiating ${SettingsAwareViewCapture::class.java.simpleName}")
                    SettingsAwareViewCapture(
                            context.applicationContext,
                            ViewCapture.createAndStartNewLooperExecutor(
                                    "SAViewCapture",
                                    Process.THREAD_PRIORITY_FOREGROUND
                            )
                    )
                }
                else -> {
                    Log.i(TAG, "instantiating ${PerfettoViewCapture::class.java.simpleName}")
                    PerfettoViewCapture(
                            context.applicationContext,
                            ViewCapture.createAndStartNewLooperExecutor(
                                    "PerfettoViewCapture",
                                    Process.THREAD_PRIORITY_FOREGROUND
                            )
                    )
                }
            }.also { instance = it }
        }
    }
}
