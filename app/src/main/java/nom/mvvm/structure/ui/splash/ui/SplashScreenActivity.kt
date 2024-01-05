package nom.mvvm.structure.ui.splash.ui

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nom.mvvm.structure.R
import nom.mvvm.structure.data.database.country.Item
import nom.mvvm.structure.databinding.FragmentSplashScreenBinding
import nom.mvvm.structure.ui.splash.viewmodel.SplashScreenViewModel
import nom.mvvm.structure.utils.extensions.views.addUniqueTextChangedListener
import nom.mvvm.structure.utils.extensions.views.hide
import nom.mvvm.structure.utils.extensions.views.show

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val viewModel: SplashScreenViewModel by viewModels()
    private val binding by lazy { FragmentSplashScreenBinding.inflate(layoutInflater) }
    private val selectedItem = MutableLiveData<Item>()
    private var position = 0
    private var positionChanged = false
    private var rate = 0
    private var isUpdating = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setVisibility()
        lifecycleScope.launch {
            viewModel.itemList.collectLatest {
                Log.d("SplashScreenActivity", "itemList: $it")
                if (it.isNotEmpty()) {
                    val itemsAdapter =
                        ArrayAdapter(
                            this@SplashScreenActivity,
                            R.layout.dropdown_item,
                            it.map { it.name })
                    binding.dropdown.setAdapter(itemsAdapter)
                    binding.dropdown.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            positionChanged = this@SplashScreenActivity.position == position
                            Log.d("SplashScreenActivity", "onItemClick: $position")
                            this@SplashScreenActivity.position = position
                            selectedItem.value = it[position]
                        }
                    if (selectedItem.value == null) {
                        selectedItem.value = it[position]
                        binding.dropdown.setText(it[position].name, false)
                    }
                }
            }

        }

        selectedItem.observe(this) {
            Log.d("SplashScreenActivity", "selectedItem: $it")
            rate = it?.ratePerKg ?: 0
            it?.let {
                isUpdating = true
                binding.etPrice.setText("")
                binding.etWeightKg.setText("")
                binding.etWeightG.setText("")
                isUpdating = false
                binding.openRate.isVisible = (rate) > 0
                binding.closeRate.isVisible = !binding.openRate.isVisible
                binding.rate1.isVisible = !binding.openRate.isVisible

                binding.closeRate.setOnClickListener {
                    binding.rate1.hide()
                    binding.closeRate.hide()
                    binding.openRate.show()
                }

                binding.openRate.setOnClickListener {
                    binding.rate1.show()
                    binding.openRate.hide()
                    binding.closeRate.show()
                }


                val coroutineScope = CoroutineScope(Dispatchers.Main)
                var insertJob: Job? = null
                isUpdating = true
                binding.etRateKg.setText(if ((rate) > 0) rate.toString() else "")
                isUpdating = false
                binding.etRateKg.addUniqueTextChangedListener { nullableQuery ->
                    nullableQuery?.let { query ->
                        if (!isUpdating) {
                            rate = query.toString().toIntOrNull() ?: 0
                            Log.d("SplashScreenActivity", "rate in listener: $rate")
                            updatePrice()
                            insertJob?.cancel()
                            insertJob = coroutineScope.launch {
                                delay(1000)
                                if (query.isNotEmpty()) {
                                    val item = it.copy(
                                        ratePerKg = query.toString().toInt()
                                    )
                                    viewModel.updateItem(item)
                                } else setVisibility()
                            }
                        }
                    }
                }

                binding.etWeightKg.addUniqueTextChangedListener {
                    if (!isUpdating)
                        updatePrice()
                }

                binding.etWeightG.addUniqueTextChangedListener {
                    if (!isUpdating)
                        updatePrice()
                }

                binding.etPrice.addUniqueTextChangedListener { nullableQuery ->
                    if (!isUpdating && rate > 0) {
                        nullableQuery?.let { query ->
                            if (query.isNotEmpty()) {
                                isUpdating = true
                                val price = query.toString().toDouble()
                                val totalWeightInKg = price / (rate)
                                val weightInKg = totalWeightInKg.toInt()
                                val weightInGrams = ((totalWeightInKg - weightInKg) * 1000).toInt()

                                if (weightInKg > 0) {
                                    binding.etWeightKg.setText(weightInKg.toString())
                                    binding.etWeightG.setText(if (weightInGrams > 0) weightInGrams.toString() else null)
                                } else {
                                    binding.etWeightKg.text = null
                                    binding.etWeightG.setText(weightInGrams.toString())
                                }
                                isUpdating = false
                            } else {
                                binding.etWeightKg.text = null
                                binding.etWeightG.text = null
                            }
                        }
                    }
                    setVisibility()
                }
            }
        }
    }

    private fun updatePrice() {
        isUpdating = true
        val totalWeightInKg =
            (if (binding.etWeightKg.text.isNullOrBlank()) 0.0 else binding.etWeightKg.text.toString()
                .toDouble())
        val totalWeightInGrams =
            totalWeightInKg * 1000 + (if (binding.etWeightG.text.isNullOrBlank()) 0.0 else binding.etWeightG.text.toString()
                .toDouble())
        val price = ((totalWeightInGrams / 1000) * (rate))
        binding.etPrice.setText(if (price > 0.0) price.toString() else null)
        isUpdating = false
    }

    private fun setVisibility() {
        val weightKg = binding.etWeightKg.text.toString().toDoubleOrNull() ?: 0.0
        val weightG = binding.etWeightG.text.toString().toDoubleOrNull() ?: 0.0
        val price = binding.etPrice.text.toString().toDoubleOrNull() ?: 0.0

        val isWeightGreaterThanZero = weightKg > 0.0 || weightG > 0.0
        val isPriceGreaterThanZero = price > 0.0

        val isValidInput = isWeightGreaterThanZero && isPriceGreaterThanZero

        binding.tvDescription.isVisible = isValidInput && rate > 0
        if (isValidInput) {
            binding.tvDescription.text = generateDescription(
                selectedItem.value?.name ?: "",
                rate,
                weightKg.toInt(),
                weightG.toInt(),
                price
            )
        }
    }

    private fun generateDescription(
        itemName: String,
        ratePerKg: Int?,
        weightKg: Int,
        weightG: Int,
        price: Double
    ): String {
        val weightDescription = when {
            weightKg > 0 && weightG > 0 -> "$weightKg کلو $weightG گرام"
            weightKg > 0 -> "$weightKg کلوگرام"
            else -> "$weightG گرام"
        }
        return "$ratePerKg روپے فی کلو کے حساب سے $weightDescription $itemName کی قیمت $price روپے ہے۔"
    }
}