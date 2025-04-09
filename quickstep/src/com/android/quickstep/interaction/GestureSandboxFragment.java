
package com.android.quickstep.interaction;

import android.app.Activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/** Displays one page of the gesture nav tutorial. */
public abstract class GestureSandboxFragment extends Fragment {

    void onAttachedToWindow() {}

    void onDetachedFromWindow() {}

    boolean canRecreateFragment() {
        return false;
    }

    @Nullable
    GestureSandboxFragment recreateFragment() {
        return null;
    }

    boolean shouldDisableSystemGestures() {
        return true;
    }

    void close() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
    }
}
