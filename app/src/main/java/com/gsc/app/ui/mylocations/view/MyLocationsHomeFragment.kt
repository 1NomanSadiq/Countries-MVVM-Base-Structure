package com.gsc.app.ui.mylocations.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.SupportMapFragment
import com.gsc.app.databinding.FragmentMyLocationsHomeBinding
import com.gsc.app.ui.base.BaseFragment
import com.gsc.app.ui.mylocations.state.MyLocationsNavigationState
import com.gsc.app.ui.mylocations.state.MyLocationsUiState
import com.gsc.app.ui.mylocations.viewmodel.MyLocationsViewModel
import com.gsc.app.utils.extensions.common.dialog
import com.gsc.app.utils.extensions.common.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyLocationsHomeFragment : BaseFragment<FragmentMyLocationsHomeBinding>() {

    private val viewModel: MyLocationsViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentMyLocationsHomeBinding =
        FragmentMyLocationsHomeBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializations()
        collectFlows()
    }

    private fun initializations() {
        binding.actionBar.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun collectFlows() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { uiState.collect { handleUiState(it) } }
            launch { navigationState.collect { handleNavigationState(it) } }
        }

    }

    private fun handleUiState(it: MyLocationsUiState) {
//        binding.progressBar.isVisible = it is HomeUiState.Loading
        when (it) {
            is MyLocationsUiState.Error -> dialog(it.message).show()
            is MyLocationsUiState.Idle -> Unit
            is MyLocationsUiState.Loading -> Unit
            is MyLocationsUiState.ShowLocations -> Unit
        }
    }

    private fun handleNavigationState(state: MyLocationsNavigationState) = when (state) {
        is MyLocationsNavigationState.MoveToMyLocationDirections -> moveToMyLocationDirections(state.locationId)
    }

    private fun moveToMyLocationDirections(locationId: Int) {
        findNavController().navigate(
            MyLocationsHomeFragmentDirections.toMyLocationDirections(locationId)
        )
    }
}