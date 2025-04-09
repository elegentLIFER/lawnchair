

package com.android.wm.shell.onehanded;

import static org.junit.Assert.assertEquals;

import androidx.test.filters.SmallTest;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.testing.UiEventLoggerFake;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@SmallTest
public class OneHandedUiEventLoggerTest extends OneHandedTestCase {

    private UiEventLoggerFake mUiEventLogger;

    @Parameterized.Parameter
    public int mTag;

    @Parameterized.Parameter(1)
    public String mExpectedMessage;

    public UiEventLogger.UiEventEnum mUiEvent;

    @Before
    public void setFakeLoggers() {
        mUiEventLogger = new UiEventLoggerFake();
    }

    @Test
    @Ignore("b/184813408, go/wm-tests showing test flaky")
    public void testLogEvent() {
        if (mUiEvent != null) {
            assertEquals(1, mUiEventLogger.numLogs());
            assertEquals(mUiEvent.getId(), mUiEventLogger.eventId(0));
        }
    }

    @Parameterized.Parameters(name = "{index}: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Triggers
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_GESTURE_IN,
                        "writeEvent one_handed_trigger_gesture_in"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_GESTURE_OUT,
                        "writeEvent one_handed_trigger_gesture_out"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_OVERSPACE_OUT,
                        "writeEvent one_handed_trigger_overspace_out"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_POP_IME_OUT,
                        "writeEvent one_handed_trigger_pop_ime_out"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_ROTATION_OUT,
                        "writeEvent one_handed_trigger_rotation_out"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_APP_TAPS_OUT,
                        "writeEvent one_handed_trigger_app_taps_out"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_TIMEOUT_OUT,
                        "writeEvent one_handed_trigger_timeout_out"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_TRIGGER_SCREEN_OFF_OUT,
                        "writeEvent one_handed_trigger_screen_off_out"},
                // Settings toggles
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_ENABLED_ON,
                        "writeEvent one_handed_settings_enabled_on"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_ENABLED_OFF,
                        "writeEvent one_handed_settings_enabled_off"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_APP_TAPS_EXIT_ON,
                        "writeEvent one_handed_settings_app_taps_exit_on"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_APP_TAPS_EXIT_OFF,
                        "writeEvent one_handed_settings_app_taps_exit_off"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_TIMEOUT_EXIT_ON,
                        "writeEvent one_handed_settings_timeout_exit_on"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_TIMEOUT_EXIT_OFF,
                        "writeEvent one_handed_settings_timeout_exit_off"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_TIMEOUT_SECONDS_NEVER,
                        "writeEvent one_handed_settings_timeout_seconds_never"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_TIMEOUT_SECONDS_4,
                        "writeEvent one_handed_settings_timeout_seconds_4"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_TIMEOUT_SECONDS_8,
                        "writeEvent one_handed_settings_timeout_seconds_8"},
                {OneHandedUiEventLogger.EVENT_ONE_HANDED_SETTINGS_TIMEOUT_SECONDS_12,
                        "writeEvent one_handed_settings_timeout_seconds_12"}
        });
    }
}
