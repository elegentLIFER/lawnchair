

package com.android.launcher3.util.rule;

import static androidx.test.InstrumentationRegistry.getInstrumentation;

import android.app.Instrumentation;
import android.app.UiAutomation;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rule which captures a screen record for a test.
 * After adding this rule to the test class, apply the annotation @ScreenRecord to individual tests
 */
public class ScreenRecordRule implements TestRule {

    private static final String TAG = "ScreenRecordRule";

    @Override
    public Statement apply(Statement base, Description description) {
        if (description.getAnnotation(ScreenRecord.class) == null) {
            return base;
        }

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Instrumentation inst = getInstrumentation();
                UiAutomation automation = inst.getUiAutomation();
                UiDevice device = UiDevice.getInstance(inst);

                File outputFile = new File(inst.getTargetContext().getFilesDir(),
                        "screenrecord-" + description.getMethodName() + ".mp4");
                device.executeShellCommand("killall screenrecord");
                ParcelFileDescriptor output =
                        automation.executeShellCommand("screenrecord " + outputFile);
                String screenRecordPid = device.executeShellCommand("pidof screenrecord");
                boolean success = false;
                try {
                    base.evaluate();
                    success = true;
                } finally {
                    device.executeShellCommand("kill -INT " + screenRecordPid);
                    Log.e(TAG, "Screenrecord captured at: " + outputFile);
                    output.close();
                    if (success) {
                        automation.executeShellCommand("rm " + outputFile);
                    }
                }
            }
        };
    }

    /**
     * Interface to indicate that the test should capture screenrecord
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ScreenRecord {
    }
}
