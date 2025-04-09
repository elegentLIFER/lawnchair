
package com.android.launcher3.testcomponent;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;

import android.app.LauncherActivity;
import android.content.Intent;

public class TestLauncherActivity extends LauncherActivity {

    @Override
    protected Intent getTargetIntent() {
        return new Intent(ACTION_MAIN, null)
                .addCategory(CATEGORY_LAUNCHER)
                .addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    }
}
