
package com.android.quickstep;

import static com.android.launcher3.states.RotationHelper.deltaRotation;
import static com.android.quickstep.util.RecentsOrientedState.postDisplayRotation;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.android.launcher3.Utilities;

public class OrientationRectF extends RectF {

    private static final String TAG = "OrientationRectF";
    private static final boolean DEBUG = false;

    private final int mRotation;
    private final float mHeight;
    private final float mWidth;

    private final Matrix mTmpMatrix = new Matrix();
    private final float[] mTmpPoint = new float[2];

    public OrientationRectF(float left, float top, float right, float bottom, int rotation) {
        super(left, top, right, bottom);
        mRotation = rotation;
        mHeight = bottom;
        mWidth = right;
    }

    @Override
    public String toString() {
        String s = super.toString();
        s += " rotation: " + mRotation;
        return s;
    }

    @Override
    public boolean contains(float x, float y) {
        // Mark bottom right as included in the Rect (copied from Rect src, added "=" in "<=")
        return left < right && top < bottom  // check for empty first
                && x >= left && x <= right && y >= top && y <= bottom;
    }

    public boolean applyTransformFromRotation(MotionEvent event, int currentRotation,
            boolean forceTransform) {
        return applyTransform(event, deltaRotation(currentRotation, mRotation), forceTransform);
    }

    public boolean applyTransformToRotation(MotionEvent event, int currentRotation,
            boolean forceTransform) {
        return applyTransform(event, deltaRotation(mRotation, currentRotation), forceTransform);
    }

    public boolean applyTransform(MotionEvent event, int deltaRotation, boolean forceTransform) {
        mTmpMatrix.reset();
        postDisplayRotation(deltaRotation, mHeight, mWidth, mTmpMatrix);
        if (forceTransform) {
            if (DEBUG) {
                Log.d(TAG, "Transforming rotation due to forceTransform, "
                        + "deltaRotation: " + deltaRotation
                        + "mRotation: " + mRotation
                        + " this: " + this);
            }
            if (Utilities.ATLEAST_S) {
                event.applyTransform(mTmpMatrix);
            } else {
                event.transform(mTmpMatrix);
            }
            return true;
        }
        mTmpPoint[0] = event.getX();
        mTmpPoint[1] = event.getY();
        mTmpMatrix.mapPoints(mTmpPoint);

        if (DEBUG) {
            Log.d(TAG, "original: " + event.getX() + ", " + event.getY()
                    + " new: " + mTmpPoint[0] + ", " + mTmpPoint[1]
                    + " rect: " + this + " forceTransform: " + forceTransform
                    + " contains: " + contains(mTmpPoint[0], mTmpPoint[1])
                    + " this: " + this);
        }

        if (contains(mTmpPoint[0], mTmpPoint[1])) {
            if (Utilities.ATLEAST_S) {
                event.applyTransform(mTmpMatrix);
            } else {
                event.transform(mTmpMatrix);
            }
            return true;
        }
        return false;
    }

    int getRotation() {
        return mRotation;
    }
}
