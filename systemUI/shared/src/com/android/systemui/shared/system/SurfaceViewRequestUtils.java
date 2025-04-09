

package com.android.systemui.shared.system;

import android.annotation.Nullable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.view.SurfaceView;

/** Util class that wraps a SurfaceView request into a bundle. */
public class SurfaceViewRequestUtils {
    private static final String KEY_HOST_TOKEN = "host_token";
    private static final String KEY_SURFACE_CONTROL = "surface_control";
    private static final String KEY_DISPLAY_ID = "display_id";

    /** Creates a SurfaceView based bundle that stores the input host token and surface control. */
    public static Bundle createSurfaceBundle(SurfaceView surfaceView) {
        Bundle bundle = new Bundle();
        bundle.putBinder(KEY_HOST_TOKEN, surfaceView.getHostToken());
        bundle.putParcelable(KEY_SURFACE_CONTROL, surfaceView.getSurfaceControl());
        bundle.putInt(KEY_DISPLAY_ID, surfaceView.getDisplay().getDisplayId());
        return bundle;
    }

    /**
     * Retrieves the SurfaceControl from a bundle created by
     * {@link #createSurfaceBundle(SurfaceView)}.
     */
    public static SurfaceControl getSurfaceControl(Bundle bundle) {
        return bundle.getParcelable(KEY_SURFACE_CONTROL);
    }

    /**
     * Retrieves the input token from a bundle created by {@link #createSurfaceBundle(SurfaceView)}.
     */
    public static @Nullable IBinder getHostToken(Bundle bundle) {
        return bundle.getBinder(KEY_HOST_TOKEN);
    }

    /**
     * Retrieves the display id from a bundle created by {@link #createSurfaceBundle(SurfaceView)}.
     */
    public static int getDisplayId(Bundle bundle) {
        return bundle.getInt(KEY_DISPLAY_ID);
    }

    private SurfaceViewRequestUtils() {}
}
