

package com.android.systemui.unfold.progress;


/**
 * Implemented by remote processes to receive unfold animation events from System UI.
 */
oneway interface IUnfoldTransitionListener {
    /**
    * Sent when unfold animation started.
    */
    void onTransitionStarted() = 1;

    /**
    * Sent when unfold animation progress changes.
    */
    void onTransitionProgress(float progress) = 2;

    /**
    * Sent when unfold animation finished.
    */
    void onTransitionFinished() = 3;
}
