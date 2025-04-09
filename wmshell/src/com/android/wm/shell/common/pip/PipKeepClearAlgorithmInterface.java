

package com.android.wm.shell.common.pip;

import android.graphics.Rect;

import java.util.Set;

/**
 * Interface for interacting with keep clear algorithm used to move PiP window out of the way of
 * keep clear areas.
 */
public interface PipKeepClearAlgorithmInterface {

    /**
     * Adjust the position of picture in picture window based on the registered keep clear areas.
     * @param pipBoundsState state of the PiP to use for the calculations
     * @param pipBoundsAlgorithm algorithm implementation used to get the entry destination bounds
     * @return
     */
    default Rect adjust(PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm) {
        return pipBoundsState.getBounds();
    }

    /**
     * Calculate the bounds so that none of the keep clear areas are occluded, while the bounds stay
     * within the allowed bounds. If such position is not feasible, return original bounds.
     * @param defaultBounds initial bounds used in the calculation
     * @param restrictedKeepClearAreas registered restricted keep clear areas
     * @param unrestrictedKeepClearAreas registered unrestricted keep clear areas
     * @param allowedBounds bounds that define the allowed space for the output, result will always
     *                      be inside those bounds
     * @return bounds that don't cover any of the keep clear areas and are within allowed bounds
     */
    default Rect findUnoccludedPosition(Rect defaultBounds, Set<Rect> restrictedKeepClearAreas,
            Set<Rect> unrestrictedKeepClearAreas, Rect allowedBounds) {
        return defaultBounds;
    }
}
