package com.gsc.app.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsc.app.network.model.response.countries.Country
import com.gsc.app.network.repository.CountriesRepo
import com.gsc.app.ui.home.state.HomeNavigationState
import com.gsc.app.ui.home.state.HomeUiState
import com.gsc.app.utils.extensions.common.getData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo
) :
    ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _selectedItem: MutableSharedFlow<Country?> = MutableStateFlow(null)
    val selectedItem = _selectedItem.asSharedFlow()


    private val _navigationState: MutableSharedFlow<HomeNavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            _uiState.emit(HomeUiState.Loading("Loading countries"))
            countriesRepo.getAllCountries().getData {
                onSuccess = { _uiState.emit(HomeUiState.ShowMap(it)) }
                onError = { _uiState.emit(HomeUiState.Error(it)) }
            }
            _uiState.emit(HomeUiState.Idle)
        }
    }

    fun onMyLocationsClick() {
        viewModelScope.launch {
            _navigationState.emit(
                HomeNavigationState.MoveToMyLocationActivity
            )
        }
    }

}
