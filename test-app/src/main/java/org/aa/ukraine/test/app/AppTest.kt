package org.aa.ukraine.test.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.aa.ukraine.ui.MainActivity

class AppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test1() {
        composeTestRule.onNodeWithText("Compose", substring = true).assertExists()
    }
}
