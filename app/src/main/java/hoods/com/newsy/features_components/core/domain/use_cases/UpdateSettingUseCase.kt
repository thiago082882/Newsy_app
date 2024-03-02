package hoods.com.newsy.features_components.core.domain.use_cases


import hoods.com.newsy.features_components.core.domain.models.Setting


class UpdateSettingUseCase(
    private val settingRepository: hoods.com.newsy.features_components.core.domain.repository.SettingRepository,
) {
    suspend operator fun invoke(setting: Setting) {
        settingRepository.insertSetting(setting)
    }
}