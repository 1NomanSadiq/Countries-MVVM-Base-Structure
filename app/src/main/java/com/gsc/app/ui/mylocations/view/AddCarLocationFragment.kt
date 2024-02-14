package com.gsc.app.ui.mylocations.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gsc.app.databinding.FragmentAddCarLocationBinding
import com.gsc.app.databinding.FragmentMapBinding
import com.gsc.app.ui.base.BaseFragment
import com.gsc.app.ui.home.state.HomeNavigationState
import com.gsc.app.ui.home.state.HomeUiState
import com.gsc.app.ui.home.viewmodel.HomeViewModel
import com.gsc.app.utils.extensions.common.dialog
import com.gsc.app.utils.extensions.common.launchAndRepeatWithViewLifecycle
import com.gsc.app.utils.location.Locations.requestLocationUpdates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCarLocationFragment : BaseFragment<FragmentAddCarLocationBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentAddCarLocationBinding =
        FragmentAddCarLocationBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializations()
        collectFlows()
    }

    private fun initializations() {
        binding.buttonAdd.setOnClickListener {
            dialog("You clicked add button").show()
        }
        binding.actionBar.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction().add(binding.flMap.id, mapFragment).commit()
        mapFragment.getMapAsync { map ->
            map.isBuildingsEnabled = true

            val marker = MarkerOptions()
                .position(LatLng(0.0, 0.0))
            var shouldUpdate = true
            requestLocationUpdates { result ->
                if (shouldUpdate) {
                    val latLng = LatLng(
                        result.lastLocation?.latitude ?: 0.0,
                        result.lastLocation?.longitude ?: 0.0
                    )
                    marker.position(latLng)
                    map.addMarker(marker)
                    val location = CameraUpdateFactory.newLatLngZoom(latLng, 14f)
                    map.animateCamera(location)
                    shouldUpdate = false
                }
            }
        }
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
//        findNavController().navigate(MapFragmentDirections.toCountryDetails())
    }
}