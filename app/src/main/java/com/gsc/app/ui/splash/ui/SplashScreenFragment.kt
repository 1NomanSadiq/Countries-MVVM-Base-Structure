package com.gsc.app.ui.splash.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.gsc.app.databinding.FragmentSplashScreenBinding
import com.gsc.app.ui.base.BaseFragment
import com.gsc.app.ui.splash.state.SplashNavigationState
import com.gsc.app.ui.splash.state.SplashUiState
import com.gsc.app.ui.splash.viewmodel.SplashScreenViewModel
import com.gsc.app.utils.extensions.common.dialog
import com.gsc.app.utils.extensions.common.launchAndRepeatWithViewLifecycle

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentSplashScreenBinding =
        FragmentSplashScreenBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }


    private fun observeViewModel() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { uiState.collect { handleUiState(it) } }
            launch { navigationState.collect { handleNavigationState(it) } }
        }

    }

    private fun handleUiState(it: SplashUiState) {
        when (it) {
            is SplashUiState.Error -> {
                dialog(it.message).show()
            }

            else -> {}
        }
    }

    private fun handleNavigationState(state: SplashNavigationState) = when (state) {
        is SplashNavigationState.MoveToCountriesScreen -> moveToCountries()
    }

    private fun moveToCountries() {
        findNavController().navigate(SplashScreenFragmentDirections.toCountriesActivity())
            .also { finishActivity() }
    }
}