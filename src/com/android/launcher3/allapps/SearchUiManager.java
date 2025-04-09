
package com.android.launcher3.allapps;

import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.android.launcher3.ExtendedEditText;

/**
 * Interface for controlling the Apps search UI.
 */
public interface SearchUiManager {

    /**
     * Initializes the search manager.
     */
    void initializeSearch(ActivityAllAppsContainerView<?> containerView);

    /**
     * Notifies the search manager to close any active search session.
     */
    void resetSearch();

    /**
     * Called before dispatching a key event, in case the search manager wants to initialize
     * some UI beforehand.
     */
    default void preDispatchKeyEvent(KeyEvent keyEvent) { };

    /**
     * @return the edit text object
     */
    @Nullable
    ExtendedEditText getEditText();

    /**
     * Hint to the edit text that it is about to be focused or unfocused. This can be used to start
     * animating the edit box accordingly, e.g. after a gesture completes.
     *
     * @param focused true if the edit text is about to be focused, false if it will be unfocused
     */
    default void prepareToFocusEditText(boolean focused) {}

    /**
     * Sets whether EditText background should be visible
     * @param maxAlpha defines the maximum alpha the background should animates to
     */
    default void setBackgroundVisibility(boolean visible, float maxAlpha) {}

    /**
     * Returns whether a visible background is set on EditText
     */
    default boolean getBackgroundVisibility() {
        return false;
    }

    /**
     * sets highlight result's title
     */
    default void setFocusedResultTitle(
            @Nullable CharSequence title, @Nullable CharSequence subtitle, boolean showArrow) {}

    /** Refresh the currently displayed list of results. */
    default void refreshResults() {}

    /** Returns whether search is in zero state. */
    default boolean inZeroState() {
        return false;
    }
}
