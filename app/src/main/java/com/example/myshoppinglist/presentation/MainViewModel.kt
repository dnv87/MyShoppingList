package com.example.myshoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.data.ShopListRepositoryImpl // это не правильно
import com.example.myshoppinglist.domain.*

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopListUseCase = EditShopItemUseCase(repository)

    //val shopList = MutableLiveData<List<ShopItem>>()
        val shopList = getShopListUseCase.getShopList()

    /*fun getShopList(){
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }*/

    fun deleteShopItem( shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
//        getShopList() //обновляем состояние списка
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopListUseCase.editShopItem(newItem)
//        getShopList() не нужен т.к. реализовали LiveData
    }
}