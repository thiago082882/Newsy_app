package hoods.com.newsy.feature_presentations.setting.viewmodel

sealed class SettingsEvents {
    data class LanguageChange(val languageIndex: Int) : SettingsEvents()
    data class CountryChange(val countryIndex: Int) : SettingsEvents()
    object SaveSetting : SettingsEvents()
}