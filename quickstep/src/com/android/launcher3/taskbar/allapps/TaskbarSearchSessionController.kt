

package com.android.launcher3.taskbar.allapps

import android.content.Context
import android.view.View
import com.android.launcher3.R
import com.android.launcher3.allapps.AllAppsTransitionListener
import com.android.launcher3.anim.PendingAnimation
import com.android.launcher3.config.FeatureFlags
import com.android.launcher3.dragndrop.DragOptions.PreDragCondition
import com.android.launcher3.model.data.ItemInfo
import com.android.launcher3.util.ResourceBasedOverride
import com.android.launcher3.util.ResourceBasedOverride.Overrides

/** Stub for managing the Taskbar search session. */
open class TaskbarSearchSessionController : ResourceBasedOverride, AllAppsTransitionListener {

    /** Start the search session lifecycle. */
    open fun startLifecycle() = Unit

    /** Destroy the search session. */
    open fun onDestroy() = Unit

    /** Updates the predicted items shown in the zero-state. */
    open fun setZeroStatePredictedItems(items: List<ItemInfo>) = Unit

    /** Updates the search suggestions shown in the zero-state. */
    open fun setZeroStateSearchSuggestions(items: List<ItemInfo>) = Unit

    override fun onAllAppsTransitionStart(toAllApps: Boolean) = Unit

    override fun onAllAppsTransitionEnd(toAllApps: Boolean) = Unit

    /** Creates a [PreDragCondition] for [view], if it is a search result that requires one. */
    open fun createPreDragConditionForSearch(view: View): PreDragCondition? = null

    open fun canHandleBackInvoked(): Boolean = false

    open fun handleBackInvoked(): Boolean = false

    open fun onAllAppsAnimationPending(
        animation: PendingAnimation,
        toAllApps: Boolean,
        showKeyboard: Boolean,
    ) = Unit

    companion object {
        @JvmStatic
        fun newInstance(context: Context): TaskbarSearchSessionController {
            if (!FeatureFlags.ENABLE_ALL_APPS_SEARCH_IN_TASKBAR.get()) {
                return TaskbarSearchSessionController()
            }

            return Overrides.getObject(
                TaskbarSearchSessionController::class.java,
                context,
                R.string.taskbar_search_session_controller_class,
            )
        }
    }
}
