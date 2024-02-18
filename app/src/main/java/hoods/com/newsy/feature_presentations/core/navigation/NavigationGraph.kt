package hoods.com.newsy.feature_presentations.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hoods.com.newsy.feature_presentations.home.HomeScreen

@Composable
fun NewsyNavigationGraph(
    navController: NavHostController = rememberNavController(),
    navActions: NewsyNavigationActions,
    openDrawer: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = UiScreen.HomeScreen().route
    ) {
        composable(route = UiScreen.HomeScreen().route) {
            HomeScreen(
                onViewMoreClick = navActions.navigateToHeadlineScreen,
                onHeadlineItemClick = {
                    navActions.navigateToDetail(
                        it, UiScreen.HeadlineScreen().route
                    )
                },
                onDiscoverItemClick = {
                    navActions.navigateToDetail(
                        it, UiScreen.DiscoverScreen().route
                    )
                },
                onSearchClick = {
                    navActions.navigateToSearch()
                },
                openDrawer = openDrawer
            )
        }
    }

}