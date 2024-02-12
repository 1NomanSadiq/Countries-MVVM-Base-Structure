package com.gsc.app.ui.countries.state

import com.gsc.app.network.model.response.countries.Country

sealed class CountriesNavigationState {
    data class MoveToCountriesDetailFragment(val item: Country) : CountriesNavigationState()
}