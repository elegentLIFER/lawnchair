
package com.android.launcher3.testcomponent;

import android.os.Bundle;

public class ImeTestActivity extends OtherBaseTestingActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Requests to focus an editor and show IME for test.
        addEditor("Focused editor for test", "Focused editor for test", true);
    }
}
