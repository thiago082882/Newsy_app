package hoods.com.newsy.features_components.discover.domain.use_cases

import hoods.com.newsy.features_components.discover.domain.repository.DiscoveryRepository

class GetDiscoverCurrentCategoryUseCase (
    private  val repository : DiscoveryRepository
) {
    suspend operator fun invoke():String{
        return  repository.getDiscoverCurrentCategory()
    }
}