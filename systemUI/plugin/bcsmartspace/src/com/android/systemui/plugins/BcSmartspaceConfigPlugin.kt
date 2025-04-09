

package com.android.systemui.plugins

// TODO(b/265360975): Evaluate this plugin approach.
/** Plugin to provide BC smartspace configuration */
interface BcSmartspaceConfigPlugin {
    /** Gets default date/weather disabled status. */
    val isDefaultDateWeatherDisabled: Boolean
}
