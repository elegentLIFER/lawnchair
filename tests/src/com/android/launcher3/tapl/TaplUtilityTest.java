
package com.android.launcher3.tapl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaplUtilityTest {

    @Test
    public void testMakeMultilinePattern() {
        // Original title will match.
        assertTrue(AppIcon.makeMultilinePattern("Play Store")
                .matcher("Play Store").matches());
        assertTrue(AppIcon.makeMultilinePattern("PlayStore")
                .matcher("PlayStore").matches());

        // Original title with whitespace added will match.
        assertTrue(AppIcon.makeMultilinePattern("PlayStore")
                .matcher("Play\nStore").matches());
        assertTrue(AppIcon.makeMultilinePattern("PlayStore")
                .matcher("Play Store").matches());
        // Original title with whitespace removed will also match.
        assertTrue(AppIcon.makeMultilinePattern("Play Store")
                .matcher("PlayStore").matches());
        // Or whitespace replaced with a different kind of whitespace (both of above conditions).
        assertTrue(AppIcon.makeMultilinePattern("Play Store")
                .matcher("Play\nStore").matches());
        assertTrue(AppIcon.makeMultilinePattern("Play Store")
                .matcher("Play \n Store").matches());

        // Any non-whitespace character added to the title will not match.
        assertFalse(AppIcon.makeMultilinePattern("Play Store")
                .matcher("Play Store has 7 notifications").matches());
        assertFalse(AppIcon.makeMultilinePattern("Play Store")
                .matcher("Play  Store!").matches());
        // Title is case-sensitive.
        assertFalse(AppIcon.makeMultilinePattern("Play Store")
                .matcher("play store").matches());
        assertFalse(AppIcon.makeMultilinePattern("Play Store")
                .matcher("play  store").matches());
        // Removing non whitespace characters will not match.
        assertFalse(AppIcon.makeMultilinePattern("Play Store")
                .matcher("").matches());
        assertFalse(AppIcon.makeMultilinePattern("Play Store")
                .matcher("Play Stor").matches());
        assertFalse(AppIcon.makeMultilinePattern("Play Store")
                .matcher("Play").matches());
    }
}
