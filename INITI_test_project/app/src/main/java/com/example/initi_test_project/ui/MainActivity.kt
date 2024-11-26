package com.example.initi_test_project.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.initi_test_project.ui.screens.HomeScreens
import com.example.initi_test_project.ui.screens.homeScreens
import com.example.initi_test_project.ui.theme.INITI_test_projectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            INITI_test_projectTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeScreens.HomeMainScreen.route,
                ) {
                    homeScreens(navController)
                }
            }
        }
    }
}
