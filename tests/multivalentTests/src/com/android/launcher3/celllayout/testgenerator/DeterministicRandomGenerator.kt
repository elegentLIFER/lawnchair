
package com.android.launcher3.celllayout.testgenerator

import java.util.Random

abstract class DeterministicRandomGenerator(private val generator: Random) {
    fun getRandom(start: Int, end: Int): Int = start + (if (end == 0) 0 else generator.nextInt(end))
}
