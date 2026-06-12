package com.kadiri.elkitabi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kadiri.elkitabi.core.designsystem.theme.KadiriTheme
import com.kadiri.elkitabi.core.navigation.KadiriBottomNavigation
import com.kadiri.elkitabi.core.navigation.KadiriNavGraph
import com.kadiri.elkitabi.core.navigation.shouldShowBottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KadiriTheme {
                KadiriApp()
            }
        }
    }
}

@Composable
fun KadiriApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (currentDestination?.shouldShowBottomBar() == true) {
                KadiriBottomNavigation(navController = navController)
            }
        }
    ) { paddingValues ->
        KadiriNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
