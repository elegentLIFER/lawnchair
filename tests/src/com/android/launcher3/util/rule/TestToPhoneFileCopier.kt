

package com.android.launcher3.util.rule

import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/** Copy a file from the tests assets folder to the phone. */
class TestToPhoneFileCopier(
    val src: String,
    dest: String,
    private val removeOnFinish: Boolean = false
) : TestRule {

    private val dstFile =
        File(InstrumentationRegistry.getInstrumentation().targetContext.dataDir, dest)

    fun getDst() = dstFile.absolutePath

    fun before() =
        dstFile.writeBytes(
            InstrumentationRegistry.getInstrumentation().context.assets.open(src).readBytes()
        )

    fun after() {
        if (removeOnFinish) {
            dstFile.delete()
        }
    }

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                before()
                try {
                    base.evaluate()
                } finally {
                    after()
                }
            }
        }
}
