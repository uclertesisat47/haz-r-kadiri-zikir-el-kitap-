package com.kadiri.elkitabi.core.navigation

import kotlinx.serialization.Serializable

@Serializable object HomeRoute
@Serializable object KitapListeRoute
@Serializable data class KitapDetayRoute(val kitapId: String)
@Serializable object ZikirMatikRoute
@Serializable object ZikirIstatistikRoute
@Serializable object ZikirGecmisRoute
@Serializable object OzelZikirRoute
@Serializable object DualarRoute
@Serializable object NamazVakitleriRoute
@Serializable object KibleRoute
@Serializable object EsmaUlHusnaRoute
@Serializable object SilsileRoute
@Serializable object HicriTakvimRoute
@Serializable object IlahilerRoute
@Serializable object AmelDefteriRoute
@Serializable object AyarlarRoute

// YENİ ROTALAR — Bölüm 2 & 3
@Serializable object HasenatBahcesiRoute
@Serializable object MoodDuaRoute
@Serializable object RamazanRoute
@Serializable object ManeviGunlukRoute
@Serializable object QuizMenuRoute
@Serializable data class QuizRoute(val kategori: String)
@Serializable object GunlukPlanRoute
@Serializable object SiyerRoute
@Serializable object ZekatRoute
@Serializable object KazaNamaziRoute
@Serializable object VirdRehberRoute
@Serializable object NamazFocusRoute
@Serializable object QuranEkranRoute
@Serializable object OnboardingRoute
@Serializable object YedeklemeRoute
@Serializable object PaylasimKartlariRoute

// Bottom bar destinasyonlar
val bottomBarRoutes = setOf(
    HomeRoute::class.qualifiedName,
    KitapListeRoute::class.qualifiedName,
    ZikirMatikRoute::class.qualifiedName,
    DualarRoute::class.qualifiedName
)

fun androidx.navigation.NavDestination.shouldShowBottomBar(): Boolean {
    val routeName = route ?: return false
    return bottomBarRoutes.any { routeName.startsWith(it ?: "") }
}
