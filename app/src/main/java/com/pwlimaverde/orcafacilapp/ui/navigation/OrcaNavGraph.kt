package com.pwlimaverde.orcafacilapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pwlimaverde.orcafacilapp.ui.screen.BudgetDetailScreen
import com.pwlimaverde.orcafacilapp.ui.screen.HomeScreen

@Composable
fun OrcaNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.BudgetDetail.route,
            arguments = listOf(navArgument("budgetId") { type = NavType.LongType })
        ) {
            BudgetDetailScreen(navController = navController)
        }
    }
}
