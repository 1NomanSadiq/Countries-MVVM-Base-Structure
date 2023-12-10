package nom.mvvm.structure.ui.countries.state

import nom.mvvm.structure.network.model.response.countries.Country

sealed class CountriesNavigationState {
    data class MoveToCountriesDetailFragment(val item: Country) : CountriesNavigationState()
}