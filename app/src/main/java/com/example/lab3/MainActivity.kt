package com.example.lab3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.lab3.data.Expense
import com.example.lab3.data.sampleExpenses
import com.example.lab3.ui.ExpenseDashboard
import com.example.lab3.ui.theme.Lab3Theme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class MainActivity : ComponentActivity() {
    private val TAG = "ExpenseTrackerApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")

        setContent {
            Lab3Theme {
                ExpenseTrackerApp(tag = TAG)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}

@Composable
fun ExpenseTrackerApp(tag: String) {
    val expenses = remember {
        mutableStateListOf<Expense>().apply {
            addAll(sampleExpenses)
        }
    }

    var recentlyDeletedExpense by remember { mutableStateOf<Expense?>(null) }
    var recentlyDeletedIndex by remember { mutableIntStateOf(-1) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val snackbarMessage = stringResource(id = R.string.expense_deleted)
    val undoActionLabel = stringResource(id = R.string.undo)

    val sampleCategories = listOf("Food", "Transport", "Shopping", "Bills")
    val sampleAmounts = listOf(15.50, 24.00, 49.99, 12.00, 85.00, 32.50)

    fun undoDelete() {
        recentlyDeletedExpense?.let { restored ->
            val indexToInsert = if (recentlyDeletedIndex in 0..expenses.size) recentlyDeletedIndex else 0
            expenses.add(indexToInsert, restored)
            Log.d(tag, "Expense added (undo): id=${restored.id}, category=${restored.category}, amount=${restored.amount}")
            recentlyDeletedExpense = null
            recentlyDeletedIndex = -1
        }
    }

    ExpenseDashboard(
        expenses = expenses,
        onAddExpense = {
            val newExpense = Expense(
                id = UUID.randomUUID().toString(),
                amount = sampleAmounts.random(),
                currency = "USD",
                category = sampleCategories.random(),
                date = LocalDate.now(),
                remark = "Sample Expense"
            )
            expenses.add(0, newExpense)
            Log.d(tag, "Expense added: id=${newExpense.id}, category=${newExpense.category}, amount=${newExpense.amount}")
        },
        onDeleteExpense = { expenseToDelete ->
            val index = expenses.indexOf(expenseToDelete)
            if (index != -1) {
                recentlyDeletedExpense = expenseToDelete
                recentlyDeletedIndex = index
                expenses.removeAt(index)
                Log.d(tag, "Expense deleted: id=${expenseToDelete.id}, category=${expenseToDelete.category}, amount=${expenseToDelete.amount}")

                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result = snackbarHostState.showSnackbar(
                        message = snackbarMessage,
                        actionLabel = undoActionLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        undoDelete()
                    }
                }
            }
        },
        onUndoDelete = {
            undoDelete()
        },
        canUndo = recentlyDeletedExpense != null,
        snackbarHostState = snackbarHostState
    )
}
