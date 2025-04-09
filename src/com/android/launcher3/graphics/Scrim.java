

package com.android.launcher3.graphics;

import static com.android.launcher3.icons.GraphicsUtils.setColorAlphaBound;

import android.graphics.Canvas;
import android.util.FloatProperty;
import android.view.View;

import app.lawnchair.theme.color.tokens.ColorTokens;

/**
 * Contains general scrim properties such as wallpaper-extracted color that subclasses can use.
 */
public class Scrim {

    public static final FloatProperty<Scrim> SCRIM_PROGRESS =
            new FloatProperty<Scrim>("scrimProgress") {
                @Override
                public Float get(Scrim scrim) {
                    return scrim.mScrimProgress;
                }

                @Override
                public void setValue(Scrim scrim, float v) {
                    scrim.setScrimProgress(v);
                }
            };

    protected final View mRoot;

    protected float mScrimProgress;
    protected int mScrimColor;
    protected int mScrimAlpha = 0;

    public Scrim(View view) {
        mRoot = view;
        mScrimColor = ColorTokens.WallpaperPopupScrim.resolveColor(mRoot.getContext());
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(setColorAlphaBound(mScrimColor, mScrimAlpha));
    }

    private void setScrimProgress(float progress) {
        if (mScrimProgress != progress) {
            mScrimProgress = progress;
            mScrimAlpha = Math.round(255 * mScrimProgress);
            mRoot.invalidate();
        }
    }
}
