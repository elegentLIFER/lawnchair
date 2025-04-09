

package com.android.systemui.unfold.progress;


import com.android.systemui.unfold.progress.IUnfoldTransitionListener;


/**
 * Interface exposed by System UI to allow remote process to register for unfold animation events.
 */
oneway interface IUnfoldAnimation {

    /**
     * Sets a listener for the animation.
     *
     * Only one listener is supported. If there are multiple, the earlier one will be overridden.
     */
    void setListener(in IUnfoldTransitionListener listener);
}
