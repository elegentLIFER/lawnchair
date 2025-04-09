

package com.android.wm.shell.onehanded;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import androidx.annotation.NonNull;

import com.android.wm.shell.R;

import java.io.PrintWriter;

/**
 * The util for handling A11y events.
 */
public final class OneHandedAccessibilityUtil {
    private static final String TAG = "OneHandedAccessibilityUtil";

    private final AccessibilityManager mAccessibilityManager;
    private final String mStartOneHandedDescription;
    private final String mStopOneHandedDescription;
    private final String mPackageName;

    private String mDescription;

    public OneHandedAccessibilityUtil(Context context) {
        mAccessibilityManager = AccessibilityManager.getInstance(context);
        mPackageName = context.getPackageName();
        mStartOneHandedDescription = context.getResources().getString(
                R.string.accessibility_action_start_one_handed);
        mStopOneHandedDescription = context.getResources().getString(
                R.string.accessibility_action_stop_one_handed);
    }

    /**
     * Gets One-Handed start description.
     * @return text of start description.
     */
    public String getOneHandedStartDescription() {
        return mStartOneHandedDescription;
    }

    /**
     * Gets One-Handed stop description.
     * @return text of stop description.
     */
    public String getOneHandedStopDescription() {
        return mStopOneHandedDescription;
    }

    /**
     * Announcement of A11y Events
     * @param description for accessibility announcement text
     */
    public void announcementForScreenReader(String description) {
        if (!mAccessibilityManager.isTouchExplorationEnabled()) {
            return;
        }
        mDescription = description;
        final AccessibilityEvent event = AccessibilityEvent.obtain();
        event.setPackageName(mPackageName);
        event.setEventType(AccessibilityEvent.TYPE_ANNOUNCEMENT);
        event.getText().add(mDescription);
        mAccessibilityManager.sendAccessibilityEvent(event);
    }

    public void dump(@NonNull PrintWriter pw) {
        final String innerPrefix = "  ";
        pw.println(TAG);
        pw.print(innerPrefix + "mPackageName=");
        pw.println(mPackageName);
        pw.print(innerPrefix + "mDescription=");
        pw.println(mDescription);
    }
}
