
package com.android.launcher3.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * A simple drawable which draws a bitmap at a fixed position irrespective of the bounds
 */
public class ShiftedBitmapDrawable extends Drawable {

    private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
    private final Bitmap mBitmap;
    private float mShiftX;
    private float mShiftY;

    private final ConstantState mConstantState;

    public ShiftedBitmapDrawable(Bitmap bitmap, float shiftX, float shiftY) {
        mBitmap = bitmap;
        mShiftX = shiftX;
        mShiftY = shiftY;

        mConstantState = new MyConstantState(mBitmap, mShiftX, mShiftY);
    }

    public float getShiftX() {
        return mShiftX;
    }

    public float getShiftY() {
        return mShiftY;
    }

    public void setShiftX(float shiftX) {
        mShiftX = shiftX;
    }

    public void setShiftY(float shiftY) {
        mShiftY = shiftY;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mShiftX, mShiftY, mPaint);
    }

    @Override
    public void setAlpha(int i) { }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public ConstantState getConstantState() {
        return mConstantState;
    }

    private static class MyConstantState extends ConstantState {
        private final Bitmap mBitmap;
        private float mShiftX;
        private float mShiftY;

        MyConstantState(Bitmap bitmap, float shiftX, float shiftY) {
            mBitmap = bitmap;
            mShiftX = shiftX;
            mShiftY = shiftY;
        }

        @Override
        public Drawable newDrawable() {
            return new ShiftedBitmapDrawable(mBitmap, mShiftX, mShiftY);
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }
}
