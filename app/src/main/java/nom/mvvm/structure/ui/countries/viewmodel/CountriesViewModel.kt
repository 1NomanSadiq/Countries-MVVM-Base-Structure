package nom.mvvm.structure.ui.countries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nom.mvvm.structure.network.model.response.countries.Country
import nom.mvvm.structure.network.repository.CountriesRepo
import nom.mvvm.structure.ui.countries.state.CountriesNavigationState
import nom.mvvm.structure.ui.countries.state.CountriesUiState
import nom.mvvm.structure.utils.Result
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo
) :
    ViewModel() {
    private val _uiState: MutableStateFlow<CountriesUiState> =
        MutableStateFlow(CountriesUiState.Idle)
    val uiState = _uiState.asSharedFlow()

    val _selectedItem: MutableStateFlow<Country?> = MutableStateFlow(null)
    val selectedItem = _selectedItem.asStateFlow()


    private val _navigationState: MutableSharedFlow<CountriesNavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchCountries()
        }
    }

    private suspend fun fetchCountries() {
        viewModelScope.launch {
            _uiState.emit(CountriesUiState.Loading("Loading countries"))
            countriesRepo.getAllCountries().collectLatest {
                _uiState.emit(
                    when (it.status) {
                        Result.Status.SUCCESS -> {
                            CountriesUiState.ShowCountries(it.data!!)
                        }

                        Result.Status.ERROR -> {
                            CountriesUiState.Error(it.message)
                        }
                    }
                )
            }
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
