package com.gsc.app.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.ui.setupWithNavController
import com.gsc.app.R
import com.gsc.app.databinding.ActivityHomeBinding
import com.gsc.app.ui.base.BaseActivity
import com.gsc.app.utils.extensions.common.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val navController by lazy { getNavController(R.id.container) }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityHomeBinding =
        ActivityHomeBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.locationsButton.setOnClickListener {
            navController.navigate(R.id.my_locations_activity)
        }
    }
}