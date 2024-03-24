package hoods.com.newsy.feature_presentations.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hoods.com.newsy.R
import hoods.com.newsy.feature_presentations.core.navigation.UiScreen

@Composable
fun NewysAppDrawerContent(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToFavouriteScreen: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(modifier) {
        NewsyLogo(
            modifier = Modifier
                .padding(
                    horizontal = 28.dp,
                    vertical = 24.dp
                )
        )

        NavigationDrawerItem(
            label = { Text("Home") },
            icon = { Icon(imageVector = Icons.Filled.Home, null) },
            selected = currentRoute == UiScreen.HomeScreen().route,
            onClick = { navigateToHome();closeDrawer() },
            modifier = Modifier.padding(
                NavigationDrawerItemDefaults.ItemPadding
            )
        )

        NavigationDrawerItem(
            label = { Text("Favourite") },
            icon = { Icon(imageVector = Icons.Filled.Bookmark, null) },
            selected = currentRoute == UiScreen.FavouriteScreen().route,
            onClick = { navigateToFavouriteScreen();closeDrawer() },
            modifier = Modifier.padding(
                NavigationDrawerItemDefaults.ItemPadding
            )
        )

        NavigationDrawerItem(
            label = { Text("Settings") },
            icon = { Icon(imageVector = Icons.Filled.Settings, null) },
            selected = currentRoute == UiScreen.SettingsScreen().route,
            onClick = { navigateToSetting();closeDrawer() },
            modifier = Modifier.padding(
                NavigationDrawerItemDefaults.ItemPadding
            )
        )


    }


}

@Composable
fun NewsyLogo(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_newsy_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(defaultSpacing))
        Icon(
            painterResource(R.drawable.ic_newsy_water_mark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}





