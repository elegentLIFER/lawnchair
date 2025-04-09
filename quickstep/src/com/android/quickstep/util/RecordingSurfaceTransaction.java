
package com.android.quickstep.util;

/**
 * Extension for {@link SurfaceTransaction} which records the commands for mocking
 */
public class RecordingSurfaceTransaction extends SurfaceTransaction {

    /**
     * A mock builder which can be used for recording values
     */
    public final MockProperties mockProperties = new MockProperties();

}
