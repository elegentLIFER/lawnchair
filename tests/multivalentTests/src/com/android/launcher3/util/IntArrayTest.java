
package com.android.launcher3.util;

import static com.google.common.truth.Truth.assertThat;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for {@link IntArray}
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class IntArrayTest {

    @Test
    public void concatAndParseString() {
        int[] array = new int[] {0, 2, 3, 9};
        String concat = IntArray.wrap(array).toConcatString();

        int[] parsed = IntArray.fromConcatString(concat).toArray();
        assertThat(array).isEqualTo(parsed);
    }
}
