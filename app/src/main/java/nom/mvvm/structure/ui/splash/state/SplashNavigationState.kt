package nom.mvvm.structure.ui.splash.state

sealed class SplashNavigationState {
    data object MoveToCountriesScreen : SplashNavigationState()
}