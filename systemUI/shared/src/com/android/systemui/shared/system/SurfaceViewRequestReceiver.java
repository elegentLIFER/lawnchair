

package com.android.systemui.shared.system;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Size;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.window.InputTransferToken;

/**
 * A generic receiver that specifically handles SurfaceView request created by {@link
 * com.android.systemui.shared.system.SurfaceViewRequestUtils}.
 */
public class SurfaceViewRequestReceiver {

    private final int mOpacity;
    private SurfaceControlViewHost mSurfaceControlViewHost;

    public SurfaceViewRequestReceiver() {
        this(PixelFormat.TRANSPARENT);
    }

    public SurfaceViewRequestReceiver(int opacity) {
        mOpacity = opacity;
    }

    /** See {@link #onReceive(Context, Bundle, View, Size)}. */
    public void onReceive(Context context, Bundle bundle, View view) {
        onReceive(context, bundle, view, null);
    }

    /**
     * Called whenever a surface view request is received.
     * @param view     the view rendering content, on the receiver end of the surface request.
     * @param viewSize when {@param viewSize} is not specified, we will use the surface control size
     *                 to attach the view to the window.
     */
    public void onReceive(Context context, Bundle bundle, View view, Size viewSize) {
        if (mSurfaceControlViewHost != null) {
            mSurfaceControlViewHost.release();
        }

        SurfaceControl surfaceControl = SurfaceViewRequestUtils.getSurfaceControl(bundle);
        if (surfaceControl != null) {
            if (viewSize == null) {
                viewSize = new Size(surfaceControl.getWidth(), surfaceControl.getHeight());
            }

            IBinder hostToken = SurfaceViewRequestUtils.getHostToken(bundle);

            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            mSurfaceControlViewHost = new SurfaceControlViewHost(context,
                    dm.getDisplay(SurfaceViewRequestUtils.getDisplayId(bundle)),
                    new InputTransferToken(hostToken), "SurfaceViewRequestReceiver");
            WindowManager.LayoutParams layoutParams =
                    new WindowManager.LayoutParams(
                            viewSize.getWidth(),
                            viewSize.getHeight(),
                            WindowManager.LayoutParams.TYPE_APPLICATION,
                            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                            mOpacity);

            // This aspect scales the view to fit in the surface and centers it
            final float scale = Math.min(surfaceControl.getWidth() / (float) viewSize.getWidth(),
                    surfaceControl.getHeight() / (float) viewSize.getHeight());
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setPivotX(0);
            view.setPivotY(0);
            view.setTranslationX((surfaceControl.getWidth() - scale * viewSize.getWidth()) / 2);
            view.setTranslationY((surfaceControl.getHeight() - scale * viewSize.getHeight()) / 2);

            mSurfaceControlViewHost.setView(view, layoutParams);
        }
    }
}
