package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.accessibility.enableAccessibilityChecks
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.enableAccessibilityChecks()
    }

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        // Given
        val state = CurrencyListUiState(isLoading = true)

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyListContent(state = state, onItemClick = { _, _ -> }, onRetry = { })
            }
        }

        // Then
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun successState_showsListOfCurrenciesAndDate() {
        // Given
        val currencies = listOf(
            CurrencyUi("USD", "US Dollar", "A", "4.50"),
            CurrencyUi("EUR", "Euro", "A", "5.00")
        )
        val state = CurrencyListUiState(currencies = currencies, effectiveDate = "2026-01-01")

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyListContent(state = state, onItemClick = { _, _ -> }, onRetry = { })
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("US Dollar, code USD, rate 4.50").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Euro, code EUR, rate 5.00").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessage() {
        // Given
        val errorMessage = "Failed to load currencies"
        val state = CurrencyListUiState(error = errorMessage)

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyListContent(state = state, onItemClick = { _, _ -> }, onRetry = { })
            }
        }

        // Then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun itemClick_triggersCallback() {
        // Given
        var wasClicked = false
        val currency = CurrencyUi("USD", "US Dollar", "A", "4.50")
        val state = CurrencyListUiState(currencies = listOf(currency))
        val expectedContentDescription = "US Dollar, code USD, rate 4.50"

        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyListContent(state = state, onItemClick = { _, _ -> wasClicked = true }, onRetry = { })
            }
        }

        // When
        composeTestRule.onNodeWithContentDescription(expectedContentDescription).performClick()

        // Then
        assertTrue(wasClicked)
    }

    @Test
    fun accessibility_currencyItemHasCorrectContentDescription() {
        // Given
        val currency = CurrencyUi("USD", "US Dollar", "A", "4.50")
        val state = CurrencyListUiState(currencies = listOf(currency))
        val expectedContentDescription = "US Dollar, code USD, rate 4.50"

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyListContent(state = state, onItemClick = { _, _ -> }, onRetry = { })
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription(expectedContentDescription).assertExists()
    }
}
