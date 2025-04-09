

package com.android.systemui.plugins;

import android.os.Parcelable;

import com.android.systemui.plugins.annotations.ProvidesInterface;

import java.util.ArrayList;

/**
 * Implement this interface to get suggest for one search.
 */
@ProvidesInterface(action = OneSearch.ACTION, version = OneSearch.VERSION)
public interface OneSearch extends Plugin {
    String ACTION = "com.android.systemui.action.PLUGIN_ONE_SEARCH";
    int VERSION = 6;

    /**
     * Get the content provider warmed up.
     */
    void warmUp();

    /**
     * Get the suggest search target list for the query.
     *
     * @param query The query to get the search suggests for.
     */
    ArrayList<Parcelable> getSuggests(Parcelable query);

    /** Get image bitmap with the URL. */
    Parcelable getImageBitmap(String imageUrl);

    void setSuggestOnChrome(boolean enable);

    /**
     * Notifies search events to plugin
     *
     * @param event the SearchTargetEvent event created due to user action
     */
    void notifyEvent(Parcelable event);
}
