
package com.android.quickstep.util;

import static com.android.systemui.shared.system.InputChannelCompat.mergeMotionEvent;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Utility class to dispatch touch events to a different class. It stores the events locally
 * until a valid dispatcher is available.
 */
public class CachedEventDispatcher {

    private Consumer<MotionEvent> mConsumer;

    private ArrayList<MotionEvent> mCache;
    private MotionEvent mLastEvent;

    public void dispatchEvent(MotionEvent event) {
        if (mConsumer != null) {
            mConsumer.accept(event);
        } else {
            if (mLastEvent == null || !mergeMotionEvent(event, mLastEvent)) {
                // Queue event.
                if (mCache == null) {
                    mCache = new ArrayList<>();
                }
                mLastEvent = MotionEvent.obtain(event);
                mCache.add(mLastEvent);
            }
        }
    }

    public void setConsumer(Consumer<MotionEvent> consumer) {
        if (consumer == null) {
            return;
        }
        mConsumer = consumer;
        int cacheCount = mCache == null ? 0 : mCache.size();
        for (int i = 0; i < cacheCount; i++) {
            MotionEvent ev = mCache.get(i);
            mConsumer.accept(ev);
            ev.recycle();
        }
        mCache = null;
        mLastEvent = null;
    }

    public boolean hasConsumer() {
        return mConsumer != null;
    }
}
