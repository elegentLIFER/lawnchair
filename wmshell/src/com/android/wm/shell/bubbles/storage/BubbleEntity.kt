
package com.android.wm.shell.bubbles.storage

import android.annotation.DimenRes
import android.annotation.UserIdInt

data class BubbleEntity(
    @UserIdInt val userId: Int,
    val packageName: String,
    val shortcutId: String,
    val key: String,
    val desiredHeight: Int,
    @DimenRes val desiredHeightResId: Int,
    val title: String? = null,
    val taskId: Int,
    val locus: String? = null,
    val isDismissable: Boolean = false
)
