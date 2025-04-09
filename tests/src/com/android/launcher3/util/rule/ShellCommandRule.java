
package com.android.launcher3.util.rule;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static com.android.launcher3.tapl.TestHelpers.getLauncherInMyProcess;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;

import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.android.systemui.shared.system.PackageManagerWrapper;

import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;

/**
 * Test rule which executes a shell command at the start of the test.
 */
public class ShellCommandRule implements TestRule {
    private final String mCmd;
    private final String mRevertCommand;
    private final boolean mCheckSuccess;
    private final Runnable mAdditionalChecks;

    public ShellCommandRule(String cmd, @Nullable String revertCommand, boolean checkSuccess,
            Runnable additionalChecks) {
        mCmd = cmd;
        mRevertCommand = revertCommand;
        mCheckSuccess = checkSuccess;
        mAdditionalChecks = additionalChecks;
    }

    public ShellCommandRule(String cmd, @Nullable String revertCommand) {
        this(cmd, revertCommand, false, null);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final String result =
                        UiDevice.getInstance(getInstrumentation()).executeShellCommand(mCmd);
                if (mCheckSuccess) {
                    Assert.assertTrue(
                            "Failed command: " + mCmd + ", result: " + result,
                            "Success".equals(result.replaceAll("\\s", "")));
                }
                if (mAdditionalChecks != null) mAdditionalChecks.run();
                try {
                    base.evaluate();
                } finally {
                    if (mRevertCommand != null) {
                        final String revertResult = UiDevice.getInstance(
                                getInstrumentation()).executeShellCommand(mRevertCommand);
                        if (mCheckSuccess) {
                            Assert.assertTrue(
                                    "Failed command: " + mRevertCommand
                                            + ", result: " + revertResult,
                                    "Success".equals(result.replaceAll("\\s", "")));
                        }
                    }
                }
            }
        };
    }

    /**
     * Grants the launcher permission to bind widgets.
     */
    public static ShellCommandRule grantWidgetBind() {
        return new ShellCommandRule("appwidget grantbind --package "
                + InstrumentationRegistry.getTargetContext().getPackageName(), null);
    }

    /**
     * Sets the target launcher as default launcher.
     */
    public static ShellCommandRule setDefaultLauncher() {
        final ActivityInfo launcher = getLauncherInMyProcess();
        return new ShellCommandRule(getLauncherCommand(launcher), null, true, () ->
                Assert.assertEquals("Setting default launcher failed",
                        new ComponentName(launcher.packageName, launcher.name)
                                .flattenToString(),
                        PackageManagerWrapper.getInstance().getHomeActivities(new ArrayList<>())
                                .flattenToString()));
    }

    public static String getLauncherCommand(ActivityInfo launcher) {
        return "cmd package set-home-activity " +
                new ComponentName(launcher.packageName, launcher.name).flattenToString();
    }

    /**
     * Disables heads up notification for the duration of the test
     */
    public static ShellCommandRule disableHeadsUpNotification() {
        return new ShellCommandRule("settings put global heads_up_notifications_enabled 0",
                "settings put global heads_up_notifications_enabled 1");
    }
}
