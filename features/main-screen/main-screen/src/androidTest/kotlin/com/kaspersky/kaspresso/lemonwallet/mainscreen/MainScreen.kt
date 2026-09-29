package com.kaspersky.kaspresso.lemonwallet.mainscreen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.KNode
import org.junit.Rule
import org.junit.Test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.main_screen.ui.MainScreen

object MainScreenK : KScreen<MainScreenK>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val container = KNode{hasTestTag("main_screen_container")}
    val storageBlock = KNode{hasTestTag("storage_block")}
}
@HiltAndroidTest
class MainScreenIsolatedTest : TestCase() {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    // Используем ComponentActivity для изоляции экрана
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun mainScreen_renders_all_submodules() = run {
        step("Отрисовка главного экрана со всеми блоками") {
            composeTestRule.setContent {
                // Здесь вызываешь свой MainScreen, который собирает блоки
                MainScreen()
            }
        }

        step("Проверка, что все блоки из разных модулей отобразились") {
            MainScreenK {
                container.assertIsDisplayed()
                storageBlock.assertIsDisplayed()
            }
        }
    }
}