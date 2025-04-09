

package com.android.wm.shell.unfold;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

import android.annotation.ColorRes;
import android.annotation.NonNull;
import android.content.Context;
import android.view.SurfaceControl;

import com.android.wm.shell.R;

/**
 * Controls background color layer for the unfold animations
 */
public class UnfoldBackgroundController {

    private static final int BACKGROUND_LAYER_Z_INDEX = -1;
    private final float[] mBackgroundColor;
    private final float[] mSplitScreenBackgroundColor;
    private float[] mBackgroundColorSet;
    private SurfaceControl mBackgroundLayer;
    private boolean mSplitScreenVisible = false;

    public UnfoldBackgroundController(@NonNull Context context) {
        mBackgroundColor = getRGBColorFromId(context, R.color.unfold_background);
        mSplitScreenBackgroundColor = getRGBColorFromId(context, R.color.split_divider_background);
    }

    /**
     * Ensures that unfold animation background color layer is present,
     * @param transaction where we should add the background if it is not added
     */
    public void ensureBackground(@NonNull SurfaceControl.Transaction transaction) {
        float[] expectedColor = getCurrentBackgroundColor();
        if (mBackgroundLayer != null) {
            if (mBackgroundColorSet != expectedColor) {
                transaction.setColor(mBackgroundLayer, expectedColor);
                mBackgroundColorSet = expectedColor;
            }
            return;
        }

        SurfaceControl.Builder colorLayerBuilder = new SurfaceControl.Builder()
                .setName("app-unfold-background")
                .setCallsite("AppUnfoldTransitionController")
                .setColorLayer();
        mBackgroundLayer = colorLayerBuilder.build();

        transaction
                .setColor(mBackgroundLayer, expectedColor)
                .show(mBackgroundLayer)
                .setLayer(mBackgroundLayer, BACKGROUND_LAYER_Z_INDEX);
        mBackgroundColorSet = expectedColor;
    }

    /**
     * Ensures that the background is not visible
     * @param transaction as part of which the removal will happen if needed
     */
    public void removeBackground(@NonNull SurfaceControl.Transaction transaction) {
        if (mBackgroundLayer == null) return;
        if (mBackgroundLayer.isValid()) {
            transaction.remove(mBackgroundLayer);
        }
        mBackgroundLayer = null;
    }

    /**
     * Expected to be called whenever split screen visibility changes.
     *
     * @param visible True when split screen is visible
     */
    public void onSplitVisibilityChanged(boolean visible) {
        mSplitScreenVisible = visible;
    }

    private float[] getCurrentBackgroundColor() {
        if (mSplitScreenVisible) {
            return mSplitScreenBackgroundColor;
        } else {
            return mBackgroundColor;
        }
    }

    private float[] getRGBColorFromId(Context context, @ColorRes int id) {
        int colorInt = context.getResources().getColor(id);
        return new float[]{
                (float) red(colorInt) / 255.0F,
                (float) green(colorInt) / 255.0F,
                (float) blue(colorInt) / 255.0F
        };
    }
}
