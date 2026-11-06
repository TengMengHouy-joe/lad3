package com.example.lab3.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.lab3.R
import com.example.lab3.data.Expense

@Composable
fun getCategoryDisplayName(category: String): String {
    return when (category.lowercase()) {
        "food" -> stringResource(id = R.string.category_food)
        "transport" -> stringResource(id = R.string.category_transport)
        "shopping" -> stringResource(id = R.string.category_shopping)
        "bills" -> stringResource(id = R.string.category_bills)
        "other" -> stringResource(id = R.string.category_other)
        else -> category
    }
}

@Composable
fun CategoryView(expenses: List<Expense>, modifier: Modifier = Modifier) {
    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }
    val maxSpending = categoryTotals.values.maxOrNull() ?: 1.0

    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_normal))) {
        Text(
            text = stringResource(id = R.string.spending_by_category),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        categoryTotals.forEach { (category, total) ->
            Column(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_extra_small))) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = getCategoryDisplayName(category),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "$${"%.2f".format(total)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
                LinearProgressIndicator(
                    progress = { (total / maxSpending).toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.progress_height)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}
