package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyData
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrenciesUseCase
import com.tomasz.nbpcurrencies.feature.currency.presentation.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
class CurrencyListViewModelTest {

    private val getCurrenciesUseCase: GetCurrenciesUseCase = mockk()
    private lateinit var viewModel: CurrencyListViewModel

    @Test
    fun `when use case is successful, then state contains currency list`() =
        runTest {
            // Given
            val mockCurrencyData = CurrencyData("2026-01-01", emptyList())
            coEvery { getCurrenciesUseCase.invoke() } returns Result.success(mockCurrencyData)

            // When
            viewModel = CurrencyListViewModel(getCurrenciesUseCase)

            // Then
            val state = viewModel.state.value
            assertTrue(state.currencies.isEmpty())
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
