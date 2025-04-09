

package com.android.launcher3.testcomponent;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast Receiver to receive app unarchival broadcast. It is used to fulfill archiving
 * platform requirements.
 */
public class UnarchiveBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
