package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomasz.nbpcurrencies.feature.currency.domain.usecase.GetCurrencyDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    private val getCurrencyDetailsUseCase: GetCurrencyDetailsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyDetailsUiState())
    val state: StateFlow<CurrencyDetailsUiState> = _state.asStateFlow()

    fun getCurrencyDetails(
        table: String,
        code: String,
    ) {
        if (_state.value.isLoading || _state.value.currencyDetails?.code == code) {
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getCurrencyDetailsUseCase(table, code)
                .onSuccess { domainDetails ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            currencyDetails = domainDetails.toUi(),
                            error = null
                        )
                    }
                }.onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}
