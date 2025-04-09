
package com.android.launcher3.util;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.EdgeEffect;

import app.lawnchair.ui.StretchEdgeEffect;

/**
 * Extension of {@link EdgeEffect} which translates the content instead of the default
 * platform implementation
 */
public class TranslateEdgeEffect extends StretchEdgeEffect {

    private final float[] mTmpOut = new float[5];
    private boolean mInvalidated = false;

    public TranslateEdgeEffect(Context context) {
        super(context);
        setPostInvalidateOnAnimation(() -> mInvalidated = true);
    }

    @Override
    public boolean draw(Canvas canvas) {
        return false;
    }

    public boolean getTranslationShift(float[] out) {
        mInvalidated = false;
        super.getScale(mTmpOut, StretchEdgeEffect.POSITION_TOP);

        out[0] = getDistance();
        return mInvalidated;
    }
}
