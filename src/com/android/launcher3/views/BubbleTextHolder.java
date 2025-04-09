
package com.android.launcher3.views;

import com.android.launcher3.BubbleTextView;

/**
 * Views that contain {@link BubbleTextView} should implement this interface.
 */
public interface BubbleTextHolder extends FloatingIconViewCompanion {
    BubbleTextView getBubbleText();

    @Override
    default void setIconVisible(boolean visible) {
        getBubbleText().setIconVisible(visible);
    }

    @Override
    default void setForceHideDot(boolean hide) {
        getBubbleText().setForceHideDot(hide);
    }
}
