package com.tomasz.nbpcurrencies.feature.currency.domain.usecase

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyData
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetCurrenciesUseCase(
    private val repository: CurrencyRepository,
) {
    suspend operator fun invoke(): Result<CurrencyData> =
        runCatching {
            coroutineScope {
                val fetchCurrenciesFromTableA = async { repository.getCurrencies("A").getOrThrow() }
                val fetchCurrenciesFromTableB = async { repository.getCurrencies("B").getOrThrow() }

                val (resultFromTableA, resultFromTableB) = awaitAll(
                    fetchCurrenciesFromTableA,
                    fetchCurrenciesFromTableB
                )

                val combinedCurrencies = resultFromTableA.currencies + resultFromTableB.currencies
                val latestEffectiveDate = maxOf(resultFromTableA.effectiveDate, resultFromTableB.effectiveDate)

                CurrencyData(latestEffectiveDate, combinedCurrencies)
            }
        }
}
