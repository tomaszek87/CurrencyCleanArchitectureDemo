package com.tomasz.nbpcurrencies.feature.currency.domain.usecase

import com.tomasz.nbpcurrencies.feature.currency.domain.model.Currency
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyData
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class GetCurrenciesUseCaseTest {

    private val repository: CurrencyRepository = mockk()
    private val useCase = GetCurrenciesUseCase(repository)

    @Test
    fun `invoke returns success with latest date and merged currencies when both APIs succeed`() =
        runTest {
            // Given
            val tableAData = CurrencyData(
                "2026-01-01",
                listOf(Currency("USD", "US Dollar", "A", BigDecimal("4.50")))
            )
            val tableBData = CurrencyData(
                "2026-01-02",
                listOf(Currency("EUR", "Euro", "B", BigDecimal("5.00")))
            )

            coEvery { repository.getCurrencies("A") } returns Result.success(tableAData)
            coEvery { repository.getCurrencies("B") } returns Result.success(tableBData)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isSuccess)
            val currencyData = result.getOrNull()
            assertNotNull(currencyData)

            assertEquals("2026-01-02", currencyData.effectiveDate)
            assertEquals(2, currencyData.currencies.size)
            assertEquals(listOf("USD", "EUR"), currencyData.currencies.map { it.code })

            coVerifyAll {
                repository.getCurrencies("A")
                repository.getCurrencies("B")
            }
        }

    @Test
    fun `invoke returns success with empty currencies when both tables are empty`() =
        runTest {
            // Given
            coEvery { repository.getCurrencies("A") } returns Result.success(CurrencyData("2026-01-01", emptyList()))
            coEvery { repository.getCurrencies("B") } returns Result.success(CurrencyData("2026-01-01", emptyList()))

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isSuccess)
            val currencyData = result.getOrNull()
            assertNotNull(currencyData)
            assertEquals(0, currencyData.currencies.size)

            coVerifyAll {
                repository.getCurrencies("A")
                repository.getCurrencies("B")
            }
        }

    @Test
    fun `invoke returns failure when one API call fails`() =
        runTest {
            // Given
            val tableAData = CurrencyData(
                "2026-01-01",
                listOf(Currency("USD", "US Dollar", "A", BigDecimal("4.50")))
            )
            val expectedException = RuntimeException("API Error")

            coEvery { repository.getCurrencies("A") } returns Result.success(tableAData)
            coEvery { repository.getCurrencies("B") } returns Result.failure(expectedException)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertNotNull(exception)
            assertTrue(exception is RuntimeException)
            assertEquals("API Error", exception.message)

            coVerifyAll {
                repository.getCurrencies("A")
                repository.getCurrencies("B")
            }
        }
}
