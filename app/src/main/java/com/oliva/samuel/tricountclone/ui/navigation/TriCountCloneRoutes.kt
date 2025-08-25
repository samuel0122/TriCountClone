package com.oliva.samuel.tricountclone.ui.navigation

import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.TricountId
import kotlinx.serialization.Serializable

object TricountCloneDestinations {

    @Serializable
    object SplashScreen

    @Serializable
    object MainScreen

    @Serializable
    object TricountsScreen

    @Serializable
    data class TricountDetailScreen(private val _tricountId: String) {
        constructor(tricountId: TricountId) : this(tricountId.toString())

        val tricountId: TricountId get() = TricountId.fromString(_tricountId)
    }

    @Serializable
    data class ExpenseDetailScreen(private val _expenseId: String) {
        constructor(expenseId: ExpenseId) : this(expenseId.toString())

        val expenseId: ExpenseId get() = ExpenseId.fromString(_expenseId)
    }
}
