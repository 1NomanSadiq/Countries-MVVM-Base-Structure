package com.gsc.app.ui.mylocations.view

import android.view.LayoutInflater
import com.gsc.app.databinding.ActivityMyLocationsBinding
import com.gsc.app.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyLocationsActivity : BaseActivity<ActivityMyLocationsBinding>() {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMyLocationsBinding =
        ActivityMyLocationsBinding.inflate(inflater)
}