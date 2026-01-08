package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyListUiState())
    val state: StateFlow<CurrencyListUiState> = _state.asStateFlow()

    init {
        getCurrencies()
    }

    fun onRetry() {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            getCurrenciesUseCase().onSuccess { currencyData ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        currencies = currencyData.currencies.toUi().sortedByName(),
                        effectiveDate = currencyData.effectiveDate,
                        error = null
                    )
                }
            }.onFailure { error ->
                // we should map error to human readable message
                // for testing purposes i'm passing the exception directly to ui
                _state.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}
