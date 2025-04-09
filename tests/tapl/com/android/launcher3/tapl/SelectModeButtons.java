

package com.android.launcher3.tapl;

import static android.view.KeyEvent.KEYCODE_ESCAPE;

import static com.android.launcher3.testing.shared.TestProtocol.OVERVIEW_STATE_ORDINAL;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiObject2;

import com.android.launcher3.testing.shared.TestProtocol;

import java.util.regex.Pattern;

/**
 * View containing select mode buttons
 */
public class SelectModeButtons {
    private final UiObject2 mSelectModeButtons;
    private final LauncherInstrumentation mLauncher;
    private static final Pattern EVENT_ALT_ESC_UP = Pattern.compile(
            "Key event: KeyEvent.*?action=ACTION_UP.*?keyCode=KEYCODE_ESCAPE.*?metaState=0");


    SelectModeButtons(LauncherInstrumentation launcherInstrumentation) {
        mSelectModeButtons = launcherInstrumentation.waitForLauncherObject("select_mode_buttons");
        mLauncher = launcherInstrumentation;
    }

    /**
     * Click close button.
     */
    @NonNull
    public Overview clickClose() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck();
             LauncherInstrumentation.Closable c =
                     mLauncher.addContextLayer("want to click close button")) {
            UiObject2 close = mLauncher.waitForObjectInContainer(mSelectModeButtons, "close");
            mLauncher.clickLauncherObject(close);
            try (LauncherInstrumentation.Closable c1 = mLauncher.addContextLayer(
                    "clicked close button")) {
                return new Overview(mLauncher);
            }
        }
    }

    /**
     * Close select mode when ESC key is pressed.
     * @return The Overview
     */
    @NonNull
    public Overview dismissByEscKey() {
        try (LauncherInstrumentation.Closable e = mLauncher.eventsCheck()) {
            mLauncher.expectEvent(TestProtocol.SEQUENCE_MAIN, EVENT_ALT_ESC_UP);
            mLauncher.runToState(
                    () -> mLauncher.getDevice().pressKeyCode(KEYCODE_ESCAPE),
                    OVERVIEW_STATE_ORDINAL,
                    "pressing Esc");
            try (LauncherInstrumentation.Closable c = mLauncher.addContextLayer(
                    "pressed esc key")) {
                return new Overview(mLauncher);
            }
        }
    }

}
