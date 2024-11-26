package com.example.initi_test_project.ui.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.initi_test_project.ui.screens.inet_list.InetListScreen
import com.example.initi_test_project.ui.screens.main.MainScreen

sealed class HomeScreens(
    val route: String,
) {
    object HomeMainScreen : HomeScreens("home_main_screen")

    object InetListScreen : HomeScreens("inet_list_screen")
}

fun NavGraphBuilder.homeScreens(navController: NavHostController) {
    composable(HomeScreens.HomeMainScreen.route) { MainScreen(navController) }
    composable(
        HomeScreens.InetListScreen.route + "/{orgId}",
        arguments =
            listOf(
                navArgument(name = "orgId") { NavType.StringType },
            ),
    ) { backStackEntry ->
        val orgId = (backStackEntry.arguments?.getString("orgId") ?: return@composable)
        InetListScreen(navController, orgId)
    }
}
