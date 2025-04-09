

package com.android.wm.shell;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * Basic test handler that immediately executes anything that is posted on it.
 */
public class TestHandler extends Handler {
    public TestHandler(Looper looper) {
        super(looper);
    }

    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        dispatchMessage(msg);
        return true;
    }
}
