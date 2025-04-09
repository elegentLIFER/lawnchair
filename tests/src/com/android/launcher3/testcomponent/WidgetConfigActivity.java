
package com.android.launcher3.testcomponent;

import android.os.Bundle;

/**
 * Simple activity for widget configuration
 */
public class WidgetConfigActivity extends BaseTestingActivity {

    public static final String SUFFIX_FINISH = "-finish";
    public static final String EXTRA_CODE = "code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addButton("Cancel", "clickCancel");
        addButton("OK", "clickOK");
    }

    public void clickCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void clickOK() {
        setResult(RESULT_OK);
        finish();
    }
}
