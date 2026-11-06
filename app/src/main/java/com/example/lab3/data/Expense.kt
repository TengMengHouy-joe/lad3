package com.example.lab3.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Expense(
    val id: String,
    val amount: Double,
    val currency: String = "USD",
    val category: String,
    val date: LocalDate,
    val remark: String? = null
)


val sampleExpenses = listOf(
    Expense("1", 12.50, "USD", "Food", LocalDate.now(), "Lunch"),
    Expense("2", 45.00, "USD", "Transport", LocalDate.now().minusDays(1), "Gas"),
    Expense("3", 120.00, "USD", "Shopping", LocalDate.now().minusDays(2), "Clothes"),
    Expense("4", 8.00, "USD", "Food", LocalDate.now().minusDays(3), "Coffee"),
    Expense("5", 60.00, "USD", "Bills", LocalDate.now().minusDays(4), "Internet")
)