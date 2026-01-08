package com.tomasz.currency

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TopAppBarState(
    @param:StringRes val titleResId: Int? = null,
    val effectiveDate: String? = null,
    val showBackButton: Boolean = false,
)

class SharedViewModel : ViewModel() {
    private val _topAppBarState = MutableStateFlow(TopAppBarState())
    val topAppBarState = _topAppBarState.asStateFlow()

    fun updateTopAppBarAction(
        @StringRes titleResId: Int,
        effectiveDate: String?,
        showBackButton: Boolean,
    ) {
        _topAppBarState.update {
            it.copy(
                titleResId = titleResId,
                effectiveDate = effectiveDate,
                showBackButton = showBackButton
            )
        }
    }
}
