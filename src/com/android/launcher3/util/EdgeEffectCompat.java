
package com.android.launcher3.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EdgeEffect;

import com.android.launcher3.Utilities;

import app.lawnchair.ui.StretchEdgeEffect;

/**
 * Extension of {@link EdgeEffect} to allow backwards compatibility
 */
public class EdgeEffectCompat extends EdgeEffect {

    public EdgeEffectCompat(Context context) {
        super(context);
    }

    @Override
    public float getDistance() {
        return Utilities.ATLEAST_S ? super.getDistance() : 0;
    }

    @Override
    public float onPullDistance(float deltaDistance, float displacement) {
        if (Utilities.ATLEAST_S) {
            return super.onPullDistance(deltaDistance, displacement);
        } else {
            onPull(deltaDistance, displacement);
            return deltaDistance;
        }
    }

    public float onPullDistance(float deltaDistance, float displacement, MotionEvent ev) {
        return onPullDistance(deltaDistance, displacement);
    }

    public void onFlingVelocity(int velocity) { }

    public void onRelease(MotionEvent ev) {
        onRelease();
    }

    public static EdgeEffectCompat create(Context context, View view) {
        if (Utilities.ATLEAST_S) {
            return new EdgeEffectCompat(context);
        } else {
            StretchEdgeEffect effect = new StretchEdgeEffect(context);
            effect.setPostInvalidateOnAnimation(view::postInvalidateOnAnimation);
            return effect;
        }
    }
}
