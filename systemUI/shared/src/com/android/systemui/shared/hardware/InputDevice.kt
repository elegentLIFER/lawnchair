

package com.android.systemui.shared.hardware

import android.view.InputDevice

/**
 * Returns true if [InputDevice] is electronic components to allow a user to use an active stylus in
 * the host device or a passive stylus is detected by the host device.
 */
val InputDevice.isInternalStylusSource: Boolean
    get() = isAnyStylusSource && !isExternal

/** Returns true if [InputDevice] is an active stylus. */
val InputDevice.isExternalStylusSource: Boolean
    get() = isAnyStylusSource && isExternal

/**
 * Returns true if [InputDevice] supports any stylus source.
 *
 * @see InputDevice.isInternalStylusSource
 * @see InputDevice.isExternalStylusSource
 */
val InputDevice.isAnyStylusSource: Boolean
    get() = supportsSource(InputDevice.SOURCE_STYLUS)
