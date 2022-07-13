package com.example.pizzamarketone.screens.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.battisq.pizzamarket.Pizza
import com.battisq.pizzamarket.PizzaDatabase
import com.battisq.pizzamarket.PizzaEntity
import com.example.pizzamarketone.database.pizzaSpisokCart

class MenuViewVodel : ViewModel(){

    val pizzaSpisok = PizzaDatabase.pizzaDao.getAll()  // список пицц из БД

    // создание LiveData
    private var PizzesMutLive = MutableLiveData<List<PizzaEntity>>()
    val PizzesLive: LiveData<List<PizzaEntity>> = PizzesMutLive

    //private var PriceMutLive = MutableLiveData<Double?>()
    //val PriceLive: LiveData<Double?> = PriceMutLive

    init{
        PizzesMutLive.value = pizzaSpisok // обновление значения LiveData
    }

    //fun refreshPrice(){
    //    PriceMutLive.value = pizzaSpisokCart.getPriceOrderALL()
    //}
}