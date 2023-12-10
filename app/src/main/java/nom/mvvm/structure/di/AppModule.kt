package nom.mvvm.structure.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nom.mvvm.structure.data.DataStorePreference
import nom.mvvm.structure.data.DataStorePreferenceRepository
import nom.mvvm.structure.utils.dispatchers.DispatchersProviders
import nom.mvvm.structure.utils.dispatchers.DispatchersProvidersImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): DataStorePreferenceRepository =
        DataStorePreference(context)


    @Provides
    fun provideDispatchersProvider(): DispatchersProviders {
        return DispatchersProvidersImpl
    }
}
