package com.tomasz.nbpcurrencies.feature.currency.data.repository

import com.tomasz.nbpcurrencies.feature.currency.data.model.toDomain
import com.tomasz.nbpcurrencies.feature.currency.data.service.CurrencyService
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyData
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.domain.model.exception.EmptyBodyException
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyService,
) : CurrencyRepository {

    override suspend fun getCurrencies(table: String): Result<CurrencyData> =
        runCatching {
            val currencyTable = api.getCurrencies(table).firstOrNull()
                ?: throw EmptyBodyException("Response body is empty for table: $table")
            CurrencyData(
                effectiveDate = currencyTable.effectiveDate,
                currencies = currencyTable.rates.map { it.toDomain(table) }
            )
        }

    override suspend fun getCurrencyDetails(
        table: String,
        code: String,
        startDate: String,
        endDate: String,
    ): Result<CurrencyDetails> =
        runCatching {
            api.getRatesByDateRange(
                table = table,
                code = code,
                startDate = startDate,
                endDate = endDate
            ).toDomain()
        }
}
