

package com.android.wm.shell.compatui;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * A component which can provide a {@link View} to use as a container for a Dialog
 */
public interface DialogContainerSupplier {

    /**
     * @return The {@link View} to use as a container for a Dialog
     */
    View getDialogContainerView();

    /**
     * @return The {@link Drawable} to use as background of the dialog.
     */
    Drawable getBackgroundDimDrawable();
}
