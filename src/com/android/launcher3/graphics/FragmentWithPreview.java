
package com.android.launcher3.graphics;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Extension of fragment, with support for preview mode.
 */
public class FragmentWithPreview extends Fragment {

    private Context mPreviewContext;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInit(savedInstanceState);
    }

    public void onInit(Bundle savedInstanceState) { }


    public Context getContext() {
        return mPreviewContext != null ? mPreviewContext : getActivity();
    }

    void enterPreviewMode(Context context) {
        mPreviewContext = context;
    }

    public boolean isInPreviewMode() {
        return mPreviewContext != null;
    }
}
