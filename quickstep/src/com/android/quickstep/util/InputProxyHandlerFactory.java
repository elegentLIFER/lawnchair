
package com.android.quickstep.util;

import androidx.annotation.UiThread;

import com.android.quickstep.BaseContainerInterface;
import com.android.quickstep.GestureState;
import com.android.quickstep.InputConsumer;
import com.android.quickstep.inputconsumers.OverviewInputConsumer;
import com.android.quickstep.views.RecentsViewContainer;

import java.util.function.Supplier;

/**
 * A factory that creates a input consumer for
 *  {@link com.android.quickstep.util.InputConsumerProxy}.
 */
public class InputProxyHandlerFactory implements Supplier<InputConsumer> {

    private final BaseContainerInterface mContainerInterface;
    private final GestureState mGestureState;

    @UiThread
    public InputProxyHandlerFactory(BaseContainerInterface activityInterface,
            GestureState gestureState) {
        mContainerInterface = activityInterface;
        mGestureState = gestureState;
    }

    /**
     * Called to create a input proxy for the running task
     */
    @Override
    public InputConsumer get() {
        RecentsViewContainer container = mContainerInterface.getCreatedContainer();
        return container == null ? InputConsumer.NO_OP
                : new OverviewInputConsumer(mGestureState, container, null, true);
    }
}
