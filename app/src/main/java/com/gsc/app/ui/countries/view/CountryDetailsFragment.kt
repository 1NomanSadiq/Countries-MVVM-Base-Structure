package com.gsc.app.ui.countries.view

import android.os.Bundle
import android.text.util.Linkify.WEB_URLS
import android.view.LayoutInflater
import android.view.View
import androidx.core.text.util.LinkifyCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import coil.load
import coil.size.Scale
import com.gsc.app.R
import com.gsc.app.databinding.FragmentCountryDetailsBinding
import com.gsc.app.databinding.LayoutTitleAndValueBinding
import com.gsc.app.network.model.response.countries.Country
import com.gsc.app.ui.base.BaseFragment
import com.gsc.app.ui.countries.state.CountriesUiState
import com.gsc.app.ui.countries.viewmodel.CountriesViewModel
import com.gsc.app.utils.extensions.common.dialog
import com.gsc.app.utils.extensions.common.ifNullOrBlank
import com.gsc.app.utils.extensions.common.launchAndRepeatWithViewLifecycle
import com.gsc.app.utils.extensions.views.setTextOrGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailsFragment : BaseFragment<FragmentCountryDetailsBinding>() {

    private val viewModel: CountriesViewModel by activityViewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentCountryDetailsBinding =
        FragmentCountryDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectFlows()
    }


    private fun collectFlows() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { uiState.collect { handleUiState(it) } }
            launch { selectedItem.collectLatest { showCountryDetails(it!!) } }
        }

    }


    private fun showCountryDetails(item: Country) {
        with(binding) {
            ivCoatOfArm.load(item.coatOfArms?.png.ifNullOrBlank { "" }) {
                placeholder(R.drawable.placeholder)
                scale(Scale.FIT)
            }
            flagAlt.setTextOrGone(item.flags?.alt)
            coatOfArmAlt.setTextOrGone(item.coatOfArms?.alt)
            name.setTitleAndValueOrGone("Name:", item.nameStr)
            capital.setTitleAndValueOrGone("Capital:", item.capitals)
            subregion.setTitleAndValueOrGone("SubRegion:", item.subregion)
            continents.setTitleAndValueOrGone("Continents", item.continent)
            population.setTitleAndValueOrGone("Population:", item.population.toString())
            area.setTitleAndValueOrGone("Area:", item.area.toString())
            idd.setTitleAndValueOrGone("Country Code:", item.countryCode)
            demonyms.setTitleAndValueOrGone("Demonym:", item.demonym)
            timezones.setTitleAndValueOrGone("Timezones:", item.timezone)
            maps.setTitleAndValueOrGone("Maps:", item.map)
            LinkifyCompat.addLinks(maps.tvValue, WEB_URLS)
            latlng.setTitleAndValueOrGone("Latitude and Longitude:", item.latlngStr)
        }
    }

    private fun LayoutTitleAndValueBinding.setTitleAndValueOrGone(title: String?, value: String?) {
        tvTitle.setTextOrGone(title)
        tvValue.setTextOrGone(value)
        root.isVisible = tvTitle.isVisible && tvValue.isVisible
    }

    private fun handleUiState(it: CountriesUiState) {
        when (it) {
            is CountriesUiState.Error -> {
                dialog(it.message).show()
            }

            else -> {}
        }
    }
}