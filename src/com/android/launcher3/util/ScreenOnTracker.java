
package com.android.launcher3.util;

import static android.content.Intent.ACTION_SCREEN_OFF;
import static android.content.Intent.ACTION_SCREEN_ON;
import static android.content.Intent.ACTION_USER_PRESENT;

import android.content.Context;
import android.content.Intent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Utility class for tracking if the screen is currently on or off
 */
public class ScreenOnTracker implements SafeCloseable {

    public static final MainThreadInitializedObject<ScreenOnTracker> INSTANCE =
            new MainThreadInitializedObject<>(ScreenOnTracker::new);

    private final SimpleBroadcastReceiver mReceiver = new SimpleBroadcastReceiver(this::onReceive);
    private final CopyOnWriteArrayList<ScreenOnListener> mListeners = new CopyOnWriteArrayList<>();

    private final Context mContext;
    private boolean mIsScreenOn;

    private ScreenOnTracker(Context context) {
        // Assume that the screen is on to begin with
        mContext = context;
        mIsScreenOn = true;
        mReceiver.register(context, ACTION_SCREEN_ON, ACTION_SCREEN_OFF, ACTION_USER_PRESENT);
    }

    @Override
    public void close() {
        mReceiver.unregisterReceiverSafely(mContext);
    }

    private void onReceive(Intent intent) {
        String action = intent.getAction();
        if (ACTION_SCREEN_ON.equals(action)) {
            mIsScreenOn = true;
            dispatchScreenOnChanged();
        } else if (ACTION_SCREEN_OFF.equals(action)) {
            mIsScreenOn = false;
            dispatchScreenOnChanged();
        } else if (ACTION_USER_PRESENT.equals(action)) {
            mListeners.forEach(ScreenOnListener::onUserPresent);
        }
    }

    private void dispatchScreenOnChanged() {
        mListeners.forEach(l -> l.onScreenOnChanged(mIsScreenOn));
    }

    /** Returns if the screen is on or not */
    public boolean isScreenOn() {
        return mIsScreenOn;
    }

    /** Adds a listener for screen on changes */
    public void addListener(ScreenOnListener listener) {
        mListeners.add(listener);
    }

    /** Removes a previously added listener */
    public void removeListener(ScreenOnListener listener) {
        mListeners.remove(listener);
    }

    /**
     * Interface to listen for screen on changes
     */
    public interface ScreenOnListener {

        /**
         * Called when the screen turns on/off
         */
        void onScreenOnChanged(boolean isOn);

        /**
         * Called when the keyguard goes away
         */
        default void onUserPresent() { }
    }
}
