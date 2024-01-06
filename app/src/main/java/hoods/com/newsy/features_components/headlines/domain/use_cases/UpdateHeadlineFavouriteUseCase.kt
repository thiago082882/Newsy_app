package hoods.com.newsy.features_components.headlines.domain.use_cases

import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headlines.domain.repository.HeadlineRepository

class UpdateHeadlineFavouriteUseCase(
    val repository: HeadlineRepository
){
    suspend operator fun invoke(article : NewsyArticle) = repository.updateFavoriteArticle(article)
}

