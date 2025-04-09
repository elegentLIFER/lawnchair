

package com.android.launcher3.testcomponent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import com.android.launcher3.R;

import java.util.UUID;

/**
 * A custom shortcut is a 1x1 widget that launches a specific intent when user tap on it.
 * Custom shortcuts are replaced by deep shortcuts after api 25.
 */
public class CustomShortcutConfigActivity extends BaseTestingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent launchIntent = new Intent(this, BaseTestingActivity.class)
                .setAction("com.android.launcher3.intent.action.test_shortcut");
        Intent shortcutIntent = createShortcutResultIntent(
                this, UUID.randomUUID().toString(), "Shortcut",
                R.drawable.ic_widget, launchIntent);
        setResult(RESULT_OK, shortcutIntent);
        finish();
    }

    private static Intent createShortcutResultIntent(
            Context context, String uniqueId, String name, int iconId, Intent launchIntent) {
        ShortcutInfo shortcutInfo =
                createShortcutInfo(context, uniqueId, name, iconId, launchIntent);
        ShortcutManager sm = context.getSystemService(ShortcutManager.class);
        return sm.createShortcutResultIntent(shortcutInfo);
    }

    private static ShortcutInfo createShortcutInfo(
            Context context, String uniqueId, String name, int iconId, Intent launchIntent) {
        return new ShortcutInfo.Builder(context, uniqueId)
                .setShortLabel(name)
                .setLongLabel(name)
                .setIcon(Icon.createWithResource(context, iconId))
                .setIntent(launchIntent)
                .build();
    }
}
