package com.gsc.app.ui.countries.state

import com.gsc.app.network.model.response.countries.Country


sealed class CountriesUiState {
    data object Idle : CountriesUiState()
    data class Loading(val message: String) : CountriesUiState()
    data class ShowCountries(val items: List<Country>) : CountriesUiState()
    data class Error(val message: String) : CountriesUiState()
}