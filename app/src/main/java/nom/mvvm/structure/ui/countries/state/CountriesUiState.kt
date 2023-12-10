package nom.mvvm.structure.ui.countries.state

import nom.mvvm.structure.network.model.response.countries.Country


sealed class CountriesUiState {
    data object Idle : CountriesUiState()
    data class Loading(val message: String) : CountriesUiState()
    data class ShowCountries(val items: List<Country>) : CountriesUiState()
    data class Error(val message: String) : CountriesUiState()
}