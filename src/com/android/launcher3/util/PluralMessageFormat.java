
package com.android.launcher3.util;

import android.content.Context;
import android.icu.text.MessageFormat;

import androidx.annotation.StringRes;

import java.util.HashMap;
import java.util.Locale;

/** A helper class to format common ICU plural strings. */
public class PluralMessageFormat {

    /**
     * Returns a plural string from a ICU format message template, which takes "count" as an
     * argument.
     *
     * <p>An example of ICU format message template provided by {@code stringId}:
     * {count, plural, =1{# widget} other{# widgets}}
     */
    public static final String getIcuPluralString(Context context, @StringRes int stringId,
            int count) {
        MessageFormat icuCountFormat = new MessageFormat(
                context.getResources().getString(stringId),
                Locale.getDefault());
        HashMap<String, Object> args = new HashMap();
        args.put("count", count);
        return icuCountFormat.format(args);
    }
}
