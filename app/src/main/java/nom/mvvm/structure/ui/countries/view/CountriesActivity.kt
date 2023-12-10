package nom.mvvm.structure.ui.countries.view

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import nom.mvvm.structure.databinding.ActivityCountriesBinding
import nom.mvvm.structure.ui.base.BaseActivity

@AndroidEntryPoint
class CountriesActivity : BaseActivity<ActivityCountriesBinding>() {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityCountriesBinding =
        ActivityCountriesBinding.inflate(inflater)
}