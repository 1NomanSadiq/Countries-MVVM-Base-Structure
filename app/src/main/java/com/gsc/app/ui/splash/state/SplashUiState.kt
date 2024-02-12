package com.gsc.app.ui.splash.state


sealed class SplashUiState {
    data object Idle: SplashUiState()
    data object Loading : SplashUiState()
    data class Success(val data: Any?) : SplashUiState()
    data class Error(val message: String) : SplashUiState()
}