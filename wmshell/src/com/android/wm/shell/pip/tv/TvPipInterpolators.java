

package com.android.wm.shell.pip.tv;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/**
 * All interpolators needed for TV specific Pip animations
 */
public class TvPipInterpolators {

    /**
     * A standard ease-in-out curve reserved for moments of interaction (button and card states).
     */
    public static final Interpolator STANDARD = new PathInterpolator(0.2f, 0.1f, 0f, 1f);

    /**
     * A sharp ease-out-expo curve created for snappy but fluid browsing between cards and clusters.
     */
    public static final Interpolator BROWSE = new PathInterpolator(0.18f, 1f, 0.22f, 1f);

    /**
     * A smooth ease-out-expo curve created for incoming elements (forward, back, overlay).
     */
    public static final Interpolator ENTER = new PathInterpolator(0.12f, 1f, 0.4f, 1f);

    /**
     * A smooth ease-in-out-expo curve created for outgoing elements (forward, back, overlay).
     */
    public static final Interpolator EXIT = new PathInterpolator(0.4f, 1f, 0.12f, 1f);

}
