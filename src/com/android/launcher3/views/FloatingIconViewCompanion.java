
package com.android.launcher3.views;

import android.view.View;

/**
 * A view that can be drawn (in some capacity) via) {@link FloatingIconView}.
 * This interface allows us to hide certain properties of the view that the FloatingIconView
 * cannot draw, which allows us to make a seamless handoff between the FloatingIconView and
 * the companion view.
 */
public interface FloatingIconViewCompanion {
    void setIconVisible(boolean visible);
    void setForceHideDot(boolean hide);
    default void setForceHideRing(boolean hide) {}
    default void resetIconScale(boolean shouldReset) {}

    /**
     * Sets the visibility of icon and dot of the view
     */
    static void setPropertiesVisible(View view, boolean visible) {
        if (view instanceof FloatingIconViewCompanion) {
            ((FloatingIconViewCompanion) view).setIconVisible(visible);
            ((FloatingIconViewCompanion) view).setForceHideDot(!visible);
            ((FloatingIconViewCompanion) view).setForceHideRing(!visible);
            ((FloatingIconViewCompanion) view).resetIconScale(true);
        } else {
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
