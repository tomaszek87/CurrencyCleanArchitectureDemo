package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.domain.model.Rate
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrencyDetailsUseCase
import com.tomasz.nbpcurrencies.feature.currency.presentation.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherRule::class)
class CurrencyDetailsViewModelTest {

    private val getCurrencyDetailsUseCase: GetCurrencyDetailsUseCase = mockk()
    private lateinit var viewModel: CurrencyDetailsViewModel

    @Test
    fun `when use case is successful, then state contains currency details`() =
        runTest {
            // Given
            val mockDetails = CurrencyDetails(
                code = "USD",
                name = "US Dollar",
                table = "A",
                currentRate = Rate("1", "2026-01-05", BigDecimal("4.0")),
                historicalRates = listOf(
                    Rate("2", "2026-01-04", BigDecimal("4.1")),
                    Rate("3", "2026-01-03", BigDecimal("3.5"))
                )
            )
            coEvery { getCurrencyDetailsUseCase(any(), any()) } returns Result.success(mockDetails)

            // When
            viewModel = CurrencyDetailsViewModel(getCurrencyDetailsUseCase)
            viewModel.getCurrencyDetails("A", "USD")

            // Then
            val state = viewModel.state.value
            val uiDetails = state.currencyDetails
            assertNotNull(uiDetails)
            assertEquals(false, state.isLoading)
            assertEquals("US Dollar", uiDetails.name)
            assertEquals(2, uiDetails.historicalRates.size)

            val firstHistoryRate = uiDetails.historicalRates[0]
            assertEquals("2026-01-04", firstHistoryRate.effectiveDate)
            assertEquals("4.1", firstHistoryRate.averageValue)
            assertEquals(false, firstHistoryRate.isDifferentByTenPercent)

            val secondHistoryRate = uiDetails.historicalRates[1]
            assertEquals("2026-01-03", secondHistoryRate.effectiveDate)
            assertEquals("3.5", secondHistoryRate.averageValue)
            assertEquals(true, secondHistoryRate.isDifferentByTenPercent)

            assertNull(state.error)
        }

    @Test
    fun `when use case fails, then state contains error`() =
        runTest {
            // Given
            val exception = RuntimeException("Network Error")
            coEvery { getCurrencyDetailsUseCase(any(), any()) } returns Result.failure(exception)

            // When
            viewModel = CurrencyDetailsViewModel(getCurrencyDetailsUseCase)
            viewModel.getCurrencyDetails("A", "USD")

            // Then
            val state = viewModel.state.value
            assertEquals(false, state.isLoading)
            assertNull(state.currencyDetails)
            assertEquals("Network Error", state.error)
        }
}
