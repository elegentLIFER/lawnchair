
package com.android.launcher3.folder;

import android.graphics.drawable.Drawable;

import com.android.launcher3.model.data.ItemInfo;

/**
 * Manages the parameters used to draw a Folder preview item.
 */
class PreviewItemDrawingParams {
    float index;
    float transX;
    float transY;
    float scale;
    public FolderPreviewItemAnim anim;
    public boolean hidden;
    public Drawable drawable;
    public ItemInfo item;

    PreviewItemDrawingParams(float transX, float transY, float scale) {
        this.transX = transX;
        this.transY = transY;
        this.scale = scale;
    }

    public void update(float transX, float transY, float scale) {
        // We ensure the update will not interfere with an animation on the layout params
        // If the final values differ, we cancel the animation.
        if (anim != null) {
            if (anim.finalState[1] == transX || anim.finalState[2] == transY
                    || anim.finalState[0] == scale) {
                return;
            }
            anim.cancel();
        }

        this.transX = transX;
        this.transY = transY;
        this.scale = scale;
    }
}
