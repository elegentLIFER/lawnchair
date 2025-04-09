

package com.android.wm.shell.transition;

import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.PixelFormat;
import android.hardware.HardwareBuffer;
import android.view.SurfaceControl;
import android.view.SurfaceSession;

/**
 * Represents a surface that is displayed over a transition surface.
 */
class WindowThumbnail {

    private SurfaceControl mSurfaceControl;

    private WindowThumbnail() {}

    /** Create a thumbnail surface and attach it over a parent surface. */
    static WindowThumbnail createAndAttach(SurfaceSession surfaceSession, SurfaceControl parent,
            HardwareBuffer thumbnailHeader, SurfaceControl.Transaction t) {
        WindowThumbnail windowThumbnail = new WindowThumbnail();
        windowThumbnail.mSurfaceControl = new SurfaceControl.Builder(surfaceSession)
                .setParent(parent)
                .setName("WindowThumanil : " + parent.toString())
                .setCallsite("WindowThumanil")
                .setFormat(PixelFormat.TRANSLUCENT)
                .build();

        GraphicBuffer graphicBuffer = GraphicBuffer.createFromHardwareBuffer(thumbnailHeader);
        t.setBuffer(windowThumbnail.mSurfaceControl, graphicBuffer);
        t.setColorSpace(windowThumbnail.mSurfaceControl, ColorSpace.get(ColorSpace.Named.SRGB));
        t.setLayer(windowThumbnail.mSurfaceControl, Integer.MAX_VALUE);
        t.show(windowThumbnail.mSurfaceControl);
        t.apply();

        return windowThumbnail;
    }

    SurfaceControl getSurface() {
        return mSurfaceControl;
    }

    /** Remove the thumbnail surface and release the surface. */
    void destroy(SurfaceControl.Transaction t) {
        if (mSurfaceControl == null) {
            return;
        }

        t.remove(mSurfaceControl);
        t.apply();
        mSurfaceControl.release();
        mSurfaceControl = null;
    }
}
