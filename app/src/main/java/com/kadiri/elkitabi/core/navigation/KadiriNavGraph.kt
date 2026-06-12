package com.kadiri.elkitabi.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kadiri.elkitabi.features.amel.presentation.AmelDefteriScreen
import com.kadiri.elkitabi.features.dua.presentation.DualarScreen
import com.kadiri.elkitabi.features.esma.presentation.EsmaUlHusnaScreen
import com.kadiri.elkitabi.features.home.presentation.HomeScreen
import com.kadiri.elkitabi.features.ilahi.presentation.IlahilerScreen
import com.kadiri.elkitabi.features.kible.presentation.KibleScreen
import com.kadiri.elkitabi.features.kitap.presentation.KitapDetayScreen
import com.kadiri.elkitabi.features.kitap.presentation.KitapListScreen
import com.kadiri.elkitabi.features.namaz.presentation.NamazVakitleriScreen
import com.kadiri.elkitabi.features.settings.presentation.AyarlarScreen
import com.kadiri.elkitabi.features.silsile.presentation.SilsileScreen
import com.kadiri.elkitabi.features.takvim.presentation.HicriTakvimScreen
import com.kadiri.elkitabi.features.zikir.presentation.OzelZikirScreen
import com.kadiri.elkitabi.features.zikir.presentation.ZikirGecmisScreen
import com.kadiri.elkitabi.features.zikir.presentation.ZikirIstatistikScreen
import com.kadiri.elkitabi.features.zikir.presentation.ZikirMatikScreen

@Composable
fun KadiriNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController    = navController,
        startDestination = HomeRoute,
        modifier         = modifier
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onNavigateToKitap   = { navController.navigate(KitapListeRoute) },
                onNavigateToZikir   = { navController.navigate(ZikirMatikRoute) },
                onNavigateToDua     = { navController.navigate(DualarRoute) },
                onNavigateToNamaz   = { navController.navigate(NamazVakitleriRoute) },
                onNavigateToKible   = { navController.navigate(KibleRoute) },
                onNavigateToEsma    = { navController.navigate(EsmaUlHusnaRoute) },
                onNavigateToSilsile = { navController.navigate(SilsileRoute) },
                onNavigateToTakvim  = { navController.navigate(HicriTakvimRoute) },
                onNavigateToIlahi   = { navController.navigate(IlahilerRoute) },
                onNavigateToAmel    = { navController.navigate(AmelDefteriRoute) },
                onNavigateToAyarlar = { navController.navigate(AyarlarRoute) }
            )
        }
        composable<KitapListeRoute> {
            KitapListScreen(
                onBack      = { navController.popBackStack() },
                onKitapSec  = { kitapId -> navController.navigate(KitapDetayRoute(kitapId)) }
            )
        }
        composable<KitapDetayRoute> { backStack ->
            val route = backStack.toRoute<KitapDetayRoute>()
            KitapDetayScreen(
                kitapId = route.kitapId,
                onBack  = { navController.popBackStack() }
            )
        }
        composable<ZikirMatikRoute> {
            ZikirMatikScreen(
                onNavigateToIstatistik = { navController.navigate(ZikirIstatistikRoute) },
                onNavigateToGecmis     = { navController.navigate(ZikirGecmisRoute) },
                onNavigateToOzel       = { navController.navigate(OzelZikirRoute) }
            )
        }
        composable<ZikirIstatistikRoute> {
            ZikirIstatistikScreen(onBack = { navController.popBackStack() })
        }
        composable<ZikirGecmisRoute> {
            ZikirGecmisScreen(onBack = { navController.popBackStack() })
        }
        composable<OzelZikirRoute> {
            OzelZikirScreen(onBack = { navController.popBackStack() })
        }
        composable<DualarRoute> {
            DualarScreen(onBack = { navController.popBackStack() })
        }
        composable<NamazVakitleriRoute> {
            NamazVakitleriScreen(onBack = { navController.popBackStack() })
        }
        composable<KibleRoute> {
            KibleScreen(onBack = { navController.popBackStack() })
        }
        composable<EsmaUlHusnaRoute> {
            EsmaUlHusnaScreen(onBack = { navController.popBackStack() })
        }
        composable<SilsileRoute> {
            SilsileScreen(onBack = { navController.popBackStack() })
        }
        composable<HicriTakvimRoute> {
            HicriTakvimScreen(onBack = { navController.popBackStack() })
        }
        composable<IlahilerRoute> {
            IlahilerScreen(onBack = { navController.popBackStack() })
        }
        composable<AmelDefteriRoute> {
            AmelDefteriScreen(onBack = { navController.popBackStack() })
        }
        composable<AyarlarRoute> {
            AyarlarScreen(onBack = { navController.popBackStack() })
        }
    }
}
