package hoods.com.newsy.features_components.discover.domain.use_cases

import androidx.paging.PagingData
import hoods.com.newsy.features_components.core.domain.models.Country
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.discover.domain.repository.DiscoveryRepository
import kotlinx.coroutines.flow.Flow


class FetchDiscoverArticleUseCase(
    private  val repository : DiscoveryRepository
) {
    operator fun invoke(
        category :String,
        language : String,
        country: String
    ): Flow<PagingData<NewsyArticle>> =
        repository.fetchDiscoverArticles(category, country, language)
}