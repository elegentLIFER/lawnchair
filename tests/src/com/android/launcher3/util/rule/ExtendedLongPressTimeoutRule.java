

package com.android.launcher3.util.rule;

import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewConfiguration;

import androidx.test.InstrumentationRegistry;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ExtendedLongPressTimeoutRule implements TestRule {

    private static final String TAG = "ExtendedLongPressTimeoutRule";

    private static final float LONG_PRESS_TIMEOUT_MULTIPLIER = 10f;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                ContentResolver contentResolver = InstrumentationRegistry.getInstrumentation()
                        .getContext()
                        .getContentResolver();
                int prevLongPressTimeout = Settings.Secure.getInt(
                        contentResolver,
                        Settings.Secure.LONG_PRESS_TIMEOUT,
                        ViewConfiguration.getLongPressTimeout());
                int newLongPressTimeout =
                        (int) (prevLongPressTimeout * LONG_PRESS_TIMEOUT_MULTIPLIER);

                try {
                    Log.d(TAG, "In try-block: Setting long press timeout from "
                            + prevLongPressTimeout + "ms to " + newLongPressTimeout + "ms");
                    Settings.Secure.putInt(
                            contentResolver,
                            Settings.Secure.LONG_PRESS_TIMEOUT,
                            (int) (prevLongPressTimeout * LONG_PRESS_TIMEOUT_MULTIPLIER));

                    base.evaluate();
                } catch (Exception e) {
                    Log.e(TAG, "Error", e);
                    throw e;
                } finally {
                    Log.d(TAG, "In finally-block: resetting long press timeout to "
                            + prevLongPressTimeout + "ms");
                    Settings.Secure.putInt(
                            contentResolver,
                            Settings.Secure.LONG_PRESS_TIMEOUT,
                            prevLongPressTimeout);
                }
            }
        };
    }
}
