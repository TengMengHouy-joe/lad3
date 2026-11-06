package com.example.lab3.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab3.data.Expense
import com.example.lab3.data.sampleExpenses
import com.example.lab3.ui.components.CategoryView
import com.example.lab3.ui.components.ExpenseRow
import com.example.lab3.ui.components.TotalDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDashboard(expenses: List<Expense>) {
    val totalThisMonth = expenses.sumOf { it.amount }

    Scaffold(
        // Set background explicitly to background color scheme
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Expense Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                TotalDisplay(totalAmount = totalThisMonth)
            }
            item {
                CategoryView(expenses = expenses)
            }
            item {
                Text(
                    text = "Recent Expenses",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(expenses) { expense ->
                ExpenseRow(expense = expense)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    MaterialTheme {
        ExpenseDashboard(expenses = sampleExpenses)
    }
}