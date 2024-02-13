package com.gsc.app.ui.splash.state

sealed class SplashNavigationState {
    data object MoveToHomeScreen : SplashNavigationState()
}