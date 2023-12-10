package nom.mvvm.structure.ui.countries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import nom.mvvm.structure.R
import nom.mvvm.structure.databinding.ItemCountryBinding
import nom.mvvm.structure.network.model.response.countries.Country
import nom.mvvm.structure.utils.extensions.adapter.BaseAdapter
import nom.mvvm.structure.utils.extensions.views.load
import nom.mvvm.structure.utils.extensions.views.setTextOrGone


class CountriesAdapter : BaseAdapter<Country, ItemCountryBinding>() {
    override fun inflateLayout(inflater: LayoutInflater, parent: ViewGroup) =
        ItemCountryBinding.inflate(inflater, parent, false)

    override fun bind(binding: ItemCountryBinding, item: Country) {
        with(binding) {
            tvCountryName.text = item.name.common
            tvCapital.setTextOrGone(item.capital.firstOrNull())
            tvContinent.text = item.continents.first()
            tvPopulation.text = item.population.toString()
            tvTimezone.text = item.timezones.first()
            ivFlag.load(item.flags.png, R.drawable.ic_circle_shape)
        }
    }
}


