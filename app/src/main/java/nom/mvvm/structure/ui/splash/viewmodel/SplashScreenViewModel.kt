package nom.mvvm.structure.ui.splash.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import nom.mvvm.structure.ui.base.BaseViewModel
import nom.mvvm.structure.ui.countries.state.CountriesUiState
import nom.mvvm.structure.ui.splash.state.SplashNavigationState
import nom.mvvm.structure.ui.splash.state.SplashUiState
import nom.mvvm.structure.utils.dispatchers.DispatchersProviders
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel
@Inject
constructor(
    dispatchers: DispatchersProviders
) : BaseViewModel(dispatchers) {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<SplashNavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()


    init {
        onInitialState()
    }

    private fun onInitialState() = launchOnMainImmediate {
        _uiState.emit(SplashUiState.Loading)
        delay(2000)
        _navigationState.emit(SplashNavigationState.MoveToCountriesScreen)
    }
}

