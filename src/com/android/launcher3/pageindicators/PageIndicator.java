
package com.android.launcher3.pageindicators;

/**
 * Base class for a page indicator.
 */
public interface PageIndicator {

    void setScroll(int currentScroll, int totalScroll);

    void setActiveMarker(int activePage);

    void setMarkersCount(int numMarkers);

    /**
     * Sets a flag indicating whether to pause scroll.
     * <p>Should be set to {@code true} while the screen is binding or new data is being applied,
     * and to {@code false} once done. This prevents animation conflicts due to scrolling during
     * those periods.</p>
     */
    default void setPauseScroll(boolean pause, boolean isTwoPanels) {
        // No-op by default
    }

    /**
     * Sets the flag if the Page Indicator should autohide.
     */
    default void setShouldAutoHide(boolean shouldAutoHide) {
        // No-op by default
    }

    /**
     * Pauses all currently running animations.
     */
    default void pauseAnimations() {
        // No-op by default
    }

    /**
     * Force-ends all currently running or paused animations.
     */
    default void skipAnimationsToEnd() {
        // No-op by default
    }

    /**
     * Sets the paint color.
     */
    default void setPaintColor(int color) {
        // No-op by default
    }
}
