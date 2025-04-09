

package com.android.quickstep.inputconsumers;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.launcher3.R;
import com.android.launcher3.util.ResourceBasedOverride;
import com.android.quickstep.NavHandle;

/**
 * Class for extending nav handle long press behavior
 */
public class NavHandleLongPressHandler implements ResourceBasedOverride {

    /** Creates NavHandleLongPressHandler as specified by overrides */
    public static NavHandleLongPressHandler newInstance(Context context) {
        return Overrides.getObject(NavHandleLongPressHandler.class, context,
                R.string.nav_handle_long_press_handler_class);
    }

    /**
     * Called when nav handle is long pressed to get the Runnable that should be executed by the
     * caller to invoke long press behavior. If null is returned that means long press couldn't be
     * handled.
     * <p>
     * A Runnable is returned here to ensure the InputConsumer can call
     * {@link android.view.InputMonitor#pilferPointers()} before invoking the long press behavior
     * since pilfering can break the long press behavior.
     *
     * @param navHandle to handle this long press
     */
    public @Nullable Runnable getLongPressRunnable(NavHandle navHandle) {
        return null;
    }

    /**
     * Called when nav handle gesture starts.
     *
     * @param navHandle to handle the animation for this touch
     */
    public void onTouchStarted(NavHandle navHandle) {}

    /**
     * Called when nav handle gesture is finished by the user lifting their finger or the system
     * cancelling the touch for some other reason.
     *
     * @param navHandle to handle the animation for this touch
     * @param reason why the touch ended
     */
    public void onTouchFinished(NavHandle navHandle, String reason) {}
}
