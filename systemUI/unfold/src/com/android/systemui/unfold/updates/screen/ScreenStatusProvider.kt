
package com.android.systemui.unfold.updates.screen

import com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
import com.android.systemui.unfold.util.CallbackController

interface ScreenStatusProvider : CallbackController<ScreenListener> {

    interface ScreenListener {
        /**
         * Called when the screen is on and ready (windows are drawn and screen blocker is removed)
         */
        fun onScreenTurnedOn()

        /**
         * Called when the screen is starting to be turned off.
         */
        fun onScreenTurningOff()

        /**
         * Called when the screen is starting to be turned on.
         */
        fun onScreenTurningOn()

        /**
         * Called when the screen is already turned on but it happened before the creation
         * of the unfold progress provider, so we won't play the actual animation but we treat
         * the current state of the screen as 'turned on'
         */
        fun markScreenAsTurnedOn()
    }
}
