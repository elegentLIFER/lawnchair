

package com.android.wm.shell.common;

import android.view.SurfaceControl;
import android.view.SurfaceSession;

/**
 * Helpers for handling surface.
 */
public class SurfaceUtils {
    /** Creates a dim layer above host surface. */
    public static SurfaceControl makeDimLayer(SurfaceControl.Transaction t, SurfaceControl host,
            String name, SurfaceSession surfaceSession) {
        final SurfaceControl dimLayer = makeColorLayer(host, name, surfaceSession);
        t.setLayer(dimLayer, Integer.MAX_VALUE).setColor(dimLayer, new float[]{0f, 0f, 0f});
        return dimLayer;
    }

    /** Creates a color layer for host surface. */
    public static SurfaceControl makeColorLayer(SurfaceControl host, String name,
            SurfaceSession surfaceSession) {
        return new SurfaceControl.Builder(surfaceSession)
                .setParent(host)
                .setColorLayer()
                .setName(name)
                .setCallsite("SurfaceUtils.makeColorLayer")
                .build();
    }
}
