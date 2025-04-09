

package com.android.wm.shell.pip;

import android.app.RemoteAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Forwards changes to the Picture-in-Picture params to all listeners.
 */
public class PipParamsChangedForwarder {

    private final List<PipParamsChangedCallback>
            mPipParamsChangedListeners = new ArrayList<>();

    /**
     * Add a listener that implements at least one of the callbacks.
     */
    public void addListener(PipParamsChangedCallback listener) {
        if (mPipParamsChangedListeners.contains(listener)) {
            return;
        }
        mPipParamsChangedListeners.add(listener);
    }

    /**
     * Call to notify all listeners of the changed aspect ratio.
     */
    public void notifyAspectRatioChanged(float aspectRatio) {
        for (PipParamsChangedCallback listener : mPipParamsChangedListeners) {
            listener.onAspectRatioChanged(aspectRatio);
        }
    }

    /**
     * Call to notify all listeners of the changed expanded aspect ratio.
     */
    public void notifyExpandedAspectRatioChanged(float aspectRatio) {
        for (PipParamsChangedCallback listener : mPipParamsChangedListeners) {
            listener.onExpandedAspectRatioChanged(aspectRatio);
        }
    }

    /**
     * Call to notify all listeners of the changed title.
     */
    public void notifyTitleChanged(CharSequence title) {
        String value = title == null ? null : title.toString();
        for (PipParamsChangedCallback listener : mPipParamsChangedListeners) {
            listener.onTitleChanged(value);
        }
    }

    /**
     * Call to notify all listeners of the changed subtitle.
     */
    public void notifySubtitleChanged(CharSequence subtitle) {
        String value = subtitle == null ? null : subtitle.toString();
        for (PipParamsChangedCallback listener : mPipParamsChangedListeners) {
            listener.onSubtitleChanged(value);
        }
    }

    /**
     * Call to notify all listeners of the changed app actions or close action.
     */
    public void notifyActionsChanged(List<RemoteAction> actions, RemoteAction closeAction) {
        for (PipParamsChangedCallback listener : mPipParamsChangedListeners) {
            listener.onActionsChanged(actions, closeAction);
        }
    }

    /**
     * Contains callbacks for PiP params changes. Subclasses can choose which changes they want to
     * listen to by only overriding those selectively.
     */
    public interface PipParamsChangedCallback {

        /**
         * Called if aspect ratio changed.
         */
        default void onAspectRatioChanged(float aspectRatio) {
        }

        /**
         * Called if expanded aspect ratio changed.
         */
        default void onExpandedAspectRatioChanged(float aspectRatio) {
        }

        /**
         * Called if either the actions or the close action changed.
         */
        default void onActionsChanged(List<RemoteAction> actions, RemoteAction closeAction) {
        }

        /**
         * Called if the title changed.
         */
        default void onTitleChanged(String title) {
        }

        /**
         * Called if the subtitle changed.
         */
        default void onSubtitleChanged(String subtitle) {
        }
    }
}
