
package com.android.launcher3.util;


import android.app.ActivityOptions;
import android.os.Bundle;

/**
 * A wrapper around {@link ActivityOptions} to allow custom functionality in launcher
 */
public class ActivityOptionsWrapper {

    public final ActivityOptions options;
    public final RunnableList onEndCallback;

    public ActivityOptionsWrapper(ActivityOptions options, RunnableList onEndCallback) {
        this.options = options;
        this.onEndCallback = onEndCallback;
    }

    /**
     * @see {@link ActivityOptions#toBundle()}
     */
    public Bundle toBundle() {
        return options.toBundle();
    }
}
