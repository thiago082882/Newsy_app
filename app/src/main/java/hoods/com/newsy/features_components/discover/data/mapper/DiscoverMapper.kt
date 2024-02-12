package hoods.com.newsy.features_components.discover.data.mapper

import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.discover.data.local.models.DiscoveryArticleDto

class DiscoverMapper : Mapper<DiscoveryArticleDto, NewsyArticle> {
    override fun toModel(value: DiscoveryArticleDto): NewsyArticle = value.run {
        NewsyArticle(
            id = id,
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source,
            title = title,
            url = url,
            urlToImage = urlToImage,
            favourite = favourite,
            category = category,
            page = page
        )
    }

    override fun fromModel(value: NewsyArticle): DiscoveryArticleDto = value.run {
        DiscoveryArticleDto(
            id = id,
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source,
            title = title,
            url = url,
            urlToImage = urlToImage,
            favourite = favourite,
            category = category,
            page = page
        )
    }
}