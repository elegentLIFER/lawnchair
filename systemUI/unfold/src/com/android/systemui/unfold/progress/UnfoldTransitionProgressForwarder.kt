
package com.android.systemui.unfold.progress

import android.os.RemoteException
import android.util.Log
import com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
import javax.inject.Inject

/** Forwards received unfold events to [remoteListener], when present. */
class UnfoldTransitionProgressForwarder @Inject constructor() :
    TransitionProgressListener, IUnfoldAnimation.Stub() {

    private var remoteListener: IUnfoldTransitionListener? = null

    override fun onTransitionStarted() {
        try {
            Log.d(TAG, "onTransitionStarted")
            remoteListener?.onTransitionStarted()
        } catch (e: RemoteException) {
            Log.e(TAG, "Failed call onTransitionStarted", e)
        }
    }

    override fun onTransitionFinished() {
        try {
            Log.d(TAG, "onTransitionFinished")
            remoteListener?.onTransitionFinished()
        } catch (e: RemoteException) {
            Log.e(TAG, "Failed call onTransitionFinished", e)
        }
    }

    override fun onTransitionProgress(progress: Float) {
        try {
            remoteListener?.onTransitionProgress(progress)
        } catch (e: RemoteException) {
            Log.e(TAG, "Failed call onTransitionProgress", e)
        }
    }

    override fun setListener(listener: IUnfoldTransitionListener?) {
        remoteListener = listener
    }

    companion object {
        private val TAG = UnfoldTransitionProgressForwarder::class.java.simpleName
    }
}
