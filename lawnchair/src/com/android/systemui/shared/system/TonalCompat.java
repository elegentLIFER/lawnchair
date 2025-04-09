
package com.android.systemui.shared.system;

import android.app.WallpaperColors;
import android.content.Context;

import com.android.internal.colorextraction.ColorExtractor.GradientColors;
import com.android.internal.colorextraction.types.Tonal;
import com.android.launcher3.Utilities;

public class TonalCompat {

    private final Tonal mTonal;

    public TonalCompat(Context context) {
        mTonal = Utilities.ATLEAST_S ? new Tonal(context) : null;
    }

    public ExtractionInfo extractDarkColors(WallpaperColors colors) {
        if (mTonal != null) {
            GradientColors darkColors = new GradientColors();
            mTonal.extractInto(colors, new GradientColors(), darkColors, new GradientColors());

            ExtractionInfo result = new ExtractionInfo();
            result.mainColor = darkColors.getMainColor();
            result.secondaryColor = darkColors.getSecondaryColor();
            result.supportsDarkText = darkColors.supportsDarkText();
            if (colors != null && Utilities.ATLEAST_S) {
                result.supportsDarkTheme =
                    (colors.getColorHints() & WallpaperColors.HINT_SUPPORTS_DARK_THEME) != 0;
            }
            return result;
        }
        return new ExtractionInfo();
    }

    public static class ExtractionInfo {
        public int mainColor;
        public int secondaryColor;
        public boolean supportsDarkText;
        public boolean supportsDarkTheme;
    }
}
