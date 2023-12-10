package nom.mvvm.structure.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nom.mvvm.structure.data.SharedPreference
import nom.mvvm.structure.data.SharedPreferenceRepository
import nom.mvvm.structure.utils.dispatchers.DispatchersProviders
import nom.mvvm.structure.utils.dispatchers.DispatchersProvidersImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferenceRepository =
        SharedPreference(context)


    @Provides
    fun provideDispatchersProvider(): DispatchersProviders {
        return DispatchersProvidersImpl
    }
}
