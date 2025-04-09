

package com.android.systemui.plugins;

import android.content.Context;
import android.view.View;

/**
 * Interface to deal with lack of multiple inheritance
 *
 * This interface is designed to be used as a base class for plugin interfaces
 * that need fragment methods. Plugins should not extend Fragment directly, so
 * plugins that are fragments should be extending PluginFragment, but in SysUI
 * these same versions should extend Fragment directly.
 *
 * Only methods that are on Fragment should be included here.
 */
public interface FragmentBase {
    View getView();
    Context getContext();
}
