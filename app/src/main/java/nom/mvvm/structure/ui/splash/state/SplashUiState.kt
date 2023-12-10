package nom.mvvm.structure.ui.splash.state

import nom.mvvm.structure.ui.countries.state.CountriesUiState


sealed class SplashUiState {
    data object Idle: SplashUiState()
    data object Loading : SplashUiState()
    data class Success(val data: Any?) : SplashUiState()
    data class Error(val message: String) : SplashUiState()
}