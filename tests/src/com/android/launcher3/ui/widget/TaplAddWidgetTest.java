
package com.android.launcher3.ui.widget;

import static com.android.launcher3.util.rule.TestStabilityRule.LOCAL;
import static com.android.launcher3.util.rule.TestStabilityRule.PLATFORM_POSTSUBMIT;

import static org.junit.Assert.assertNotNull;

import android.platform.test.annotations.PlatinumTest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.android.launcher3.Launcher;
import com.android.launcher3.celllayout.FavoriteItemsTransaction;
import com.android.launcher3.tapl.Widget;
import com.android.launcher3.tapl.WidgetResizeFrame;
import com.android.launcher3.ui.AbstractLauncherUiTest;
import com.android.launcher3.ui.PortraitLandscapeRunner.PortraitLandscape;
import com.android.launcher3.ui.TestViewHelpers;
import com.android.launcher3.util.rule.ShellCommandRule;
import com.android.launcher3.util.rule.TestStabilityRule.Stability;
import com.android.launcher3.widget.LauncherAppWidgetProviderInfo;

import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test to add widget from widget tray
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaplAddWidgetTest extends AbstractLauncherUiTest<Launcher> {

    @Rule
    public ShellCommandRule mGrantWidgetRule = ShellCommandRule.grantWidgetBind();

    @Test
    @PortraitLandscape
    public void testDragIcon() throws Throwable {
        mLauncher.enableDebugTracing(); // b/289161193
        commitTransactionAndLoadHome(new FavoriteItemsTransaction(mTargetContext));

        waitForLauncherCondition("Workspace didn't finish loading", l -> !l.isWorkspaceLoading());

        final LauncherAppWidgetProviderInfo widgetInfo =
                TestViewHelpers.findWidgetProvider(false /* hasConfigureScreen */);

        WidgetResizeFrame resizeFrame = mLauncher
                .getWorkspace()
                .openAllWidgets()
                .getWidget(widgetInfo.getLabel(mTargetContext.getPackageManager()))
                .dragWidgetToWorkspace();

        assertNotNull("Widget resize frame not shown after widget add", resizeFrame);
        resizeFrame.dismiss();

        final Widget widget = mLauncher.getWorkspace().tryGetWidget(widgetInfo.label,
                DEFAULT_UI_TIMEOUT);
        assertNotNull("Widget not found on the workspace", widget);
        widget.launch(getAppPackageName());
        mLauncher.disableDebugTracing(); // b/289161193
    }

    /**
     * Test dragging a custom shortcut to the workspace and launch it.
     *
     * A custom shortcut is a 1x1 widget that launches a specific intent when user tap on it.
     * Custom shortcuts are replaced by deep shortcuts after api 25.
     */
    @Test
    @PortraitLandscape
    public void testDragCustomShortcut() throws Throwable {
        // TODO(b/322820039): Enable test for tablets - the picker UI has changed and test needs to
        //  be updated to look for appropriate UI elements.
        Assume.assumeFalse(mLauncher.isTablet());
        commitTransactionAndLoadHome(new FavoriteItemsTransaction(mTargetContext));

        mLauncher.getWorkspace().openAllWidgets()
                .getWidget("com.android.launcher3.testcomponent.CustomShortcutConfigActivity")
                .dragToWorkspace(false, true);
        mLauncher.getWorkspace().getWorkspaceAppIcon("Shortcut")
                .launch(getAppPackageName());
    }

    /**
     * Test dragging a widget to the workspace and resize it.
     */
    @Stability(flavors = LOCAL | PLATFORM_POSTSUBMIT) // b/316910614
    @PlatinumTest(focusArea = "launcher")
    @Test
    public void testResizeWidget() throws Throwable {
        commitTransactionAndLoadHome(new FavoriteItemsTransaction(mTargetContext));

        waitForLauncherCondition("Workspace didn't finish loading", l -> !l.isWorkspaceLoading());

        final LauncherAppWidgetProviderInfo widgetInfo =
                TestViewHelpers.findWidgetProvider(false /* hasConfigureScreen */);

        WidgetResizeFrame resizeFrame = mLauncher
                .getWorkspace()
                .openAllWidgets()
                .getWidget(widgetInfo.getLabel(mTargetContext.getPackageManager()))
                .dragWidgetToWorkspace();

        assertNotNull("Widget resize frame not shown after widget add", resizeFrame);
        resizeFrame.resize();
    }
}
