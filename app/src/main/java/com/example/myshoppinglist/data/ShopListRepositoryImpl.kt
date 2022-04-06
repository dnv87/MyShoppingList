package com.example.myshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

// данный object является синглтоном, т.е. мы всегда будем работать с одним и тем же экземпляром
object ShopListRepositoryImpl : ShopListRepository {
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    //создаём пересменную в которой хранятся данные
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id)}) //сортируем по id

    private var autoIncrementId = 0

    init {
        for (i in 0 until 100) {
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }


    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        uplateList()
    }


    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        uplateList()
    }


    override fun editShopItem(shopItem: ShopItem) {
        val oldItem = getShopItem(shopItem.id)
        deleteShopItem(oldItem)
        addShopItem(shopItem)
    }


    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        }
            ?: throw RuntimeException("Element with id $shopItemId not found") //если null то исключение
    }


    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }


    private fun uplateList() {
        shopListLD.value = shopList.toList() // возврвщаем копию списка. Можно использовать postvalue
    }
}