
package com.android.quickstep.util;

import android.os.VibrationEffect;
import com.android.launcher3.Utilities;

public class VibrationConstants {
    public static final VibrationEffect EFFECT_TEXTURE_TICK = Utilities.ATLEAST_Q ?
            VibrationEffect.createPredefined(VibrationEffect.EFFECT_TEXTURE_TICK) : VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
}
