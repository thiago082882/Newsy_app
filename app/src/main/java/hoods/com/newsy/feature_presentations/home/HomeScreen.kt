package hoods.com.newsy.feature_presentations.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import hoods.com.newsy.feature_presentations.core.components.HeaderTitle
import hoods.com.newsy.feature_presentations.core.components.itemSpacing
import hoods.com.newsy.feature_presentations.home.components.HomeTopAppBar
import hoods.com.newsy.feature_presentations.home.viewmodel.HomeViewModel
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.utils.ArticleCategory
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: () -> Unit,
    openDrawer: () -> Unit,
    onSearchClick: () -> Unit

) {

    val homeState = viewModel.homeState
    val headlineArticles = homeState.headlineArticles.collectAsLazyPagingItems()
    val categories = ArticleCategory.values()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) {

            }
        },
        topBar = {
            HomeTopAppBar(openDrawer = openDrawer, onSearch = onSearchClick)
        }
    ) { innerPading ->
        LazyColumn(contentPadding = innerPading) {

        }

    }

}

private fun LazyListScope.HeadlineItems(
    headlineArticles : LazyPagingItems<NewsyArticle>,
    scope : CoroutineScope,
    snackBarHostState : SnackbarHostState,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id:Int) -> Unit,
    onFavouriteHeadlineChange : (NewsyArticle) -> Unit

){
item {
HeaderTitle(title = "Hot News", icon = Icons.Default.LocalFireDepartment)
    Spacer(modifier = Modifier.size(itemSpacing))
}
    item {

    }
}