package nom.mvvm.structure.ui.countries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import nom.mvvm.structure.R
import nom.mvvm.structure.databinding.ItemCountryBinding
import nom.mvvm.structure.network.model.response.countries.Country
import nom.mvvm.structure.utils.extensions.adapter.BaseAdapter
import nom.mvvm.structure.utils.extensions.common.ifNullOrBlank
import nom.mvvm.structure.utils.extensions.views.load
import nom.mvvm.structure.utils.extensions.views.setTextOrGone


class CountriesAdapter : BaseAdapter<Country, ItemCountryBinding>() {
    override fun inflateLayout(inflater: LayoutInflater, parent: ViewGroup) =
        ItemCountryBinding.inflate(inflater, parent, false)

    override fun bind(binding: ItemCountryBinding, item: Country) {
        with(binding) {
            tvCountryName.setTextOrGone(item.name?.common)
            tvCapital.setTextOrGone(item.capital?.firstOrNull())
            tvContinent.setTextOrGone(item.continents?.firstOrNull())
            tvPopulation.setTextOrGone(item.population.toString())
            tvTimezone.setTextOrGone(item.timezones?.firstOrNull())
            ivFlag.load(item.flags?.png.ifNullOrBlank { "" }, R.drawable.ic_circle_shape)
        }
    }
}


