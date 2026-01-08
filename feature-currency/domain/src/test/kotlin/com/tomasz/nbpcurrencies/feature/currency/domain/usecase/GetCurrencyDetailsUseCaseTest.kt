package com.tomasz.nbpcurrencies.feature.currency.domain.usecase

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GetCurrencyDetailsUseCaseTest {

    private val repository: CurrencyRepository = mockk()
    private val useCase = GetCurrencyDetailsUseCase(repository)

    @Test
    fun `invoke should return success when repository call is successful`() =
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
            assertEquals(mockDetails, result.getOrNull())
            coVerify { repository.getCurrencyDetails(any(), any(), any(), any()) }
        }

    @Test
    fun `invoke should return failure when repository call fails`() =
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
            assertTrue(exception is RuntimeException)
            assertEquals("API Error", exception?.message)
        }
}
