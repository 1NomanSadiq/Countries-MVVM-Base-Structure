package com.gsc.app.ui.countries.view

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import com.gsc.app.databinding.ActivityCountriesBinding
import com.gsc.app.ui.base.BaseActivity

@AndroidEntryPoint
class CountriesActivity : BaseActivity<ActivityCountriesBinding>() {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityCountriesBinding =
        ActivityCountriesBinding.inflate(inflater)
}