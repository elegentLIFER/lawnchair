
package com.android.systemui.unfold.util

import android.os.Trace
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Qualifier

/**
 * Listener that logs start and end of the fold-unfold transition.
 *
 * [tracePrefix] arg helps in differentiating those. Currently, this is expected to be logged twice
 * for each fold/unfold: in (1) systemui and (2) launcher process.
 */
class ATraceLoggerTransitionProgressListener
@AssistedInject
internal constructor(@UnfoldTransitionATracePrefix tracePrefix: String, @Assisted details: String) :
    TransitionProgressListener {

    private val traceName = "$tracePrefix$details#$UNFOLD_TRANSITION_TRACE_NAME"

    override fun onTransitionStarted() {
        Trace.beginAsyncSection(traceName, /* cookie= */ 0)
    }

    override fun onTransitionFinished() {
        Trace.endAsyncSection(traceName, /* cookie= */ 0)
    }

    override fun onTransitionProgress(progress: Float) {
        Trace.setCounter(traceName, (progress * 100).toLong())
    }

    @AssistedFactory
    interface Factory {
        /** Creates an [ATraceLoggerTransitionProgressListener] with [details] in the track name. */
        fun create(details: String): ATraceLoggerTransitionProgressListener
    }
}

private const val UNFOLD_TRANSITION_TRACE_NAME = "FoldUnfoldTransitionInProgress"

@Qualifier annotation class UnfoldTransitionATracePrefix
