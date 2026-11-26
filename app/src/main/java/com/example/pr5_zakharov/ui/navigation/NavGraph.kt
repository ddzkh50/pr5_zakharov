package com.example.pr5_zakharov.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pr5_zakharov.data.SpecialtyRepository
import com.example.pr5_zakharov.ui.screens.DetailScreen
import com.example.pr5_zakharov.ui.screens.ListScreen

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{specialtyId}"

    fun detail(specialtyId: Int): String = "detail/$specialtyId"
}

@Composable
fun CatalogNavGraph(
    navController: NavHostController,
    darkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LIST
    ) {
        composable(route = Routes.LIST) {
            ListScreen(
                specialties = SpecialtyRepository.specialties,
                darkTheme = darkTheme,
                onToggleTheme = onToggleTheme,
                onSpecialtyClick = { id ->
                    navController.navigate(Routes.detail(id))
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("specialtyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val specialtyId = backStackEntry.arguments?.getInt("specialtyId") ?: 0
            DetailScreen(
                specialtyId = specialtyId,
                darkTheme = darkTheme,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
