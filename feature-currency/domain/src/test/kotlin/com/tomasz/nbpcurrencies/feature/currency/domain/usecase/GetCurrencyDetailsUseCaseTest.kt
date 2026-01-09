package com.tomasz.nbpcurrencies.feature.currency.domain.usecase

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class GetCurrencyDetailsUseCaseTest {

    private val repository: CurrencyRepository = mockk()
    private val useCase = GetCurrencyDetailsUseCase(repository)

    @Test
    fun `invoke returns success when repository call succeeds`() =
        runTest {
            // Given
            val table = "A"
            val code = "USD"
            val mockDetails = CurrencyDetails(code, "US Dollar", table, null, emptyList())

            coEvery { repository.getCurrencyDetails(any(), any(), any(), any()) } returns Result.success(mockDetails)

            // When
            val result = useCase(table, code)

            // Then
            assertTrue(result.isSuccess)
            val details = result.getOrNull()
            assertNotNull(details)
            assertEquals(mockDetails, details)

            coVerifyAll {
                repository.getCurrencyDetails(any(), any(), any(), any())
            }
        }

    @Test
    fun `invoke returns failure when repository call fails`() =
        runTest {
            // Given
            val table = "A"
            val code = "USD"
            val expectedException = RuntimeException("API Error")

            coEvery { repository.getCurrencyDetails(any(), any(), any(), any()) } returns Result.failure(
                expectedException
            )

            // When
            val result = useCase(table, code)

            // Then
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertNotNull(exception)
            assertTrue(exception is RuntimeException)
            assertEquals("API Error", exception.message)

            coVerifyAll {
                repository.getCurrencyDetails(any(), any(), any(), any())
            }
        }
}
