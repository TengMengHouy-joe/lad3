package com.example.lab3.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab3.R
import com.example.lab3.data.Expense
import com.example.lab3.data.sampleExpenses
import com.example.lab3.ui.components.CategoryView
import com.example.lab3.ui.components.ExpenseRow
import com.example.lab3.ui.components.TotalDisplay
import com.example.lab3.ui.theme.Lab3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDashboard(
    expenses: List<Expense>,
    onAddExpense: () -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    onUndoDelete: () -> Unit,
    modifier: Modifier = Modifier,
    canUndo: Boolean = false,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    // Calculated total from list
    val totalThisMonth = expenses.sumOf { it.amount }

    // Derived state for empty check to avoid unnecessary recalculations
    val isEmpty by remember(expenses) {
        derivedStateOf { expenses.isEmpty() }
    }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.top_app_bar_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    if (canUndo) {
                        IconButton(onClick = onUndoDelete) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Undo,
                                contentDescription = stringResource(id = R.string.undo),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddExpense,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_expense)
                    )
                },
                text = { Text(text = stringResource(id = R.string.add_expense)) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = dimensionResource(id = R.dimen.fab_elevation)
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
                    text = stringResource(id = R.string.recent_expenses),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_normal),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
                )
            }
            if (isEmpty) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_normal)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.padding_extra_large)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.no_expenses_yet),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(
                    items = expenses,
                    key = { it.id }
                ) { expense ->
                    ExpenseRow(
                        expense = expense,
                        onDeleteExpense = onDeleteExpense
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(
    expenses: List<Expense>,
    onAddExpense: () -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    onUndoDelete: () -> Unit,
    modifier: Modifier = Modifier,
    canUndo: Boolean = false,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    ExpenseDashboard(
        expenses = expenses,
        onAddExpense = onAddExpense,
        onDeleteExpense = onDeleteExpense,
        onUndoDelete = onUndoDelete,
        modifier = modifier,
        canUndo = canUndo,
        snackbarHostState = snackbarHostState
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    Lab3Theme {
        ExpenseDashboard(
            expenses = sampleExpenses,
            onAddExpense = {},
            onDeleteExpense = {},
            onUndoDelete = {}
        )
    }
}
