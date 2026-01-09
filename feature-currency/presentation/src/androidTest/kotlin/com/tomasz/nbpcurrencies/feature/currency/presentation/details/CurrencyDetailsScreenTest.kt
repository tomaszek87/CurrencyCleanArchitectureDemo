package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.accessibility.enableAccessibilityChecks
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.tryPerformAccessibilityChecks
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import org.junit.Rule
import org.junit.Test

class CurrencyDetailsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        // Given
        val state = CurrencyDetailsUiState(isLoading = true)

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = state)
            }
        }

        // Then
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun successState_showsCurrencyDetails() {
        // Given
        val mockDetails = CurrencyDetailsUi(
            code = "EUR",
            name = "euro",
            table = "A",
            currentRate = RateUi("001/A/NBP/2026", "2026-01-01", "4.5000", false),
            historicalRates = listOf(RateUi("002/A/NBP/2026", "2026-01-02", "4.5890", false))
        )
        val state = CurrencyDetailsUiState(currencyDetails = mockDetails)

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = state)
            }
        }

        // Then
        composeTestRule.onNodeWithText("euro").assertIsDisplayed()
        composeTestRule.onNodeWithText("EUR").assertIsDisplayed()
        composeTestRule.onNodeWithText("Last Rate: 4.5000").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessage() {
        // Given
        val errorMessage = "Failed to load details"
        val state = CurrencyDetailsUiState(error = errorMessage)

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = state)
            }
        }

        // Then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun accessibility_historyItemHasCorrectContentDescription() {
        // Given
        val mockDetails = CurrencyDetailsUi(
            code = "EUR",
            name = "euro",
            table = "A",
            currentRate = RateUi("001/A/NBP/2026", "2026-01-01", "4.5000", false),
            historicalRates = listOf(RateUi("002/A/NBP/2026", "2026-01-02", "5.1000", true))
        )
        val state = CurrencyDetailsUiState(currencyDetails = mockDetails)
        val expectedDescription = "Rate on 2026-01-02 was 5.1000, which is a change of more than 10%"

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = state)
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription(expectedDescription).assertExists()
    }

    @Test
    fun performAccessibilityChecks() {
        // Given
        val mockDetails = CurrencyDetailsUi(
            code = "EUR",
            name = "euro",
            table = "A",
            currentRate = RateUi("001/A/NBP/2026", "2026-01-01", "4.5000", false),
            historicalRates = listOf(RateUi("002/A/NBP/2026", "2026-01-02", "5.1000", true))
        )
        val state = CurrencyDetailsUiState(currencyDetails = mockDetails)

        composeTestRule.enableAccessibilityChecks()

        // When
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = state)
            }
        }

        // Then
        composeTestRule.onRoot().tryPerformAccessibilityChecks()
    }
}
