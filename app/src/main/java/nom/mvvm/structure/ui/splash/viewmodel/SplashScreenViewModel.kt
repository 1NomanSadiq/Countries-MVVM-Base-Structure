package nom.mvvm.structure.ui.splash.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import nom.mvvm.structure.data.database.country.Item
import nom.mvvm.structure.data.database.country.ItemDao
import nom.mvvm.structure.ui.base.BaseViewModel
import nom.mvvm.structure.utils.dispatchers.DispatchersProviders
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    dispatchers: DispatchersProviders,
    private val itemDao: ItemDao,
) : BaseViewModel(dispatchers) {
    private val itemsNames = listOf("سریا", "پائپ", "چادر چورس", "چادر گول ٹکی", "چادر رِنگ", "ہچ")
    private val items = mutableListOf<Item>()

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val itemList = _items.asStateFlow()

    val selectedItem = MutableStateFlow<Item?>(null)

    init {
        itemsNames.forEach {
            items.add(Item(it))
        }
        launchOnMainImmediate {
            itemDao.getAllItems().collectLatest {
                if (it.isEmpty()) {
                    itemDao.insertAll(items)
                } else {
                    _items.emit(it)
                }
            }
        }
    }

    suspend fun updateItem(item: Item) {
        itemDao.insertAll(listOf(item))
    }

}

