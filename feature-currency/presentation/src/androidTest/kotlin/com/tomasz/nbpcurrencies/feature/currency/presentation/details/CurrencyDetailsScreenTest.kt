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
import kotlin.test.Test
import org.junit.Before
import org.junit.Rule

class CurrencyDetailsScreenTest {

    companion object {
        private const val LOADING_TAG = "loading_indicator"
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.enableAccessibilityChecks()
    }

    private fun setContent(state: CurrencyDetailsUiState) {
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = state)
            }
        }
    }

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        val state = CurrencyDetailsUiState(isLoading = true)
        setContent(state)

        composeTestRule.onNodeWithTag(LOADING_TAG).assertIsDisplayed()
    }

    @Test
    fun successState_showsCurrencyDetails() {
        val mockDetails = mockCurrencyDetailsUi()
        val state = CurrencyDetailsUiState(currencyDetails = mockDetails)
        setContent(state)

        composeTestRule.onNodeWithText(mockDetails.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(mockDetails.code).assertIsDisplayed()
        composeTestRule.onNodeWithText("Last Rate: ${mockDetails.currentRate?.averageValue}").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessage() {
        val errorMessage = "Failed to load details"
        val state = CurrencyDetailsUiState(error = errorMessage)
        setContent(state)

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun accessibility_historyItemHasCorrectContentDescription() {
        val mockDetails = mockCurrencyDetailsUi()
        val state = CurrencyDetailsUiState(currencyDetails = mockDetails)
        setContent(state)

        val historicalRate = mockDetails.historicalRates.first()
        val expectedDescription =
            "Rate on ${historicalRate.effectiveDate} was ${historicalRate.averageValue}, which is a change of more than 10%"

        composeTestRule.onNodeWithContentDescription(expectedDescription)
            .assertIsDisplayed()
    }

    @Test
    fun performAccessibilityChecks() {
        val mockDetails = mockCurrencyDetailsUi()
        val state = CurrencyDetailsUiState(currencyDetails = mockDetails)
        setContent(state)

        composeTestRule.onRoot().tryPerformAccessibilityChecks()
    }

    private fun mockCurrencyDetailsUi() =
        CurrencyDetailsUi(
            code = "EUR",
            name = "euro",
            table = "A",
            currentRate = RateUi("001/A/NBP/2026", "2026-01-01", "4.5000", false),
            historicalRates = listOf(RateUi("002/A/NBP/2026", "2026-01-02", "5.1000", true))
        )
}
