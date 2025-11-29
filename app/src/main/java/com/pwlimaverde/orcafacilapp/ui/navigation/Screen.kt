package com.pwlimaverde.orcafacilapp.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object BudgetDetail : Screen("budget_detail/{budgetId}") {
        fun createRoute(budgetId: Long) = "budget_detail/$budgetId"
    }
}
