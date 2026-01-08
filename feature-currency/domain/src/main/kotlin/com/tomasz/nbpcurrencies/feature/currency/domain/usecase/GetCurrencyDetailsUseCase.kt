package com.tomasz.nbpcurrencies.feature.currency.domain.usecase

import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.text.format

class GetCurrencyDetailsUseCase(
    private val repository: CurrencyRepository,
) {
    suspend operator fun invoke(
        table: String,
        code: String,
        days: Int = 14,
    ): Result<CurrencyDetails> {
        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(days.toLong())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return repository.getCurrencyDetails(
            table = table,
            code = code,
            startDate = startDate.format(formatter),
            endDate = endDate.format(formatter)
        )
    }
}
