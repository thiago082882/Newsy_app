package hoods.com.newsy.feature_presentations.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.newsy.features_components.headlines.domain.use_cases.HeadlineUseCases
import hoods.com.newsy.utils.Utils
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases
) : ViewModel() {

    var homeState by mutableStateOf(HomeState())

    init {
        loadArticles()
    }

    private fun loadArticles() {
        homeState = homeState.copy(
            headlineArticles =
            headlineUseCases.fetchHeadlineArticleUseCase(
                category = homeState.selectedHeadlineCategory.category,
                countryCode = Utils.countryCodeList[0].code,
                languageCode = Utils.languageCodeList[0].code
            ).cachedIn(viewModelScope)

        )
    }

    fun onHomeUIEvents(homeUiEvents: HomeUiEvents){
        when(homeUiEvents){
            is HomeUiEvents.ViewMoreClicked -> {}
            is HomeUiEvents.ArticleClicked -> {}
            is HomeUiEvents.CategoryChange-> {}
            is HomeUiEvents.PreferencePanelToggle -> {}
            is HomeUiEvents.OnHeadlineFavouriteChange -> {
                viewModelScope.launch {
                    val isFavourite = homeUiEvents.article.favourite
                    val update = homeUiEvents.article.copy(
                        favourite =  !isFavourite
                    )
                    headlineUseCases.updateHeadlineFavouriteUseCase(
                        update
                    )
                }
            }
        }
    }

}