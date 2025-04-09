

@file:JvmName("CommonConstants")

package com.android.wm.shell.flicker.utils

import android.tools.traces.component.ComponentNameMatcher

const val SYSTEM_UI_PACKAGE_NAME = "com.android.systemui"
const val LAUNCHER_UI_PACKAGE_NAME = "com.google.android.apps.nexuslauncher"
val APP_PAIR_SPLIT_DIVIDER_COMPONENT = ComponentNameMatcher("", "AppPairSplitDivider#")
val DOCKED_STACK_DIVIDER_COMPONENT = ComponentNameMatcher("", "DockedStackDivider#")
val SPLIT_SCREEN_DIVIDER_COMPONENT = ComponentNameMatcher("", "StageCoordinatorSplitDivider#")
val SPLIT_DECOR_MANAGER = ComponentNameMatcher("", "SplitDecorManager#")

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}
