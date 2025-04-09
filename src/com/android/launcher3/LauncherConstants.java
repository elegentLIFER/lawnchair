
package com.android.launcher3;

public class LauncherConstants {

    /**
     * Trace events to visualize using Systrace tool.
     */
    public static class TraceEvents {

        public static final String DISPLAY_ALL_APPS_TRACE_METHOD_NAME = "DisplayAllApps";
        public static final int DISPLAY_WORKSPACE_TRACE_COOKIE = 0;
        public static final int DISPLAY_ALL_APPS_TRACE_COOKIE = 1;
        public static final int COLD_STARTUP_TRACE_COOKIE = 2;
        public static final String ON_CREATE_EVT = "Launcher.onCreate";
        public static final String ON_START_EVT = "Launcher.onStart";
        public static final String ON_RESUME_EVT = "Launcher.onResume";
        public static final String ON_NEW_INTENT_EVT = "Launcher.onNewIntent";
        static final String DISPLAY_WORKSPACE_TRACE_METHOD_NAME = "DisplayWorkspaceFirstFrame";
        static final String COLD_STARTUP_TRACE_METHOD_NAME = "LauncherColdStartup";
    }

    /**
     * This are the different codes the Launcher can receive when a new Launcher Intent is created.
     */
    public static class ActivityCodes {

        public static final int REQUEST_BIND_PENDING_APPWIDGET = 12;
        public static final int REQUEST_RECONFIGURE_APPWIDGET = 13;
        public static final int REQUEST_HOME_ROLE = 14;
        static final int REQUEST_CREATE_SHORTCUT = 1;
        static final int REQUEST_CREATE_APPWIDGET = 5;
        static final int REQUEST_PICK_APPWIDGET = 9;
        static final int REQUEST_BIND_APPWIDGET = 11;
    }

    /**
     * Keys used to get the saved values of the previous Activity instance.
     */
    public static class SavedInstanceKeys {

        // Type: int
        public static final String RUNTIME_STATE = "launcher.state";
        // Type PendingSplitSelectInfo<Parcelable>
        public static final String PENDING_SPLIT_SELECT_INFO = "launcher.pending_split_select_info";
        // Type: PendingRequestArgs
        static final String RUNTIME_STATE_PENDING_REQUEST_ARGS = "launcher.request_args";
        // Type: int
        static final String RUNTIME_STATE_PENDING_REQUEST_CODE = "launcher.request_code";
        // Type: ActivityResultInfo
        static final String RUNTIME_STATE_PENDING_ACTIVITY_RESULT = "launcher.activity_result";
        // Type: SparseArray<Parcelable>
        static final String RUNTIME_STATE_WIDGET_PANEL = "launcher.widget_panel";
        // Type int[]
        static final String RUNTIME_STATE_CURRENT_SCREEN_IDS = "launcher.current_screen_ids";
    }
}
