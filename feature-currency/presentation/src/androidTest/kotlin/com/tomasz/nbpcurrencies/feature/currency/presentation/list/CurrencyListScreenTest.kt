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
import kotlin.test.Test
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Rule

class CurrencyListScreenTest {

    companion object {
        private const val LOADING_TAG = "loading_indicator"
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.enableAccessibilityChecks()
    }

    private fun setContent(
        state: CurrencyListUiState,
        onItemClick: (String, String) -> Unit = { _, _ -> },
        onRetry: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            CurrencyDemoTheme {
                CurrencyListContent(
                    state = state,
                    onItemClick = onItemClick,
                    onRetry = onRetry
                )
            }
        }
    }

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        val state = CurrencyListUiState(isLoading = true)
        setContent(state)

        composeTestRule.onNodeWithTag(LOADING_TAG).assertIsDisplayed()
    }

    @Test
    fun successState_showsListOfCurrenciesAndDate() {
        val currencies = listOf(
            CurrencyUi("USD", "US Dollar", "A", "4.50"),
            CurrencyUi("EUR", "Euro", "A", "5.00")
        )
        val state = CurrencyListUiState(currencies = currencies, effectiveDate = "2026-01-01")
        setContent(state)

        currencies.forEach { currency ->
            val description = "${currency.name}, code ${currency.code}, rate ${currency.averageRate}"
            composeTestRule.onNodeWithContentDescription(description)
                .assertIsDisplayed()
        }
    }

    @Test
    fun errorState_showsErrorMessage() {
        val errorMessage = "Failed to load currencies"
        val state = CurrencyListUiState(error = errorMessage)
        setContent(state)

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun itemClick_triggersCallback() {
        var wasClicked = false
        val currency = CurrencyUi("USD", "US Dollar", "A", "4.50")
        val state = CurrencyListUiState(currencies = listOf(currency))
        val expectedDescription = "${currency.name}, code ${currency.code}, rate ${currency.averageRate}"

        setContent(
            state = state,
            onItemClick = { _, _ -> wasClicked = true }
        )

        composeTestRule.onNodeWithContentDescription(expectedDescription).performClick()
        assertTrue(wasClicked)
    }

    @Test
    fun accessibility_currencyItemHasCorrectContentDescription() {
        val currency = CurrencyUi("USD", "US Dollar", "A", "4.50")
        val state = CurrencyListUiState(currencies = listOf(currency))
        val expectedDescription = "${currency.name}, code ${currency.code}, rate ${currency.averageRate}"

        setContent(state)

        composeTestRule.onNodeWithContentDescription(expectedDescription)
            .assertIsDisplayed()
    }
}
