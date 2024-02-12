package com.gsc.app.ui.splash.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import com.gsc.app.databinding.ActivitySplashScreenBinding
import com.gsc.app.ui.base.BaseActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashScreenBinding =
        ActivitySplashScreenBinding.inflate(inflater)
}