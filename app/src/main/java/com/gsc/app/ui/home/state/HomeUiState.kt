package com.gsc.app.ui.home.state

import com.gsc.app.network.model.response.countries.Country


sealed class HomeUiState {
    data object Idle : HomeUiState()
    data class Loading(val message: String) : HomeUiState()
    data class ShowMap(val items: List<Country>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}