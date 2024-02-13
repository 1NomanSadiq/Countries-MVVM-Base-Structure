package com.gsc.app.ui.countries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.size.Scale
import com.gsc.app.R
import com.gsc.app.databinding.ItemCountryBinding
import com.gsc.app.network.model.response.countries.Country
import com.gsc.app.utils.extensions.common.ifNullOrBlank
import com.gsc.app.utils.extensions.views.setTextOrGone
import com.gsc.app.utils.misc.BaseAdapter


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
            ivFlag.load(item.flags?.png.ifNullOrBlank { "" }) {
                placeholder(R.drawable.ic_circle_shape)
                scale(Scale.FILL)
            }
        }
    }
}

