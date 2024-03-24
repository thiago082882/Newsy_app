package hoods.com.newsy.feature_presentations.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import hoods.com.newsy.feature_presentations.core.navigation.NewsyNavigationActions
import hoods.com.newsy.feature_presentations.core.navigation.NewsyNavigationGraph

@Composable
fun NewsyApp() {
    val navController = rememberNavController()
    val navActions = remember{
        NewsyNavigationActions(navController)

    }
    NewsyNavigationGraph(
        navController=navController,
        navActions =navActions,
        openDrawer = {}
    )

}