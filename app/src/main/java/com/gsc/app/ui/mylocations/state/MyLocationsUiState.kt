package com.gsc.app.ui.mylocations.state

import com.gsc.app.network.model.response.countries.Country


sealed class MyLocationsUiState {
    data object Idle : MyLocationsUiState()
    data class Loading(val message: String) : MyLocationsUiState()
    data class ShowLocations(val locations: List<Country>) : MyLocationsUiState()
    data class Error(val message: String) : MyLocationsUiState()
}