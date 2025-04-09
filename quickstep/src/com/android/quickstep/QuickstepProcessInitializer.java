
package com.android.quickstep;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.os.Trace;
import android.os.UserManager;
import android.util.Log;
import android.view.ThreadedRenderer;

import com.android.launcher3.BuildConfig;
import com.android.launcher3.MainProcessInitializer;
import com.android.launcher3.Utilities;
import com.android.systemui.shared.system.InteractionJankMonitorWrapper;

@SuppressWarnings("unused")
public class QuickstepProcessInitializer extends MainProcessInitializer {

        private static final String TAG = "QuickstepProcessInitializer";
        private static final int SETUP_DELAY_MILLIS = 5000;

        public QuickstepProcessInitializer(Context context) {
                // Fake call to create an instance of InteractionJankMonitor to avoid binder
                // calls during
                // its initialization during transitions.
                InteractionJankMonitorWrapper.cancel(-1);
        }

        @Override
        protected void init(Context context) {
                // Workaround for b/120550382, an external app can cause the launcher process to
                // start for
                // a work profile user which we do not support. Disable the application
                // immediately when we
                // detect this to be the case.
                UserManager um = (UserManager) context.getSystemService(Context.USER_SERVICE);
                if (um.isManagedProfile()) {
                        PackageManager pm = context.getPackageManager();
                        pm.setApplicationEnabledSetting(context.getPackageName(),
                                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0 /* flags */);
                        Log.w(TAG, "Disabling " + BuildConfig.APPLICATION_ID
                                        + ", unable to run in a managed profile");
                        return;
                }

                super.init(context);

                // Elevate GPU priority for Quickstep and Remote animations.
                try {
                        if (!Utilities.ATLEAST_Q) return;
                        ThreadedRenderer.setContextPriority(
                                        ThreadedRenderer.EGL_CONTEXT_PRIORITY_HIGH_IMG);
                } catch (Exception e) {
                        Log.e(TAG, "init: " + e);
                }
        }
}
