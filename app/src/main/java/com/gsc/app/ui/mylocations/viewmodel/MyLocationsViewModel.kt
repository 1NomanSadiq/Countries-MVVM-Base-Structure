package com.gsc.app.ui.mylocations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsc.app.network.model.response.countries.Country
import com.gsc.app.network.repository.CountriesRepo
import com.gsc.app.ui.mylocations.state.MyLocationsNavigationState
import com.gsc.app.ui.mylocations.state.MyLocationsUiState
import com.gsc.app.utils.extensions.common.getData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLocationsViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo
) :
    ViewModel() {
    private val _uiState: MutableStateFlow<MyLocationsUiState> =
        MutableStateFlow(MyLocationsUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _selectedItem: MutableSharedFlow<Country?> = MutableStateFlow(null)
    val selectedItem = _selectedItem.asSharedFlow()

    private val _navigationState: MutableSharedFlow<MyLocationsNavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            _uiState.emit(MyLocationsUiState.Loading("Loading countries"))
            countriesRepo.getAllCountries().getData {
                onSuccess = { _uiState.emit(MyLocationsUiState.ShowLocations(it)) }
                onError = { _uiState.emit(MyLocationsUiState.Error(it)) }
            }
            _uiState.emit(MyLocationsUiState.Idle)
        }
    }

    fun onLocationClick(locationId: Int) {
        viewModelScope.launch {
            _navigationState.emit(
                MyLocationsNavigationState.MoveToMyLocationDirections(locationId)
            )
        }
    }

}
