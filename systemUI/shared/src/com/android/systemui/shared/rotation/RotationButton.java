

package com.android.systemui.shared.rotation;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Interface of a rotation button that interacts {@link RotationButtonController}.
 * This interface exists because of the two different styles of rotation button in Sysui,
 * one in contextual for 3 button nav and a floating rotation button for gestural.
 */
public interface RotationButton {
    default void setRotationButtonController(RotationButtonController rotationButtonController) { }
    default void setUpdatesCallback(RotationButtonUpdatesCallback updatesCallback) { }

    default View getCurrentView() {
        return null;
    }
    default boolean show() { return false; }
    default boolean hide() { return false; }
    default boolean isVisible() {
        return false;
    }
    default void onTaskbarStateChanged(boolean taskbarVisible, boolean taskbarStashed) {}
    default void updateIcon(int lightIconColor, int darkIconColor) { }
    default void setOnClickListener(View.OnClickListener onClickListener) { }
    default void setOnHoverListener(View.OnHoverListener onHoverListener) { }
    default Drawable getImageDrawable() {
        return null;
    }
    default void setDarkIntensity(float darkIntensity) { }
    default boolean acceptRotationProposal() {
        return getCurrentView() != null;
    }

    /**
     * Callback for updates provided by a rotation button
     */
    interface RotationButtonUpdatesCallback {
        default void onVisibilityChanged(boolean isVisible) {};
        default void onPositionChanged() {};
    }
}
