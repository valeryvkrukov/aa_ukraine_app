package org.aa.ukraine.test.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.aa.ukraine.core.data.di.fakeMains
import org.aa.ukraine.ui.MainActivity

@HiltAndroidTest
class AppTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test1() {
        // TODO: Add navigation tests
        composeTestRule.onNodeWithText(fakeMains.first(), substring = true).assertExists()
    }
}
