
package com.android.quickstep.util;

import static android.view.Display.DEFAULT_DISPLAY;

import android.content.Context;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.view.DisplayCutout;
import android.view.Surface;
import android.view.WindowManager;
import android.view.WindowMetrics;

import com.android.internal.policy.SystemBarUtils;
import com.android.launcher3.logging.FileLog;
import com.android.launcher3.statehandlers.DesktopVisibilityController;
import com.android.launcher3.util.WindowBounds;
import com.android.launcher3.util.window.CachedDisplayInfo;
import com.android.launcher3.util.window.WindowManagerProxy;
import com.android.quickstep.LauncherActivityInterface;

import java.util.List;
import java.util.Set;

/**
 * Extension of {@link WindowManagerProxy} with some assumption for the default
 * system Launcher
 */
public class SystemWindowManagerProxy extends WindowManagerProxy {

    public SystemWindowManagerProxy(Context context) {
        super(true);
    }

    @Override
    public Rect getCurrentBounds(Context displayInfoContext) {
        return displayInfoContext.getResources().getConfiguration().windowConfiguration
                .getMaxBounds();
    }

    @Override
    public boolean isInDesktopMode() {
        DesktopVisibilityController desktopController = LauncherActivityInterface.INSTANCE
                .getDesktopVisibilityController();
        return desktopController != null && desktopController.areDesktopTasksVisible();
    }

    @Override
    public int getRotation(Context displayInfoContext) {
        return displayInfoContext.getResources().getConfiguration().windowConfiguration
                .getRotation();
    }

    @Override
    protected int getStatusBarHeight(Context context, boolean isPortrait, int statusBarInset) {
        // See b/264656380, calculate the status bar height manually as the inset in the
        // system
        // server might not be updated by this point yet causing extra DeviceProfile
        // updates
        return SystemBarUtils.getStatusBarHeight(context);
    }

    @Override
    public ArrayMap<CachedDisplayInfo, List<WindowBounds>> estimateInternalDisplayBounds(
            Context displayInfoContext) {
        ArrayMap<CachedDisplayInfo, List<WindowBounds>> result = new ArrayMap<>();
        WindowManager windowManager = displayInfoContext.getSystemService(WindowManager.class);
        Set<WindowMetrics> possibleMaximumWindowMetrics = windowManager
                .getPossibleMaximumWindowMetrics(DEFAULT_DISPLAY);
        FileLog.d("b/283944974", "possibleMaximumWindowMetrics: " + possibleMaximumWindowMetrics);
        for (WindowMetrics windowMetrics : possibleMaximumWindowMetrics) {
            CachedDisplayInfo info = getDisplayInfo(windowMetrics, Surface.ROTATION_0);
            List<WindowBounds> bounds = estimateWindowBounds(displayInfoContext, info);
            result.put(info, bounds);
        }
        return result;
    }

    @Override
    protected DisplayCutout rotateCutout(DisplayCutout original, int startWidth, int startHeight,
            int fromRotation, int toRotation) {
        return original.getRotated(startWidth, startHeight, fromRotation, toRotation);
    }
}
