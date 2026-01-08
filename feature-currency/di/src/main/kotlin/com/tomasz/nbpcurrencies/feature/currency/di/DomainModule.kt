package com.tomasz.nbpcurrencies.feature.currency.di

import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrenciesUseCase
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrencyDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetCurrenciesUseCase(repository: CurrencyRepository): GetCurrenciesUseCase {
        return GetCurrenciesUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCurrencyDetailsUseCase(repository: CurrencyRepository): GetCurrencyDetailsUseCase {
        return GetCurrencyDetailsUseCase(repository)
    }
}
