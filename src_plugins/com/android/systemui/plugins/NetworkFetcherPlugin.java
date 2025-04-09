

package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * Implement this plugin to proxy network requests
 */
@ProvidesInterface(action = NetworkFetcherPlugin.ACTION, version = NetworkFetcherPlugin.VERSION)
public interface NetworkFetcherPlugin extends Plugin {
    String ACTION = "com.android.systemui.action.PLUGIN_NETWORK_FETCHER_ACTIONS";
    int VERSION = 1;

    /** Fetches the provided user and return all byte contents */
    byte[] fetchUrl(String url) throws Exception;
}
