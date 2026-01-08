package com.tomasz.nbpcurrencies.feature.currency.domain.usecase

import com.tomasz.nbpcurrencies.feature.currency.domain.model.Currency
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyData
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.mockk
import java.math.BigDecimal
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GetCurrenciesUseCaseTest {

    private val repository: CurrencyRepository = mockk()
    private val useCase = GetCurrenciesUseCase(repository)

    @Test
    fun `invoke should return success with latest date when both api calls are successful`() =
        runTest {
            // Given
            val tableAData = CurrencyData("2026-01-01", listOf(Currency("USD", "US Dollar", "A", BigDecimal("4.50"))))
            val tableBData = CurrencyData("2026-01-02", listOf(Currency("EUR", "Euro", "B", BigDecimal("5.00"))))

            coEvery { repository.getCurrencies("A") } returns Result.success(tableAData)
            coEvery { repository.getCurrencies("B") } returns Result.success(tableBData)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isSuccess)
            val currencyData = result.getOrNull()
            assertEquals("2026-01-02", currencyData?.effectiveDate)
            assertEquals(2, currencyData?.currencies?.size)
        }

    @Test
    fun `invoke should return failure when one of the api calls fails`() =
        runTest {
            // Given
            val tableAData = CurrencyData("2026-01-01", listOf(Currency("USD", "US Dollar", "A", BigDecimal("4.50"))))
            val expectedException = RuntimeException("API Error")

            coEvery { repository.getCurrencies("A") } returns Result.success(tableAData)
            coEvery { repository.getCurrencies("B") } returns Result.failure(expectedException)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertTrue(exception is RuntimeException)
            assertEquals("API Error", exception?.message)
        }
}
