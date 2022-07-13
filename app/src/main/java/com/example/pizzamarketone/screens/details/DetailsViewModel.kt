package com.example.pizzamarketone.screens.details

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.battisq.pizzamarket.PizzaDao
import com.battisq.pizzamarket.PizzaDatabase
import com.battisq.pizzamarket.PizzaEntity
import com.example.pizzamarketone.database.pizzaSpisokCart
import com.example.pizzamarketone.screens.menu.MenuViewVodel

class DetailsViewModel : ViewModel() {

    // создание LiveData
    private var DetailsMutLive = MutableLiveData<Double?>()  // заполняем индексами
    val DetailsLive: LiveData<Double?> = DetailsMutLive

    fun add(index: Int){
        pizzaSpisokCart.addpizza(PizzaDatabase.pizzaDao.getById(index)!!)
        DetailsMutLive.value = pizzaSpisokCart.getPriceOrderALL()  // получение цены
    }

    fun refresh(data: String?){
        if(data == null){
            if(pizzaSpisokCart.getPriceOrderALL() != 0.0){
                DetailsMutLive.value = pizzaSpisokCart.getPriceOrderALL()
            }else{
                DetailsMutLive.value = data
            }
        }else if(data == "RefreshData"){
            DetailsMutLive.value = pizzaSpisokCart.getPriceOrderALL()  // получение цены
        }
    }
}