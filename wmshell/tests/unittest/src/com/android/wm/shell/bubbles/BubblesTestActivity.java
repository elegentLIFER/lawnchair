

package com.android.wm.shell.bubbles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.wm.shell.tests.R;

/**
 * Referenced by NotificationTestHelper#makeBubbleMetadata
 */
public class BubblesTestActivity extends Activity {

    public static final String BUBBLE_ACTIVITY_OPENED = "BUBBLE_ACTIVITY_OPENED";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent i = new Intent(BUBBLE_ACTIVITY_OPENED);
        sendBroadcast(i);
    }
}
