

package com.android.launcher3.proxy;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;

import com.android.launcher3.util.StartActivityParams;

public class ProxyActivityStarter extends Activity {

    private static final String TAG = "ProxyActivityStarter";

    public static final String EXTRA_PARAMS = "start-activity-params";

    private StartActivityParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisible(false);

        mParams = getIntent().getParcelableExtra(EXTRA_PARAMS);
        if (mParams == null) {
            Log.d(TAG, "Proxy activity started without params");
            finishAndRemoveTask();
            return;
        }

        if (savedInstanceState != null) {
            // Already started the activity. Just wait for the result.
            return;
        }

        try {
            if (mParams.intent != null) {
                startActivity();
                return;
            } else if (mParams.intentSender != null) {
                startIntentSender();
                return;
            }
        } catch (NullPointerException | ActivityNotFoundException | SecurityException
                | SendIntentException e) {
            mParams.deliverResult(this, RESULT_CANCELED, null);
        }
        finishAndRemoveTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mParams.requestCode) {
            mParams.deliverResult(this, resultCode, data);
        }
        finishAndRemoveTask();
    }

    public static Intent getLaunchIntent(Context context, StartActivityParams params) {
        return new Intent(context, ProxyActivityStarter.class)
                .putExtra(EXTRA_PARAMS, params)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    private void startActivity() throws SendIntentException {
        if (mParams.requireActivityResult) {
            startActivityForResult(mParams.intent, mParams.requestCode, mParams.options);
        } else {
            startActivity(mParams.intent, mParams.options);
            finishAndRemoveTask();
        }
    }

    private void startIntentSender() throws SendIntentException {
        if (mParams.requireActivityResult) {
            startIntentSenderForResult(mParams.intentSender, mParams.requestCode,
                    mParams.fillInIntent, mParams.flagsMask, mParams.flagsValues,
                    mParams.extraFlags,
                    mParams.options);
        } else {
            startIntentSender(mParams.intentSender, mParams.fillInIntent, mParams.flagsMask,
                    mParams.flagsValues, mParams.extraFlags, mParams.options);
            finishAndRemoveTask();
        }
    }
}
