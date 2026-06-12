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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kadiri.elkitabi.core.data.preferences.UserPreferencesDataStore
import com.kadiri.elkitabi.core.designsystem.theme.KadiriTheme
import com.kadiri.elkitabi.core.navigation.KadiriBottomNavigation
import com.kadiri.elkitabi.core.navigation.KadiriNavGraph
import com.kadiri.elkitabi.core.navigation.OnboardingRoute
import com.kadiri.elkitabi.core.navigation.HomeRoute
import com.kadiri.elkitabi.core.navigation.shouldShowBottomBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPrefs: UserPreferencesDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KadiriTheme {
                KadiriApp(userPrefs = userPrefs)
            }
        }
    }
}

@Composable
fun KadiriApp(userPrefs: UserPreferencesDataStore) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val onboardingDone by userPrefs.onboardingDone.collectAsState(initial = true)
    val startDestination: Any = if (onboardingDone) HomeRoute else OnboardingRoute

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
            navController    = navController,
            startDestination = startDestination,
            modifier         = Modifier.padding(paddingValues)
        )
    }
}
