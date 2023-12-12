package nom.mvvm.structure.ui.countries.state

import nom.mvvm.structure.data.database.providerfacility.Country


sealed class CountriesNavigationState {
    data class MoveToCountriesDetailFragment(val item: Country) : CountriesNavigationState()
}