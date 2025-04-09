
package com.android.launcher3.tapl;

import androidx.test.uiautomator.UiObject2;

import com.android.launcher3.testing.shared.TestProtocol;

/**
 * Container that can be used to input a search query and retrieve a {@link SearchResultFromQsb}
 * instance.
 */
interface SearchInputSource {
    String INPUT_RES = "input";

    /** Set the already focused search input edit text and update search results. */
    default SearchResultFromQsb searchForInput(String input) {
        LauncherInstrumentation launcher = getLauncher();
        try (LauncherInstrumentation.Closable c = launcher.addContextLayer(
                "want to search for result with an input");
             LauncherInstrumentation.Closable e = launcher.eventsCheck()) {
            launcher.executeAndWaitForLauncherEvent(
                    () -> {
                        UiObject2 editText = launcher.waitForLauncherObject(INPUT_RES);
                        launcher.waitForObjectFocused(editText, "search input");
                        editText.setText(input);
                    },
                    event -> TestProtocol.SEARCH_RESULT_COMPLETE.equals(event.getClassName()),
                    () -> "Didn't receive a search result completed message", "searching");
            return getSearchResultForInput();
        }
    }

    /** This method requires public access, however should not be called in tests. */
    LauncherInstrumentation getLauncher();

    /** This method requires public access, however should not be called in tests. */
    SearchResultFromQsb getSearchResultForInput();
}
