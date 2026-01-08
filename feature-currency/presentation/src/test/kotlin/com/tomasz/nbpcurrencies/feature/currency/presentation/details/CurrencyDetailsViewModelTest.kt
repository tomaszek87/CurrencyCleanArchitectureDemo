package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrencyDetailsUseCase
import com.tomasz.nbpcurrencies.feature.currency.presentation.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
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
                currentRate = null,
                historicalRates = emptyList()
            )
            coEvery { getCurrencyDetailsUseCase(any(), any()) } returns Result.success(mockDetails)

            // When
            viewModel = CurrencyDetailsViewModel(getCurrencyDetailsUseCase)
            viewModel.getCurrencyDetails("A", "USD")

            // Then
            val state = viewModel.state.value
            assertEquals(false, state.isLoading)
            assertEquals("US Dollar", state.currencyDetails?.name)
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
