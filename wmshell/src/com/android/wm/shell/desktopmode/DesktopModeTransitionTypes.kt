

package com.android.wm.shell.desktopmode

import android.view.WindowManager.TransitionType
import com.android.wm.shell.common.desktopmode.DesktopModeTransitionSource
import com.android.wm.shell.transition.Transitions.TRANSIT_DESKTOP_MODE_TYPES

/**
 * Contains desktop mode [TransitionType]s (extended from [TRANSIT_DESKTOP_MODE_TYPES]) and helper
 * methods.
 */
object DesktopModeTransitionTypes {

    const val TRANSIT_ENTER_DESKTOP_FROM_APP_HANDLE_MENU_BUTTON = TRANSIT_DESKTOP_MODE_TYPES + 1
    const val TRANSIT_ENTER_DESKTOP_FROM_APP_FROM_OVERVIEW = TRANSIT_DESKTOP_MODE_TYPES + 2
    const val TRANSIT_ENTER_DESKTOP_FROM_KEYBOARD_SHORTCUT = TRANSIT_DESKTOP_MODE_TYPES + 3
    const val TRANSIT_ENTER_DESKTOP_FROM_UNKNOWN = TRANSIT_DESKTOP_MODE_TYPES + 4
    const val TRANSIT_EXIT_DESKTOP_MODE_TASK_DRAG = TRANSIT_DESKTOP_MODE_TYPES + 5
    const val TRANSIT_EXIT_DESKTOP_MODE_HANDLE_MENU_BUTTON = TRANSIT_DESKTOP_MODE_TYPES + 6
    const val TRANSIT_EXIT_DESKTOP_MODE_KEYBOARD_SHORTCUT = TRANSIT_DESKTOP_MODE_TYPES + 7
    const val TRANSIT_EXIT_DESKTOP_MODE_UNKNOWN = TRANSIT_DESKTOP_MODE_TYPES + 8

    /** Return whether the [TransitionType] corresponds to a transition to enter desktop mode. */
    @JvmStatic
    fun @receiver:TransitionType Int.isEnterDesktopModeTransition(): Boolean {
        return this in
            listOf(
                TRANSIT_ENTER_DESKTOP_FROM_APP_HANDLE_MENU_BUTTON,
                TRANSIT_ENTER_DESKTOP_FROM_APP_FROM_OVERVIEW,
                TRANSIT_ENTER_DESKTOP_FROM_KEYBOARD_SHORTCUT,
                TRANSIT_ENTER_DESKTOP_FROM_UNKNOWN
            )
    }

    /**
     * Returns corresponding desktop mode enter [TransitionType] for a
     * [DesktopModeTransitionSource].
     */
    @JvmStatic
    @TransitionType
    fun DesktopModeTransitionSource.getEnterTransitionType(): Int {
        return when (this) {
            DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON ->
                TRANSIT_ENTER_DESKTOP_FROM_APP_HANDLE_MENU_BUTTON
            DesktopModeTransitionSource.APP_FROM_OVERVIEW ->
                TRANSIT_ENTER_DESKTOP_FROM_APP_FROM_OVERVIEW
            DesktopModeTransitionSource.KEYBOARD_SHORTCUT ->
                TRANSIT_ENTER_DESKTOP_FROM_KEYBOARD_SHORTCUT
            else -> TRANSIT_ENTER_DESKTOP_FROM_UNKNOWN
        }
    }

    /** Return whether the [TransitionType] corresponds to a transition to exit desktop mode. */
    @JvmStatic
    fun @receiver:TransitionType Int.isExitDesktopModeTransition(): Boolean {
        return this in
            listOf(
                TRANSIT_EXIT_DESKTOP_MODE_TASK_DRAG,
                TRANSIT_EXIT_DESKTOP_MODE_HANDLE_MENU_BUTTON,
                TRANSIT_EXIT_DESKTOP_MODE_KEYBOARD_SHORTCUT,
                TRANSIT_EXIT_DESKTOP_MODE_UNKNOWN
            )
    }

    /**
     * Returns corresponding desktop mode exit [TransitionType] for a [DesktopModeTransitionSource].
     */
    @JvmStatic
    @TransitionType
    fun DesktopModeTransitionSource.getExitTransitionType(): Int {
        return when (this) {
            DesktopModeTransitionSource.TASK_DRAG -> TRANSIT_EXIT_DESKTOP_MODE_TASK_DRAG
            DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON ->
                TRANSIT_EXIT_DESKTOP_MODE_HANDLE_MENU_BUTTON
            DesktopModeTransitionSource.KEYBOARD_SHORTCUT ->
                TRANSIT_EXIT_DESKTOP_MODE_KEYBOARD_SHORTCUT
            else -> TRANSIT_EXIT_DESKTOP_MODE_UNKNOWN
        }
    }
}
