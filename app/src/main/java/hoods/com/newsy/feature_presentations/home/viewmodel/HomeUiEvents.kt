package hoods.com.newsy.feature_presentations.home.viewmodel

import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.utils.ArticleCategory

sealed class HomeUiEvents{
    object  ViewMoreClicked : HomeUiEvents()
    data class  ArticleClicked(val url :String):HomeUiEvents()
    data class  CategoryChange(val category: ArticleCategory):HomeUiEvents()
    data class  PreferencePanelToggle(val isOpen : Boolean):HomeUiEvents()
    data class OnHeadlineFavouriteChange(val article: NewsyArticle):HomeUiEvents()
}
