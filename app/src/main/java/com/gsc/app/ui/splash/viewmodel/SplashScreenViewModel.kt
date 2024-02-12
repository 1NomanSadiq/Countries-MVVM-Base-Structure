package com.gsc.app.ui.splash.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import com.gsc.app.ui.base.BaseViewModel
import com.gsc.app.ui.splash.state.SplashNavigationState
import com.gsc.app.ui.splash.state.SplashUiState
import com.gsc.app.utils.dispatchers.DispatchersProviders
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

