package com.example.pizzamarketone.screens.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.battisq.pizzamarket.PizzaDatabase
import com.example.pizzamarketone.database.pizzaSpisokCart

class PreviewViewVodel : ViewModel(){

    // создание LiveData
    private var MutLive = MutableLiveData<Int>()
    val Live: LiveData<Int> = MutLive

    fun addItem(id: String?){
        if(id != null){
            var ID: Int = id.toInt()
            pizzaSpisokCart.addpizza(PizzaDatabase.pizzaDao.getById(ID)!!)
            MutLive.value = 1
        }
    }
    fun clean(){
        MutLive.value = 0
    }
}