

package com.android.launcher3.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * View that draws a bitmap horizontally centered. If the image width is greater than the view
 * width, the image is scaled down appropriately.
 */
public class WidgetImageView extends View {

    private final RectF mDstRectF = new RectF();
    private Drawable mDrawable;

    public WidgetImageView(Context context) {
        this(context, null);
    }

    public WidgetImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /** Set the drawable to use for this view. */
    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null) {
            updateDstRectF();
            mDrawable.setBounds(getBitmapBounds());
            mDrawable.draw(canvas);
        }
    }

    /**
     * Prevents the inefficient alpha view rendering.
     */
    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    private void updateDstRectF() {
        float myWidth = getWidth();
        float myHeight = getHeight();
        final float bitmapWidth = mDrawable.getIntrinsicWidth();
        final float bitmapHeight = mDrawable.getIntrinsicHeight();
        final float bitmapAspectRatio = bitmapWidth / bitmapHeight;
        final float containerAspectRatio = myWidth / myHeight;

        // Scale by width if image has larger aspect ratio than the container else by height; and
        // avoid cropping the previews
        final float scale = bitmapAspectRatio > containerAspectRatio ? myWidth / bitmapWidth
                : myHeight / bitmapHeight;

        final float scaledWidth = bitmapWidth * scale;
        final float scaledHeight = bitmapHeight * scale;

        // Avoid cropping by checking bounds after scaling.
        if (scaledWidth > myWidth) {
            mDstRectF.left = 0;
            mDstRectF.right = scaledWidth;
        } else {
            mDstRectF.left = (myWidth - scaledWidth) / 2;
            mDstRectF.right = (myWidth + scaledWidth) / 2;
        }
        if (scaledHeight > myHeight) {
            mDstRectF.top = 0;
            mDstRectF.bottom = scaledHeight;
        } else {
            mDstRectF.top = (myHeight - scaledHeight) / 2;
            mDstRectF.bottom = (myHeight + scaledHeight) / 2;
        }
    }

    /**
     * @return the bounds where the image was drawn.
     */
    public Rect getBitmapBounds() {
        updateDstRectF();
        Rect rect = new Rect();
        mDstRectF.round(rect);
        return rect;
    }
}
