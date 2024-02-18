package hoods.com.newsy.features_components.discover.domain.use_cases

import hoods.com.newsy.features_components.discover.domain.repository.DiscoveryRepository

class UpdateCurrentCategoryUseCase (
    private  val repository : DiscoveryRepository
    ) {
        suspend operator fun invoke(category:String){
        repository.updateCategory(category)
    }
}