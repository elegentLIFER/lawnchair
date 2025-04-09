

package com.android.launcher3.secondarydisplay;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.android.launcher3.R;
import com.android.launcher3.dragndrop.DragView;

/**
 * A DragView drawn/used by the Secondary Launcher activity.
 */
public class SecondaryDragView extends DragView<SecondaryDisplayLauncher> {

    public SecondaryDragView(SecondaryDisplayLauncher launcher,
            Drawable drawable,
            int registrationX, int registrationY, float initialScale, float scaleOnDrop,
            float finalScaleDps) {
        super(launcher, drawable, registrationX, registrationY, initialScale, scaleOnDrop,
                finalScaleDps);
    }

    public SecondaryDragView(SecondaryDisplayLauncher launcher, View content, int width, int height,
            int registrationX, int registrationY, float initialScale, float scaleOnDrop,
            float finalScaleDps) {
        super(launcher, content, width, height, registrationX, registrationY, initialScale,
                scaleOnDrop, finalScaleDps);
    }

    @Override
    public void animateTo(int toTouchX, int toTouchY, Runnable onCompleteRunnable, int duration) {
        Runnable onAnimationEnd = () -> {
            if (onCompleteRunnable != null) {
                onCompleteRunnable.run();
            }
            mActivity.getDragLayer().removeView(this);
        };

        duration = Math.max(duration,
                getResources().getInteger(R.integer.config_dropAnimMinDuration));

        animate()
                .translationX(toTouchX - mRegistrationX)
                .translationY(toTouchY - mRegistrationY)
                .scaleX(mScaleOnDrop)
                .scaleY(mScaleOnDrop)
                .withEndAction(onAnimationEnd)
                .setDuration(duration)
                .start();
    }
}
