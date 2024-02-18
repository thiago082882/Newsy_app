package hoods.com.newsy.features_components.discover.domain.use_cases

data class DiscoverUseCases(
    val fetchDiscoverArticleUseCase: FetchDiscoverArticleUseCase,
    val getDiscoverArticleUseCase: GetDiscoverCurrentCategoryUseCase,
    val updateCurrentCategoryUseCase: UpdateCurrentCategoryUseCase,
    val updateFavouriteDiscoverArticleUseCase: UpdateFavouriteDiscoverArticleUseCase
) {

}