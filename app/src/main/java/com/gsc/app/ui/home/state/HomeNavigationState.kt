package com.gsc.app.ui.home.state

sealed class HomeNavigationState {
    data object MoveToMyLocationActivity : HomeNavigationState()
}