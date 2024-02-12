package com.gsc.app.ui.countries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.gsc.app.databinding.FragmentCountriesBinding
import com.gsc.app.ui.base.BaseFragment
import com.gsc.app.ui.countries.state.CountriesNavigationState
import com.gsc.app.ui.countries.state.CountriesUiState
import com.gsc.app.ui.countries.viewmodel.CountriesViewModel
import com.gsc.app.ui.home.view.CountriesFragmentDirections
import com.gsc.app.utils.extensions.common.dialog
import com.gsc.app.utils.extensions.common.launchAndRepeatWithViewLifecycle
import com.gsc.app.utils.extensions.views.attach

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