

package com.android.quickstep

import android.os.RemoteException
import android.util.Log
import com.android.launcher3.config.FeatureFlags
import com.android.launcher3.util.Executors
import com.android.wm.shell.shared.IHomeTransitionListener.Stub
import com.android.wm.shell.shared.IShellTransitions

/** Class to track visibility state of Launcher */
class HomeVisibilityState {

    var isHomeVisible = true
        private set

    private var listeners = mutableSetOf<VisibilityChangeListener>()

    fun addListener(l: VisibilityChangeListener) = listeners.add(l)

    fun removeListener(l: VisibilityChangeListener) = listeners.remove(l)

    fun init(transitions: IShellTransitions?) {
        if (!FeatureFlags.enableHomeTransitionListener()) return
        try {
            transitions?.setHomeTransitionListener(
                object : Stub() {
                    override fun onHomeVisibilityChanged(isVisible: Boolean) {
                        Executors.MAIN_EXECUTOR.execute {
                            isHomeVisible = isVisible
                            listeners.forEach { it.onHomeVisibilityChanged(isVisible) }
                        }
                    }
                }
            )
        } catch (e: RemoteException) {
            Log.w(TAG, "Failed call setHomeTransitionListener", e)
        }
    }

    interface VisibilityChangeListener {
        fun onHomeVisibilityChanged(isVisible: Boolean)
    }

    override fun toString() = "{HomeVisibilityState isHomeVisible=$isHomeVisible}"

    companion object {

        private const val TAG = "HomeVisibilityState"
    }
}
