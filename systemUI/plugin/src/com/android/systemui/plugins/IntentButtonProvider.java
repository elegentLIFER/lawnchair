

package com.android.systemui.plugins;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * An Intent Button represents a triggerable element in SysUI that consists of an
 * Icon and an intent to trigger when it is activated (clicked, swiped, etc.).
 */
@ProvidesInterface(version = IntentButtonProvider.VERSION)
public interface IntentButtonProvider extends Plugin {

    public static final int VERSION = 1;

    public IntentButton getIntentButton();

    public interface IntentButton {
        public static class IconState {
            public boolean isVisible = true;
            public CharSequence contentDescription = null;
            public Drawable drawable;
            public boolean tint = true;
        }

        public IconState getIcon();

        public Intent getIntent();
    }
}
