package com.gsc.app.ui.home.view

import android.view.LayoutInflater
import com.gsc.app.databinding.ActivityHomeBinding
import com.gsc.app.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityHomeBinding =
        ActivityHomeBinding.inflate(inflater)
}