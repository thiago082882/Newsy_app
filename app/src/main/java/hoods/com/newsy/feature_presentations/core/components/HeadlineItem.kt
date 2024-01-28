package hoods.com.newsy.feature_presentations.core.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hoods.com.newsy.R
import hoods.com.newsy.feature_presentations.core.ui.theme.NewsyTheme
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.utils.Utils
import kotlinx.coroutines.delay

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlineItem(

    articles: List<NewsyArticle>,
    articleCount: Int,
    onCardClick: (NewsyArticle) -> Unit,
    onViewMoreClick: () -> Unit,
    onFavouriteChange: (NewsyArticle) -> Unit,
) {
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }

    var pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            articleCount

        })

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (isDragged.not()) {
            isAutoScrolling = false

        } else {
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < articleCount - 1) currentPage + 1 else 0
                scrollToPage(target)
            }

        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(defaultPadding),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            pageSpacing = itemSpacing
        ) { page ->
            if (isAutoScrolling) {
                AnimatedContent(
                    targetState = page, label = ""
                ) { index ->

                    HeadlineCard(
                        modifier = Modifier,
                        articles = articles[index],
                        onCardClick = onCardClick,
                        onFavouriteChange = onFavouriteChange
                    )
                }
            }else {
                HeadlineCard(
                    modifier = Modifier,
                    articles = articles[page],
                    onCardClick = onCardClick,
                    onFavouriteChange = onFavouriteChange
                )
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        TextButton(onClick = onViewMoreClick, modifier = Modifier.align(Alignment.End)){
            Text(text = "View more")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineCard(
    modifier: Modifier = Modifier,
    articles: NewsyArticle,
    onCardClick: (NewsyArticle) -> Unit,
    onFavouriteChange: (NewsyArticle) -> Unit
) {

    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data(articles.urlToImage)
        .crossfade(true)
        .build()

    val favouriteIcon = if (articles.favourite) Icons.Default.BookmarkAdded
    else Icons.Default.Bookmarks
    Card(onClick = { onCardClick(articles) }) {

        Column {
            AsyncImage(
                model = imgRequest,
                placeholder = painterResource(R.drawable.ideogram_2_),
                error = painterResource(id = R.drawable.ideogram_2_),
                contentScale = ContentScale.Crop,
                contentDescription = "news image",
                modifier = Modifier.height(150.dp)
            )
            Text(
                text = articles.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(itemSpacing)
            )
            Row {
                Column(
                    modifier = Modifier.padding(itemPadding)
                ) {
                    Text(
                        text = articles.source,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = Utils.formatPublishedAtDate(articles.publishedAt),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                IconButton(onClick = {
                    onFavouriteChange(articles)
                }) {

                    Icon(imageVector = favouriteIcon, contentDescription = "favourite")

                }
            }

        }

    }

}

@Preview(showSystemUi =true  )
@Composable
fun PrevHeadlineItem() {
    val article = listOf(
        NewsyArticle(
            author = "Rob Tornoe,Vinny Vella,Nick Vadala",
            content = "",
            description = "Authorities have sent an alert to residents is East Nantmeal",
            publishedAt = "2020-09-12T04:18:45Z",
            source = "The Philadelphia Inquirer",
            title = "Danelo Cavalcante Prison Escape",
            url = "https://www.linkedin.com/feed/",
            urlToImage = "https://media.licdn.com/dms/image/D4D03AQELgn_8pwwReQ/profile-displayphoto-shrink_100_100/0/1677420027121?e=1712188800&v=beta&t=FWW-OZKsAOSKI_7Uiv19UKD9GeX4GEXgZ6voCvB_xlk",
            id = 0,
            favourite= false,
            category = "Sports",
            page = 0

        ),
        NewsyArticle(
            author = "Rob Tornoe,Vinny Vella,Nick Vadala",
            content = "",
            description = "Authorities have sent an alert to residents is East Nantmeal",
            publishedAt = "2020-09-12T04:18:45Z",
            source = "The Philadelphia Inquirer",
            title = "Danelo Cavalcante Prison Escape",
            url = "https://www.linkedin.com/feed/",
            urlToImage = "https://media.licdn.com/dms/image/D4D03AQELgn_8pwwReQ/profile-displayphoto-shrink_100_100/0/1677420027121?e=1712188800&v=beta&t=FWW-OZKsAOSKI_7Uiv19UKD9GeX4GEXgZ6voCvB_xlk",
            id = 1,
            favourite= false,
            category = "Sports",
            page = 1

        ),
    )
    NewsyTheme {
        HeadlineItem(
            articles =  article,
            articleCount =2 ,
            onCardClick = {},
            onViewMoreClick = { /*TODO*/ },
            onFavouriteChange = {}
        )
    }
    
}