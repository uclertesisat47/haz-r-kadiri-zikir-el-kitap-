package com.kadiri.elkitabi.core.designsystem.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector

object KadiriIcons {
    val Home         = Icons.Filled.Home
    val HomeOutlined = Icons.Outlined.Home

    val Kitap         = Icons.Filled.MenuBook
    val KitapOutlined = Icons.Outlined.MenuBook

    val Music         = Icons.Filled.MusicNote
    val MusicOutlined = Icons.Outlined.MusicNote

    val More         = Icons.Filled.MoreHoriz
    val MoreOutlined = Icons.Outlined.MoreHoriz

    val Settings  = Icons.Filled.Settings
    val Bookmark  = Icons.Filled.Bookmark
    val BookmarkBorder = Icons.Filled.BookmarkBorder

    val Star  = Icons.Filled.Star
    val Sun   = Icons.Filled.WbSunny
    val Moon  = Icons.Filled.NightsStay
    val Dot   = Icons.Filled.FiberManualRecord
}

data class KadiriBottomNavItem(
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
