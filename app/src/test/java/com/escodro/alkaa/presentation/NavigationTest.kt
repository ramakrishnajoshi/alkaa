package com.escodro.alkaa.presentation

import android.os.Build
import androidx.test.core.app.ActivityScenario
import com.escodro.alkaa.R
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.fakes.RoboMenuItem

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
internal class NavigationTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun `test navigation to task list`() {
        // Click on the task list item
        val item = RoboMenuItem(R.id.nav_graph_task)
        item.click()

        // Assert that the resource from task list screen is displayed
        assertDisplayed(R.id.text_tasklist_title)
    }
}
