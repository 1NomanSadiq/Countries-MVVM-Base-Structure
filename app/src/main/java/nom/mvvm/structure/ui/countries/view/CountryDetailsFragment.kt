package nom.mvvm.structure.ui.countries.view

import android.os.Bundle
import android.text.util.Linkify.WEB_URLS
import android.view.LayoutInflater
import android.view.View
import androidx.core.text.util.LinkifyCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nom.mvvm.structure.data.database.country.Country
import nom.mvvm.structure.databinding.FragmentCountryDetailsBinding
import nom.mvvm.structure.databinding.LayoutTitleAndValueBinding
import nom.mvvm.structure.ui.base.BaseFragment
import nom.mvvm.structure.ui.countries.state.CountriesUiState
import nom.mvvm.structure.ui.countries.viewmodel.CountriesViewModel
import nom.mvvm.structure.utils.extensions.common.dialog
import nom.mvvm.structure.utils.extensions.common.launchAndRepeatWithViewLifecycle
import nom.mvvm.structure.utils.extensions.common.ifNullOrBlank
import nom.mvvm.structure.utils.extensions.views.load
import nom.mvvm.structure.utils.extensions.views.setTextOrGone

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
            ivCoatOfArm.load(item.coatOfArms?.png.ifNullOrBlank { "" } , centerCrop = false)
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