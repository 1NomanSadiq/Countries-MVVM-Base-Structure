package nom.mvvm.structure.ui.countries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nom.mvvm.structure.databinding.FragmentCountriesBinding
import nom.mvvm.structure.ui.base.BaseFragment
import nom.mvvm.structure.ui.countries.state.CountriesNavigationState
import nom.mvvm.structure.ui.countries.state.CountriesUiState
import nom.mvvm.structure.ui.countries.viewmodel.CountriesViewModel
import nom.mvvm.structure.utils.extensions.adapter.attach
import nom.mvvm.structure.utils.extensions.common.dialog
import nom.mvvm.structure.utils.extensions.common.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class CountriesFragment : BaseFragment<FragmentCountriesBinding>() {

    private val viewModel: CountriesViewModel by activityViewModels()
    private val adapter by lazy { CountriesAdapter() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentCountriesBinding =
        FragmentCountriesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        collectFlows()
    }

    private fun initViews() {
        binding.rvCountries.attach(
            adapter = adapter,
            onItemClick = { _, country ->
                viewModel.onCountryClicked(country)
            }
        )
        binding.rvCountries.adapter = adapter
    }


    private fun collectFlows() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { uiState.collect { handleUiState(it) } }
            launch { navigationState.collect { handleNavigationState(it) } }
        }

    }

    private fun handleUiState(it: CountriesUiState) {
        binding.progressBar.isVisible = it is CountriesUiState.Loading
        when (it) {
            is CountriesUiState.Error -> dialog(it.message).show()
            is CountriesUiState.Idle -> Unit
            is CountriesUiState.Loading -> Unit
            is CountriesUiState.ShowCountries -> adapter.pushData(it.items)
        }
    }

    private fun handleNavigationState(state: CountriesNavigationState) = when (state) {
        is CountriesNavigationState.MoveToCountriesDetailFragment -> moveToCountriesDetailFragment()
    }

    private fun moveToCountriesDetailFragment() {
        findNavController().navigate(CountriesFragmentDirections.toCountryDetails())
    }
}