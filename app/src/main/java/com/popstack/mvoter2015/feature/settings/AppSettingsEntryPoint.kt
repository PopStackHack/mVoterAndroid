package com.popstack.mvoter2015.feature.settings

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface AppSettingsEntryPoint {
  fun appSettings(): AppSettings
}