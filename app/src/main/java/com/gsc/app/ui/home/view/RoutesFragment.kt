package com.gsc.app.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gsc.app.databinding.FragmentCountriesBinding
import com.gsc.app.databinding.FragmentRoutesBinding
import com.gsc.app.ui.base.BaseFragment
import com.gsc.app.ui.home.state.HomeNavigationState
import com.gsc.app.ui.home.state.HomeUiState
import com.gsc.app.ui.home.viewmodel.HomeViewModel
import com.gsc.app.utils.extensions.common.dialog
import com.gsc.app.utils.extensions.common.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoutesFragment : BaseFragment<FragmentRoutesBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRoutesBinding =
        FragmentRoutesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        collectFlows()
    }

    private fun initViews() {

    }


    private fun collectFlows() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { uiState.collect { handleUiState(it) } }
            launch { navigationState.collect { handleNavigationState(it) } }
        }

    }

    private fun handleUiState(it: HomeUiState) {
//        binding.progressBar.isVisible = it is HomeUiState.Loading
        when (it) {
            is HomeUiState.Error -> dialog(it.message).show()
            is HomeUiState.Idle -> Unit
            is HomeUiState.Loading -> Unit
            is HomeUiState.ShowMap -> Unit
        }
    }

    private fun handleNavigationState(state: HomeNavigationState) = when (state) {
        is HomeNavigationState.MoveToMyLocationActivity -> moveToMyLocationActivity()
    }

    private fun moveToMyLocationActivity() {
        findNavController().navigate(CountriesFragmentDirections.toCountryDetails())
    }
}