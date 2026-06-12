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
