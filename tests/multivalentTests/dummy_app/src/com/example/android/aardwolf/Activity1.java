

package com.example.android.aardwolf;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Activity1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.empty_activity, null);
        setContentView(view);
    }
}

