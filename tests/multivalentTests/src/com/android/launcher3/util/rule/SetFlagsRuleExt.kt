

package com.android.launcher3.util.rule

import android.platform.test.flag.junit.SetFlagsRule

fun SetFlagsRule.setFlags(enabled: Boolean, vararg flagName: String) {
    if (enabled) enableFlags(*flagName) else disableFlags(*flagName)
}
