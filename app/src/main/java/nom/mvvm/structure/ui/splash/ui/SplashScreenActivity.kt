package nom.mvvm.structure.ui.splash.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import nom.mvvm.structure.databinding.FragmentSplashScreenBinding

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    val items = listOf("سریا", "پائپ", "چادر چورس", "چادر گول ٹکی", "چادر رِنگ", "ہچ")
    private val binding by lazy { FragmentSplashScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.dropdown
    }
}