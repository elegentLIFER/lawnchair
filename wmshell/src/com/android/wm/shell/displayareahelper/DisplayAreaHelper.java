

package com.android.wm.shell.displayareahelper;

import android.view.SurfaceControl;

import java.util.function.Consumer;

/**
 * Interface that allows to perform various display area related actions
 */
public interface DisplayAreaHelper {

    /**
     * Updates SurfaceControl builder to reparent it to the root display area
     * @param displayId id of the display to which root display area it should be reparented to
     * @param builder surface control builder that should be updated
     * @param onUpdated callback that is invoked after updating the builder, called on
     *                  the shell main thread
     */
    default void attachToRootDisplayArea(int displayId, SurfaceControl.Builder builder,
            Consumer<SurfaceControl.Builder> onUpdated) {
    }

}
