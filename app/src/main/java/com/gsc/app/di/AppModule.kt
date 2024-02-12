package com.gsc.app.di

import android.content.Context
import com.gsc.app.data.DataStorePreference
import com.gsc.app.data.DataStorePreferenceRepository
import com.gsc.app.data.SharedPreference
import com.gsc.app.utils.dispatchers.DispatchersProviders
import com.gsc.app.utils.dispatchers.DispatchersProvidersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDataStorePrefs(@ApplicationContext context: Context): DataStorePreferenceRepository =
        DataStorePreference(context)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreference =
        SharedPreference(context)


    @Provides
    fun provideDispatchersProvider(): DispatchersProviders {
        return DispatchersProvidersImpl
    }
}
