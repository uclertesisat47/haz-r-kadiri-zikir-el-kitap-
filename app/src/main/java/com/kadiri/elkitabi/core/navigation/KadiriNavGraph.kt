package com.kadiri.elkitabi.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kadiri.elkitabi.features.amel.presentation.AmelDefteriScreen
import com.kadiri.elkitabi.features.backup.YedeklemeScreen
import com.kadiri.elkitabi.features.bahce.presentation.HasenatBahcesiScreen
import com.kadiri.elkitabi.features.dua.presentation.DualarScreen
import com.kadiri.elkitabi.features.esma.presentation.EsmaUlHusnaScreen
import com.kadiri.elkitabi.features.gunluk.presentation.ManeviGunlukScreen
import com.kadiri.elkitabi.features.home.presentation.HomeScreen
import com.kadiri.elkitabi.features.ilahi.presentation.IlahilerScreen
import com.kadiri.elkitabi.features.kazanamaz.presentation.KazaNamaziScreen
import com.kadiri.elkitabi.features.kible.presentation.KibleScreen
import com.kadiri.elkitabi.features.kitap.presentation.KitapDetayScreen
import com.kadiri.elkitabi.features.kitap.presentation.KitapListScreen
import com.kadiri.elkitabi.features.mooddua.presentation.MoodDuaScreen
import com.kadiri.elkitabi.features.namaz.presentation.NamazFocusScreen
import com.kadiri.elkitabi.features.namaz.presentation.NamazVakitleriScreen
import com.kadiri.elkitabi.features.onboarding.OnboardingScreen
import com.kadiri.elkitabi.features.planlayici.presentation.GunlukPlanScreen
import com.kadiri.elkitabi.features.quiz.presentation.QuizMenuScreen
import com.kadiri.elkitabi.features.quiz.presentation.QuizScreen
import com.kadiri.elkitabi.features.quran.presentation.QuranEkranScreen
import com.kadiri.elkitabi.features.ramazan.presentation.RamazanScreen
import com.kadiri.elkitabi.features.settings.presentation.AyarlarScreen
import com.kadiri.elkitabi.features.sharing.PaylasimKartlariScreen
import com.kadiri.elkitabi.features.silsile.presentation.SilsileScreen
import com.kadiri.elkitabi.features.siyer.presentation.SiyerScreen
import com.kadiri.elkitabi.features.takvim.presentation.HicriTakvimScreen
import com.kadiri.elkitabi.features.vird.presentation.VirdRehberScreen
import com.kadiri.elkitabi.features.zekat.presentation.ZekatScreen
import com.kadiri.elkitabi.features.zikir.presentation.OzelZikirScreen
import com.kadiri.elkitabi.features.zikir.presentation.ZikirGecmisScreen
import com.kadiri.elkitabi.features.zikir.presentation.ZikirIstatistikScreen
import com.kadiri.elkitabi.features.zikir.presentation.ZikirMatikScreen

@Composable
fun KadiriNavGraph(
    navController: NavHostController,
    startDestination: Any = HomeRoute,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController    = navController,
        startDestination = startDestination,
        modifier         = modifier
    ) {
        // ── Onboarding ──────────────────────────────────────────────────
        composable<OnboardingRoute> {
            OnboardingScreen(
                onTamamla = {
                    navController.navigate(HomeRoute) {
                        popUpTo(OnboardingRoute) { inclusive = true }
                    }
                }
            )
        }

        // ── Ana Sayfa ───────────────────────────────────────────────────
        composable<HomeRoute> {
            HomeScreen(
                onNavigateToKitap      = { navController.navigate(KitapListeRoute) },
                onNavigateToZikir      = { navController.navigate(ZikirMatikRoute) },
                onNavigateToDua        = { navController.navigate(DualarRoute) },
                onNavigateToNamaz      = { navController.navigate(NamazVakitleriRoute) },
                onNavigateToKible      = { navController.navigate(KibleRoute) },
                onNavigateToEsma       = { navController.navigate(EsmaUlHusnaRoute) },
                onNavigateToSilsile    = { navController.navigate(SilsileRoute) },
                onNavigateToTakvim     = { navController.navigate(HicriTakvimRoute) },
                onNavigateToIlahi      = { navController.navigate(IlahilerRoute) },
                onNavigateToAmel       = { navController.navigate(AmelDefteriRoute) },
                onNavigateToAyarlar    = { navController.navigate(AyarlarRoute) },
                onNavigateToBahce      = { navController.navigate(HasenatBahcesiRoute) },
                onNavigateToMoodDua    = { navController.navigate(MoodDuaRoute) },
                onNavigateToRamazan    = { navController.navigate(RamazanRoute) },
                onNavigateToGunluk     = { navController.navigate(ManeviGunlukRoute) },
                onNavigateToQuiz       = { navController.navigate(QuizMenuRoute) },
                onNavigateToPlanlayici = { navController.navigate(GunlukPlanRoute) },
                onNavigateToKuran      = { navController.navigate(QuranEkranRoute) },
                onNavigateToSiyer      = { navController.navigate(SiyerRoute) },
                onNavigateToZekat      = { navController.navigate(ZekatRoute) },
                onNavigateToVird       = { navController.navigate(VirdRehberRoute) }
            )
        }

        // ── El Kitabı ───────────────────────────────────────────────────
        composable<KitapListeRoute> {
            KitapListScreen(
                onBack     = { navController.popBackStack() },
                onKitapSec = { kitapId -> navController.navigate(KitapDetayRoute(kitapId)) }
            )
        }
        composable<KitapDetayRoute> { backStack ->
            val route = backStack.toRoute<KitapDetayRoute>()
            KitapDetayScreen(kitapId = route.kitapId, onBack = { navController.popBackStack() })
        }

        // ── Zikirmatik ──────────────────────────────────────────────────
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

        // ── Diğer Orijinal ──────────────────────────────────────────────
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

        // ── YENİ EKRANLAR ───────────────────────────────────────────────
        composable<HasenatBahcesiRoute> {
            HasenatBahcesiScreen(onBack = { navController.popBackStack() })
        }
        composable<MoodDuaRoute> {
            MoodDuaScreen(onBack = { navController.popBackStack() })
        }
        composable<RamazanRoute> {
            RamazanScreen(onBack = { navController.popBackStack() })
        }
        composable<ManeviGunlukRoute> {
            ManeviGunlukScreen(onBack = { navController.popBackStack() })
        }
        composable<QuizMenuRoute> {
            QuizMenuScreen(
                onBack        = { navController.popBackStack() },
                onKategoriSec = { kategori -> navController.navigate(QuizRoute(kategori)) }
            )
        }
        composable<QuizRoute> { backStack ->
            val route = backStack.toRoute<QuizRoute>()
            QuizScreen(
                kategori = route.kategori,
                onBack   = { navController.popBackStack() }
            )
        }
        composable<GunlukPlanRoute> {
            GunlukPlanScreen(onBack = { navController.popBackStack() })
        }
        composable<SiyerRoute> {
            SiyerScreen(onBack = { navController.popBackStack() })
        }
        composable<ZekatRoute> {
            ZekatScreen(onBack = { navController.popBackStack() })
        }
        composable<KazaNamaziRoute> {
            KazaNamaziScreen(onBack = { navController.popBackStack() })
        }
        composable<VirdRehberRoute> {
            VirdRehberScreen(onBack = { navController.popBackStack() })
        }
        composable<NamazFocusRoute> {
            NamazFocusScreen(onBack = { navController.popBackStack() })
        }
        composable<QuranEkranRoute> {
            QuranEkranScreen(onBack = { navController.popBackStack() })
        }
        composable<YedeklemeRoute> {
            YedeklemeScreen(onBack = { navController.popBackStack() })
        }
        composable<PaylasimKartlariRoute> {
            PaylasimKartlariScreen(onBack = { navController.popBackStack() })
        }
    }
}
