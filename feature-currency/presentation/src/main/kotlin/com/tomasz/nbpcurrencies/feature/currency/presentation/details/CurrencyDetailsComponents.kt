package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.tomasz.currency.core.ui.theme.CurrencyTheme.colors
import com.tomasz.currency.core.ui.theme.CurrencyTheme.dimensions
import com.tomasz.currency.core.ui.theme.CurrencyTheme.typography
import com.tomasz.nbpcurrencies.feature.currency.presentation.R

@Composable
internal fun DetailsHeader(
    name: String,
    code: String,
    currentRate: RateUi?,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensions.defaultElevation)
    ) {
        Column(modifier = Modifier.padding(dimensions.paddingVeryLarge)) {
            Text(
                text = name,
                style = typography.headlineSmall
            )

            Text(
                modifier = Modifier.padding(bottom = dimensions.paddingMedium),
                text = code,
                style = typography.headlineSmall
            )

            currentRate?.let {
                Text(
                    modifier = Modifier.padding(top = dimensions.paddingMedium),
                    text = stringResource(R.string.last_rate, it.averageValue.toPlainString()),
                    style = typography.bodyLarge
                )

                Text(
                    text = stringResource(R.string.date, it.effectiveDate),
                    style = typography.bodyLarge
                )
            }
        }
    }
}

@Composable
internal fun HistoryHeader(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.history_header),
        style = typography.titleMedium
    )
}

@Composable
internal fun HistoryItem(
    modifier: Modifier = Modifier,
    rate: RateUi,
) {
    val textColor = if (rate.isDifferentByTenPercent) {
        colors.error
    } else {
        Color.Unspecified
    }

    val accessibilityPercentageString = if (rate.isDifferentByTenPercent) {
        stringResource(R.string.history_item_desc_significant_change)
    } else {
        ""
    }

    val description = stringResource(
        R.string.history_item_desc,
        rate.effectiveDate,
        rate.averageValue.toPlainString(),
        accessibilityPercentageString
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensions.paddingLarge)
            .semantics(mergeDescendants = true) {
                contentDescription = description
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.clearAndSetSemantics { },
            text = rate.effectiveDate,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier.clearAndSetSemantics { },
            text = rate.averageValue.toPlainString(),
            color = textColor,
            style = typography.bodyLarge
        )
    }
}
