package com.tomasz.nbpcurrencies.feature.currency.domain.repository

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyData
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails

interface CurrencyRepository {
    suspend fun getCurrencies(table: String): Result<CurrencyData>
    suspend fun getCurrencyDetails(
        table: String,
        code: String,
        startDate: String,
        endDate: String,
    ): Result<CurrencyDetails>
}
