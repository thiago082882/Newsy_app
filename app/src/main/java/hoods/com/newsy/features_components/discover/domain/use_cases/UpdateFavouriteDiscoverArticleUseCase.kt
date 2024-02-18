package hoods.com.newsy.features_components.discover.domain.use_cases

import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.discover.domain.repository.DiscoveryRepository

class UpdateFavouriteDiscoverArticleUseCase(
    private val repository: DiscoveryRepository
) {
    suspend operator fun invoke(article: NewsyArticle) {
        repository.updateFavouriteDiscoverCategory(article)
    }
}