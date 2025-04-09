

package com.android.wm.shell.bubbles;

import android.os.Bundle;
import com.android.wm.shell.common.bubbles.BubbleBarLocation;
/**
 * Listener interface that Launcher attaches to SystemUI to get bubbles callbacks.
 */
oneway interface IBubblesListener {

    /**
     * Called when the bubbles state changes.
     */
    void onBubbleStateChange(in Bundle update);

    /**
     * Called when bubble bar should temporarily be animated to a new location.
     * Does not result in a state change.
     */
    void animateBubbleBarLocation(in BubbleBarLocation location);
}
