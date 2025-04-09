
package com.android.quickstep.util;

import static com.android.launcher3.util.MultiTranslateDelegate.INDEX_MOVE_FROM_CENTER_ANIM;

import android.annotation.NonNull;
import android.view.View;

import com.android.launcher3.Reorderable;
import com.android.launcher3.util.MultiTranslateDelegate;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator.TranslationApplier;

/**
 * Class that allows to set translations for move from center animation independently
 * from other translations for certain launcher views
 */
public class LauncherViewsMoveFromCenterTranslationApplier implements TranslationApplier {

    @Override
    public void apply(@NonNull View view, float x, float y) {
        if (view instanceof Reorderable) {
            MultiTranslateDelegate mtd = ((Reorderable) view).getTranslateDelegate();
            mtd.setTranslation(INDEX_MOVE_FROM_CENTER_ANIM, x, y);
        } else {
            view.setTranslationX(x);
            view.setTranslationY(y);
        }
    }
}
