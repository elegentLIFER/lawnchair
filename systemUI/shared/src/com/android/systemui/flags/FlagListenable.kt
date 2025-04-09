
package com.android.systemui.flags

/**
 * Plugin for loading flag values
 */
interface FlagListenable {
    /** Add a listener to be alerted when the given flag changes.  */
    fun addListener(flag: Flag<*>, listener: Listener)

    /** Remove a listener to be alerted when any flag changes.  */
    fun removeListener(listener: Listener)

    /** A simple listener to be alerted when a flag changes.  */
    fun interface Listener {
        /** Called when the flag changes */
        fun onFlagChanged(event: FlagEvent)
    }

    /** An event representing the change */
    interface FlagEvent {
        /** the id of the flag which changed */
        val flagName: String
        /** if all listeners alerted invoke this method, the restart will be skipped */
        fun requestNoRestart()
    }
}
