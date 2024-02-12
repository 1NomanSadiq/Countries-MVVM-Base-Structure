package com.gsc.app.di

import com.gsc.app.network.api.ApiService
import com.gsc.app.network.repository.CountriesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun providesCountriesRepo(apiService: ApiService) =
        CountriesRepo(apiService)
}
