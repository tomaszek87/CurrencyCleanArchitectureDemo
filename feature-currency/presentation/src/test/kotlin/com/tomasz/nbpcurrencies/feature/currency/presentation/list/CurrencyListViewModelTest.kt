package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import com.tomasz.nbpcurrencies.feature.currency.domain.model.Currency
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyData
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrenciesUseCase
import com.tomasz.nbpcurrencies.feature.currency.presentation.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherRule::class)
class CurrencyListViewModelTest {

    private val getCurrenciesUseCase: GetCurrenciesUseCase = mockk()
    private lateinit var viewModel: CurrencyListViewModel

    @Test
    fun `when use case is successful, then state contains currency list`() =
        runTest {
            // Given
            val mockCurrencies = listOf(
                Currency("USD", "Dollar", "A", BigDecimal("4.0")),
                Currency("EUR", "Euro", "A", BigDecimal("4.5"))
            )
            val mockCurrencyData = CurrencyData("2026-01-01", mockCurrencies)
            coEvery { getCurrenciesUseCase.invoke() } returns Result.success(mockCurrencyData)

            // When
            viewModel = CurrencyListViewModel(getCurrenciesUseCase)

            // Then
            val state = viewModel.state.value
            assertEquals(2, state.currencies.size)
            assertEquals("Dollar", state.currencies[0].name)
            assertEquals("4.0", state.currencies[0].averageRate)
            assertEquals("Euro", state.currencies[1].name)
            assertEquals("4.5", state.currencies[1].averageRate)
            assertEquals("2026-01-01", state.effectiveDate)
            assertNull(state.error)
        }

    @Test
    fun `when use case fails, then state contains error`() =
        runTest {
            // Given
            val exception = RuntimeException("Network Error")
            coEvery { getCurrenciesUseCase.invoke() } returns Result.failure(exception)

            // When
            viewModel = CurrencyListViewModel(getCurrenciesUseCase)

            // Then
            val state = viewModel.state.value
            assertTrue(state.currencies.isEmpty())
            assertEquals("Network Error", state.error)
            assertNull(state.effectiveDate)
        }
}
