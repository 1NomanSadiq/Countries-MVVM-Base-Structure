package nom.mvvm.structure.ui.countries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nom.mvvm.structure.data.database.providerfacility.Country
import nom.mvvm.structure.network.repository.CountriesRepo
import nom.mvvm.structure.ui.countries.state.CountriesNavigationState
import nom.mvvm.structure.ui.countries.state.CountriesUiState
import nom.mvvm.structure.utils.extensions.common.getData
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo
) :
    ViewModel() {
    private val _uiState: MutableStateFlow<CountriesUiState> =
        MutableStateFlow(CountriesUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _selectedItem: MutableSharedFlow<Country?> = MutableStateFlow(null)
    val selectedItem = _selectedItem.asSharedFlow()


    private val _navigationState: MutableSharedFlow<CountriesNavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            _uiState.emit(CountriesUiState.Loading("Loading countries"))
            countriesRepo.getAllCountries().getData {
                onSuccess = { _uiState.emit(CountriesUiState.ShowCountries(it)) }
                onError = { _uiState.emit(CountriesUiState.Error(it)) }
            }
            _uiState.emit(CountriesUiState.Idle)
        }
    }

    fun onCountryClicked(item: Country) {
        viewModelScope.launch {
            _selectedItem.emit(item)
            _navigationState.emit(
                CountriesNavigationState.MoveToCountriesDetailFragment(
                    item
                )
            )
        }
    }

}
