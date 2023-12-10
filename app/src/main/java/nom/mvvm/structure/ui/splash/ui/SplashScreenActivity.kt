package nom.mvvm.structure.ui.splash.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import nom.mvvm.structure.databinding.ActivitySplashScreenBinding
import nom.mvvm.structure.ui.base.BaseActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashScreenBinding =
        ActivitySplashScreenBinding.inflate(inflater)
}