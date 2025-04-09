
package com.android.launcher3.testcomponent;

import android.app.Instrumentation;
import android.net.Uri;
import android.os.Bundle;

import androidx.test.InstrumentationRegistry;

/**
 * Content provider to receive commands from tests
 */
public class TestCommandReceiver {

    public static final String ENABLE_TEST_LAUNCHER = "enable-test-launcher";
    public static final String DISABLE_TEST_LAUNCHER = "disable-test-launcher";
    public static final String KILL_PROCESS = "kill-process";
    public static final String GET_SYSTEM_HEALTH_MESSAGE = "get-system-health-message";
    public static final String SET_LIST_VIEW_SERVICE_BINDER = "set-list-view-service-binder";

    public static final String EXTRA_VALUE = "value";

    public static Bundle callCommand(String command) {
        return callCommand(command, null);
    }

    public static Bundle callCommand(String command, String arg) {
        return callCommand(command, arg, null);
    }

    public static Bundle callCommand(String command, String arg, Bundle extras) {
        Instrumentation inst = InstrumentationRegistry.getInstrumentation();
        Uri uri = Uri.parse("content://" + inst.getContext().getPackageName() + ".commands");
        return inst.getTargetContext().getContentResolver().call(uri, command, arg, extras);
    }
}
