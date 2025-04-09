
package com.android.wm.shell.bubbles

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.android.wm.shell.bubbles.BubbleDebugConfig.DEBUG_USER_EDUCATION
import com.android.wm.shell.bubbles.BubbleDebugConfig.TAG_BUBBLES
import com.android.wm.shell.bubbles.BubbleDebugConfig.TAG_WITH_CLASS_NAME

/** Manages bubble education flags. Provides convenience methods to check the education state */
class BubbleEducationController(private val context: Context) {
    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    /** Whether the user has seen the stack education */
    @get:JvmName(name = "hasSeenStackEducation")
    var hasSeenStackEducation: Boolean
        get() = prefs.getBoolean(PREF_STACK_EDUCATION, false)
        set(value) = prefs.edit { putBoolean(PREF_STACK_EDUCATION, value) }

    /** Whether the user has seen the expanded view "manage" menu education */
    @get:JvmName(name = "hasSeenManageEducation")
    var hasSeenManageEducation: Boolean
        get() = prefs.getBoolean(PREF_MANAGED_EDUCATION, false)
        set(value) = prefs.edit { putBoolean(PREF_MANAGED_EDUCATION, value) }

    /** Whether education view should show for the collapsed stack. */
    fun shouldShowStackEducation(bubble: BubbleViewProvider?): Boolean {
        if (BubbleDebugConfig.neverShowUserEducation(context)) {
            logDebug("Show stack edu: never")
            return false
        }

        val shouldShow =
            bubble != null &&
                bubble.isConversationBubble && // show education for conversation bubbles only
                (!hasSeenStackEducation || BubbleDebugConfig.forceShowUserEducation(context))
        logDebug("Show stack edu: $shouldShow")
        return shouldShow
    }

    /** Whether the educational view should show for the expanded view "manage" menu. */
    fun shouldShowManageEducation(bubble: BubbleViewProvider?): Boolean {
        if (BubbleDebugConfig.neverShowUserEducation(context)) {
            logDebug("Show manage edu: never")
            return false
        }

        val shouldShow =
            bubble != null &&
                bubble.isConversationBubble && // show education for conversation bubbles only
                (!hasSeenManageEducation || BubbleDebugConfig.forceShowUserEducation(context))
        logDebug("Show manage edu: $shouldShow")
        return shouldShow
    }

    private fun logDebug(message: String) {
        if (DEBUG_USER_EDUCATION) {
            Log.d(TAG, message)
        }
    }

    companion object {
        private val TAG = if (TAG_WITH_CLASS_NAME) "BubbleEducationController" else TAG_BUBBLES
        const val PREF_STACK_EDUCATION: String = "HasSeenBubblesOnboarding"
        const val PREF_MANAGED_EDUCATION: String = "HasSeenBubblesManageOnboarding"
    }
}

/** Convenience extension method to check if the bubble is a conversation bubble */
private val BubbleViewProvider.isConversationBubble: Boolean
    get() = if (this is Bubble) isConversation else false
