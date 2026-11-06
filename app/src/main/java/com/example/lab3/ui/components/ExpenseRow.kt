package com.example.lab3.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab3.data.Expense
import java.time.LocalDate

@Composable
fun ExpenseRow(expense: Expense, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = expense.category,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = expense.date.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "${expense.currency} ${"%.2f".format(expense.amount)}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseRowPreview() {
    ExpenseRow(
        expense = Expense("1", 12.50, "USD", "Food", LocalDate.now(), "Lunch")
    )
}