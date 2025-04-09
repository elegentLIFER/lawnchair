

package com.android.launcher3.uioverrides;

import android.app.ActivityOptions;
import android.app.Person;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.window.RemoteTransition;

import com.android.launcher3.Utilities;
import com.android.quickstep.util.FadeOutRemoteTransition;

import java.util.Collections;
import java.util.Map;

import app.lawnchair.util.LawnchairUtilsKt;

/**
 * A wrapper for the hidden API calls
 */
public class ApiWrapper {

    public static final boolean TASKBAR_DRAWN_IN_PROCESS = true;

    public static Person[] getPersons(ShortcutInfo si) {
        if (!Utilities.ATLEAST_Q)
            return Utilities.EMPTY_PERSON_ARRAY;
        Person[] persons = si.getPersons();
        return persons == null ? Utilities.EMPTY_PERSON_ARRAY : persons;
    }

    public static Map<String, LauncherActivityInfo> getActivityOverrides(Context context) {
        return LawnchairUtilsKt.isDefaultLauncher(context)
                && Utilities.ATLEAST_Q ?
                getLauncherActivityOverrides(context)
                : Collections.emptyMap();
    }

    private static Map<String, LauncherActivityInfo> getLauncherActivityOverrides(Context context) {
        try {
            return context.getSystemService(LauncherApps.class).getActivityOverrides();
        } catch (Throwable t) {
            return Collections.emptyMap();
        }
    }

    /**
     * Creates an ActivityOptions to play fade-out animation on closing targets
     */
    public static ActivityOptions createFadeOutAnimOptions(Context context) {
        try {
            ActivityOptions options = ActivityOptions.makeBasic();
            options.setRemoteTransition(new RemoteTransition(new FadeOutRemoteTransition()));
            return options;
        } catch (Throwable t) {
            // TODO Create our own custom closing animation
            return ActivityOptions.makeCustomAnimation(context, 0, android.R.anim.fade_out);
        }
    }
}
