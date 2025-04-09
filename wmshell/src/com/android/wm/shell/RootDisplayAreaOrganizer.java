

package com.android.wm.shell;

import static com.android.wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;

import android.annotation.SuppressLint;
import android.app.WindowConfiguration;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import android.window.WindowContainerTransaction;

import androidx.annotation.NonNull;

import com.android.internal.protolog.common.ProtoLog;
import com.android.wm.shell.sysui.ShellInit;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;

/** Display area organizer for the root display areas */
public class RootDisplayAreaOrganizer extends DisplayAreaOrganizer {

    private static final String TAG = RootDisplayAreaOrganizer.class.getSimpleName();

    /** {@link DisplayAreaInfo} list, which is mapped by display IDs. */
    private final SparseArray<DisplayAreaInfo> mDisplayAreasInfo = new SparseArray<>();
    /** Display area leashes, which is mapped by display IDs. */
    private final SparseArray<SurfaceControl> mLeashes = new SparseArray<>();

    public RootDisplayAreaOrganizer(@NonNull Executor executor, @NonNull ShellInit shellInit) {
        super(executor);
        shellInit.addInitCallback(this::onInit, this);
    }

    @SuppressLint("MissingPermission") // Only called by SysUI.
    private void onInit() {
        final List<DisplayAreaAppearedInfo> infos = registerOrganizer(FEATURE_ROOT);
        for (int i = infos.size() - 1; i >= 0; --i) {
            onDisplayAreaAppeared(infos.get(i).getDisplayAreaInfo(), infos.get(i).getLeash());
        }
    }

    public void attachToDisplayArea(int displayId, SurfaceControl.Builder b) {
        final SurfaceControl sc = mLeashes.get(displayId);
        if (sc != null) {
            b.setParent(sc);
        }
    }

    @Override
    public void onDisplayAreaAppeared(@NonNull DisplayAreaInfo displayAreaInfo,
            @NonNull SurfaceControl leash) {
        if (displayAreaInfo.featureId != FEATURE_ROOT) {
            throw new IllegalArgumentException(
                    "Unknown feature: " + displayAreaInfo.featureId
                            + "displayAreaInfo:" + displayAreaInfo);
        }

        final int displayId = displayAreaInfo.displayId;
        if (mDisplayAreasInfo.get(displayId) != null) {
            throw new IllegalArgumentException(
                    "Duplicate DA for displayId: " + displayId
                            + " displayAreaInfo:" + displayAreaInfo
                            + " mDisplayAreasInfo.get():" + mDisplayAreasInfo.get(displayId));
        }

        leash.setUnreleasedWarningCallSite("RootDisplayAreaOrganizer.onDisplayAreaAppeared");
        mDisplayAreasInfo.put(displayId, displayAreaInfo);
        mLeashes.put(displayId, leash);
    }

    @Override
    public void onDisplayAreaVanished(@NonNull DisplayAreaInfo displayAreaInfo) {
        final int displayId = displayAreaInfo.displayId;
        if (mDisplayAreasInfo.get(displayId) == null) {
            throw new IllegalArgumentException(
                    "onDisplayAreaVanished() Unknown DA displayId: " + displayId
                            + " displayAreaInfo:" + displayAreaInfo
                            + " mDisplayAreasInfo.get():" + mDisplayAreasInfo.get(displayId));
        }

        mDisplayAreasInfo.remove(displayId);
        mLeashes.get(displayId).release();
        mLeashes.remove(displayId);
    }

    @Override
    public void onDisplayAreaInfoChanged(@NonNull DisplayAreaInfo displayAreaInfo) {
        final int displayId = displayAreaInfo.displayId;
        if (mDisplayAreasInfo.get(displayId) == null) {
            throw new IllegalArgumentException(
                    "onDisplayAreaInfoChanged() Unknown DA displayId: " + displayId
                            + " displayAreaInfo:" + displayAreaInfo
                            + " mDisplayAreasInfo.get():" + mDisplayAreasInfo.get(displayId));
        }

        mDisplayAreasInfo.put(displayId, displayAreaInfo);
    }

    /**
     * Create a {@link WindowContainerTransaction} to update display windowing mode.
     *
     * @param displayId display id to update windowing mode for
     * @param windowingMode target {@link WindowConfiguration.WindowingMode}
     * @return {@link WindowContainerTransaction} with pending operation to set windowing mode
     */
    public WindowContainerTransaction prepareWindowingModeChange(int displayId,
            @WindowConfiguration.WindowingMode int windowingMode) {
        WindowContainerTransaction wct = new WindowContainerTransaction();
        DisplayAreaInfo displayAreaInfo = mDisplayAreasInfo.get(displayId);
        if (displayAreaInfo == null) {
            ProtoLog.e(WM_SHELL_DESKTOP_MODE,
                    "unable to update windowing mode for display %d display not found", displayId);
            return wct;
        }

        ProtoLog.d(WM_SHELL_DESKTOP_MODE,
                "setWindowingMode: displayId=%d current wmMode=%d new wmMode=%d", displayId,
                displayAreaInfo.configuration.windowConfiguration.getWindowingMode(),
                windowingMode);

        wct.setWindowingMode(displayAreaInfo.token, windowingMode);
        return wct;
    }

    public void dump(@NonNull PrintWriter pw, String prefix) {
        final String innerPrefix = prefix + "  ";
        final String childPrefix = innerPrefix + "  ";
        pw.println(prefix + this);

        for (int i = 0; i < mDisplayAreasInfo.size(); i++) {
            int displayId = mDisplayAreasInfo.keyAt(i);
            DisplayAreaInfo displayAreaInfo = mDisplayAreasInfo.get(displayId);
            int windowingMode =
                    displayAreaInfo.configuration.windowConfiguration.getWindowingMode();
            pw.println(innerPrefix + "# displayId=" + displayId + " wmMode=" + windowingMode);
        }
    }

    @Override
    public String toString() {
        return TAG + "#" + mDisplayAreasInfo.size();
    }

}
